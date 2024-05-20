package com.carbontest.treasuremap.enums;

public enum EntityType {
	AVENTURIER('A'),
	MONTAGNE('M'),
	TRESOR('T'),
	CARTE('C');
	
	private final char type;
	
	EntityType(char type) {
	    this.type = type;
	}
	 
	// Getter to retrieve the entity type
	public char getType() {
	    return type;
	}
	
	// Static method to convert a character to an entity type
   public static EntityType fromChar(char c) {
       for (EntityType type : EntityType.values()) {
           if (type.getType() == c) {
               return type;
           }
       }
       throw new IllegalArgumentException("No entity type like " + c);
   }


}
