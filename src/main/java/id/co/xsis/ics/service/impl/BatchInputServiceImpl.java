package com.xsis.ics.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Messagebox;

import com.xsis.ics.common.Constant;
import com.xsis.ics.dao.ICanvasserDao;
import com.xsis.ics.dao.ICanvasserVisitDao;
import com.xsis.ics.dao.IOutletDao;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserVisit;
import com.xsis.ics.domain.Outlet;
import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.service.IBatchInputService;
import com.xsis.ics.util.DateUtils;
import com.xsis.ics.util.ObjectUtil;
import com.xsis.ics.util.StringUtil;

public class BatchInputServiceImpl implements IBatchInputService {
	private static final char CSV_SEPARATOR = '|';
	private static final int MAX_FIELDS = 60;
	private static final String MANDATORY_FLAG="M";
	private static final String[][] VALIDATOR={{"M","\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}","Invalid VisitTime"}, // visit_time
				{"N","\\d*|\\d*\\.\\d*","TripLength"}, // trip_length
				{"M","[YyNn]","VisitFlag"}, // visit_flag
				{"N",".*","VisitNotes"}, // visit_notes
				{"N","[YyNn]{0,1}+","TransactionFlag"}, // transaction_flag
				{"N","\\d{0,4}+","TransactionSP"}, // transaction_sp
				{"N","\\d{0,8}+","TransactionDP"}, // transaction_dp
				{"N","\\d{0,5}+","TransactionDP1"}, // transaction_dp1
				{"N","\\d{0,4}+","TransactionDP5"}, // transaction_dp5
				{"N","\\d{0,4}+","TransactionDP10"}, // transaction_dp10
				{"N","\\d{0,4}+","TransactionPV10"}, // transaction_pv10
				{"N","\\d{0,3}+","TransactionPV50"}, // transaction_pv50
				{"N","\\d{0,9}+","StockSpXL"}, //StockSpXL
				{"N","\\d{0,9}+","StockSpIsatMtr"}, //StockSpIsatMtr
				{"N","\\d{0,9}+","StockSpIsatIm3"}, //StockSpIsatIm3
				{"N","\\d{0,9}+","StockSpTselSmp"}, //StockSpTselSmp
				{"N","\\d{0,9}+","StockSpTselAs"}, //StockSpTselAs
				{"N","\\d{0,9}+","StockSpOther"}, //StockSpOther
				{"N","\\d{0,9}+","StockPv10XL"}, //StockPv10XL
				{"N","\\d{0,9}+","StockPv50XL"}, //StockPv50XL
				{"N","\\d{0,9}+","StockPvIsat"}, //StockPvIsat
				{"N","\\d{0,9}+","StockPvTsel"}, //StockPvTsel
				{"N","\\d{0,9}+","StockPvOther"}, //StockPvOther
				{"N","[YyNn]{0,1}+","BrandingPaintedXL"}, //BrandingPaintedXL
				{"N","\\d{0,5}+","NumOfPosterXL"}, //NumOfPosterXL
				{"N","\\d{0,5}+","NumOfPosterTsel"}, //NumOfPosterTsel
				{"N","\\d{0,5}+","NumOfPosterIsat"}, //NumOfPosterIsat
				{"N","\\d{0,5}+","NumOfShopBlindXL"}, //NumOfShopBlindXL
				{"N","\\d{0,5}+","NumOfShopBlindTsel"}, //NumOfShopBlindTsel
				{"N","\\d{0,5}+","NumOfShopBlindIsat"}, //NumOfShopBlindIsat
				{"N","\\d{0,5}+","NumOfFlyerTsel"}, //NumOfFlyerTsel
				{"N","\\d{0,5}+","NumOfFlyerXL"}, //NumOfFlyerXL
				{"N","\\d{0,5}+","NumOfFlyerIsat"}, //NumOfFlyerIsat
				{"N","\\d{0,5}+","NumOfNeonBoxXL"}, //NumOfNeonBoxXL
				{"N","\\d{0,5}+","NumOfNeonBoxTsel"}, //NumOfNeonBoxTsel
				{"N","\\d{0,5}+","NumOfNeonBoxIsat"}, //NumOfNeonBoxIsat
				{"N","\\d{0,5}+","BrandingOther"}, //BrandingOther
				{"N",".*","Questionnaire1"}, //Questionnaire1
				{"N",".*","Questionnaire2"}, //Questionnaire2
				{"N",".*","Questionnaire3"}, //Questionnaire3
				{"N",".*","Questionnaire4"}, //Questionnaire4
				{"N",".*","Questionnaire5"}, //Questionnaire5
				{"N",".*","Questionnaire6"}, //Questionnaire6
				{"N",".*","Questionnaire7"}, //Questionnaire7
				{"N",".*","Questionnaire8"}, //Questionnaire8
				{"N",".*","Questionnaire9"}, //Questionnaire9
				{"N",".*","Questionnaire10"}, //Questionnaire10
				{"N",".*","Answer1"}, //Answer1
				{"N",".*","Answer2"}, //Answer2
				{"N",".*","Answer3"}, //Answer3
				{"N",".*","Answer4"}, //Answer4
				{"N",".*","Answer5"}, //Answer5
				{"N",".*","Answer6"}, //Answer6
				{"N",".*","Answer7"}, //Answer7
				{"N",".*","Answer8"}, //Answer8
				{"N",".*","Answer9"}, //Answer9
				{"N",".*","Answer10"}, //Answer10
				{"M","[YyNn]","TransactionKPIFlag"}, //TransactionKPIFlag
				{"M","\\d+","OutletId"}, //OutletId
				{"M","\\d+","CanvasserId"} //CanvasserId							
				}; 	
	
