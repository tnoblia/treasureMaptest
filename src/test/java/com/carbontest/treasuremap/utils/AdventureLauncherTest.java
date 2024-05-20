package com.carbontest.treasuremap.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.carbontest.treasuremap.entity.Adventurer;
import com.carbontest.treasuremap.entity.BordersIntersection;
import com.carbontest.treasuremap.entity.Mountain;
import com.carbontest.treasuremap.entity.TreasurePlace;
import com.carbontest.treasuremap.entity.base.IEntity;
import com.carbontest.treasuremap.entity.utils.Position;
import com.carbontest.treasuremap.enums.Orientation;
import com.carbontest.treasuremap.utils.interfaces.IAdventureLauncher;

@SpringBootTest
public class AdventureLauncherTest {
	
	@Autowired
	private IAdventureLauncher adventureLauncher;
	
	private BordersIntersection borderIntersections;
	private Adventurer adventurer;
	private Adventurer adventurer2;
	
	private Mountain mountain;
	
	private TreasurePlace treasurePlace;
	
	@BeforeEach
	public void testSetup() {
		 /*Map looks like this:
		  *   T A -
		  *   - M A 
		  *   - - - 
		  * */
		 List<IEntity> entitiesList = new ArrayList<>();
		 this.borderIntersections = new BordersIntersection( 3,3);
		 this.adventurer = new Adventurer("Jeannot", 1, 0, "S", "AGAG");
		 this.adventurer2 =new Adventurer("Jeannette", 2, 1, "N", "DGA");
		 this.mountain = new Mountain( 1, 1);
		 this.treasurePlace = new TreasurePlace( 0, 0,10);
		 
		 entitiesList.add(borderIntersections);
		 entitiesList.add(adventurer);
		 entitiesList.add(mountain);
		 entitiesList.add(treasurePlace);
		 entitiesList.add(adventurer2);
		 
		 this.adventureLauncher.setEntitiesList(entitiesList);
		}
	 
	 @Test
	 public void retrieveBordersIntersectionFromEntitiesListTest() {
		 BordersIntersection retrievedBordersIntersection = this.adventureLauncher.retrieveBordersIntersectionFromEntitiesList();
		 assertEquals(this.borderIntersections,retrievedBordersIntersection,"Borders intersection should be properly retrieved");
	}
	 @Test
	 public void retrieveAdventurersFromEntitiesListTest() {
		 List<Adventurer> AdventurerRetrieved = this.adventureLauncher.retrieveAdventurersFromEntitiesList();
		 assertEquals(this.adventurer,AdventurerRetrieved.get(0),"Entity retrieved should be an adventurer");
		 assertEquals(2,AdventurerRetrieved.size(),"There should be two adventurers");
	 }
	 
	 @Test
	 public void retrieveTreasurePlacesFromEntitiesListTest() {
		 List<TreasurePlace> treasuresRetrieved = this.adventureLauncher.retrieveTreasurePlacesFromEntitiesList();
		 assertEquals(this.treasurePlace,treasuresRetrieved.get(0),"Entity retrieved should be the treasure place");
		 assertEquals(1,treasuresRetrieved.size(),"Number of treasure entities dont match");
		 }
	 
	 @Test
	 public void retrieveUnstackablesFromEntitiesListTest() {
		 List<IEntity> unstackablesRetrieved = this.adventureLauncher.retrieveUnstackablesFromEntitiesList();
		 assertEquals(4,unstackablesRetrieved.size(),"Number of unstackable entities doesnt match");
		   
	 }
	 
