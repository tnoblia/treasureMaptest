package com.carbontest.treasuremap.entity.factories.interfaces;

import com.carbontest.treasuremap.entity.interfaces.IStackable;

public interface IStackableFactory{

	public IStackable createTreasurePlace(int xPosition, int yPosition, int numberTreasures);
}
