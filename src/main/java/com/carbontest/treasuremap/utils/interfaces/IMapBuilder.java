package com.carbontest.treasuremap.utils.interfaces;

import java.util.List;

import com.carbontest.treasuremap.entity.interfaces.IEntity;
import com.carbontest.treasuremap.enums.EntityType;

public interface IMapBuilder {

	//used to retrieve the parameter of each entity type from config
	public List<String> retrieveEntitiesParameters(EntityType entitytype);
	
	//Build mountains and put them in a list
	IMapBuilder setMountains();

	//Build treasure places and put them in a list
	IMapBuilder setTreasures();

	//Build adventurers and put them in a list
	IMapBuilder setAdventurers();

	List<IEntity> buildMapAndEntities();

	List<String> getMapConfig();

	void setMapConfig(List<String> mapConfig);

	void setEntitiesList(List<IEntity> entitiesList);
	
	public List<IEntity> getEntitiesList();

	}