	 	@ParameterizedTest
	    @CsvSource({
	    	"2, 2, 0, 10",
	        "0, 0, 1, 9",
	    })
	 public void LookForTreasureTest(int xPosition,
					int yPosition, int numberTreasuresAdventurer,int numberTreasuresTreasurePlace) {
		/*Map looks like this:
		  *   T - -
		  *   - M A 
		  *   - - - 
		  * */
	 	this.adventurer.setXPosition(xPosition);
	 	this.adventurer.setYPosition(yPosition);
		this.adventureLauncher.lookForTreasureOnPlace(this.adventurer);
		assertEquals(numberTreasuresAdventurer,this.adventurer.getNumberTreasures(),"Adventurer's number of treasures is not right");
		assertEquals(numberTreasuresTreasurePlace,this.treasurePlace.getNumberTreasures(),"Treasure places number of treasures is not right");
	 }
	 
	 	@ParameterizedTest
	    @CsvSource({
	        "S, 1, 0",
	        "S, 2, 0",
	        "N, 1, 0",
	        "O, 0, 1",
	        "S, 2, 2",
	        "E, 2, 2",
	    })
	 public void SettleOnUnsettablePlaceTesting(String beginOrientationStr, int xPosition,int yPosition) {
		 /*Map looks like this:
		  *   T - -
		  *   - M A 
		  *   - - - 
		  * */
		Orientation beginOrientation = Orientation.fromLetter(beginOrientationStr);
		this.adventurer.setOrientation(beginOrientation);
		Position beginPosition = new Position(xPosition,yPosition);
		this.adventurer.setXPosition(xPosition);
		this.adventurer.setYPosition(yPosition);
		this.adventurer.moveForward();
		this.adventureLauncher.settleOnPlaceAndLookForTreasureOrMoveBackward(adventurer);
		assertEquals(beginPosition,this.adventurer.getPosition(),"Adventurer is not on right position");
	 	}
	 	
	 	
	 	@ParameterizedTest
	    @CsvSource({
	        "O, 1, 0, 1",
	        "O, 2, 2, 0",
	    })
	 public void SettleOnSettablePlaceTesting(String beginOrientationStr, int xPosition,
			 								int yPosition, int numberTreasures) {
		 /*Map looks like this:
		  *   T - -
		  *   - M A 
		  *   - - - 
		  * */
		Orientation beginOrientation = Orientation.fromLetter(beginOrientationStr);
		this.adventurer.setOrientation(beginOrientation);
		Position endPosition = new Position(xPosition-1,yPosition);
		this.adventurer.setXPosition(xPosition);
		this.adventurer.setYPosition(yPosition);
		this.adventurer.moveForward();
		this.adventureLauncher.settleOnPlaceAndLookForTreasureOrMoveBackward(adventurer);
		assertEquals(endPosition,this.adventurer.getPosition(),"Adventurer is not on right position");
		assertEquals(numberTreasures,this.adventurer.getNumberTreasures(),"Adventurer doesnt have the right number of treasure");
	 	
	 	}
	 
	 @Test
	 public void eachAdventurerMakeOneStepTest(){
		 /*Map looks like this
		  *   T A -  <- adventurer face south, pattern : AGAG  
		  *   - M A  <- adventurer2 face north, pattern :DGA   
		  *   - - - 
		  * */
		 //3 iterations of each pattern
		 for(int i =0;i<3;i++) {
			 this.adventureLauncher.eachAdventurerMakeOneStep();
		 }
		 assertEquals(new Adventurer("Jeannot",2,0,"E","G"),this.adventurer,"First adventurer is not at the right place");
		 assertEquals(new Adventurer("Jeannette",2,1,"N",""),this.adventurer2,"Second adventurer is not at the right place");
		 }
	 
	 @Test
	 public void testWholeAdventureLaunch(){
		 /*Map looks like this
		  *   T A -  <- adventurer face south, pattern : AGAG    
		  *   - M A  <- adventurer2 face north, pattern :DGA   
		  *   - - - 
		  * */
		 this.adventureLauncher.launchAdventures();
		 assertEquals(new Adventurer("Jeannot",2,0,"N",""),this.adventurer,"First adventurer is not at the right place");
		 assertEquals(new Adventurer("Jeannette",2,1,"N",""),this.adventurer2,"Second adventurer is not at the right place");
		 

	 }
}
