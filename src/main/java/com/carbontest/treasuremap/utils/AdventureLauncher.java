package com.carbontest.treasuremap.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.carbontest.treasuremap.entity.Adventurer;
import com.carbontest.treasuremap.entity.Position;
import com.carbontest.treasuremap.entity.TreasurePlace;
import com.carbontest.treasuremap.entity.interfaces.IEntity;
import com.carbontest.treasuremap.enums.AdventurerMove;
import com.carbontest.treasuremap.utils.interfaces.IAdventureLauncher;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AdventureLauncher implements IAdventureLauncher {

	private List<IEntity> entitiesList;
	
	public AdventureLauncher() {
	}
	
	@Override
	public Position retrieveBordersIntersectionFromEntitiesList(){
		Position bordersIntersection = null;
		for (IEntity entity : this.getEntitiesList()) {
			if(entity instanceof Position) {
				bordersIntersection =  ((Position)entity);
			}
		}
		return bordersIntersection;
	}
	
	@Override
	public List<Adventurer> retrieveAdventurersFromEntitiesList(){
		List<Adventurer> adventurersRetrieved = new ArrayList<>();
		for (IEntity entity : this.getEntitiesList()) {
			if(entity instanceof Adventurer) {
				adventurersRetrieved.add((Adventurer)entity);
			}
		}
		return adventurersRetrieved;
	}
	
	@Override
	public List<IEntity> retrieveUnstackablesFromEntitiesList(){
		List<IEntity> entitiesRetrieved = new ArrayList<>();
		for (IEntity entity : this.getEntitiesList()) {
			if(!(entity instanceof TreasurePlace)) {
				entitiesRetrieved.add((IEntity)entity);
			}
		}
		return entitiesRetrieved;
	}
	
	@Override
	public List<TreasurePlace> retrieveTreasurePlacesFromEntitiesList(){
		List<TreasurePlace> treasurePlacesRetrieved = new ArrayList<>();
		for (IEntity entity : this.getEntitiesList()) {
			if(entity instanceof TreasurePlace) {
				treasurePlacesRetrieved.add((TreasurePlace)entity);
			}
		}
		return treasurePlacesRetrieved;
	}
	
	//if adventurer is on a treasure place, he retrieves a treasure from it.
	@Override
	public void lookForTreasureOnPlace(Adventurer adventurer){
		for(TreasurePlace treasurePlace:this.retrieveTreasurePlacesFromEntitiesList()) {
			if(treasurePlace.getPosition().equals(adventurer.getPosition())) {
				adventurer.earnTreasure(treasurePlace.retrieveTreasure());
			}
		}
	}
	
	@Override
	public void settleOnPlaceAndLookForTreasureOrMoveBackward(Adventurer adventurer){
		boolean adventurerSettles = true;
		Position bordersIntersection = this.retrieveBordersIntersectionFromEntitiesList();
		//check if adventurer is out of map's bonds
		if(adventurer.getXPosition()>= bordersIntersection.getXPosition() || adventurer.getXPosition()< 0
			||adventurer.getYPosition()>= bordersIntersection.getYPosition() || adventurer.getYPosition()< 0) {
			adventurerSettles = false;
		}
		//check if adventurer landed on an unstackable object (ie: mountain or other adventurer)
		for(IEntity unstackableEntity:this.retrieveUnstackablesFromEntitiesList()) {
			if(unstackableEntity.getPosition().equals(adventurer.getPosition())
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
		for (Adventurer adventurer:this.retrieveAdventurersFromEntitiesList()) {
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
		for (Adventurer adventurer:this.retrieveAdventurersFromEntitiesList()) {
			while(adventurer.getPattern().size()>0) {
				this.eachAdventurerMakeOneStep();
			}
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
}