	private Logger logger = Logger.getLogger(BatchInputCvrTargetServiceImpl.class);
	private ICanvasserVisitDao canvasserVisitDao;
	private ICanvasserDao canvasserDao;
	private IOutletDao outletDao;

	public void setCanvasserDao(ICanvasserDao canvasserDao) {
		this.canvasserDao = canvasserDao;
	}

	public void setOutletDao(IOutletDao outletDao) {
		this.outletDao = outletDao;
	}

	public ICanvasserVisitDao getCanvasserVisitDao() {
		return canvasserVisitDao;
	}

	public void setCanvasserVisitDao(ICanvasserVisitDao canvasserVisitDao) {
		this.canvasserVisitDao = canvasserVisitDao;
	}	

	private boolean isCanvasserIdInList(List<Long> cvrIds, Long cvrId, int line)
			throws InterruptedException {
		if (cvrIds.contains(cvrId))
			return true;
		else {
			return false;
		}
	}

	private boolean isOutletIdInList(List<Long> outIds, Long outId, int line)
			throws InterruptedException {
		if (outIds.contains(outId))
			return true;
		else {			
			return false;
		}
	}

	@Override
	public void saveOrUpdateList(List<CanvasserVisit> visits) {

		doDeleteCanvasserVisitsExist(visits);
		canvasserVisitDao.saveOrUpdateAll(visits);
	}

	private void doDeleteCanvasserVisitsExist(List<CanvasserVisit> visits) {

		if (ObjectUtil.isNotEmpty(visits)) {

			for (Iterator<CanvasserVisit> iterator = visits.iterator(); iterator
					.hasNext();) {
				CanvasserVisit canvasserVisit = (CanvasserVisit) iterator
						.next();

				Canvasser cvr = canvasserVisit.getCanvasser();
				Timestamp vt = canvasserVisit.getVisitTime();
				Outlet outlet = canvasserVisit.getOutlet();

				canvasserVisitDao.deleteAllCanvasserVisitBaseOnCvsIDOutIDVisitTime(cvr,outlet, vt);
			}
		}

	}
	
	@Override
	public List<CanvasserVisit> readFile(InputStream inputStream,
			String fileName, Long by, Long userOwner) throws Exception,
			IOException {
		List<CanvasserVisit> result = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		try{
			result = this.processFile(reader, fileName, by, userOwner);		
		}finally{
			reader.close();
		}
		return result;
	}

	@Override
	public List<CanvasserVisit> readFile(String inputString, String fileName,
			Long by, Long userOwner) throws Exception, IOException {
		List<CanvasserVisit> result = null;
		BufferedReader reader = new BufferedReader(new StringReader(inputString));
		try{
			result = this.processFile(reader, fileName, by, userOwner);
		}finally{
			reader.close();
		}
		return result;
	}
	
