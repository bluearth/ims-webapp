package com.xsis.ics.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;


import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class CanvasserVisit extends BaseDomain implements BaseEntity,
		Serializable {

	private static final long serialVersionUID = -2660359020721643182L;
	private Timestamp visitTime;
	private BigDecimal tripLength;
	private String visitFlag;
	private String visitNotes;
	private String transactionFlag;
	private Long transactionSp;
	private Long transactionDp;
	private Long transactionDp1;
	private Long transactionDp5;
	private Long transactionDp10;
	private Long transactionPv10;
	private Long transactionPv50;
	private Long stockSpXL;
	private Long stockSpIsatMtr;
	private Long stockSpIsatIm3;
	private Long stockSpTselSmp;
	private Long stockSpTselAs;
	private Long stockSpOther;
	private Long stockPv10XL;
	private Long stockPv50XL;
	private Long stockPvIsat;
	private Long stockPvTsel;
	private Long stockPvOther;
	private String brandingPaintedXL;
	private Long numOfPosterXL;
	private Long numOfPosterTsel;
	private Long numOfPosterIsat;
	private Long numOfShopBlindXL;
	private Long numOfShopBlindTsel;
	private Long numOfShopBlindIsat;
	private Long numOfFlyerTsel;
	private Long numOfFlyerXL;
	private Long numOfFlyerIsat;
	private Long numOfNeonBoxXL;
	private Long numOfNeonBoxTsel;
	private Long numOfNeonBoxIsat;
	private Long brandingOther;
	private String questionnaire1;
	private String questionnaire2;
	private String questionnaire3;
	private String questionnaire4;
	private String questionnaire5;
	private String questionnaire6;
	private String questionnaire7;
	private String questionnaire8;
	private String questionnaire9;
	private String questionnaire10;
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private String answer5;
	private String answer6;
	private String answer7;
	private String answer8;
	private String answer9;
	private String answer10;
	private String transactionKPI;
	private String systemOrigin;
	private Outlet outlet = new Outlet();
	private Canvasser canvasser = new Canvasser();
	private Set<SurveyResult> surveyResults;
	
	
	
	
	/**
	 * @return the surveyResults
	 */
	public Set<SurveyResult> getSurveyResults() {
		return surveyResults;
	}

	/**
	 * @param surveyResults the surveyResults to set
	 */
	public void setSurveyResults(Set<SurveyResult> surveyResults) {
		this.surveyResults = surveyResults;
	}

	public Timestamp getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Timestamp visitTime) {
		this.visitTime = visitTime;
	}

	public BigDecimal getTripLength() {
		return tripLength;
	}

	public void setTripLength(BigDecimal tripLength) {
		this.tripLength = tripLength;
	}

	public String getVisitFlag() {
		return visitFlag;
	}

	public void setVisitFlag(String visitFlag) {
		this.visitFlag = visitFlag;
	}

	public String getVisitNotes() {
		return visitNotes;
	}

	public void setVisitNotes(String visitNotes) {
		this.visitNotes = visitNotes;
	}

	public String getTransactionFlag() {
		return transactionFlag;
	}

	public void setTransactionFlag(String transactionFlag) {
		this.transactionFlag = transactionFlag;
	}

	public Long getTransactionSp() {
		return transactionSp;
	}

	public void setTransactionSp(Long transactionSp) {
		this.transactionSp = transactionSp;
	}

	public Long getTransactionDp() {
		return transactionDp;
	}

	public void setTransactionDp(Long transactionDp) {
		this.transactionDp = transactionDp;
	}

	public Long getTransactionDp1() {
		return transactionDp1;
	}

	public void setTransactionDp1(Long transactionDp1) {
		this.transactionDp1 = transactionDp1;
	}

	public Long getTransactionDp5() {
		return transactionDp5;
	}

	public void setTransactionDp5(Long transactionDp5) {
		this.transactionDp5 = transactionDp5;
	}

	public Long getTransactionDp10() {
		return transactionDp10;
	}

	public void setTransactionDp10(Long transactionDp10) {
		this.transactionDp10 = transactionDp10;
	}

	public Long getTransactionPv10() {
		return transactionPv10;
	}

	public void setTransactionPv10(Long transactionPv10) {
		this.transactionPv10 = transactionPv10;
	}

	public Long getTransactionPv50() {
		return transactionPv50;
	}

	public void setTransactionPv50(Long transactionPv50) {
		this.transactionPv50 = transactionPv50;
	}

	public Long getStockSpXL() {
		return stockSpXL;
	}

	public void setStockSpXL(Long stockSpXL) {
		this.stockSpXL = stockSpXL;
	}

	public Long getStockSpIsatMtr() {
		return stockSpIsatMtr;
	}

	public void setStockSpIsatMtr(Long stockSpIsatMtr) {
		this.stockSpIsatMtr = stockSpIsatMtr;
	}

	public Long getStockSpIsatIm3() {
		return stockSpIsatIm3;
	}

	public void setStockSpIsatIm3(Long stockSpIsatIm3) {
		this.stockSpIsatIm3 = stockSpIsatIm3;
	}

	public Long getStockSpTselSmp() {
		return stockSpTselSmp;
	}

	public void setStockSpTselSmp(Long stockSpTselSmp) {
		this.stockSpTselSmp = stockSpTselSmp;
	}

	public Long getStockSpTselAs() {
		return stockSpTselAs;
	}

	public void setStockSpTselAs(Long stockSpTselAs) {
		this.stockSpTselAs = stockSpTselAs;
	}

	public Long getStockSpOther() {
		return stockSpOther;
	}

	public void setStockSpOther(Long stockSpOther) {
		this.stockSpOther = stockSpOther;
	}

	public Long getStockPv10XL() {
		return stockPv10XL;
	}

	public void setStockPv10XL(Long stockPv10XL) {
		this.stockPv10XL = stockPv10XL;
	}

	public Long getStockPv50XL() {
		return stockPv50XL;
	}

	public void setStockPv50XL(Long stockPv50XL) {
		this.stockPv50XL = stockPv50XL;
	}

	public Long getStockPvIsat() {
		return stockPvIsat;
	}

	public void setStockPvIsat(Long stockPvIsat) {
		this.stockPvIsat = stockPvIsat;
	}

	public Long getStockPvTsel() {
		return stockPvTsel;
	}

	public void setStockPvTsel(Long stockPvTsel) {
		this.stockPvTsel = stockPvTsel;
	}

	public Long getStockPvOther() {
		return stockPvOther;
	}

	public void setStockPvOther(Long stockPvOther) {
		this.stockPvOther = stockPvOther;
	}

	public String getBrandingPaintedXL() {
		return brandingPaintedXL;
	}

	public void setBrandingPaintedXL(String brandingPaintedXL) {
		this.brandingPaintedXL = brandingPaintedXL;
	}

	public Long getNumOfPosterXL() {
		return numOfPosterXL;
	}

	public void setNumOfPosterXL(Long numOfPosterXL) {
		this.numOfPosterXL = numOfPosterXL;
	}

	public Long getNumOfPosterTsel() {
		return numOfPosterTsel;
	}

	public void setNumOfPosterTsel(Long numOfPosterTsel) {
		this.numOfPosterTsel = numOfPosterTsel;
	}

	public Long getNumOfPosterIsat() {
		return numOfPosterIsat;
	}

	public void setNumOfPosterIsat(Long numOfPosterIsat) {
		this.numOfPosterIsat = numOfPosterIsat;
	}

	public Long getNumOfShopBlindXL() {
		return numOfShopBlindXL;
	}

	public void setNumOfShopBlindXL(Long numOfShopBlindXL) {
		this.numOfShopBlindXL = numOfShopBlindXL;
	}

	public Long getNumOfShopBlindTsel() {
		return numOfShopBlindTsel;
	}

	public void setNumOfShopBlindTsel(Long numOfShopBlindTsel) {
		this.numOfShopBlindTsel = numOfShopBlindTsel;
	}

	public Long getNumOfShopBlindIsat() {
		return numOfShopBlindIsat;
	}

	public void setNumOfShopBlindIsat(Long numOfShopBlindIsat) {
		this.numOfShopBlindIsat = numOfShopBlindIsat;
	}

	public Long getNumOfFlyerTsel() {
		return numOfFlyerTsel;
	}

	public void setNumOfFlyerTsel(Long numOfFlyerTsel) {
		this.numOfFlyerTsel = numOfFlyerTsel;
	}

	public Long getNumOfFlyerXL() {
		return numOfFlyerXL;
	}

	public void setNumOfFlyerXL(Long numOfFlyerXL) {
		this.numOfFlyerXL = numOfFlyerXL;
	}

	public Long getNumOfFlyerIsat() {
		return numOfFlyerIsat;
	}

	public void setNumOfFlyerIsat(Long numOfFlyerIsat) {
		this.numOfFlyerIsat = numOfFlyerIsat;
	}

	public Long getNumOfNeonBoxXL() {
		return numOfNeonBoxXL;
	}

	public void setNumOfNeonBoxXL(Long numOfNeonBoxXL) {
		this.numOfNeonBoxXL = numOfNeonBoxXL;
	}

	public Long getNumOfNeonBoxTsel() {
		return numOfNeonBoxTsel;
	}

	public void setNumOfNeonBoxTsel(Long numOfNeonBoxTsel) {
		this.numOfNeonBoxTsel = numOfNeonBoxTsel;
	}

	public Long getNumOfNeonBoxIsat() {
		return numOfNeonBoxIsat;
	}

	public void setNumOfNeonBoxIsat(Long numOfNeonBoxIsat) {
		this.numOfNeonBoxIsat = numOfNeonBoxIsat;
	}

	public Long getBrandingOther() {
		return brandingOther;
	}

	public void setBrandingOther(Long brandingOther) {
		this.brandingOther = brandingOther;
	}

	public String getQuestionnaire1() {
		return questionnaire1;
	}

	public void setQuestionnaire1(String questionnaire1) {
		this.questionnaire1 = questionnaire1;
	}

	public String getQuestionnaire2() {
		return questionnaire2;
	}

	public void setQuestionnaire2(String questionnaire2) {
		this.questionnaire2 = questionnaire2;
	}

	public String getQuestionnaire3() {
		return questionnaire3;
	}

	public void setQuestionnaire3(String questionnaire3) {
		this.questionnaire3 = questionnaire3;
	}

	public String getQuestionnaire4() {
		return questionnaire4;
	}

	public void setQuestionnaire4(String questionnaire4) {
		this.questionnaire4 = questionnaire4;
	}

	public String getQuestionnaire5() {
		return questionnaire5;
	}

	public void setQuestionnaire5(String questionnaire5) {
		this.questionnaire5 = questionnaire5;
	}

	public String getQuestionnaire6() {
		return questionnaire6;
	}

	public void setQuestionnaire6(String questionnaire6) {
		this.questionnaire6 = questionnaire6;
	}

	public String getQuestionnaire7() {
		return questionnaire7;
	}

	public void setQuestionnaire7(String questionnaire7) {
		this.questionnaire7 = questionnaire7;
	}

	public String getQuestionnaire8() {
		return questionnaire8;
	}

	public void setQuestionnaire8(String questionnaire8) {
		this.questionnaire8 = questionnaire8;
	}

	public String getQuestionnaire9() {
		return questionnaire9;
	}

	public void setQuestionnaire9(String questionnaire9) {
		this.questionnaire9 = questionnaire9;
	}

	public String getQuestionnaire10() {
		return questionnaire10;
	}

	public void setQuestionnaire10(String questionnaire10) {
		this.questionnaire10 = questionnaire10;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public String getAnswer4() {
		return answer4;
	}

	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	public String getAnswer5() {
		return answer5;
	}

	public void setAnswer5(String answer5) {
		this.answer5 = answer5;
	}

	public String getAnswer6() {
		return answer6;
	}

	public void setAnswer6(String answer6) {
		this.answer6 = answer6;
	}

	public String getAnswer7() {
		return answer7;
	}

	public void setAnswer7(String answer7) {
		this.answer7 = answer7;
	}

	public String getAnswer8() {
		return answer8;
	}

	public void setAnswer8(String answer8) {
		this.answer8 = answer8;
	}

	public String getAnswer9() {
		return answer9;
	}

	public void setAnswer9(String answer9) {
		this.answer9 = answer9;
	}

	public String getAnswer10() {
		return answer10;
	}

	public void setAnswer10(String answer10) {
		this.answer10 = answer10;
	}

	public String getTransactionKPI() {
		return transactionKPI;
	}

	public void setTransactionKPI(String transactionKPI) {
		this.transactionKPI = transactionKPI;
	}

	public Outlet getOutlet() {
		return outlet;
	}

	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}

	public Canvasser getCanvasser() {
		return canvasser;
	}

	public void setCanvasser(Canvasser canvasser) {
		this.canvasser = canvasser;
	}

	@Override
	public boolean isNew() {
		return (getId() == null);
	}

	/**
	 * @return the originSystem
	 */
	public String getSystemOrigin() {
		return systemOrigin;
	}

	/**
	 * @param originSystem the originSystem to set
	 */
	public void setSystemOrigin(String systemOrigin) {
		this.systemOrigin = systemOrigin;
	}

	
}
