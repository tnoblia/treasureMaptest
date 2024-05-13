package com.carbontest.treasuremap.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.carbontest.treasuremap.entity.factories.AdventurerFactory;
import com.carbontest.treasuremap.entity.factories.MountainFactory;
import com.carbontest.treasuremap.entity.factories.TreasurePlaceFactory;
import com.carbontest.treasuremap.entity.interfaces.IEntity;
import com.carbontest.treasuremap.entity.interfaces.IMovable;
import com.carbontest.treasuremap.entity.interfaces.IStackable;
import com.carbontest.treasuremap.enums.Orientation;
import com.carbontest.treasuremap.utils.interfaces.IAdventureLauncher;

@SpringBootTest
@ActiveProfiles("test")
public class AdventureLauncherTest {
	
	@Autowired
	private IAdventureLauncher adventureLauncher;
	
	@Autowired
	private AdventurerFactory adventurerFactory;
	 
	@Autowired
	private MountainFactory mountainFactory;
	
	@Autowired
	private TreasurePlaceFactory treasurePlaceFactory;
	
	private IMovable adventurer;
	private IMovable adventurer2;
	
	private IEntity mountain;
	
	private IStackable treasurePlace;
	
	@BeforeEach
	public void testSetup() {
		 /*Map looks like this:
		  *   T A -
		  *   - M A 
		  *   - - - 
		  * */
		 List<IEntity> entitiesList = new ArrayList<>();
		 this.adventurer = this.adventurerFactory.createAdventurer("Jeannot", 1, 0, "S", "AGAGAGAAAGAAAGAAAGAAAGAAAAAGA");
		 this.adventurer2 = this.adventurerFactory.createAdventurer("Jeannette", 2, 1, "N", "DGAAAAAAAAAAGAAAAAAAADDDAA");
		 this.mountain = this.mountainFactory.createMountain( 1, 1);
		 this.treasurePlace = this.treasurePlaceFactory.createTreasurePlace( 0, 0,10);
		 
		 entitiesList.add(adventurer);
		 entitiesList.add(mountain);
		 entitiesList.add(treasurePlace);
		 entitiesList.add(adventurer2);
		 
		 this.adventureLauncher.setEntitiesList(entitiesList);
		 this.adventureLauncher.setMapXSize(3);
		 this.adventureLauncher.setMapYSize(3);
		}
	 
	 @AfterEach
	public void testCleanUp() {
		 this.adventureLauncher.getEntitiesList().clear();
		}
	 
	 @Test
	 public void retrieveMovablesFromEntitiesListTest() {
		 List<IMovable> AdventurerRetrieved = this.adventureLauncher.retrieveMovablesFromEntitiesList();
		 assertEquals("Jeannot",AdventurerRetrieved.get(0).getName(),"Name of adventurer retrieved should be Jeannot");
		 assertEquals("Jeannette",AdventurerRetrieved.get(1).getName(),"Name of adventurer retrieved should be Jeannette");
		 assertEquals(2,AdventurerRetrieved.size(),"There should be two movable entitiies (adventurer)");
	 }
	 
	 @Test
	 public void retrieveStackablesFromEntitiesListTest() {
		 List<IStackable> TreasuresRetrieved = this.adventureLauncher.retrieveStackablesFromEntitiesList();
		 assertEquals(1,TreasuresRetrieved.size(),"There should be one stackable entity (treasure place)");
		 assertEquals(10,TreasuresRetrieved.get(0).getNumberTreasures(),"First treasure place should shed 10 treasures");
	 }
	 
	 @Test
	 public void retrieveUnstackablesFromEntitiesListTest() {
		 List<IEntity> entitiesRetrieved = this.adventureLauncher.retrieveUnstackablesFromEntitiesList();
		 assertEquals(3,entitiesRetrieved.size(),"There should be three unstackable entities (2 adventurers and one mountain");
		 assertEquals("Jeannot",((IMovable)entitiesRetrieved.get(0)).getName(),"First entity in list should be an adventurer (named Jeannot)");
		 assertEquals("Jeannette",((IMovable)entitiesRetrieved.get(2)).getName(),"Last entity in list should be an adventurer (named Jeannette)");
		 assertEquals(1,entitiesRetrieved.get(1).getXPosition(),"Second entity in list should be the mountain, coordinates (x:0,y:0)");
		 assertEquals(1,entitiesRetrieved.get(1).getYPosition(),"Second entity in list should be the mountain, coordinates (x:0,y:0)");
		  
	 }
	 
