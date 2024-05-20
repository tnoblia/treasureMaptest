package com.carbontest.treasuremap.utils.interfaces;

import java.util.List;

import com.carbontest.treasuremap.entity.Adventurer;
import com.carbontest.treasuremap.entity.BordersIntersection;
import com.carbontest.treasuremap.entity.TreasurePlace;
import com.carbontest.treasuremap.entity.base.IEntity;

public interface IAdventureLauncher {

	BordersIntersection retrieveBordersIntersectionFromEntitiesList();

	List<Adventurer> retrieveAdventurersFromEntitiesList();

	List<IEntity> retrieveUnstackablesFromEntitiesList();

	List<TreasurePlace> retrieveTreasurePlacesFromEntitiesList();

	//if adventurer is on a treasure place, he retrieves a treasure from it.
	void lookForTreasureOnPlace(Adventurer adventurer);

	void settleOnPlaceAndLookForTreasureOrMoveBackward(Adventurer adventurer);

	void eachAdventurerMakeOneStep();

	void launchAdventures();

	List<IEntity> getEntitiesList();

	void setEntitiesList(List<IEntity> entitiesList);

}