package com.carbontest.treasuremap.utils.interfaces;

import java.util.List;

import com.carbontest.treasuremap.entity.interfaces.IEntity;
import com.carbontest.treasuremap.entity.interfaces.IMovable;
import com.carbontest.treasuremap.entity.interfaces.IStackable;

public interface IAdventureLauncher {

	List<IMovable> retrieveMovablesFromEntitiesList();

	List<IStackable> retrieveStackablesFromEntitiesList();

	List<IEntity> retrieveUnstackablesFromEntitiesList();

	//if adventurer is on a treasure place, he retrieves a treasure from it.
	void lookForTreasureOnPlace(IMovable adventurer);

	void settleOnPlaceAndLookForTreasureOrMoveBackward(IMovable adventurer);

	void eachAdventurerMakeOneStep();

	/*The for loop is not used to loop through each adventurer but rather
	to get the adventurer with the biggest pattern.
	As long as one of the adventurer has at least one step remaining in the pattern
	the loop keeps going. Steps are withdrawn in eachAdventurerMakeOneStep*/
	void launchAdventures();
	
	public void writeFileWithFinalPlan();
	
	public String finalMapParamsToString();

	List<IEntity> getEntitiesList();

	void setEntitiesList(List<IEntity> entitiesList);
	
	public int getMapXSize();

	public void setMapXSize(int mapXSize);

	public int getMapYSize();

	public void setMapYSize(int mapYSize);

}