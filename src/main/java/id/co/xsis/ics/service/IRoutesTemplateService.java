package com.xsis.ics.service;

import java.util.List;

import org.zkoss.zul.Textbox;

import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserRoutes;
import com.xsis.ics.domain.CanvasserTarget;
import com.xsis.ics.domain.Dealer;
import com.xsis.ics.domain.IcsUser;
import com.xsis.ics.domain.Lookup;
import com.xsis.ics.domain.RouteTemplate;

/**
08062011 Start by Sherwin
*/

public interface IRoutesTemplateService {

	public List<RouteTemplate> findTemplateBaseOnDealerId(Long dealerId);

	int countTemplatesBaseOnDealerId(Long dealerId, String name);

	List<RouteTemplate> getTemplatesBaseOnDealerId(Long dealerId, String name,
			int start, int to);

}
