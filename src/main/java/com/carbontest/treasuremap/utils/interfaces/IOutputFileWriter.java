package com.carbontest.treasuremap.utils.interfaces;

import java.util.List;

import com.carbontest.treasuremap.entity.base.IEntity;

public interface IOutputFileWriter {

	public String finalMapParamsToString();
	public void writeFile(String fileName);
	public List<IEntity> getEntitiesList();
	public void setEntitiesList(List<IEntity> entitiesList);
}
