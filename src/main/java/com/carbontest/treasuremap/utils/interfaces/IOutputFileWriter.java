package com.carbontest.treasuremap.utils.interfaces;

import java.util.List;

import com.carbontest.treasuremap.entity.interfaces.IEntity;

public interface IOutputFileWriter {

	public String finalMapParamsToString();
	public void writeFileWithFinalPlan();
	public List<IEntity> getEntitiesList();
	public void setEntitiesList(List<IEntity> entitiesList);
}
