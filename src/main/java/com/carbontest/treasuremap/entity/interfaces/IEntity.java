package com.carbontest.treasuremap.entity.interfaces;

import com.carbontest.treasuremap.entity.Position;

public interface IEntity {

	public int getXPosition();
	public void setXPosition(int xPosition);
	
	public int getYPosition();
	public void setYPosition(int yPosition);
	
	public Position getPosition();
	

}
