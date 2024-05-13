package com.carbontest.treasuremap.entity;

import com.carbontest.treasuremap.entity.interfaces.IMovable;
import com.carbontest.treasuremap.entity.interfaces.IStackable;

public class TreasurePlace  implements IStackable{

	private int xPosition;
	private int yPosition;
	private IMovable adventurer;
	private int numberTreasures;
	
	
	public TreasurePlace(int xPosition, int yPosition, int numberTreasures) {
		super();
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.numberTreasures = numberTreasures;
	}
	
	public int retrieveTreasure() {
		if(this.getNumberTreasures()>0) {
			this.setNumberTreasures(this.getNumberTreasures()-1);;
			return 1;
		}
		return 0;
	}
	
	public int getNumberTreasures() {
		return numberTreasures;
	}
	
	public void setNumberTreasures(int numberTreasures) {
		this.numberTreasures = numberTreasures;
	}
	
	public IMovable getAdventurer() {
		return adventurer;
	}
	
	public void setAdventurer(IMovable adventurer) {
		this.adventurer = adventurer;
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
