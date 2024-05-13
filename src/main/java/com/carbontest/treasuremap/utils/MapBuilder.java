package com.carbontest.treasuremap.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.carbontest.treasuremap.entity.factories.interfaces.IEntityFactory;
import com.carbontest.treasuremap.entity.factories.interfaces.IMovableFactory;
import com.carbontest.treasuremap.entity.factories.interfaces.IStackableFactory;
import com.carbontest.treasuremap.entity.interfaces.IEntity;
import com.carbontest.treasuremap.entity.interfaces.IMovable;
import com.carbontest.treasuremap.entity.interfaces.IStackable;
import com.carbontest.treasuremap.enums.AdventurerMove;
import com.carbontest.treasuremap.enums.EntityType;
import com.carbontest.treasuremap.enums.Orientation;
import com.carbontest.treasuremap.utils.interfaces.IConfigLoader;
import com.carbontest.treasuremap.utils.interfaces.IMapBuilder;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MapBuilder implements IMapBuilder{
	
	//Components injected by Spring Container
	private IConfigLoader configLoader;
	private IStackableFactory treasurePlaceFactory;
	private IMovableFactory adventurerFactory;
	private IEntityFactory mountainFactory;

	//Parameters used to build entities and mapLimits
	private List<String> mapConfig;
	
	//Built entities that are going used by adventureLauncher
	private List<IEntity> entitiesList;
	private Map<String,Integer> mapLimits;
	
	public MapBuilder(@Autowired IStackableFactory treasurePlaceFactory,
					@Autowired IMovableFactory adventurerFactory, 
					@Autowired IEntityFactory mountainFactory) {
		
		this.treasurePlaceFactory = treasurePlaceFactory;
		this.adventurerFactory = adventurerFactory;
		this.mountainFactory = mountainFactory;
		
		//initialize the entities list
		this.entitiesList=new ArrayList<>();
	}
	
	//used to retrieve the parameter of each entity type from config
	@Override
	public List<String> retrieveEntitiesParameters(EntityType entitytype) {
		List<String> entitiesParameters=new ArrayList<>();
		try {
			for(String parameter : this.getMapConfig()) {
				if(EntityType.fromChar(parameter.charAt(0))==entitytype) {
					entitiesParameters.add(parameter);
				}
			}
		}catch(Exception e) {
			if(e instanceof IllegalArgumentException) {
				List<String> listEntityType = new ArrayList<>();
				for(EntityType entity:EntityType.values()) {
					listEntityType.add(((Character)entity.getType()).toString()) ;
				}
				throw new IllegalArgumentException("Entities should be symbolized by one of these symbols : " + listEntityType +". "+ e.getMessage());
			}
		}
		return entitiesParameters;
	}
	
	//Build empty first draft of the Treasure Chart
	@Override
	public IMapBuilder setMapLimitsFromConfig() {
		String[] chartBondsParameters = this.retrieveEntitiesParameters(EntityType.CARTE).get(0).split("-");
		try {
			this.mapLimits = new HashMap<>();
			int mapXSize = Integer.parseInt(chartBondsParameters[1]);
			int mapYSize = Integer.parseInt(chartBondsParameters[2]);
			this.mapLimits.put("XSize",mapXSize);
			this.mapLimits.put("YSize",mapYSize);
		}catch(Exception e) {
			if(e instanceof NumberFormatException) {
				throw new NumberFormatException("Coordinates of map borders should be integers, error : "+e.getMessage());
			}
			if(e instanceof ArrayIndexOutOfBoundsException) {
				throw new ArrayIndexOutOfBoundsException("Map entity should have 2 coordinates x and y : "+e.getMessage());
			}
		}
    	return this;
	}
	
	//Build mountains and put them in a list
	@Override
	public IMapBuilder setMountains() {
		List<String> mountainsParameters = this.retrieveEntitiesParameters(EntityType.MONTAGNE);
		try {
			for (String mountainParameterString : mountainsParameters) {
				String[] mountainParameter = mountainParameterString.split("-");
				int xPosition = Integer.parseInt(mountainParameter[1]);
		    	int yPosition = Integer.parseInt(mountainParameter[2]);
		    	IEntity mountain = this.getMountainFactory().createMountain(xPosition,yPosition);
		    	this.entitiesList.add(mountain);
			}
		}catch(Exception e) {
			if(e instanceof NumberFormatException) {
				throw new NumberFormatException("Coordinates of mountain entities should be integers, error : "+e.getMessage());
			}
			if(e instanceof ArrayIndexOutOfBoundsException) {
				throw new ArrayIndexOutOfBoundsException("Mountain entity should have 2 coordinates x and y : "+e.getMessage());
			}
		}
		return this;
	}
	
	//Build treasure places and put them in a list
	@Override
	public IMapBuilder setTreasures() {
		List<String> TreasurePlacesParameters = this.retrieveEntitiesParameters(EntityType.TRESOR);
		try {
			for (String treasurePlaceParameterString : TreasurePlacesParameters) {
				String[] treasurePlaceParameter = treasurePlaceParameterString.split("-");
				int xPosition = Integer.parseInt(treasurePlaceParameter[1]);
		    	int yPosition = Integer.parseInt(treasurePlaceParameter[2]);
		    	int numberTreasures = Integer.parseInt(treasurePlaceParameter[3]);
		    	IStackable treasurePlace = this.getTreasurePlaceFactory().createTreasurePlace(xPosition,yPosition,numberTreasures);
		    	this.entitiesList.add(treasurePlace);
			}
		}catch(Exception e) {
			if(e instanceof NumberFormatException) {
				throw new NumberFormatException("Parameters of treasure entities should be integers, error : "+e.getMessage());
			}
			if(e instanceof ArrayIndexOutOfBoundsException) {
				throw new ArrayIndexOutOfBoundsException("Treasure entity should have 3 coordinates x, y and number of treasures: "+e.getMessage());
			}
		}
		return this;
	}
	
	//Build adventurers and put them in a list
	@Override
	public IMapBuilder setAdventurers() {
		List<String> adventurersParameters = this.retrieveEntitiesParameters(EntityType.AVENTURIER);
		try {
			for (String adventurerParameterString : adventurersParameters) {
				String[] adventurerParameter = adventurerParameterString.split("-");
				String name = adventurerParameter[1];
				int xPosition = Integer.parseInt(adventurerParameter[2]);
		    	int yPosition = Integer.parseInt(adventurerParameter[3]);
		    	String orientation = adventurerParameter[4];
		    	String pattern = adventurerParameter[5];
		    	IMovable adventurer = this.getAdventurerFactory().createAdventurer(name,xPosition,yPosition,orientation,pattern);
		    	this.entitiesList.add(adventurer);
			}
		}catch(Exception e) {
			if(e instanceof NumberFormatException) {
				throw new NumberFormatException("Parameters of adventurers should be integers for x and y. error : "+e.getMessage());
			}
			if(e instanceof ArrayIndexOutOfBoundsException) {
				throw new ArrayIndexOutOfBoundsException("Adventurers entity should have 5 parameters: name, x, y, orientation and pattern : "+e.getMessage());
			}
			if(e instanceof IllegalArgumentException) {
				List<String> listMoves = new ArrayList<>();
				List<String> listOrientation = new ArrayList<>();
				for(AdventurerMove adMove:AdventurerMove.values()) {
					listMoves.add(((Character)adMove.getMove()).toString()) ;
				}
				for(Orientation orientation:Orientation.values()) {
					listOrientation.add(((Character)orientation.getCardinalPoint()).toString()) ;
				}
				throw new IllegalArgumentException("For adventurer, orientation symbols should be in list "+ listOrientation +"  and move symbols should be in list "+ listMoves +". "+e.getMessage());
				
			}
			
		}
		return this;
	}
	
	@Override
	public Map<String,Integer> getMapLimits() {
			if(this.entitiesList!=null) {
				this.checkForEntitiesOutOfMapBonds();
			}
		return mapLimits;
	}
	
	@Override
	public List<IEntity> getEntitiesList() {
			if(this.mapLimits!=null) {
				this.checkForEntitiesOutOfMapBonds();
			}
			this.checkForEntitiesToNotStackOnEachOther();
		return entitiesList;
	}
	
	public void checkForEntitiesToNotStackOnEachOther() throws RuntimeException {
		for(IEntity entity:this.entitiesList) {
			for(IEntity entityChecked:this.entitiesList) {
				if(entity!=entityChecked && 
						entity.getYPosition()==entityChecked.getYPosition()&&
						entity.getXPosition()==entityChecked.getXPosition()) {
					StringBuilder errorMessage = new StringBuilder("Two entities are on the same place at x : ");
					errorMessage.append(entityChecked.getXPosition());
					errorMessage.append(" y : ");
					errorMessage.append(entityChecked.getYPosition());
					throw new RuntimeException(errorMessage.toString());
				}
			}
		}
	}
	public void checkForEntitiesOutOfMapBonds() throws RuntimeException{
		for(IEntity entity:this.entitiesList) {
			if(entity.getXPosition()>=this.mapLimits.get("XSize")
					|| entity.getXPosition()<0) {
				StringBuilder errorMessage = new StringBuilder("An entity is out of the map bonds, entity is at x : ");
				errorMessage.append(entity.getXPosition());
				errorMessage.append(" and max size of the map on x axis is : ");
				errorMessage.append(this.mapLimits.get("XSize"));
				throw new RuntimeException(errorMessage.toString());
			}
			if(entity.getYPosition()>=this.mapLimits.get("YSize")
					|| entity.getYPosition()<0) {
				StringBuilder errorMessage = new StringBuilder("An entity is out of the map bonds, entity is at y : ");
				errorMessage.append(entity.getYPosition());
				errorMessage.append(" and max size of the map on y axis is : ");
				errorMessage.append(this.mapLimits.get("YSize"));
				throw new RuntimeException(errorMessage.toString());
			}
		}
	}
	
	//Getters and setters
	@Override
	public IConfigLoader getConfigLoader() {
		return configLoader;
	}

	@Override
	public void setConfigLoader(IConfigLoader configLoader) {
		this.configLoader = configLoader;
	}

	@Override
	public IStackableFactory getTreasurePlaceFactory() {
		return treasurePlaceFactory;
	}

	@Override
	public void setTreasurePlaceFactory(IStackableFactory treasurePlaceFactory) {
		this.treasurePlaceFactory = treasurePlaceFactory;
	}

	@Override
	public IMovableFactory getAdventurerFactory() {
		return adventurerFactory;
	}

	@Override
	public void setAdventurerFactory(IMovableFactory adventurerFactory) {
		this.adventurerFactory = adventurerFactory;
	}

	@Override
	public IEntityFactory getMountainFactory() {
		return mountainFactory;
	}

	@Override
	public void setMountainFactory(IEntityFactory mountainFactory) {
		this.mountainFactory = mountainFactory;
	}

	@Override
	public List<String> getMapConfig() {
		return mapConfig;
	}

	@Override
	public void setMapConfig(List<String> mapConfig) {
		this.mapConfig = mapConfig;
	}

	@Override
	public void setEntitiesList(List<IEntity> entitiesList) {
		this.entitiesList = entitiesList;
	}
	
	@Override
	public void getMapLimits(Map<String,Integer> mapLimits) {
		this.mapLimits = mapLimits;
	}
	
	
	
}
