package com.carbontest.treasuremap.entity;

import java.util.ArrayList;
import java.util.List;

import com.carbontest.treasuremap.entity.interfaces.IEntity;
import com.carbontest.treasuremap.enums.AdventurerMove;
import com.carbontest.treasuremap.enums.Orientation;

public class Adventurer implements IEntity{
	
	private Position position;
	private Orientation orientation;
	private int numberTreasures;
	private String name;
	private List<AdventurerMove> pattern;
	
	public Adventurer(String name,int xPosition, int yPosition, String orientation,String pattern) {
		this.position = new Position(xPosition,yPosition);
		this.orientation = Orientation.fromLetter(orientation);
		this.name = name;
		this.pattern = new ArrayList<>();
		for(int i=0; i<pattern.length();i++) {
			this.pattern.add(AdventurerMove.fromChar(pattern.charAt(i)));
		}
		
	}
	
	public void earnTreasure(int NumberOfTreasuresAdded) {
		this.setNumberTreasures(this.getNumberTreasures()+NumberOfTreasuresAdded);
	}
	
	public void actOnPatternStep(AdventurerMove patternStep) {
		switch(patternStep) {
			case AVANCE:
				this.moveForward();
				break;
			case GAUCHE:
				this.turnLeft();
				break;
			case DROITE:
				this.turnRight();
				break;
		}
	}
	
	public void moveForward() {
		switch(this.getOrientation()) {
			case OUEST:
				this.setXPosition(this.getXPosition()-1);
				break;
			case EST:
				this.setXPosition(this.getXPosition()+1);
				break;
			case NORD:
				this.setYPosition(this.getYPosition()-1);
				break;
			case SUD:
				this.setYPosition(this.getYPosition()+1);
				break;
		}
	}
	
	public void moveBackward() {
		switch(this.getOrientation()) {
			case OUEST:
				this.setXPosition(this.getXPosition()+1);
				break;
			case EST:
				this.setXPosition(this.getXPosition()-1);
				break;
			case NORD:
				this.setYPosition(this.getYPosition()+1);
				break;
			case SUD:
				this.setYPosition(this.getYPosition()-1);
				break;
		}
	}
	
	public void turnRight() {
		switch(this.getOrientation()) {
			case OUEST:
				this.setOrientation(Orientation.NORD);
				break;
			case EST:
				this.setOrientation(Orientation.SUD);
				break;
			case NORD:
				this.setOrientation(Orientation.EST);
				break;
			case SUD:
				this.setOrientation(Orientation.OUEST);
				break;
		}
	}
	
	public void turnLeft() {
		switch(this.getOrientation()) {
			case OUEST:
				this.setOrientation(Orientation.SUD);
				break;
			case EST:
				this.setOrientation(Orientation.NORD);
				break;
			case NORD:
				this.setOrientation(Orientation.OUEST);
				break;
			case SUD:
				this.setOrientation(Orientation.EST);
				break;
		}
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
	
	
	
	public int getNumberTreasures() {
		return numberTreasures;
	}

	public void setNumberTreasures(int numberTreasures) {
		this.numberTreasures = numberTreasures;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AdventurerMove> getPattern() {
		return this.pattern;
	}

	public void setPattern(List<AdventurerMove> pattern) {
		this.pattern = pattern;
	}

}
