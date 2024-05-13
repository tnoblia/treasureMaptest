package com.carbontest.treasuremap.utils.interfaces;

import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.Resource;

public interface IConfigLoader {
	
	public InputStream loadResource();
	public String getMapString();
	public List<String> getMapParameters();
	
	public Resource getResourceFile();
	public void setResourceFile(Resource resourceFile);
}
