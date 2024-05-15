package com.carbontest.treasuremap.entity;

import com.carbontest.treasuremap.entity.interfaces.IEntity;

public class Position implements IEntity{
	
	private int xPosition;
	private int yPosition;
	
	public Position(int xPosition,int yPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || this.getClass()!= obj.getClass()) {
			return false;
		}
		Position objectPosition = (Position) obj;
		return xPosition == objectPosition.xPosition && yPosition == objectPosition.yPosition;
	}
	
	public int getXPosition() {
		return xPosition;
	}
	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	public int getYPosition() {
		return yPosition;
	}
	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	
	public Position getPosition() {
		return this;
	}

}
