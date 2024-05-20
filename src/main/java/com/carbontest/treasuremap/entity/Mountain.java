package com.carbontest.treasuremap.entity;

import com.carbontest.treasuremap.entity.interfaces.IEntity;

public class Mountain implements IEntity{
	
	private Position position;
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || this.getClass()!= obj.getClass()) {
			return false;
		}
		Mountain comparedMountain = (Mountain) obj;
		return comparedMountain.getPosition().equals(this.getPosition());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("Mountain : ");
		sb.append(this.getPosition());
		return sb.toString();
	}
	
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
