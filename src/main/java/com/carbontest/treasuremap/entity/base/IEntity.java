package com.carbontest.treasuremap.entity.base;

import com.carbontest.treasuremap.entity.utils.Position;

public interface IEntity {

	public int getXPosition();
	public void setXPosition(int xPosition);
	
	public int getYPosition();
	public void setYPosition(int yPosition);
	
	public Position getPosition();
	

}
