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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Messagebox;

import com.xsis.ics.common.Constant;
import com.xsis.ics.dao.ICanvasserDao;
import com.xsis.ics.dao.ICanvasserVisitDao;
import com.xsis.ics.dao.ILookupDao;
import com.xsis.ics.dao.ISurveyDao;
import com.xsis.ics.dao.ISurveyDetailDao;
import com.xsis.ics.dao.ISurveyResultDao;
import com.xsis.ics.dao.IOutletDao;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserVisit;
import com.xsis.ics.domain.Survey;
import com.xsis.ics.domain.SurveyDetail;
import com.xsis.ics.domain.SurveyResult;
import com.xsis.ics.domain.Outlet;
import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.service.IBatchSurveyInputService;
import com.xsis.ics.util.DateUtils;
import com.xsis.ics.util.StringUtil;



public class BatchSurveyInputServiceImpl implements IBatchSurveyInputService {
	private static final char CSV_SEPARATOR = '|';
	public int MAX_FIELD = 3;
	public int MAX_FIELDS = 0;
	private static final String MANDATORY_FLAG="M";
		
	

	
	
	
	
	private static final String[][] VALIDATOR={{"M","\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}","survey_datetime"}, // visit_time
				{"M","\\d+","canvasser"}, 	
				{"M","\\d+","outlet"}	
								}; 	
	
	private Logger logger = Logger.getLogger(BatchInputCvrTargetServiceImpl.class);
	private ISurveyResultDao surveyResultDao;
	private ICanvasserDao canvasserDao;
	private IOutletDao outletDao;
	private ISurveyDao surveyDao;
	private ICanvasserVisitDao canvasserVisitDao;
	private ISurveyDetailDao surveyDetailDao;
	private ILookupDao lookupDao;

	
	public void setLookupDao(ILookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}
	
	public ILookupDao getLookupDao() {
		return lookupDao;
	}
	
	public void setCanvasserDao(ICanvasserDao canvasserDao) {
		this.canvasserDao = canvasserDao;
	}

	public void setOutletDao(IOutletDao outletDao) {
		this.outletDao = outletDao;
	}


	public ISurveyResultDao getSurveyResultDao() {
		return surveyResultDao;
	}

	
	
	public ICanvasserVisitDao getCanvasserVisitDao() {
		return canvasserVisitDao;
	}

	public void setCanvasserVisitDao(ICanvasserVisitDao canvasserVisitDao) {
		this.canvasserVisitDao = canvasserVisitDao;
	}	

	
	
	public void setSurveyResultDao(ISurveyResultDao surveyResultDao) {
		this.surveyResultDao = surveyResultDao;
	}	

	
	public void setSurveyDao(ISurveyDao surveyDao) {
		this.surveyDao = surveyDao;
	}	

	public ISurveyDao getSurveyDao() {
		return surveyDao;
	}

	public ISurveyDetailDao getSurveyDetailDao() {
		return surveyDetailDao;
	}

	/**
	 * @param surveyDetailDao the surveyDetailDao to set
	 */
	public void setSurveyDetailDao(ISurveyDetailDao surveyDetailDao) {
		this.surveyDetailDao = surveyDetailDao;
	}
	

