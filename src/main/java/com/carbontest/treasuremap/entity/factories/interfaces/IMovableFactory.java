package com.carbontest.treasuremap.entity.factories.interfaces;

import com.carbontest.treasuremap.entity.interfaces.IMovable;

public interface IMovableFactory {

	public IMovable createAdventurer(String name, int xPosition, int yPosition, String orientation, String pattern);
}
