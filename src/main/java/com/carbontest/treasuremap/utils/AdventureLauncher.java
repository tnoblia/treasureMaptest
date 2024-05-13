package com.carbontest.treasuremap.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.carbontest.treasuremap.entity.interfaces.IEntity;
import com.carbontest.treasuremap.entity.interfaces.IMovable;
import com.carbontest.treasuremap.entity.interfaces.IStackable;
import com.carbontest.treasuremap.enums.AdventurerMove;
import com.carbontest.treasuremap.enums.EntityType;
import com.carbontest.treasuremap.utils.interfaces.IAdventureLauncher;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AdventureLauncher implements IAdventureLauncher {

	List<IEntity> entitiesList;
	
	int mapXSize;
	int mapYSize;
	
	@Value("${spring.map.write.file}")
    private String filePath;
	
	public AdventureLauncher() {
	}
	
	@Override
	public List<IMovable> retrieveMovablesFromEntitiesList(){
		List<IMovable> movablesRetrieved = new ArrayList<>();
		for (IEntity entity : this.getEntitiesList()) {
			if(entity instanceof IMovable) {
				movablesRetrieved.add((IMovable)entity);
			}
		}
		return movablesRetrieved;
	}
	
	@Override
	public List<IStackable> retrieveStackablesFromEntitiesList(){
		List<IStackable> stackablesRetrieved = new ArrayList<>();
		for (IEntity entity : this.getEntitiesList()) {
			if(entity instanceof IStackable) {
				stackablesRetrieved.add((IStackable)entity);
			}
		}
		return stackablesRetrieved;
	}
	
	@Override
	public List<IEntity> retrieveUnstackablesFromEntitiesList(){
		List<IEntity> unstackablesRetrieved = new ArrayList<>();
		for (IEntity entity : this.getEntitiesList()) {
			if(!(entity instanceof IStackable)) {
				unstackablesRetrieved.add((IEntity)entity);
			}
		}
		return unstackablesRetrieved;
	}
	
	//if adventurer is on a treasure place, he retrieves a treasure from it.
	@Override
	public void lookForTreasureOnPlace(IMovable adventurer){
		for(IStackable treasurePlace:this.retrieveStackablesFromEntitiesList()) {
			if(treasurePlace.getXPosition()==adventurer.getXPosition() 
			&& treasurePlace.getYPosition()==adventurer.getYPosition()) {
				adventurer.earnTreasure(treasurePlace.retrieveTreasure());
			}
		}
	}
	
	@Override
	public void settleOnPlaceAndLookForTreasureOrMoveBackward(IMovable adventurer){
		boolean adventurerSettles = true;
		//check if adventurer is out of map's bonds
		if(adventurer.getXPosition()>= this.getMapXSize() || adventurer.getXPosition()< 0) {
			adventurerSettles = false;
		}
		if(adventurer.getYPosition()>= this.getMapYSize() || adventurer.getYPosition()< 0) {
			adventurerSettles = false;
		}
		//check if adventurer landed on an unstackable object (ie: mountain or other adventurer)
		for(IEntity unstackableEntity:this.retrieveUnstackablesFromEntitiesList()) {
			if(unstackableEntity.getXPosition()==adventurer.getXPosition() 
			&& unstackableEntity.getYPosition()==adventurer.getYPosition()
			&& unstackableEntity!=adventurer) {
				adventurerSettles = false;
			}
		}
		if (adventurerSettles) {
			this.lookForTreasureOnPlace(adventurer);
		}else {
			adventurer.moveBackward();
		}
	}
	
	
	
	@Override
	public void eachAdventurerMakeOneStep() {
		for (IMovable adventurer:this.retrieveMovablesFromEntitiesList()) {
			if(adventurer.getPattern().size()>0) {
				AdventurerMove step = adventurer.getPattern().remove(0);
				adventurer.actOnPatternStep(step);
				if(step ==AdventurerMove.AVANCE) {
					this.settleOnPlaceAndLookForTreasureOrMoveBackward(adventurer);
				}
			}
		}
	}
	
	/*The for loop is not used to loop through each adventurer but rather
	to get the adventurer with the biggest pattern.
	As long as one of the adventurer has at least one step remaining in the pattern
	the loop keeps going. Steps are withdrawn in eachAdventurerMakeOneStep*/
	@Override
	public void launchAdventures() {
		for (IMovable adventurer:this.retrieveMovablesFromEntitiesList()) {
			while(adventurer.getPattern().size()>0) {
				this.eachAdventurerMakeOneStep();
			}
		}
	}
	
	public String finalMapParamsToString() {
		String SEPARATOR = " - ";
		String BACK_TO_LINE = "\n";
		StringBuilder mapParams = new StringBuilder("");
		mapParams.append(EntityType.CARTE.getType()+SEPARATOR+this.getMapXSize());
		mapParams.append(SEPARATOR+this.getMapYSize()+BACK_TO_LINE);
		StringBuilder movablesParams = new StringBuilder("");
		for(IEntity entity:this.retrieveUnstackablesFromEntitiesList()) {
			if(entity instanceof IMovable) {
				movablesParams.append(EntityType.AVENTURIER.getType());
				movablesParams.append(SEPARATOR+((IMovable)entity).getName());
				movablesParams.append(SEPARATOR+entity.getXPosition()+SEPARATOR+entity.getYPosition());
				movablesParams.append(SEPARATOR+((IMovable)entity).getOrientation().getCardinalPoint());
				movablesParams.append(SEPARATOR+((IMovable)entity).getNumberTreasures()+BACK_TO_LINE);
			}else {
				mapParams.append(EntityType.MONTAGNE.getType());
				mapParams.append(SEPARATOR+entity.getXPosition()+SEPARATOR+entity.getYPosition()+BACK_TO_LINE);
			}
		}
		for(IStackable stackable:this.retrieveStackablesFromEntitiesList()) {
			mapParams.append(EntityType.TRESOR.getType());
			mapParams.append(SEPARATOR+stackable.getXPosition()+SEPARATOR+stackable.getYPosition());
			mapParams.append(SEPARATOR+stackable.getNumberTreasures()+BACK_TO_LINE);
			
		}
		mapParams.append(movablesParams.toString());
		return mapParams.toString();
	}
	
	public void writeFileWithFinalPlan() {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(this.finalMapParamsToString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public List<IEntity> getEntitiesList() {
		return entitiesList;
	}

	@Override
	public void setEntitiesList(List<IEntity> entitiesList) {
		this.entitiesList = entitiesList;
	}

	public int getMapXSize() {
		return mapXSize;
	}

	public void setMapXSize(int mapXSize) {
		this.mapXSize = mapXSize;
	}

	public int getMapYSize() {
		return mapYSize;
	}

	public void setMapYSize(int mapYSize) {
		this.mapYSize = mapYSize;
	}
}
