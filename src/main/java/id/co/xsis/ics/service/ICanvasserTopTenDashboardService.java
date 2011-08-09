package com.xsis.ics.service;

import java.util.List;

import org.zkoss.zul.CategoryModel;

public interface ICanvasserTopTenDashboardService {
	
	public CategoryModel getTopTenDepo(CategoryModel categoryModel,String userType,Long userOwner);
	public CategoryModel getTopTenCanvasser(CategoryModel categoryModel,String userType,Long userOwner);
	public CategoryModel getTopTenDealer(CategoryModel categoryModel,String userType,Long userOwner);
	public CategoryModel getTopTenSubRegion(CategoryModel categoryModel,String userType,Long userOwner);
	public CategoryModel getTopTenRegion(CategoryModel categoryModel,String userType,Long userOwner);
	public CategoryModel getTopTenTerritory(CategoryModel categoryModel,String userType,Long userOwner);

}
