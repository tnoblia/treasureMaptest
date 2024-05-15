package com.carbontest.treasuremap.entity;

import com.carbontest.treasuremap.entity.interfaces.IEntity;

public class Mountain implements IEntity{
	
	private Position position;
	
	
	public Mountain(int xPosition,int yPosition) {
		this.position = new Position(xPosition,yPosition);
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
