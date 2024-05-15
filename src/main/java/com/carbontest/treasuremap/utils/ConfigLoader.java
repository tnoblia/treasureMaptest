package com.carbontest.treasuremap.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.carbontest.treasuremap.utils.interfaces.IConfigLoader;

@Component
public class ConfigLoader implements IConfigLoader{
    
    private ResourceLoader resourceLoader;

	public ConfigLoader(@Autowired ResourceLoader resourceLoader) {
    	this.resourceLoader = resourceLoader;
    }
	
    public InputStream loadResource() {
    	InputStream input=null;
        try{
        	Resource resource = resourceLoader.getResource("classpath:map.cfg");
            input = resource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }
    
    public String getMapString() {
    	InputStream inputStream = this.loadResource();
        StringBuilder mapContentBuilder = new StringBuilder();
        try(Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");) {
	        while(scanner.hasNext()) {
	        	mapContentBuilder.append(scanner.next());
	        }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return mapContentBuilder.toString();
    }
    
    public List<String> getMapParameters() {
    	String mapString = this.getMapString();
    	List<String> listParameters = Arrays.asList(mapString.split("\n"));
    	List<String> filteredlistParameters = new ArrayList<>();
    	for(String stringRawParameter : listParameters) {
    		if(stringRawParameter.charAt(0) != "#".charAt(0)) {
    			String filteredParameter = stringRawParameter.replace(" ", "").replace("\r","");
    			if(filteredParameter!="") {
    				filteredlistParameters.add(filteredParameter);
    			}
    		}
    	}
    	return filteredlistParameters;
    }
    
    public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}
    
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}


    
}