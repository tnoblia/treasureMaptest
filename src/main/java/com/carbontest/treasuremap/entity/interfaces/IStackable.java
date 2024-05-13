package com.carbontest.treasuremap.entity.interfaces;

public interface IStackable extends IEntity {
	
	public int retrieveTreasure();
	
	public int getNumberTreasures() ;
	public void setNumberTreasures(int numberTreasures) ;
	
	public IMovable getAdventurer();
	public void setAdventurer(IMovable adventurer);
	
}