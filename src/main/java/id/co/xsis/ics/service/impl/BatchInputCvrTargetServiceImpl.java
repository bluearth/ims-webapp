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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Messagebox;

import com.xsis.ics.dao.ICanvasserDao;
import com.xsis.ics.dao.ICanvasserTargetDao;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserTarget;
import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.service.IBatchInputCvrTargetService;
import com.xsis.ics.util.DateUtils;
import com.xsis.ics.util.ObjectUtil;

public class BatchInputCvrTargetServiceImpl implements
		IBatchInputCvrTargetService {

	private Logger logger = Logger
			.getLogger(BatchInputCvrTargetServiceImpl.class);
	private ICanvasserTargetDao cvrTargetDao;
	private ICanvasserDao canvasserDao;

	public void setCanvasserDao(ICanvasserDao canvasserDao) {
		this.canvasserDao = canvasserDao;
	}

	public void setCvrTargetDao(ICanvasserTargetDao cvrTargetDao) {
		this.cvrTargetDao = cvrTargetDao;
	}

	@Override
	public List<CanvasserTarget> readFile(InputStream inputStream,
			String fileName, Long by, Long userOwner) throws Exception,
			IOException {

		File file = saveFile(fileName, inputStream);
		if (file == null)
			return null;

		List<Long> cvrIds = canvasserDao
				.getCanvasserIdBaseOnDealerId(userOwner);

		BufferedReader bufRdr = new BufferedReader(new FileReader(fileName));
		String line = null;

		List<CanvasserTarget> targets = new ArrayList<CanvasserTarget>();
		int lin = 1;

		Long cvrId = null;
		Long month = null;
		Long year = null;

		String[] tempToken;
		String tempJ;

		while ((line = bufRdr.readLine()) != null) {
			try {
				CanvasserTarget target = new CanvasserTarget();
				populateAuditableFields(target, by);
				tempToken = line.split("\\|", -1);
				for (int i = 0; i < tempToken.length; i++) {
					tempJ = tempToken[i];
					if (i == 0) {
						try {
							if (tempJ != null && !("".equals(tempJ.trim()))) {
								target.setCanvasserTargetDp(new BigDecimal(tempJ));
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.debug(e);
							showFailSaveMsg("Invalid format TARGET DP", lin);
							return null;
						}
					} else if (i == 1) {
						try {
							if (tempJ != null && !("".equals(tempJ.trim()))) {
								target.setCanvasserTargetSp(new BigDecimal(tempJ));
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.debug(e);
							showFailSaveMsg("Invalid format TARGET SP", lin);
							return null;
						}
					} else if (i == 2) {
						try {
							if (tempJ != null && !("".equals(tempJ.trim()))) {
								target.setCanvasserTargetPv(new BigDecimal(tempJ));
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.debug(e);
							showFailSaveMsg("Invalid format TARGET PV", lin);
							return null;
						}
					} else if (i == 3) {
						try {
							if (tempJ != null && !("".equals(tempJ.trim()))) {
								month = new Long(tempJ);
								target.setCanvasserTargetMonth(month);
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.debug(e);
							showFailSaveMsg("Invalid format TARGET MONTH", lin);
							return null;
						}
					}  else if (i == 4) {
						try {
							if (tempJ != null && !("".equals(tempJ.trim()))) {
								year = new Long(tempJ);
								target.setCanvasserTargetYear(year);
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.debug(e);
							showFailSaveMsg("Invalid format TARGET YEAR", lin);
							return null;
						}
					} else if (i == 5) {
						try {
							if (tempJ != null && !("".equals(tempJ.trim()))) {
								cvrId = new Long(tempJ);
								if (isCanvasserIdInList(cvrIds, cvrId, lin)) {
									Canvasser canvasser = new Canvasser(cvrId);
									target.setCanvasser(canvasser);
								} else {
									return null;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.debug(e);
							showFailSaveMsg("Invalid format CANVASSER ID", lin);
							return null;
						}
					}
				}
				if(cvrId != null && month != null & year != null){
					if (isCanvasserAssignedInList(targets, cvrId, month, year)) {
						showFailSaveMsg("Duplicate entry CANVASSER ID [" + cvrId
								+ "] for month [" + month + "-" + year + "]", lin);
						return null;
					} else
						targets.add(target);
				}
				cvrId = null;
				month = null;
				year = null;
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
		return targets;
	}

	private boolean isCanvasserAssignedInList(List<CanvasserTarget> targets,
			Long cvrId, Long month, Long year) {

		for (Iterator<CanvasserTarget> iterator = targets.iterator(); iterator
				.hasNext();) {
			CanvasserTarget canvasserTarget = (CanvasserTarget) iterator.next();

			Long cvId = canvasserTarget.getCanvasser().getId();
			Long mon = canvasserTarget.getCanvasserTargetMonth();
			Long yir = canvasserTarget.getCanvasserTargetYear();

			if (cvId.equals(cvrId) && mon.equals(month) && yir.equals(year))
				return true;
		}

		return false;

	}

	private File saveFile(String fileName, InputStream inputStream) {

		File file = new File(Labels.getLabel("utilities.batchInput.file.location") + fileName);
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

	private void populateAuditableFields(BaseDomain objectDomain, Long by) {
		if (by == null) {
			by = Long.valueOf(0);
		}

		if (objectDomain.getId() == null) {
			Long createdBy = by;
			objectDomain.setCreatedBy(createdBy);
			objectDomain.setCreationDate(new Date());
		} else {
			Long updatedBy = by;
			objectDomain.setLastUpdatedBy(updatedBy);
			objectDomain.setLastUpdatedDate(new Date());
		}
	}

	@Override
	public void saveOrUpdateList(List<CanvasserTarget> targets, Long userLogonId) {

		List<CanvasserTarget> cvrTargetsReturn = doSetCanvasserTargetWeek(
				targets, userLogonId);

		doDeleteCanvasserTargetsExist(targets);

		cvrTargetDao.saveOrUpdateAll(cvrTargetsReturn);
	}

	private void doDeleteCanvasserTargetsExist(List<CanvasserTarget> targets) {

		if (ObjectUtil.isNotEmpty(targets)) {

			for (Iterator<CanvasserTarget> iterator = targets.iterator(); iterator
					.hasNext();) {
				CanvasserTarget canvasserTarget = (CanvasserTarget) iterator
						.next();

				Canvasser cvr = canvasserTarget.getCanvasser();
				Long month = canvasserTarget.getCanvasserTargetMonth();
				Long year = canvasserTarget.getCanvasserTargetYear();

				cvrTargetDao.deleteAllCanvasserTargetBaseOnCanvasserAndMonth(
						cvr, month, year);
			}
		}

	}

	@Override
	public List<CanvasserTarget> readFile(String stringStream, String fileName,
			Long by, Long userOwner) throws Exception, IOException {

		String str = "";
		List<CanvasserTarget> targets = new ArrayList<CanvasserTarget>();

		List<Long> cvrIds = canvasserDao
				.getCanvasserIdBaseOnDealerId(userOwner);

		BufferedReader reader = new BufferedReader(new StringReader(
				stringStream));

		int lin = 1;
		Long cvrId = null;
		Long month = null;
		Long year = null;

		String[] tempToken;
		String tempJ;
		
		while ((str = reader.readLine()) != null) {
			try{
				CanvasserTarget target = new CanvasserTarget();
				populateAuditableFields(target, by);
				tempToken = str.split("\\|", -1);
				for (int i = 0; i < tempToken.length; i++) {
					tempJ = tempToken[i];
					if (i == 0) {
						try {
							if(tempJ != null && !("".equals(tempJ.trim()))){
								target.setCanvasserTargetDp(new BigDecimal(tempJ));
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.debug(e);
							showFailSaveMsg("Invalid format TARGET DP", lin);
							return null;
						}
					} else if(i == 1){
						try {
							if(tempJ != null && !("".equals(tempJ.trim()))){
								target.setCanvasserTargetSp(new BigDecimal(tempJ));
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.debug(e);
							showFailSaveMsg("Invalid format TARGET SP", lin);
							return null;
						}
					} else if(i == 2){
						try {
							if(tempJ != null && !("".equals(tempJ.trim()))){
								target.setCanvasserTargetPv(new BigDecimal(tempJ));
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.debug(e);
							showFailSaveMsg("Invalid format TARGET PV", lin);
							return null;
						}
					} else if(i == 3){
						try {
							if(tempJ != null && !("".equals(tempJ.trim()))){
								month = new Long(tempJ);
								target.setCanvasserTargetMonth(month);
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.debug(e);
							showFailSaveMsg("Invalid format TARGET MONTH", lin);
							return null;
						}
					} else if(i == 4){
						try {
							if(tempJ != null && !("".equals(tempJ.trim()))){
								year = new Long(tempJ);
								target.setCanvasserTargetYear(year);
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.debug(e);
							showFailSaveMsg("Invalid format TARGET YEAR", lin);
							return null;
						}
					}  else if(i == 5){
						try {
							if(tempJ != null && !("".equals(tempJ.trim()))){
								cvrId = new Long(tempJ);
								if (isCanvasserIdInList(cvrIds, cvrId, lin)) {
									Canvasser canvasser = new Canvasser(cvrId);
									target.setCanvasser(canvasser);
								} else {
									return null;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							logger.debug(e);
							showFailSaveMsg("Invalid format CANVASSER ID", lin);
							return null;
						}
					}
				}
				if (isCanvasserAssignedInList(targets, cvrId, month, year)) {
					showFailSaveMsg("Duplicate entry CANVASSER ID [" + cvrId
							+ "] for month [" + month + "-" + year + "]", lin);
					return null;
				} else
					targets.add(target);
				cvrId = null;
				month = null;
				year = null;
			} finally {
				tempToken = null;
				tempJ=null;
			}
			lin++;
		}
		return targets;
	}

	private boolean isCanvasserIdInList(List<Long> cvrIds, Long cvrId, int line)
			throws InterruptedException {
		logger.debug("Canvasser ID : " + cvrIds);
		if (cvrIds.contains(cvrId))
			return true;
		else {
			showFailSaveMsg("CANVASSER ID [" + cvrId + "] not exist", line);
			return false;
		}
	}

	private void showFailSaveMsg(String field, int line)
			throws InterruptedException {
		String msg = field + " in line " + line;
		Messagebox.show(msg, "ERROR", Messagebox.OK, Messagebox.ERROR);
	}

	private List<CanvasserTarget> doSetCanvasserTargetWeek(
			List<CanvasserTarget> cvrTargets, Long userLogonId) {
		List<CanvasserTarget> cvrTargetsReturn = new ArrayList<CanvasserTarget>();

		for (Iterator<CanvasserTarget> iterator = cvrTargets.iterator(); iterator
				.hasNext();) {
			CanvasserTarget canvasserTarget = (CanvasserTarget) iterator.next();

			if (canvasserTarget.getCanvasserTargetMonth() != null) {

				List<CanvasserTarget> cvrTargetWeekly = setCvrTargetWeekly(
						canvasserTarget, userLogonId);
				cvrTargetsReturn.addAll(cvrTargetWeekly);

			}

		}

		return cvrTargetsReturn;
	}

	private List<CanvasserTarget> setCvrTargetWeekly(
			CanvasserTarget canvasserTarget, Long userLogonId) {
		List<CanvasserTarget> cvrTargetsReturn = new ArrayList<CanvasserTarget>();
		Long month = canvasserTarget.getCanvasserTargetMonth();
		List<Integer> weekList = DateUtils
				.getWeeksOfYearISO8601ListBaseOnMonth(month.intValue());
		Integer weekAmount = weekList.size();

		BigDecimal bgWeekAmount = BigDecimal.valueOf(weekAmount);
		BigDecimal dp = new BigDecimal(0);
		BigDecimal pv = new BigDecimal(0);
		BigDecimal sp = new BigDecimal(0);

		if (canvasserTarget.getCanvasserTargetDp() != null)
			dp = canvasserTarget.getCanvasserTargetDp().divide(bgWeekAmount);
		if (canvasserTarget.getCanvasserTargetPv() != null)
			pv = canvasserTarget.getCanvasserTargetPv().divide(bgWeekAmount);
		if (canvasserTarget.getCanvasserTargetSp() != null)
			sp = canvasserTarget.getCanvasserTargetSp().divide(bgWeekAmount);

		for (int i = 0; i < weekAmount; i++) {
			CanvasserTarget cvrTarget = new CanvasserTarget();
			cvrTarget.setCanvasser(canvasserTarget.getCanvasser());
			cvrTarget.setCanvasserTargetDp(dp);
			cvrTarget.setCanvasserTargetPv(pv);
			cvrTarget.setCanvasserTargetSp(sp);
			cvrTarget.setCanvasserTargetWeek(new Long(weekList.get(i)));
			cvrTarget.setCanvasserTargetMonth(canvasserTarget
					.getCanvasserTargetMonth());
			cvrTarget.setCanvasserTargetYear(canvasserTarget
					.getCanvasserTargetYear());
			populateAuditableFields(cvrTarget, userLogonId);

			cvrTargetsReturn.add(cvrTarget);
		}

		return cvrTargetsReturn;
	}

}
