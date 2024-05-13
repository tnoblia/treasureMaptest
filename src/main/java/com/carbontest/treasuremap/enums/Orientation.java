package com.carbontest.treasuremap.enums;

public enum Orientation {
	
	OUEST('O'),
	EST('E'),
	SUD('S'),
	NORD('N');
	
	private final char cardinalPoint;
	
	Orientation(char cardinalPoint) {
	    this.cardinalPoint = cardinalPoint;
	}
	
	// Static method to convert a character to an Orientation
    public static Orientation fromLetter(String c) {
        for (Orientation orientation : Orientation.values()) {
            if (orientation.getCardinalPoint() == c.charAt(0)) {
                return orientation;
            }
        }
        throw new IllegalArgumentException("No orientation with cardinal point " + c);
    }
	 
	// Getter to retrieve the orientation
	public char getCardinalPoint() {
	    return cardinalPoint;
	}
}
