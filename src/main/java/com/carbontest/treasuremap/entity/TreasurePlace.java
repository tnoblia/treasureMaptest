package com.carbontest.treasuremap.entity;

import com.carbontest.treasuremap.entity.interfaces.IEntity;

public class TreasurePlace  implements IEntity{

	private Position position;
	private int numberTreasures;
	
	
	public TreasurePlace(int xPosition, int yPosition, int numberTreasures) {
		this.position = new Position(xPosition,yPosition);
		this.numberTreasures = numberTreasures;
	}
	
	public int retrieveTreasure() {
		if(this.getNumberTreasures()>0) {
			this.numberTreasures--;
			return 1;
		}
		return 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || this.getClass()!= obj.getClass()) {
			return false;
		}
		TreasurePlace comparedTreasure = (TreasurePlace) obj;
		return comparedTreasure.getPosition().equals(this.getPosition())
				&& comparedTreasure.getNumberTreasures() == this.getNumberTreasures() ;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		String SEPARATOR = " - ";
		sb.append("Treasure place : ");
		sb.append(this.getPosition());
		sb.append(SEPARATOR);
		sb.append(this.getNumberTreasures());
		return sb.toString();
		
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
	
	public int getNumberTreasures() {
		return numberTreasures;
	}
	
	public Position getPosition() {
		return this.position;
	}
	

	

	
	
}
