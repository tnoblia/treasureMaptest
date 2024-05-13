package com.carbontest.treasuremap.enums;

public enum AdventurerMove {
	AVANCE('A'),
	GAUCHE('G'),
	DROITE('D');
	
	
	private final char move;
	
	 AdventurerMove(char move) {
	    this.move = move;
	}
	 
	// Getter to retrieve the adventurer move
	public char getMove() {
	    return move;
	}
	
	// Static method to convert a character to a move
    public static AdventurerMove fromChar(char c) {
        for (AdventurerMove move : AdventurerMove.values()) {
            if (move.getMove() == c) {
                return move;
            }
        }
        throw new IllegalArgumentException("No move like " + c);
    }

}
