package com.carbontest.treasuremap.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.carbontest.treasuremap.entity.Adventurer;
import com.carbontest.treasuremap.entity.Mountain;
import com.carbontest.treasuremap.entity.Position;
import com.carbontest.treasuremap.entity.TreasurePlace;
import com.carbontest.treasuremap.entity.interfaces.IEntity;
import com.carbontest.treasuremap.enums.EntityType;
import com.carbontest.treasuremap.utils.interfaces.IMapBuilder;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MapBuilder implements IMapBuilder{
	
	//Parameters used to build entities and mapLimits
	private List<String> mapConfig;
	
	//Built entities that are going used by adventureLauncher
	private List<IEntity> entitiesList;
	
	public MapBuilder() {
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
				throw new IllegalArgumentException("Entities should be symbolized by one of these symbols : [C,M,T,A]. "+ e.getMessage());
			}
		}
		return entitiesParameters;
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
		    	IEntity mountain = new Mountain (xPosition,yPosition);
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
		    	IEntity treasurePlace = new TreasurePlace(xPosition,yPosition,numberTreasures);
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
		    	IEntity adventurer = new Adventurer(name,xPosition,yPosition,orientation,pattern);
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
				throw new IllegalArgumentException("For adventurer, orientation symbols should be in list [N,E,S,O] and move symbols should be in list [A,G,D] "+e.getMessage());
				
			}
			
		}
		return this;
	}
	
	@Override
	public List<IEntity> buildMapAndEntities() {
		String[] chartBondsParameters = this.retrieveEntitiesParameters(EntityType.CARTE).get(0).split("-");
		IEntity bordersIntersection = null;
		try {
			int mapXSize = Integer.parseInt(chartBondsParameters[1]);
			int mapYSize = Integer.parseInt(chartBondsParameters[2]);
			bordersIntersection = new Position(mapXSize,mapYSize);
		}catch(Exception e) {
			if(e instanceof NumberFormatException) {
				throw new NumberFormatException("Coordinates of map borders should be integers, error : "+e.getMessage());
			}
			if(e instanceof ArrayIndexOutOfBoundsException) {
				throw new ArrayIndexOutOfBoundsException("Map entity should have 2 coordinates x and y : "+e.getMessage());
			}
		}
		for(IEntity entity:this.entitiesList) {
			if (entity.getXPosition()<0 && entity.getYPosition()<0
					&& bordersIntersection.getXPosition() <= entity.getXPosition()
					&& bordersIntersection.getYPosition() <= entity.getYPosition()) {
				String errorMessage = "An entity is out of the map bonds, entity is at x : "
						+entity.getXPosition()+ " y : "+entity.getYPosition();
				throw new RuntimeException(errorMessage);
			}
			for(IEntity entityChecked:this.entitiesList) {
				if (entity.getPosition().equals(entityChecked.getPosition())
					&& entity!= entityChecked){
					StringBuilder errorMessage = new StringBuilder("Two entities are on the same place at x : ");
					errorMessage.append(entityChecked.getXPosition());
					errorMessage.append(" y : ");
					errorMessage.append(entityChecked.getYPosition());
					throw new RuntimeException(errorMessage.toString());
				}
			}
		}
		this.entitiesList.add(bordersIntersection);
		return entitiesList;
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
	
	public List<IEntity> getEntitiesList(){
		return this.entitiesList;
	}
	
	
	
	
}
