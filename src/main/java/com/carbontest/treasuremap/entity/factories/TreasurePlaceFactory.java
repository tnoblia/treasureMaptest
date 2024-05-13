package com.carbontest.treasuremap.entity.factories;

import org.springframework.stereotype.Component;

import com.carbontest.treasuremap.entity.TreasurePlace;
import com.carbontest.treasuremap.entity.factories.interfaces.IStackableFactory;
import com.carbontest.treasuremap.entity.interfaces.IStackable;

@Component
public class TreasurePlaceFactory implements IStackableFactory{
	
	@Override
    public IStackable createTreasurePlace(int xPosition, int yPosition, int numberTreasures) {
        return new TreasurePlace(xPosition, yPosition, numberTreasures);
    }

}
