package com.xsis.ics.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.xsis.ics.domain.CanvasserRoutes;

public interface IBatchInputCvrRouteService {
	public void saveOrUpdateList(List<CanvasserRoutes> routes);
	public List<CanvasserRoutes> readFile(InputStream inputStream, String fileName, Long by, Long userOwner) throws Exception, IOException;
	public List<CanvasserRoutes> readFile(String stringStream, String fileName, Long by, Long userOwner) throws Exception, IOException;
}
