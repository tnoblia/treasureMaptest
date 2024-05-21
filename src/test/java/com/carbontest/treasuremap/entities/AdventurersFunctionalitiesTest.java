package com.carbontest.treasuremap.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import com.carbontest.treasuremap.entity.Adventurer;
import com.carbontest.treasuremap.entity.TreasurePlace;
import com.carbontest.treasuremap.entity.utils.Position;
import com.carbontest.treasuremap.enums.Orientation;

@SpringBootTest
public class AdventurersFunctionalitiesTest {
	
	
	private Adventurer adventurer;
	
	private TreasurePlace treasurePlace;

	@BeforeEach
	public void testSetup() {
		 this.adventurer = new Adventurer("Jeannot", 0, 0, "S", "AAAADD");
		 this.treasurePlace = new TreasurePlace( 1, 1,1);
		}
	

	@ParameterizedTest
    @CsvSource({
        "S, O",
        "O, N",
        "N, E",
        "E, S"
    })
	public void turnAdventurerTests(String beginOrientationStr,String endOrientationStr) {
		Orientation beginOrientation = Orientation.fromLetter(beginOrientationStr);
		Orientation endOrientation = Orientation.fromLetter(endOrientationStr);
		this.adventurer.setOrientation(beginOrientation);
		this.adventurer.turnRight();
		assertEquals(endOrientation,this.adventurer.getOrientation(),"Adventurer's orientation is not right");
		this.adventurer.turnLeft();
		assertEquals(beginOrientation,this.adventurer.getOrientation(),"Adventurer's orientation is not right");
		
	}
	
	@ParameterizedTest
    @CsvSource({
        "S, 0,1",
        "O, -1,0",
        "N, 0,-1",
        "E, 1,0"
    })
	public void forwardAndBackwardMovementAdventurer(String beginOrientationStr,int endXPosition,
														int endYPosition) {
		Orientation beginOrientation = Orientation.fromLetter(beginOrientationStr);
		this.adventurer.setOrientation(beginOrientation);
		this.adventurer.moveForward();
		Position beginPosition = new Position(0,0);
		Position endPosition = new Position(endXPosition,endYPosition);
		assertEquals(endPosition,this.adventurer.getPosition(),"Adventurer's position is not right");
		this.adventurer.moveBackward();
		assertEquals(beginPosition,this.adventurer.getPosition(),"Adventurer's position is not right");
	}
	
	@Test
	public void retrievingTreasuresTest() {
		this.adventurer.earnTreasure(this.treasurePlace.retrieveTreasure());
		assertEquals(0,this.treasurePlace.getNumberTreasures(),"treasurePlace should shed 1 treasure");
		assertEquals(1,this.adventurer.getNumberTreasures(),"adventurer should shed 1 treasure");
		
		this.adventurer.earnTreasure(this.treasurePlace.retrieveTreasure());
		assertEquals(0,this.treasurePlace.getNumberTreasures(),"treasurePlace should still shed 0 treasure");
		assertEquals(1,this.adventurer.getNumberTreasures(),"adventurer should still shed 1 treasure");
	}
}
