package com.xsis.ics.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Messagebox;

import com.xsis.ics.dao.ICanvasserDao;
import com.xsis.ics.dao.ICanvasserRouteDetailDao;
import com.xsis.ics.dao.ICanvasserRoutesDao;
import com.xsis.ics.dao.IOutletDao;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserRouteDetail;
import com.xsis.ics.domain.CanvasserRoutes;
import com.xsis.ics.domain.Outlet;
import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.dto.BatchInputCvrRoutesModel;
import com.xsis.ics.service.IBatchInputCvrRouteService;
import com.xsis.ics.util.DateUtils;
import com.xsis.ics.util.FormatUtil;

public class BatchInputCvrRouteServiceImpl implements
		IBatchInputCvrRouteService {
	private Logger logger = Logger
			.getLogger(BatchInputCvrRouteServiceImpl.class);
	private ICanvasserRoutesDao routesDao;
	private IOutletDao outletDao;
	private ICanvasserDao canvasserDao;
	private ICanvasserRouteDetailDao routeDetailDao;

	public void setRouteDetailDao(ICanvasserRouteDetailDao routeDetailDao) {
		this.routeDetailDao = routeDetailDao;
	}

	public void setCanvasserDao(ICanvasserDao canvasserDao) {
		this.canvasserDao = canvasserDao;
	}

	public void setOutletDao(IOutletDao outletDao) {
		this.outletDao = outletDao;
	}

	public void setRoutesDao(ICanvasserRoutesDao routesDao) {
		this.routesDao = routesDao;
	}

	@Override
	public List<CanvasserRoutes> readFile(InputStream inputStream,
			String fileName, Long by, Long userOwner) throws Exception,
			IOException {
		File file = saveFile(fileName, inputStream);
		if (file == null)
			return null;

		logger.debug("User ID uploader : " + by);
		logger.debug("User Owner uploader : " + userOwner);
		List<Long> outletIds = outletDao.getOutletIdBaseOnDealerId(userOwner);
		logger.debug("Outlet ID for selected dealer : " + outletIds);
		logger.debug("Outlets Size : " + outletIds.size());
		List<Long> cvrIds = canvasserDao
				.getCanvasserIdBaseOnDealerId(userOwner);
		logger.debug("Canvasser ID for selected dealer: " + cvrIds);
		logger.debug("Canvassers Size: " + cvrIds.size());

		BufferedReader bufRdr = new BufferedReader(new FileReader(fileName));
		String line = null;

		List<BatchInputCvrRoutesModel> modelList = new ArrayList<BatchInputCvrRoutesModel>();

		String[] tempToken;
		String tempJ;

		Long cvrId = null;
		Long outletId = null;
		Date dateScheduled = null;
		Date effectiveDate = null;

		int lin = 1;
		while ((line = bufRdr.readLine()) != null) {
			try {
				BatchInputCvrRoutesModel model = new BatchInputCvrRoutesModel();
				if ((line != null) && (!line.trim().equals(""))) {
					populateAuditableFields(model, by);
					tempToken = line.split("\\|", -1);

					if (tempToken.length != 6) {
						showFailSaveMsg("Invalid format", lin);
						return null;
					}

					for (int i = 0; i < tempToken.length; i++) {
						tempJ = tempToken[i];
						if (i == 0) {
							try {
								if (tempJ != null && !("".equals(tempJ.trim()))) {
									cvrId = Long.valueOf(tempJ);
									if (isCanvasserIdInList(cvrIds, cvrId, lin))
										model.setCanvasserId(cvrId);
									else
										return null;
								}
							} catch (Exception e) {
								e.printStackTrace();
								logger.debug(e);
								showFailSaveMsg("Invalid format CANVASSER ID",
										lin);
								return null;
							}
						} else if (i == 1) {
							try {
								if (tempJ != null && !("".equals(tempJ.trim()))) {
									if (tempJ.length() != 19) {
										throw new Exception();
									}
									Date date = DateUtils.parseDate(tempJ,
											"MM/dd/yyyy hh:mm:ss");
									effectiveDate = DateUtils
											.truncateHour(date);
									model.setEffectiveDate(effectiveDate);
									model.setEffectiveDate(DateUtils
											.truncateHour(model
													.getEffectiveDate()));
									model.setIneffectiveDate(effectiveDate);
									model.setIneffectiveDate(DateUtils
											.truncateHour(model
													.getIneffectiveDate()));
								}
							} catch (Exception e) {
								e.printStackTrace();
								logger.debug(e);
								showFailSaveMsg(
										"Invalid format EFFECTIVE DATE", lin);
								return null;
							}
						} else if (i == 2) {
							try {
								if (tempJ != null && !("".equals(tempJ.trim()))) {
									outletId = Long.valueOf(tempJ);
									if (isOutletIdInList(outletIds, outletId,
											lin))
										model.setOutletId(outletId);
									else
										return null;

								}
							} catch (Exception e) {
								e.printStackTrace();
								logger.debug(e);
								showFailSaveMsg("Invalid format OUTLET ID", lin);
								return null;
							}
						} else if (i == 3) {
							try {
								if (tempJ != null && !("".equals(tempJ.trim()))) {
									model.setPriority(Long.valueOf(tempJ));
								}
							} catch (Exception e) {
								e.printStackTrace();
								logger.debug(e);
								showFailSaveMsg("Invalid format PRIORITY", lin);
								return null;
							}
						} else if (i == 4) {
							try {
								if (tempJ != null && !("".equals(tempJ.trim()))) {
									if (tempJ.length() != 19) {
										throw new Exception();
									}
									dateScheduled = DateUtils.parseDate(tempJ,
											"MM/dd/yyyy hh:mm:ss");
									model.setScheduledDate(dateScheduled);
									model.setScheduledDate(DateUtils
											.truncateHour(model
													.getScheduledDate()));
								}
							} catch (Exception e) {
								e.printStackTrace();
								logger.debug(e);
								showFailSaveMsg(
										"Invalid format SCHEDULED DATE", lin);
								return null;
							}
						}
					}
					Date tmpDate = DateUtils.truncateHour(new Date());
					if ( !(model.getEffectiveDate().after(tmpDate) && model.getScheduledDate().after(tmpDate))    ) {
						showFailSaveMsg(
								"Effective and Schedule Date Must Be Greater Than Today",
								lin);
						return null;
					} else if (!model.getScheduledDate().equals(
							model.getEffectiveDate())) {
						showFailSaveMsg(
								"Schedule Date Must Be Same With Effective Date",
								lin);
						return null;
					} else if (isOutletsAndCvrAlreadyAssignedOnTheSameFile(modelList)) {
						showFailSaveMsg(
								"Duplicate entry OUTLET ID ["
										+ outletId
										+ "], Canvasser ID ["
										+ cvrId
										+ "]  That assigned on ["
										+ FormatUtil.formatDate(effectiveDate,
												null) + "]", lin);
						return null;
					} else if (isOutletsAndCvrAlreadyAssigned(modelList,
							outletId, dateScheduled)) {
						showFailSaveMsg(
								"Duplicate entry OUTLET ID ["
										+ outletId
										+ "] assigned on ["
										+ FormatUtil.formatDate(dateScheduled,
												null) + "]", lin);
						return null;
					} else if (isOutletIsAssigned(dateScheduled, new Outlet(
							outletId))) {
						showFailSaveMsg(
								"Duplicate entry OUTLET ID ["
										+ outletId
										+ "] assigned on ["
										+ FormatUtil.formatDate(dateScheduled,
												null) + "]", lin);
						return null;
					} else {
						modelList.add(model);
					}
				}

				cvrId = null;
				outletId = null;
				dateScheduled = null;
			} finally {
				tempToken = null;
				tempJ = null;
			}
			lin++;
		}

		if (!file.delete())
			logger.debug("File not deleted!");
		else
			logger.info("File deleted!");

		List<CanvasserRoutes> routes = getRoutesFromModel(modelList);

		return routes;
	}

	private boolean isCanvasserAlreadyAssigned(Date dateScheduled, Long cvrId) {
		return routeDetailDao.isCanvasserAlreadyAssigned(dateScheduled, cvrId);
	}

	private boolean isOutletIdInList(List<Long> outletIds, Long outletId,
			int line) throws InterruptedException {
		// logger.debug("Outlet ID: " + outletIds);
		if (outletIds.contains(outletId))
			return true;
		else {
			logger.debug("Outlet ID Not exist: " + outletId);
			showFailSaveMsg("Outlet ID [" + outletId + "] is not exist", line);
			return false;
		}
	}

	private boolean isCanvasserIdInList(List<Long> cvrIds, Long cvrId, int line)
			throws InterruptedException {
		// logger.debug("Canvasser ID : " + cvrIds);
		if (cvrIds.contains(cvrId))
			return true;
		else {
			logger.debug("Canvasser ID Not exist: " + cvrId);
			showFailSaveMsg("Canvasser ID [" + cvrId + "] not exist", line);
			return false;
		}
	}

	/*
	 * private boolean isOutletsAndCvrAlreadyAssigned(
	 * List<CanvasserRouteDetail> routeDetails, Long outletId, Date
	 * dateScheduled) {
	 * 
	 * for (Iterator<CanvasserRouteDetail> iterator = routeDetails.iterator();
	 * iterator .hasNext();) { CanvasserRouteDetail canvasserRouteDetail =
	 * (CanvasserRouteDetail) iterator .next(); Long id =
	 * canvasserRouteDetail.getOutlet().getId(); Date date =
	 * canvasserRouteDetail.getScheduledDate();
	 * 
	 * if (id.equals(outletId) && date.equals(dateScheduled)) { return true; } }
	 * 
	 * return false;
	 * 
	 * }
	 */
	private boolean isOutletsAndCvrAlreadyAssignedOnTheSameFile(
			List<BatchInputCvrRoutesModel> modelList) {
		HashMap<String, BatchInputCvrRoutesModel> hasModelDetail = new HashMap<String, BatchInputCvrRoutesModel>();
		String key = null;
		String keyDetail = null;
		for (BatchInputCvrRoutesModel model : modelList) {
			keyDetail = model.getEffectiveDate().toString() + "-"
					+ model.getCanvasserId() + "-" + model.getOutletId();
			if (hasModelDetail.containsKey(keyDetail)) {
				return true;
			} else {
				hasModelDetail.put(keyDetail, model);
			}
		}
		return false;
	}

	private boolean isOutletsAndCvrAlreadyAssigned(
			List<BatchInputCvrRoutesModel> modelList, Long outletId,
			Date dateScheduled) {

		for (BatchInputCvrRoutesModel model : modelList) {
			Long id = model.getOutletId();
			Date date = model.getScheduledDate();

			if (id.equals(outletId) && date.equals(dateScheduled)) {
				return true;
			}
		}

		return false;

	}

	private File saveFile(String fileName, InputStream inputStream) {

		File file = new File(
				Labels.getLabel("utilities.batchInput.file.location")
						+ fileName);
		logger.debug("File path: " + file.getAbsolutePath());
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			in = new BufferedInputStream(inputStream);

			OutputStream fout = new FileOutputStream(file);
			out = new BufferedOutputStream(fout);
			byte buffer[] = new byte[1024];
			int ch = in.read(buffer);
			while (ch != -1) {
				out.write(buffer, 0, ch);
				ch = in.read(buffer);
			}
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (out != null)
					out.close();

				if (in != null)
					in.close();

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return file;
	}

	private void populateAuditableFields(BatchInputCvrRoutesModel model, Long by) {
		if (by == null) {
			by = Long.valueOf(0);
		}

		/*
		 * if (objectDomain.getId() == null) { Long createdBy = by;
		 * objectDomain.setCreatedBy(createdBy);
		 * objectDomain.setCreationDate(new Date()); } else { Long updatedBy =
		 * by; objectDomain.setLastUpdatedBy(updatedBy);
		 * objectDomain.setLastUpdatedDate(new Date()); }
		 */

		model.setBy(by);
		model.setCreatedDate(new Date());
	}

	@Override
	public List<CanvasserRoutes> readFile(String stringStream, String fileName,
			Long by, Long userOwner) throws Exception, IOException {

		String str = "";
		Date scheduledDate = null;
		Date effectiveDate = null;
		Long cvrId = null;
		Long outletId = null;

		logger.debug("User ID uploader : " + by);
		logger.debug("User Owner uploader : " + userOwner);
		List<Long> outletIds = outletDao.getOutletIdBaseOnDealerId(userOwner);
		logger.debug("Outlet ID for selected dealer : " + outletIds);
		List<Long> cvrIds = canvasserDao
				.getCanvasserIdBaseOnDealerId(userOwner);
		logger.debug("Canvasser ID for selected dealer: " + cvrIds);

		String[] tempToken;
		String tempJ;

		BufferedReader reader = new BufferedReader(new StringReader(
				stringStream));

		List<BatchInputCvrRoutesModel> modelList = new ArrayList<BatchInputCvrRoutesModel>();

		int lin = 1;
		while ((str = reader.readLine()) != null) {
			try {
				BatchInputCvrRoutesModel model = new BatchInputCvrRoutesModel();

				if ((str != null) && (!str.trim().equals(""))) {
					populateAuditableFields(model, by);
					tempToken = str.split("\\|", -1);

					if (tempToken.length != 6) {
						showFailSaveMsg("Invalid format", lin);
						return null;
					}
					for (int i = 0; i < tempToken.length; i++) {
						tempJ = tempToken[i];
						if (i == 0) {
							try {
								if (tempJ != null && !("".equals(tempJ.trim()))) {
									cvrId = Long.valueOf(tempJ);
									if (isCanvasserIdInList(cvrIds, cvrId, lin))
										model.setCanvasserId(cvrId);
									else
										return null;
								}
							} catch (Exception e) {
								e.printStackTrace();
								logger.debug(e);
								showFailSaveMsg("Invalid format CANVASSER ID",
										lin);
								return null;
							}
						} else if (i == 1) {
							try {
								if (tempJ != null && !("".equals(tempJ.trim()))) {
									if (tempJ.length() != 19) {
										throw new Exception();
									}
									Date date = DateUtils.parseDate(tempJ,
											"MM/dd/yyyy hh:mm:ss");
									effectiveDate = DateUtils
											.truncateHour(date);
									model.setEffectiveDate(effectiveDate);
									model.setEffectiveDate(DateUtils
											.truncateHour(model
													.getEffectiveDate()));
									model.setIneffectiveDate(effectiveDate);
									model.setIneffectiveDate(DateUtils
											.truncateHour(model
													.getIneffectiveDate()));
								}
							} catch (Exception e) {
								e.printStackTrace();
								logger.debug(e);
								showFailSaveMsg(
										"Invalid format EFFECTIVE DATE", lin);
								return null;
							}
						} else if (i == 2) {
							try {
								if (tempJ != null && !("".equals(tempJ.trim()))) {
									outletId = Long.valueOf(tempJ);
									if (isOutletIdInList(outletIds, outletId,
											lin))
										model.setOutletId(outletId);
									else
										return null;

								}
							} catch (Exception e) {
								e.printStackTrace();
								logger.debug(e);
								showFailSaveMsg("Invalid format OUTLET ID", lin);
								return null;
							}
						} else if (i == 3) {
							try {
								if (tempJ != null && !("".equals(tempJ.trim()))) {
									model.setPriority(Long.valueOf(tempJ));
								}
							} catch (Exception e) {
								e.printStackTrace();
								logger.debug(e);
								showFailSaveMsg("Invalid format PRIORITY", lin);
								return null;
							}
						} else if (i == 4) {
							try {
								if (tempJ != null && !("".equals(tempJ.trim()))) {
									if (tempJ.length() != 19) {
										throw new Exception();
									}
									scheduledDate = DateUtils.parseDate(tempJ,
											"MM/dd/yyyy hh:mm:ss");
									model.setScheduledDate(scheduledDate);
									model.setScheduledDate(DateUtils
											.truncateHour(model
													.getScheduledDate()));
								}
							} catch (Exception e) {
								e.printStackTrace();
								logger.debug(e);
								showFailSaveMsg(
										"Invalid format SCHEDULED DATE", lin);
								return null;
							}
						}
					}
					Date tmpDate = DateUtils.truncateHour(new Date());
					if ( !(model.getEffectiveDate().after(tmpDate) && model.getScheduledDate().after(tmpDate))    ) {
						showFailSaveMsg(
								"Effective and Schedule Date Must Be Greater Than Today",
								lin);
						return null;
					} else if (!model.getScheduledDate().equals(
							model.getEffectiveDate())) {
						showFailSaveMsg(
								"Schedule Date Must Be Same With Effective Date",
								lin);
						return null;
					} else if (isOutletsAndCvrAlreadyAssignedOnTheSameFile(modelList)) {
						showFailSaveMsg(
								"Duplicate entry OUTLET ID ["
										+ outletId
										+ "], Canvasser ID ["
										+ cvrId
										+ "]  That assigned on ["
										+ FormatUtil.formatDate(effectiveDate,
												null) + "]", lin);
						return null;
					} else if (isOutletsAndCvrAlreadyAssigned(modelList,
							outletId, scheduledDate)) {
						showFailSaveMsg(
								"Duplicate entry OUTLET ID ["
										+ outletId
										+ "] assigned on ["
										+ FormatUtil.formatDate(scheduledDate,
												null) + "]", lin);
						return null;
					} else if (isOutletIsAssigned(scheduledDate, new Outlet(
							outletId))) {
						showFailSaveMsg(
								"Duplicate entry OUTLET ID ["
										+ outletId
										+ "] assigned on ["
										+ FormatUtil.formatDate(scheduledDate,
												null) + "]", lin);
						return null;

					} else {
						modelList.add(model);
					}
				}

			} finally {
				tempToken = null;
				tempJ = null;
			}

			lin++;

		}

		List<CanvasserRoutes> routes = getRoutesFromModel(modelList);

		return routes;
	}

	private List<CanvasserRoutes> getRoutesFromModel(
			List<BatchInputCvrRoutesModel> modelList) {

		List<CanvasserRoutes> routes = new ArrayList<CanvasserRoutes>();

		CanvasserRoutes rut = new CanvasserRoutes();

		Date tempDate = new Date(0);
		Long tempOUtletId = new Long(0);
		Long tempCvrId = new Long(0);
		HashMap<String, BatchInputCvrRoutesModel> hasModel = new HashMap<String, BatchInputCvrRoutesModel>();
		HashMap<String, BatchInputCvrRoutesModel> hasModelDetail = new HashMap<String, BatchInputCvrRoutesModel>();
		String key = null;
		String keyDetail = null;
		for (BatchInputCvrRoutesModel model : modelList) {
			key = model.getEffectiveDate().toString() + "-"
					+ model.getCanvasserId();
			keyDetail = model.getEffectiveDate().toString() + "-"
					+ model.getCanvasserId() + "-" + model.getOutletId();
			if (!hasModel.containsKey(key)) {
				hasModel.put(key, model);
			}
			if (!hasModelDetail.containsKey(keyDetail)) {
				hasModelDetail.put(keyDetail, model);
			}
		}

		Set set = hasModel.entrySet();
		Iterator i = set.iterator();
		BatchInputCvrRoutesModel iModel = null;
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			iModel = (BatchInputCvrRoutesModel) me.getValue();
			tempDate = iModel.getEffectiveDate();
			tempCvrId = iModel.getCanvasserId();
			tempOUtletId = iModel.getOutletId();
			rut = new CanvasserRoutes();
			rut.setCanvasser(new Canvasser(iModel.getCanvasserId()));
			rut.setCreatedBy(iModel.getBy());
			rut.setCreationDate(iModel.getCreatedDate());
			rut.setEffectiveDate(iModel.getEffectiveDate());
			rut.setIneffectiveDate(iModel.getIneffectiveDate());
			routes.add(rut);
		}

		for (CanvasserRoutes route : routes) {
			Set<CanvasserRouteDetail> details = new HashSet<CanvasserRouteDetail>();
			Set setDetail = hasModelDetail.entrySet();
			Iterator iDetail = setDetail.iterator();
			BatchInputCvrRoutesModel iModelDetail = null;
			tempDate = route.getEffectiveDate();
			tempCvrId = route.getCanvasser().getId();
			while (iDetail.hasNext()) {

				Map.Entry meDetail = (Map.Entry) iDetail.next();
				iModelDetail = (BatchInputCvrRoutesModel) meDetail.getValue();
				if (iModelDetail.getEffectiveDate().equals(tempDate)
						&& iModelDetail.getCanvasserId().equals(tempCvrId)) {
					CanvasserRouteDetail detail = new CanvasserRouteDetail();
					detail.setCanvasserRoutes(route);
					detail.setOutlet(new Outlet(iModelDetail.getOutletId()));
					detail.setPriority(iModelDetail.getPriority());
					detail.setScheduledDate(iModelDetail.getScheduledDate());
					details.add(detail);
				}
			}
			if (!details.isEmpty()){
				// sort detail
				List<CanvasserRouteDetail> sortedDetails = new ArrayList<CanvasserRouteDetail>(details);
				Collections.sort(sortedDetails);
				Long priority = 1l;
				for(CanvasserRouteDetail detail : sortedDetails){
					detail.setPriority(priority++);
				}
			}
			route.setCanvasserRouteDetails(details);
		}

		return routes;
	}

	@Override
	public void saveOrUpdateList(List<CanvasserRoutes> routes) {
		routesDao.saveOrUpdateAll(routes);
	}

	private void showFailSaveMsg(String field, int line)
			throws InterruptedException {
		String msg = field + " in line " + line;
		Messagebox.show(msg, "ERROR", Messagebox.OK, Messagebox.ERROR);
	}

	public boolean isOutletIsAssigned(Date date, Outlet outlet) {
		return routeDetailDao.isOutletAssigned(date, outlet);
	}
}
