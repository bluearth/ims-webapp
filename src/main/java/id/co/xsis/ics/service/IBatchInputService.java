package com.xsis.ics.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

import com.xsis.ics.domain.CanvasserVisit;

public interface IBatchInputService {

	public void saveOrUpdateList(List<CanvasserVisit> visits);

	List<CanvasserVisit> readFile(InputStream inputStream, String fileName,
			Long by, Long userOwner) throws Exception, IOException;

	List<CanvasserVisit> readFile(String inputString, String fileName, Long by,
			Long userOwner) throws Exception, IOException;
	
	List<CanvasserVisit> readFile(Reader inputReader, String fileName, Long by,
			Long userOwner) throws Exception, IOException;
}
