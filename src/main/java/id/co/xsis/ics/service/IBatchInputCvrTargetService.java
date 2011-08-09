package com.xsis.ics.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.xsis.ics.domain.CanvasserTarget;

public interface IBatchInputCvrTargetService {
	public void saveOrUpdateList(List<CanvasserTarget> targets, Long userLogonId);
	public List<CanvasserTarget> readFile(InputStream inputStream, String fileName, Long by, Long userOwner) throws Exception, IOException;
	public List<CanvasserTarget> readFile(String stringStream, String fileName, Long by, Long userOwner) throws Exception, IOException;
}
