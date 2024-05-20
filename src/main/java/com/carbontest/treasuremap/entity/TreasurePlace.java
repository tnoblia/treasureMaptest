package com.carbontest.treasuremap.entity;

import com.carbontest.treasuremap.entity.base.Entity;

public class TreasurePlace  extends Entity{

	private int numberTreasures;
	
	
	public TreasurePlace(int xPosition, int yPosition, int numberTreasures) {
		super(xPosition,yPosition);
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
	
	public int getNumberTreasures() {
		return numberTreasures;
	}


	

	
	
}
