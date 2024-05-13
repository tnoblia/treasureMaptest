package com.carbontest.treasuremap.entity.factories;

import org.springframework.stereotype.Component;

import com.carbontest.treasuremap.entity.Adventurer;
import com.carbontest.treasuremap.entity.factories.interfaces.IMovableFactory;
import com.carbontest.treasuremap.entity.interfaces.IMovable;

@Component
public class AdventurerFactory implements IMovableFactory{
	
	@Override
    public IMovable createAdventurer(String name, int xPosition, int yPosition, String orientation, String pattern) {
        return new Adventurer(name, xPosition, yPosition, orientation, pattern);
    }

}
