package com.carbontest.treasuremap.entity;

import com.carbontest.treasuremap.entity.interfaces.IEntity;

public class Mountain implements IEntity{
	
	private int xPosition;
	private int yPosition;
	
	public Mountain(int xPosition,int yPosition) {
		this.setXPosition(xPosition);
		this.setYPosition(yPosition);
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
	


}
