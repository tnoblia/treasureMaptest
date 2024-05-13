package com.carbontest.treasuremap.entity.interfaces;

import java.util.List;

import com.carbontest.treasuremap.enums.AdventurerMove;
import com.carbontest.treasuremap.enums.Orientation;

public interface IMovable extends IEntity{
	
	public void earnTreasure(int NumberOfTreasuresAdded);
	
	public void moveBackward();
	public void moveForward();
	
	public void turnRight();
	public void turnLeft();
	
	public void actOnPatternStep(AdventurerMove patternStep);
	
	public int getNumberTreasures();
	public void setNumberTreasures(int i);
	
	public Orientation getOrientation();
	public void setOrientation(Orientation orientation);
	
	public String getName();
	public void setName(String name);

	public List<AdventurerMove> getPattern();
	public void setPattern(List<AdventurerMove> pattern);
	
	
	
}
