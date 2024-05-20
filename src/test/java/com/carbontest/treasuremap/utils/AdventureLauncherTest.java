package com.carbontest.treasuremap.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.carbontest.treasuremap.entity.Adventurer;
import com.carbontest.treasuremap.entity.Mountain;
import com.carbontest.treasuremap.entity.Position;
import com.carbontest.treasuremap.entity.TreasurePlace;
import com.carbontest.treasuremap.entity.interfaces.IEntity;
import com.carbontest.treasuremap.enums.Orientation;
import com.carbontest.treasuremap.utils.interfaces.IAdventureLauncher;

@SpringBootTest
@ActiveProfiles("test")
public class AdventureLauncherTest {
	
	@Autowired
	private IAdventureLauncher adventureLauncher;
	
	private Position borderIntersections;
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
		 this.borderIntersections = new Position( 3,3);
		 this.adventurer = new Adventurer("Jeannot", 1, 0, "S", "AGAGAGAAAGAAAGAAAGAAAGAAAAAGA");
		 this.adventurer2 =new Adventurer("Jeannette", 2, 1, "N", "DGAAAAAAAAAAGAAAAAAAADDDAA");
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
		 Position retrievedBordersIntersection = this.adventureLauncher.retrieveBordersIntersectionFromEntitiesList();
		 assertEquals(this.borderIntersections,retrievedBordersIntersection,"Borders intersection should be properly retrieved");
	}
	 @Test
	 public void retrieveAdventurersFromEntitiesListTest() {
		 List<Adventurer> AdventurerRetrieved = this.adventureLauncher.retrieveAdventurersFromEntitiesList();
		 assertEquals(this.adventurer,AdventurerRetrieved.get(0),"Name of adventurer retrieved should be Jeannot");
		 assertEquals(2,AdventurerRetrieved.size(),"There should be two movable adventurers");
	 }
	 
	 @Test
	 public void retrieveTreasurePlacesFromEntitiesListTest() {
		 List<TreasurePlace> treasuresRetrieved = this.adventureLauncher.retrieveTreasurePlacesFromEntitiesList();
		 assertEquals(this.treasurePlace,treasuresRetrieved.get(0),"Name of adventurer retrieved should be Jeannot");
		 assertEquals(1,treasuresRetrieved.size(),"There should be two movable adventurers");
		 }
	 
	 @Test
	 public void retrieveUnstackablesFromEntitiesListTest() {
		 List<IEntity> unstackablesRetrieved = this.adventureLauncher.retrieveUnstackablesFromEntitiesList();
		 assertEquals(4,unstackablesRetrieved.size(),"There should be four entities that are unstackable");
		   
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
		assertEquals(numberTreasuresAdventurer,this.adventurer.getNumberTreasures(),"Adventurer's number of treasures should be : "+numberTreasuresAdventurer);
		assertEquals(numberTreasuresTreasurePlace,this.treasurePlace.getNumberTreasures(),"Treasure places number of treasures should be : "+numberTreasuresTreasurePlace);
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
		assertEquals(beginPosition,this.adventurer.getPosition(),"Adventurer should be back on position x : " +xPosition+", y : "+yPosition);
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
		assertEquals(endPosition,this.adventurer.getPosition(),"Adventurer should be on position "+ endPosition.toString() +" but was on "+this.adventurer.getPosition().toString());
		assertEquals(numberTreasures,this.adventurer.getNumberTreasures(),"Adventurer should have : " +numberTreasures);
	 	
	 	}
	 
	 @Test
	 public void eachAdventurerMakeOneStepTest(){
		 /*Map looks like this
		  *   T A -  <- adventurer face south, pattern : AGAGAGAAAGAAAGAAAGAAAGAAAAAGA  
		  *   - M A  <- adventurer2 face north, pattern :DGAAAAAAAAAAGAAAAAAAADDDAA   
		  *   - - - 
		  * */
		 //3 iterations of each pattern
		 for(int i =0;i<3;i++) {
			 this.adventureLauncher.eachAdventurerMakeOneStep();
		 }
		 
		 //Pattern for adventurer after 3 iterations :  AGA
		 //Pattern for adventurer2 after 3 iterations : DGA
		 //On third step adventurer and and adventurer2 try to step on the same case (x:2,y:0), 
		 //adventurer should get there first because he is the first adventurer added in the entities.
		 assertEquals(2,this.adventurer.getXPosition(),"First adventurer should be on position x:2");
		 assertEquals(0,this.adventurer.getYPosition(),"First adventurer should be on position y:0");
		 assertEquals(Orientation.EST,this.adventurer.getOrientation(),"First adventurer should be facing east");
		 
		 assertEquals(2,this.adventurer2.getXPosition(),"Second adventurer should be on position x:2");
		 assertEquals(1,this.adventurer2.getYPosition(),"Second adventurer should be on position y:1");
		 assertEquals(Orientation.NORD,this.adventurer2.getOrientation(),"Second adventurer should be facing North");
	 }
	 
	 @Test
	 public void testWholeAdventureLaunch(){
		 /*Map looks like this
		  *   T A -  <- adventurer face south, pattern : AGAGAGAAAGAAAGAAAGAAAGAAAAAGA  
		  *   - M A  <- adventurer2 face north, pattern :DGAAAAAAAAAAGAAAAAAAADDDAA   
		  *   - - - 
		  * */
		 this.adventureLauncher.launchAdventures();
		 assertEquals(0,this.adventurer.getXPosition(),"First adventurer should be on position x:0");
		 assertEquals(1,this.adventurer.getYPosition(),"First adventurer should be on position y:1");
		 assertEquals(Orientation.SUD,this.adventurer.getOrientation(),"First adventurer should be facing east");
		 assertEquals(2,this.adventurer.getNumberTreasures(),"First adventurer should have 2 treasures");
		 
		 assertEquals(0,this.adventurer2.getXPosition(),"Second adventurer should be on position x:0");
		 assertEquals(2,this.adventurer2.getYPosition(),"Second adventurer should be on position y:2");
		 assertEquals(Orientation.SUD,this.adventurer2.getOrientation(),"Second adventurer should be facing North");
		 
		 assertEquals(1,this.adventurer2.getNumberTreasures(),"Second adventurer should have 1 treasure");
		 assertEquals(7,this.treasurePlace.getNumberTreasures(),"Treasure place should have 7 treasures left");

	 }
}