	@Override
	public void saveOrUpdateList(List<CanvasserVisit> canvasserVisit) {
		
		
		canvasserVisitDao.saveOrUpdateAll(canvasserVisit);
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

	protected String validateRow(List<String> row, int MAX_FIELDS, String strLookupType){
		String output = null;
		
		
		
		
		if (row.size() == MAX_FIELDS || ((row.size() == MAX_FIELDS+1)&&row.get(row.size()-1).isEmpty())){
			for(int i = 0;i<MAX_FIELDS;i++){
				String field = row.get(i)==null?"":row.get(i).trim();
				String mandatoryFlag = VALIDATOR[0][0];
				//if (MANDATORY_FLAG.equals(mandatoryFlag) && !StringUtil.isNotEmpty(field)){ //mandatory field
				//	output = "Invalid "+VALIDATOR[0][0]+": Field required";
				//	break;
				//}
				//if (!field.matches(VALIDATOR[i][1])){
				//	output = "Invalid "+VALIDATOR[i][2]+": Format is invalid";
				//	break;
				//}
			}
		}else{
			output = "Number of expected fields is not match";
		}
		return output;
	}
	
	protected List<CanvasserVisit> processFile(BufferedReader reader, String fileName, Long by,
			Long userOwner) throws Exception, IOException {

        Date visit2 = new Date();	
		List<CanvasserVisit> cs =  new ArrayList<CanvasserVisit>();
		Set<SurveyResult> surveyResults= new HashSet<SurveyResult>();
		//Survey survey= new Survey();
		 
		
		int lin = 1;
		Long surveIdx=null;
		String validationOutput ="";
		boolean stop = false;
		String row = "";
		int i = 0;
		int loop=0;
		int counter=0;
		int position=2;
		String strLookupType=null;
		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR, 2);
		boolean result = true;
			
		List<Survey> surveyId2 = surveyDao.getAllActiveSurvey();	
		
		
		String strQuestioValidation=null;
		List strLookupTypeList=null;
		boolean strLookupTypeVal=false;
		List<SurveyDetail> details = null;
		if (!surveyId2.isEmpty()){
			details = this.getSurveyDetailDao().getAllSurveyDetailBySurvey(surveyId2.get(0));
			loop = details.size();
		}
		/*for(Survey surveyIds : surveyId2){
			List<SurveyDetail> detail = this.getSurveyDetailDao().getAllSurveyDetailBySurvey(surveyIds);
			Set<SurveyDetail> surveyDetails = new HashSet<SurveyDetail>(detail);
			surveyIds.setDetails(surveyDetails);
			
			loop = detail.size();
			
	        SurveyDetail lookupType = detail.get(0);
	        strLookupType = lookupType.getLookupType();
	        strQuestioValidation = lookupType.getQuestionValidation();
	        
		}
		
		if (strLookupType.isEmpty() || strLookupType==null){
			String REGEX[][]={{"N",strQuestioValidation,"result"}};
		}else{
			strLookupTypeList = lookupDao.getLookupStringByType(strLookupType);
		
		}*/

		
		
		
		System.out.println("strLookupTypeVal ++ " + strLookupTypeVal);
		
		int MAX_FIELDS = MAX_FIELD + loop;
		
		List<Survey> surveyId = surveyDao.getAllActiveSurvey();	
		
		
             
						while (!stop && ((row = reader.readLine()) != null)) {
							
							List<String> csv = StringUtil.parseCsv(row, CSV_SEPARATOR);
							i = 0;
							
						   
							validationOutput = this.validateRow(csv, MAX_FIELDS,strLookupType);
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
								
							
								
								Long canvasserId = Long.parseLong(csv.get(1));
								
								Long outletId = Long.parseLong(csv.get(2));
								
							
								//int questionNo = Integer.parseInt(csv.get(3));
								
								CanvasserVisit cVisit = canvasserVisitDao.getCanvasserBaseOnCvsIDOutIDVisitTime(canvasserId , outletId, visit);
				
								Canvasser cvid = canvasserDao.findCanvasserByCvId(canvasserId);
								
								if (cvid ==null){
									showFailSaveMsg("Canvasser ["+canvasserId+"] does not exists", lin);
									result = false;
									break;
								}
								
								
								Outlet outlet = outletDao.findOutletByOutletId(outletId);
												
								if (outlet ==null){
									showFailSaveMsg("Outlet ["+outletId+"] does not exists", lin);
									result = false;
									break;
								}
								
									
								if(cVisit==null){
				
					                cVisit=new CanvasserVisit();					                
					                cVisit.setCanvasser(cvid);                
					                cVisit.setOutlet(outlet);
					                cVisit.setVisitTime(new Timestamp(visit.getTime()));
					                cVisit.setVisitFlag("Y");
					                cVisit.setCreatedBy(new Long(1));
					                cVisit.setCreationDate(new Date());
					                cVisit.setTransactionFlag("N");
					                cVisit.setSystemOrigin(Constant.VISIT_ORIGIN_SYSTEM_WEB);
				
					            }
								counter=0;
								 while (++counter <= loop ) {
									 if (this.isValid(details, counter, csv.get(position+counter))){
										 SurveyResult surveyResult = new SurveyResult();
											surveyResult.setVisit(cVisit); 				
											surveyResult.setSurvey(surveyId.get(0));
											surveyResult.setQuestionNo(counter);
											surveyResult.setResult(csv.get(position+counter));
											surveyResults.add(surveyResult);
											
											
									 }else{
										 showFailSaveMsg("Invalid Result", lin);
										 result = false;
										 break;
									 }
								}	
								if (result){
									cVisit.setSurveyResults(surveyResults);
									cVisit.setOutlet(outlet);
									cVisit.setVisitTime(new Timestamp(visit.getTime()));
					                cVisit.setVisitFlag("Y");
					                cVisit.setCreatedBy(new Long(1));
					                cVisit.setCreationDate(new Date());
					                cVisit.setTransactionFlag("N");
					                cVisit.setSystemOrigin(Constant.VISIT_ORIGIN_SYSTEM_WEB);					
									cs.add(cVisit);
								}
							}else{
								showFailSaveMsg(validationOutput, lin);
								result = false;
								break;
							}
							lin++;
						}
						
		
		
		//canvasserVisit
		
		if (!result){
			return null;
		}else{
			
			System.out.println("-----------------------------cs---------- > " +cs);
			return cs;
		}
	}
	
	private boolean isValid(List<SurveyDetail> details, int questionNo, String result){
		boolean hasil = false;
		for(SurveyDetail detail : details){
			if (detail.getQuestionNo() == questionNo){
				if(!((result==null || result.isEmpty()) && detail.getMandatoryFlag().equals("YES"))){
					if (StringUtil.isNotEmpty(detail.getLookupType())){
						List<String> validValues = lookupDao.getLookupStringByType(detail.getLookupType());
						hasil = validValues.contains(result);
						
						System.out.println(validValues);
					}else{
						String validationString = detail.getQuestionValidation();
						String regex = ".*\\{\\d+\\}";
			    		if (validationString.matches(regex)){
			    			validationString = validationString.replaceFirst("\\{", "{0,");
			    		}
						hasil = result.matches(validationString);
					}
				}
				break;
			}
		}
		return hasil;
	}
	

	private void showFailSaveMsg(String field, int line)
			throws InterruptedException {
		String msg = "Error input: " + field + " in line " + line;
		Messagebox.show(msg, "ERROR", Messagebox.OK, Messagebox.ERROR);
	}
}