	 @Test
	 public void LookForTreasureTest() {
		 //Make the adventurer look for a treasure where there is no treasure place
		 this.adventureLauncher.lookForTreasureOnPlace(this.adventurer);
		 assertEquals(0,this.adventurer.getNumberTreasures(),"Adventurer should have not found any treasure on that place");
		 assertEquals(10,this.treasurePlace.getNumberTreasures(),"treasure place should still have 10 treasures");

		 //Position adventurer on treasure place and verify position
		 this.adventurer.turnRight();
		 this.adventurer.moveForward();
		 assertEquals(0,this.adventurer.getXPosition(),"Adventurer should be placed on the same x axis as treasure place x:0");
		 assertEquals(0,this.adventurer.getYPosition(),"Adventurer should be placed on the same y axis as treasure place y:0");

		 //launch the search and verify that a treasure has been retrieved
		 this.adventureLauncher.lookForTreasureOnPlace(this.adventurer);
		 assertEquals(1,this.adventurer.getNumberTreasures(),"Adventurer should now have one treasure");
		 assertEquals(9,this.treasurePlace.getNumberTreasures(),"treasure place should now have 9 treasures");

	 }
	 
	 @Test
	 public void SettleOnPlaceTesting() {
		 //Make adventurer step on the mountain
		 this.adventurer.moveForward();
		 assertEquals(1,this.adventurer.getYPosition(),"Adventurer should be on mountain (y:1)");
		 assertEquals(1,this.adventurer.getXPosition(),"Adventurer should be on mountain (x:1)");
		 this.adventureLauncher.settleOnPlaceAndLookForTreasureOrMoveBackward(adventurer);
		 assertEquals(0,this.adventurer.getYPosition(),"Adventurer should be back at its original place (y:0)");
		 assertEquals(1,this.adventurer.getXPosition(),"Adventurer should be back at its original place (x:1)");
		 
		 //Orient the adventurer to the north facing map border
		 this.adventurer.turnRight();
		 this.adventurer.turnRight();
		 assertEquals(Orientation.NORD,this.adventurer.getOrientation(),"Adventurer should face North");
		 assertEquals(0,this.adventurer.getYPosition(),"Adventurer should be on north border (y:0)");
		 
		 //Make adventurer step out of north map border 
		 this.adventurer.moveForward();
		 assertEquals(-1,this.adventurer.getYPosition(),"Adventurer should have crossed the North border (y:-1)");
		 this.adventureLauncher.settleOnPlaceAndLookForTreasureOrMoveBackward(adventurer);
		 assertEquals(0,this.adventurer.getYPosition(),"Adventurer should be back on the north border (y:0)");
		 
		//Make adventurer go on treasure place and settle on it
		 this.adventurer.turnLeft();
		 this.adventurer.moveForward();
		 assertEquals(0,this.adventurer.getYPosition(),"Adventurer should be on treasure place (y:0)");
		 assertEquals(0,this.adventurer.getXPosition(),"Adventurer should be on treasure place (x:0)");
		 this.adventureLauncher.settleOnPlaceAndLookForTreasureOrMoveBackward(adventurer);
		 assertEquals(1,this.adventurer.getNumberTreasures(),"Adventurer should now have one treasure after settling on treasure place");
		 assertEquals(9,this.treasurePlace.getNumberTreasures(),"treasure place should now have 9 treasures left");
		 
		//Make adventurer step out of west map border 
		 assertEquals(Orientation.OUEST,this.adventurer.getOrientation(),"Adventurer should face West");
		 this.adventurer.moveForward();
		 assertEquals(-1,this.adventurer.getXPosition(),"Adventurer should have crossed the west border (x:-1)");
		 this.adventureLauncher.settleOnPlaceAndLookForTreasureOrMoveBackward(adventurer);
		 assertEquals(0,this.adventurer.getXPosition(),"Adventurer should be back on the west border (x:0)");
		 
		//Make adventurer go on empty space and settle on it
		 this.adventurer.turnLeft();
		 this.adventurer.moveForward();
		 assertEquals(1,this.adventurer.getYPosition(),"Adventurer should be on empty place (y:1)");
		 assertEquals(0,this.adventurer.getXPosition(),"Adventurer should be on empty place (x:0)");
		 this.adventureLauncher.settleOnPlaceAndLookForTreasureOrMoveBackward(adventurer);
		 assertEquals(1,this.adventurer.getYPosition(),"Adventurer should be on the same spot after settling on empty space (y:1)");
		 assertEquals(0,this.adventurer.getXPosition(),"Adventurer should be on the same spot after settling on empty space (x:0)");
		 
		//Make adventurer face south border
		 this.adventurer.moveForward();
		 assertEquals(Orientation.SUD,this.adventurer.getOrientation(),"Adventurer should face South");
		 assertEquals(2,this.adventurer.getYPosition(),"Adventurer should be on south border (y:2)");
		 this.adventurer.moveForward();
		 assertEquals(3,this.adventurer.getYPosition(),"Adventurer should have crossed the south border (y:3)");
		 this.adventureLauncher.settleOnPlaceAndLookForTreasureOrMoveBackward(adventurer);
		 assertEquals(2,this.adventurer.getYPosition(),"Adventurer should be back on the south border(y:2)");
		 
		//Make adventurer face East border
		 this.adventurer.turnLeft();
		 this.adventurer.moveForward();
		 this.adventurer.moveForward();
		 assertEquals(Orientation.EST,this.adventurer.getOrientation(),"Adventurer should face East");
		 assertEquals(2,this.adventurer.getXPosition(),"Adventurer should be on east border (x:2)");
		 this.adventurer.moveForward();
		 assertEquals(3,this.adventurer.getXPosition(),"Adventurer should have crossed the east border (x:3)");
		 this.adventureLauncher.settleOnPlaceAndLookForTreasureOrMoveBackward(adventurer);
		 assertEquals(2,this.adventurer.getXPosition(),"Adventurer should be back on east border (x:2)");
		 
		//Make adventurer step on other adventurer
		 this.adventurer.turnLeft();
		 this.adventurer.moveForward();
		 assertEquals(1,this.adventurer.getYPosition(),"Adventurer should be on other adventurer (y:1)");
		 assertEquals(2,this.adventurer.getXPosition(),"Adventurer should be on other adventurer (x:2)");
		 this.adventureLauncher.settleOnPlaceAndLookForTreasureOrMoveBackward(adventurer);
		 assertEquals(2,this.adventurer.getYPosition(),"Adventurer should be back at its original place (y:2)");
		 assertEquals(2,this.adventurer.getXPosition(),"Adventurer should be back at its original place (x:2)");
		 
		 //Final verification of number of treasures
		 assertEquals(1,this.adventurer.getNumberTreasures(),"Finally the adventurer should have only one treasure");
		 assertEquals(9,this.treasurePlace.getNumberTreasures(),"Finally the treasure place should have 9 treasures");
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
	 
	 @Test
	 public void MapParamsToString() {
		 assertEquals("C - 3 - 3\n"
		 		+ "M - 1 - 1\n"
		 		+ "T - 0 - 0 - 10\n"
		 		+ "A - Jeannot - 1 - 0 - S - 0\n"
		 		+ "A - Jeannette - 2 - 1 - N - 0\n",
		 		this.adventureLauncher.finalMapParamsToString(),
		 		"Final string is not what is expected");
		 
		 this.adventureLauncher.launchAdventures();
		 
		 assertEquals("C - 3 - 3\n"
		 		+ "M - 1 - 1\n"
		 		+ "T - 0 - 0 - 7\n"
		 		+ "A - Jeannot - 0 - 1 - S - 2\n"
		 		+ "A - Jeannette - 0 - 2 - S - 1\n",
			 		this.adventureLauncher.finalMapParamsToString(),
			 		"Final string is not what is expected");
	 }
}
