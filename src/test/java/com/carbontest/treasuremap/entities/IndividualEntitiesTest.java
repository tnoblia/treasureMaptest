package com.carbontest.treasuremap.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.carbontest.treasuremap.entity.Adventurer;
import com.carbontest.treasuremap.entity.Mountain;
import com.carbontest.treasuremap.entity.TreasurePlace;
import com.carbontest.treasuremap.enums.AdventurerMove;
import com.carbontest.treasuremap.enums.Orientation;

@SpringBootTest
@ActiveProfiles("test")
public class IndividualEntitiesTest {
	
	
	private Adventurer adventurer;
	
	private Mountain mountain;
	
	private TreasurePlace treasurePlace;

	@BeforeEach
	public void testSetup() {
		 this.adventurer = new Adventurer("Jeannot", 0, 0, "S", "AAAADD");
		 this.mountain = new Mountain(2, 2);
		 this.treasurePlace = new TreasurePlace( 1, 1,2);
		}
	
	@Test
	public void entityInitializationTest() {
		assertEquals("Jeannot",this.adventurer.getName(),"Adventurer's name should be Jeannot");
		assertEquals(0,this.adventurer.getXPosition(),"Adventurer's X position name should be 0");
		assertEquals(0,this.adventurer.getYPosition(),"Adventurer's Y position name should be 0");
		assertEquals(0,this.adventurer.getNumberTreasures(),"Adventurer should have 0 treasure");
		assertEquals(Orientation.SUD,this.adventurer.getOrientation(),"Adventurer's orientation should be towards South");
		assertEquals(Arrays.asList(AdventurerMove.AVANCE,AdventurerMove.AVANCE,AdventurerMove.AVANCE,
				AdventurerMove.AVANCE,AdventurerMove.DROITE,AdventurerMove.DROITE),this.adventurer.getPattern(),
				"Adventurer's pattern should be a list of each step");
		
		assertEquals(2,this.mountain.getXPosition(),"mountain's X position name should be 2");
		assertEquals(2,this.mountain.getYPosition(),"mountain's Y position name should be 2");
		
		assertEquals(1,this.treasurePlace.getXPosition(),"treasurePlace's X position name should be 1");
		assertEquals(1,this.treasurePlace.getYPosition(),"treasurePlace's Y position name should be 1");
		assertEquals(2,this.treasurePlace.getNumberTreasures(),"treasurePlace should shed 2 treasures");
	}
	
	@Test
	public void entityErrorInitializationWithWrongArgumentsTest() {
		assertThrows(IllegalArgumentException.class,()->{
			new Adventurer("Jeannot", 0, 0, "Q", "AAAADD");
			},"Expect an illegal argument exception when setting adventurer with wrong orientation ");
		assertThrows(IllegalArgumentException.class,()->{
			new Adventurer("Jeannot", 0, 0, "S", "AAQADD");
			},"Expect an illegal argument exception when setting adventurer with wrong pattern step Q");
	}
	
	@Test
	public void basicMovementsAdventurerTest() {
		 this.adventurer.turnLeft();
		 assertEquals(Orientation.EST,this.adventurer.getOrientation(),"Adventurer should face East");
		 this.adventurer.moveForward();
		 assertEquals(1,this.adventurer.getXPosition(),"Adventurer should be on position 1 along the x axis");
		 assertEquals(0,this.adventurer.getYPosition(),"Adventurer should be on position 0 along the y axis");
		 this.adventurer.moveBackward();
		 assertEquals(0,this.adventurer.getXPosition(),"Adventurer should be back at his place");
		 assertEquals(0,this.adventurer.getYPosition(),"Adventurer should be back at his place");
		 
		 
		 this.adventurer.turnLeft();
		 assertEquals(Orientation.NORD,this.adventurer.getOrientation(),"Adventurer should face North");
		 this.adventurer.moveForward();
		 assertEquals(0,this.adventurer.getXPosition(),"Adventurer should be on position 0 along the x axis");
		 assertEquals(-1,this.adventurer.getYPosition(),"Adventurer should be on position -1 along the y axis");
		 this.adventurer.moveBackward();
		 assertEquals(0,this.adventurer.getXPosition(),"Adventurer should be back at his place");
		 assertEquals(0,this.adventurer.getYPosition(),"Adventurer should be back at his place");
		 
		 
		 this.adventurer.turnLeft();
		 assertEquals(Orientation.OUEST,this.adventurer.getOrientation(),"Adventurer should face West");
		 this.adventurer.moveForward();
		 assertEquals(-1,this.adventurer.getXPosition(),"Adventurer should be on position -1 along the x axis");
		 assertEquals(0,this.adventurer.getYPosition(),"Adventurer should be on position 0 along the y axis");
		 this.adventurer.moveBackward();
		 assertEquals(0,this.adventurer.getXPosition(),"Adventurer should be back at his place");
		 assertEquals(0,this.adventurer.getYPosition(),"Adventurer should be back at his place");
		 
		 this.adventurer.turnLeft();
		 assertEquals(Orientation.SUD,this.adventurer.getOrientation(),"Adventurer should face South");
		 this.adventurer.moveForward();
		 assertEquals(0,this.adventurer.getXPosition(),"Adventurer should be on position 0 along the x axis");
		 assertEquals(1,this.adventurer.getYPosition(),"Adventurer should be on position 1 along the y axis");
		 this.adventurer.moveBackward();
		 assertEquals(0,this.adventurer.getXPosition(),"Adventurer should be back at his place");
		 assertEquals(0,this.adventurer.getYPosition(),"Adventurer should be back at his place");
		 
		 this.adventurer.turnRight();
		 assertEquals(Orientation.OUEST,this.adventurer.getOrientation(),"Adventurer should face West");
		 this.adventurer.turnRight();
		 assertEquals(Orientation.NORD,this.adventurer.getOrientation(),"Adventurer should face North");
		 this.adventurer.turnRight();
		 assertEquals(Orientation.EST,this.adventurer.getOrientation(),"Adventurer should face East");
		 this.adventurer.turnRight();
		 assertEquals(Orientation.SUD,this.adventurer.getOrientation(),"Adventurer should face South");
		}
	
	@Test
	public void retrievingTreasuresTest() {
		this.adventurer.earnTreasure(this.treasurePlace.retrieveTreasure());
		assertEquals(1,this.treasurePlace.getNumberTreasures(),"treasurePlace should shed 1 treasure");
		assertEquals(1,this.adventurer.getNumberTreasures(),"adventurer should shed 1 treasure");
		
		this.adventurer.earnTreasure(this.treasurePlace.retrieveTreasure());
		assertEquals(0,this.treasurePlace.getNumberTreasures(),"treasurePlace should shed 0 treasure");
		assertEquals(2,this.adventurer.getNumberTreasures(),"adventurer should shed 2 treasure");
		
		this.adventurer.earnTreasure(this.treasurePlace.retrieveTreasure());
		assertEquals(0,this.treasurePlace.getNumberTreasures(),"treasurePlace should still shed 0 treasure");
		assertEquals(2,this.adventurer.getNumberTreasures(),"adventurer should still shed 2 treasures");
	}
}
