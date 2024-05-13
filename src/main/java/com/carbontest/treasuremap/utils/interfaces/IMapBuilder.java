package com.carbontest.treasuremap.utils.interfaces;

import java.util.List;
import java.util.Map;

import com.carbontest.treasuremap.entity.factories.interfaces.IEntityFactory;
import com.carbontest.treasuremap.entity.factories.interfaces.IMovableFactory;
import com.carbontest.treasuremap.entity.factories.interfaces.IStackableFactory;
import com.carbontest.treasuremap.entity.interfaces.IEntity;
import com.carbontest.treasuremap.enums.EntityType;

public interface IMapBuilder {

	//used to retrieve the parameter of each entity type from config
		List<String> retrieveEntitiesParameters(EntityType entitytype);

		//Build empty first draft of the Treasure Chart
		IMapBuilder setMapLimitsFromConfig();

		//Build mountains and put them in a list
		IMapBuilder setMountains();

		//Build treasure places and put them in a list
		IMapBuilder setTreasures();

		//Build adventurers and put them in a list
		IMapBuilder setAdventurers();

		Map<String, Integer> getMapLimits();

		List<IEntity> getEntitiesList();

		void checkForEntitiesToNotStackOnEachOther() throws RuntimeException;

		void checkForEntitiesOutOfMapBonds() throws RuntimeException;

		//Getters and setters
		IConfigLoader getConfigLoader();

		void setConfigLoader(IConfigLoader configLoader);

		IStackableFactory getTreasurePlaceFactory();

		void setTreasurePlaceFactory(IStackableFactory treasurePlaceFactory);

		IMovableFactory getAdventurerFactory();

		void setAdventurerFactory(IMovableFactory adventurerFactory);

		IEntityFactory getMountainFactory();

		void setMountainFactory(IEntityFactory mountainFactory);

		List<String> getMapConfig();

		void setMapConfig(List<String> mapConfig);

		void setEntitiesList(List<IEntity> entitiesList);
		
		public void getMapLimits(Map<String,Integer> mapLimits);

	}