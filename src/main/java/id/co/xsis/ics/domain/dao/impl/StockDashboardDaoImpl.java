package com.xsis.ics.dao.impl;

import java.util.List;
import com.xsis.ics.dao.IStockDashboardDao;
import com.xsis.ics.dao.common.BaseDao;

@SuppressWarnings("unchecked")
public class StockDashboardDaoImpl extends BaseDao<Object> implements
		IStockDashboardDao {
	
	public List<Object> getSPStock(String userType, Long userOwner) {
		String hql  = " SELECT " +
					  " sum(CV.stockSpXL), " +
					  " sum(CV.stockSpIsatMtr),  " +
					  " sum(CV.stockSpIsatIm3), " +
					  " sum(CV.stockSpTselSmp), " +
					  " sum(CV.stockSpTselAs), " +
					  " sum(CV.stockSpOther) " +
					  " FROM CanvasserVisit as CV " ;
//					  " GROUP BY CV.outlet " ;					 
		
		if (userType.equalsIgnoreCase("DEALER")){
			
			hql +=  
//				" left join CV.canvasser as CA " +
					" left join CV.outlet as OU " +
					" left join OU.dealer as DE " +
					" where DE.id = ? ";
			
		} else if (userType.equalsIgnoreCase("AM")){
			
			hql +=  
//				" left join CV.canvasser as CA " +
					" left join CV.outlet as OU " +
					" left join OU.dealer as DE " +
					" left join DE.depo as DP " +
					" where DP.id = ? ";
		
		} else if (userType.equalsIgnoreCase("RSOM")){
			
			hql +=  
//				" left join CV.canvasser as CA " +
					" left join CV.outlet as OU " +
					" left join OU.dealer as DE " +
					" left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" where SR.id = ? ";
			
		} else if (userType.equalsIgnoreCase("GM")){
			
			hql +=  
//				" left join CV.canvasser as CA " +
					" left join CV.outlet as OU " +
					" left join OU.dealer as DE " +
					" left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" where RE.id = ? ";
		
		} else if (userType.equalsIgnoreCase("VP")){
			
			hql +=  
//				" left join CV.canvasser as CA " +
					" left join CV.outlet as OU " +
					" left join OU.dealer as DE " +
					" left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" left join RE.teritory as TR " +
					" where TR.id = ? ";
		
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
			
			hql += " left join CV.outlet as OU " +
					" where 0=? ";
		
		}
//		else {
			
//			hql +=  
//				" left join CV.canvasser as CA " +
//					" left join CV.outlet as OU " +
//					" left join OU.dealer as DE " +
//					" where DE.id = ?";
//		}
		
		hql +=  " and CV.visitTime =(SELECT MAX(CV2.visitTime) FROM CanvasserVisit as CV2 WHERE CV2.outlet = OU)";

		if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN"))
			return getHibernateTemplate().find(hql, userOwner.intValue());
		else
			return getHibernateTemplate().find(hql, userOwner);

	}

	public List<Object> getPVStock(String userType, Long userOwner) {

		String hql = " SELECT " +
					 " sum(CV.stockPv10XL), " +
					 " sum(CV.stockPv50XL), " +
					 " sum(CV.stockPvIsat), " +
					 " sum(CV.stockPvTsel), " +
					 " sum(CV.stockPvOther) " +
					 " FROM CanvasserVisit as CV " ;
//					 " GROUP BY CV.outlet " ;
		
		if (userType.equalsIgnoreCase("DEALER")){
			
			hql +=  
//					" left join CV.canvasser as CA " +
					" left join CV.outlet as OU " +
					" left join OU.dealer as DE " +
					" where DE.id = ? ";
			
		} else if (userType.equalsIgnoreCase("AM")){
			
			hql +=  
//				" left join CV.canvasser as CA " +
					" left join CV.outlet as OU " +
					" left join OU.dealer as DE " +
					" left join DE.depo as DP " +
					" where DP.id = ? ";
		
		} else if (userType.equalsIgnoreCase("RSOM")){
			
			hql +=  
//				" left join CV.canvasser as CA " +
					" left join CV.outlet as OU " +
					" left join OU.dealer as DE " +
					" left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" where SR.id = ? ";
			
		} else if (userType.equalsIgnoreCase("GM")){
			
			hql +=  
//				" left join CV.canvasser as CA " +
					" left join CV.outlet as OU " +
					" left join OU.dealer as DE " +
					" left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" where RE.id = ? ";
		
		} else if (userType.equalsIgnoreCase("VP")){
			
			hql +=  
//				" left join CV.canvasser as CA " +
					" left join CV.outlet as OU " +
					" left join OU.dealer as DE " +
					" left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" left join RE.teritory as TR " +
					" where TR.id = ? ";
		
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
			
			hql += " left join CV.outlet as OU " +
					"where 0=? ";
		
		} 
//		else {
			
//			hql +=  
//				" left join CV.canvasser as CA " +
//					" left join CV.outlet as OU " +
//					" left join OU.dealer as DE " +
//					" where DE.id = 0";
//		}
		
		hql +=  " and CV.visitTime =(SELECT MAX(CV2.visitTime) FROM CanvasserVisit CV2 WHERE CV2.outlet = OU)";
		
		if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN"))
			return getHibernateTemplate().find(hql, userOwner.intValue());
		else
			return getHibernateTemplate().find(hql, userOwner);
	}
}
