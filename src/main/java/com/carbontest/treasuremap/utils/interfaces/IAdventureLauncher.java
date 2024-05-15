package com.carbontest.treasuremap.utils.interfaces;

import java.util.List;

import com.carbontest.treasuremap.entity.Adventurer;
import com.carbontest.treasuremap.entity.Position;
import com.carbontest.treasuremap.entity.TreasurePlace;
import com.carbontest.treasuremap.entity.interfaces.IEntity;

public interface IAdventureLauncher {

	Position retrieveBordersIntersectionFromEntitiesList();

	List<Adventurer> retrieveAdventurersFromEntitiesList();

	List<IEntity> retrieveUnstackablesFromEntitiesList();

	List<TreasurePlace> retrieveTreasurePlacesFromEntitiesList();

	//if adventurer is on a treasure place, he retrieves a treasure from it.
	void lookForTreasureOnPlace(Adventurer adventurer);

	void settleOnPlaceAndLookForTreasureOrMoveBackward(Adventurer adventurer);

	void eachAdventurerMakeOneStep();

	/*The for loop is not used to loop through each adventurer but rather
	to get the adventurer with the biggest pattern.
	As long as one of the adventurer has at least one step remaining in the pattern
	the loop keeps going. Steps are withdrawn in eachAdventurerMakeOneStep*/
	void launchAdventures();

	List<IEntity> getEntitiesList();

	void setEntitiesList(List<IEntity> entitiesList);

}