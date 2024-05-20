package com.carbontest.treasuremap.entity.base;

import com.carbontest.treasuremap.entity.utils.Position;

public abstract class Entity implements IEntity {
	
	private Position position;
	
	public Entity(int x, int y) {
		this.position = new Position(x, y);
	}
	
	public int getXPosition() {
		return position.getXPosition();
	}
	
	public void setXPosition(int xPosition) {
		this.position.setXPosition(xPosition);
	}
	
	public int getYPosition() {
		return position.getYPosition();
	}
	
	public void setYPosition(int yPosition) {
		this.position.setYPosition(yPosition);
	}
	
	public Position getPosition() {
		return this.position;
	}

}