	@Override
	public List<CanvasserVisit> readFile(Reader inputReader, String fileName, Long by,
			Long userOwner) throws Exception, IOException {
		List<CanvasserVisit> result = null;
		BufferedReader reader = new BufferedReader(inputReader);
		try{
			result = this.processFile(reader, fileName, by, userOwner);
		}finally{
			reader.close();
		}
		return result;
	}

	protected String validateRow(List<String> row){
		String output = null;
		if (row.size() == MAX_FIELDS || ((row.size() == MAX_FIELDS+1)&&row.get(row.size()-1).isEmpty())){
			for(int i = 0;i<MAX_FIELDS;i++){
				String field = row.get(i)==null?"":row.get(i).trim();
				String mandatoryFlag = VALIDATOR[i][0];
				if (MANDATORY_FLAG.equals(mandatoryFlag) && !StringUtil.isNotEmpty(field)){ //mandatory field
					output = "Invalid "+VALIDATOR[i][2]+": Field required";
					break;
				}
				if (!field.matches(VALIDATOR[i][1])){
					output = "Invalid "+VALIDATOR[i][2]+": Format is invalid";
					break;
				}
			}
		}else{
			output = "Number of expected fields is not match";
		}
		return output;
	}
	
	protected List<CanvasserVisit> processFile(BufferedReader reader, String fileName, Long by,
			Long userOwner) throws Exception, IOException {
		Long cvrId = null;
		Long outId = null;
		Date visit2 = new Date();
		List<CanvasserVisit> cs = new ArrayList<CanvasserVisit>();
		List<Long> cvrIds = canvasserDao.getCanvasserIdBaseOnDealerId(userOwner);
		List<Long> outIds = outletDao.getOutletIdBaseOnDealerId(userOwner);
		
		int lin = 1;
		String validationOutput ="";
		boolean stop = false;
		String row = "";
		int i = 0;
		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR, 2);
		boolean result = true;
		while (!stop && ((row = reader.readLine()) != null)) {
			List<String> csv = StringUtil.parseCsv(row, CSV_SEPARATOR);
			i = 0;
			validationOutput = this.validateRow(csv);
			if (validationOutput == null){
				// check data quality
				String visitTime = csv.get(0);
				Date visit = DateUtils.parseDate(visitTime,"MM/dd/yyyy hh:mm:ss");
				visit2 = DateUtils.parseDate(visitTime,"MM/dd/yyyy");
				Timestamp ts = new Timestamp(visit.getTime());				
				if (visit.after(now.getTime())){
					showFailSaveMsg("Visit Time is in the future", lin);
					result = false;
					break;
				}
				outId = new Long(csv.get(58));
				Outlet outlet = null;
				if (isOutletIdInList(outIds, outId, lin)) { // check allowable outlet
					outlet = new Outlet(outId);
				} else {
					showFailSaveMsg("Outlet ["+outId+"] does not exists", lin);
					result = false;
					break;
				}
				cvrId = new Long(csv.get(59));
				Canvasser canvasser = null;
				if (isCanvasserIdInList(cvrIds, cvrId, lin)) { // check allowable canvasser
					canvasser = new Canvasser(cvrId);
					if (isCanvasserAssignedInList(cs, cvrId, outId, visit2)) {
						DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						String visit3 = dateFormat.format(visit2);
						showFailSaveMsg("Duplicate entry CANVASSER ID [" + cvrId
								+ "] and OUTLET ID [" + outId
								+ "] for VISIT TIME in DATE [" + visit3 + "]", lin);
						result = false;
						break;
					}
				} else {
					showFailSaveMsg("Canvasser ["+cvrId+"] does not exists", lin);
					result = false;
					break;
				}
				// OK. Create object and assign
				CanvasserVisit canvasserVisit = new CanvasserVisit();
				populateAuditableFields(canvasserVisit, by);
				canvasserVisit.setVisitTime(ts);
				canvasserVisit.setTripLength(new BigDecimal(csv.get(++i)));
				canvasserVisit.setVisitFlag(csv.get(++i));
				canvasserVisit.setVisitNotes(csv.get(++i));
				canvasserVisit.setTransactionFlag(csv.get(++i));
				canvasserVisit.setTransactionSp(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setTransactionDp(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setTransactionDp1(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setTransactionDp5(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setTransactionDp10(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setTransactionPv10(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setTransactionPv50(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setStockSpXL(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setStockSpIsatMtr(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setStockSpIsatIm3(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setStockSpTselSmp(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setStockSpTselAs(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setStockSpOther(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setStockPv10XL(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setStockPv50XL(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setStockPvIsat(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setStockPvTsel(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setStockPvOther(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setBrandingPaintedXL(csv.get(++i));
				canvasserVisit.setNumOfPosterXL(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setNumOfPosterTsel(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setNumOfPosterIsat(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setNumOfShopBlindXL(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setNumOfShopBlindTsel(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setNumOfShopBlindIsat(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setNumOfFlyerTsel(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setNumOfFlyerXL(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setNumOfFlyerIsat(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setNumOfNeonBoxXL(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setNumOfNeonBoxTsel(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setNumOfNeonBoxIsat(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setBrandingOther(StringUtil.isNotEmpty(csv.get(++i))?(new Long(csv.get(i))):0l);
				canvasserVisit.setQuestionnaire1(csv.get(++i));
				canvasserVisit.setQuestionnaire2(csv.get(++i));
				canvasserVisit.setQuestionnaire3(csv.get(++i));
				canvasserVisit.setQuestionnaire4(csv.get(++i));
				canvasserVisit.setQuestionnaire5(csv.get(++i));
				canvasserVisit.setQuestionnaire6(csv.get(++i));
				canvasserVisit.setQuestionnaire7(csv.get(++i));
				canvasserVisit.setQuestionnaire8(csv.get(++i));
				canvasserVisit.setQuestionnaire9(csv.get(++i));
				canvasserVisit.setQuestionnaire10(csv.get(++i));
				canvasserVisit.setAnswer1(csv.get(++i));
				canvasserVisit.setAnswer2(csv.get(++i));
				canvasserVisit.setAnswer3(csv.get(++i));
				canvasserVisit.setAnswer4(csv.get(++i));
				canvasserVisit.setAnswer5(csv.get(++i));
				canvasserVisit.setAnswer6(csv.get(++i));
				canvasserVisit.setAnswer7(csv.get(++i));
				canvasserVisit.setAnswer8(csv.get(++i));
				canvasserVisit.setAnswer9(csv.get(++i));
				canvasserVisit.setAnswer10(csv.get(++i));
				canvasserVisit.setTransactionKPI(csv.get(++i));
				canvasserVisit.setOutlet(outlet);
				canvasserVisit.setCanvasser(canvasser);
				canvasserVisit.setSystemOrigin(Constant.VISIT_ORIGIN_SYSTEM_WEB);
				cs.add(canvasserVisit);
			}else{
				showFailSaveMsg(validationOutput, lin);
				result = false;
				break;
			}
			lin++;
		}
		if (!result){
			return null;
		}else{
			return cs;
		}
	}
	
	private boolean isCanvasserAssignedInList(List<CanvasserVisit> cs,
			Long cvrId, Long outId, Date visit) throws Exception {

		for (Iterator<CanvasserVisit> iterator = cs.iterator(); iterator
				.hasNext();) {
			CanvasserVisit canvasserVisit = (CanvasserVisit) iterator.next();

			Long cvId = canvasserVisit.getCanvasser().getId();
			Long ouId = canvasserVisit.getOutlet().getId();

			Timestamp asd = canvasserVisit.getVisitTime();

			Date visit2 = DateUtils.parseDate(asd.toString(), "yyyy-MM-dd");

			if (cvId.equals(cvrId) && ouId.equals(outId)
					&& visit2.equals(visit))
				return true;
		}

		return false;

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

	private void showFailSaveMsg(String field, int line)
			throws InterruptedException {
		String msg = "Error input: " + field + " in line " + line;
		Messagebox.show(msg, "ERROR", Messagebox.OK, Messagebox.ERROR);
	}
}
