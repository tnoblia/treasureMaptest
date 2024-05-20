package com.carbontest.treasuremap.entity;

import com.carbontest.treasuremap.entity.base.Entity;
import com.carbontest.treasuremap.entity.base.IEntity;

public class BordersIntersection extends Entity {

	public BordersIntersection(int xPosition, int yPosition) {
		super(xPosition,yPosition);
	}
	
	public boolean encompassBetweenItselfAndOrigin(IEntity entity) {
		return entity.getXPosition()< this.getXPosition() && entity.getXPosition() >= 0
			&& entity.getYPosition()< this.getYPosition() && entity.getYPosition() >= 0;
	}
}
