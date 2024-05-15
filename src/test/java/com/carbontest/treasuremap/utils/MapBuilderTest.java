package com.carbontest.treasuremap.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.carbontest.treasuremap.entity.Adventurer;
import com.carbontest.treasuremap.entity.TreasurePlace;
import com.carbontest.treasuremap.entity.interfaces.IEntity;
import com.carbontest.treasuremap.enums.Orientation;
import com.carbontest.treasuremap.utils.interfaces.IMapBuilder;

@SpringBootTest
@ActiveProfiles("test")
public class MapBuilderTest {
	 
	 @Autowired
	 private IMapBuilder mapBuilder;
	 
	 @BeforeEach
	public void testSetup() {
		 this.mapBuilder.setMapConfig(Arrays.asList("C-5-4","M-1-1","T-3-2-4","M-2-2","A-Jean-1-2-S-DDADADADAGD","T-3-3-10"));
		}
	 
	 @Test
	 public void buildOfDraftMap() {
		 this.mapBuilder.buildMapAndEntities();
		 assertEquals(4,this.mapBuilder.getEntitiesList().get(0).getYPosition(),"Size of map draft on y axis should be 4");
		 assertEquals(5,this.mapBuilder.getEntitiesList().get(0).getXPosition(),"Size of map draft on x axis should be 5");
	 }
	 
	 @Test
	 public void setMountainsInEntitiesList() {
		 this.mapBuilder.setMountains();
		 assertEquals(1,this.mapBuilder.getEntitiesList().get(0).getXPosition(),"X position of the first mountain should be 1");
		 assertEquals(1,this.mapBuilder.getEntitiesList().get(0).getYPosition(),"Y position of the first mountain should be 1");
		 assertEquals(2,this.mapBuilder.getEntitiesList().get(1).getXPosition(),"X position of the second mountain should be 2");
		 assertEquals(2,this.mapBuilder.getEntitiesList().get(1).getYPosition(),"Y position of the second mountain should be 2");
	 }
	 
	 @Test
	 public void setAdventurerInEntitiesList() {
		 this.mapBuilder.setAdventurers();
		 assertEquals("Jean",((Adventurer) this.mapBuilder.getEntitiesList().get(0)).getName(),"Name of the adventurer should be Jean");
		 assertEquals(1,((Adventurer) this.mapBuilder.getEntitiesList().get(0)).getXPosition(),"X position of the adventurer should be 1");
		 assertEquals(2,((Adventurer) this.mapBuilder.getEntitiesList().get(0)).getYPosition(),"Y position of the adventurer should be 2");
		 assertEquals(Orientation.SUD,((Adventurer) this.mapBuilder.getEntitiesList().get(0)).getOrientation(),"Orientaition of the adventurer should be South");
	 }
	 
	 @Test
	 public void setTreasurePlacesInEntitiesList() {
		 this.mapBuilder.setTreasures();
		 assertEquals(3,((TreasurePlace) this.mapBuilder.getEntitiesList().get(0)).getXPosition(),"X position of the first treasure place should be 3");
		 assertEquals(2,((TreasurePlace) this.mapBuilder.getEntitiesList().get(0)).getYPosition(),"Y position of the first treasure place should be 2");
		 assertEquals(4,((TreasurePlace) this.mapBuilder.getEntitiesList().get(0)).getNumberTreasures(),"Number of treasures in first treasure place should be 4");
		 assertEquals(10,((TreasurePlace) this.mapBuilder.getEntitiesList().get(1)).getNumberTreasures(),"Number of treasures in second treasure place should be 10");
	 }
	 
	 @Test
	 public void setUnknownTypeEntity() {
		 this.mapBuilder.setMapConfig(Arrays.asList("C-5-4","X-2-2","T-1-1-4"));
		 Throwable thrown = assertThrows(IllegalArgumentException.class,()->{
				this.mapBuilder.setMountains();
				},"Expect an illegal argument exception when reviewing X entity ");
		 assertEquals("Entities should be symbolized by one of these symbols : [C,M,T,A]. No entity type like X"
				 ,thrown.getMessage(),"ArrayIndexOutOfBoundsException message for adventurer should be personalized");

	 }
	 
	 @Test
	 public void setIncompleteTypeEntity() {
		 //set incomplete adventurer : 
		 this.mapBuilder.setMapConfig(Arrays.asList("C-5-4","A-Jean-2-2","T-1-1-4"));
		 Throwable thrownA = assertThrows(ArrayIndexOutOfBoundsException.class,()->{
				this.mapBuilder.setAdventurers();
				},"Expect an ArrayIndexOutOfBounds exception when reviewing incomplete A entity ");
		 assertEquals("Adventurers entity should have 5 parameters: name, x, y, orientation and pattern : Index 4 out of bounds for length 4"
				 ,thrownA.getMessage(),"ArrayIndexOutOfBoundsException message for adventurer should be personalized");

		//set incomplete map : 
		 this.mapBuilder.setMapConfig(Arrays.asList("C-5","T-1-1-4"));
		 Throwable thrownC = assertThrows(ArrayIndexOutOfBoundsException.class,()->{
				this.mapBuilder.buildMapAndEntities();
				},"Expect an ArrayIndexOutOfBounds exception when reviewing incomplete C entity ");
		 assertEquals("Map entity should have 2 coordinates x and y : Index 2 out of bounds for length 2"
				 ,thrownC.getMessage(),"ArrayIndexOutOfBoundsException message for map limits should be personalized");

		//set incomplete treasure : 
		 this.mapBuilder.setMapConfig(Arrays.asList("C-5-3","T-1-1"));
		 Throwable thrownT = assertThrows(ArrayIndexOutOfBoundsException.class,()->{
				this.mapBuilder.setTreasures();
				},"Expect an ArrayIndexOutOfBounds exception when reviewing incomplete T entity ");
		 assertEquals("Treasure entity should have 3 coordinates x, y and number of treasures: Index 3 out of bounds for length 3"
				 ,thrownT.getMessage(),"ArrayIndexOutOfBoundsException message for treasure places should be personalized");

		//set incomplete mountain : 
		 this.mapBuilder.setMapConfig(Arrays.asList("C-5-3","M-1"));
		 Throwable thrownM = assertThrows(ArrayIndexOutOfBoundsException.class,()->{
				this.mapBuilder.setMountains();
				},"Expect an ArrayIndexOutOfBounds exception when reviewing incomplete M entity ");
		 assertEquals("Mountain entity should have 2 coordinates x and y : Index 2 out of bounds for length 2"
				 ,thrownM.getMessage(),"ArrayIndexOutOfBoundsException message for mountains should be personalized");
	 }
	 
	 @Test
	 public void setEntityWithWrongParametersType() {
		//set wrong type of parameter for adventurer : 
		 this.mapBuilder.setMapConfig(Arrays.asList("C-4-4","A-Jean-X-2-S-AAA","T-1-1-4"));
		 Throwable thrownA = assertThrows(NumberFormatException.class,()->{
				this.mapBuilder.setAdventurers();
				},"Expect an NumberFormatException exception when entering string instead of int for A entity ");
		 assertEquals("Parameters of adventurers should be integers for x and y. error : For input string: \"X\""
				 ,thrownA.getMessage(),"NumberFormatException message for adventurer should be personalized");

		//set wrong type of parameter for map : 
		 this.mapBuilder.setMapConfig(Arrays.asList("C-5-Y","T-1-1-4"));
		 Throwable thrownC = assertThrows(NumberFormatException.class,()->{
				this.mapBuilder.buildMapAndEntities();
				},"Expect an NumberFormatException exception when entering string instead of int for C entity");
		 assertEquals("Coordinates of map borders should be integers, error : For input string: \"Y\""
				 ,thrownC.getMessage(),"NumberFormatException message for map limits should be personalized");

		//set wrong type of parameter for treasure : 
		 this.mapBuilder.setMapConfig(Arrays.asList("C-5-3","T-1-1-Z"));
		 Throwable thrownT = assertThrows(NumberFormatException.class,()->{
				this.mapBuilder.setTreasures();
				},"Expect an NumberFormatException exception when entering string instead of int for T entity");
		 assertEquals("Parameters of treasure entities should be integers, error : For input string: \"Z\""
				 ,thrownT.getMessage(),"NumberFormatException message for treasure places should be personalized");

		//set wrong type of parameter for mountain : 
		 this.mapBuilder.setMapConfig(Arrays.asList("C-5-3","M-1-V"));
		 Throwable thrownM = assertThrows(NumberFormatException.class,()->{
				this.mapBuilder.setMountains();
				},"Expect an NumberFormatException exception when entering string instead of int for M entity");
		 assertEquals("Coordinates of mountain entities should be integers, error : For input string: \"V\""
				 ,thrownM.getMessage(),"NumberFormatException message for mountains should be personalized");
	 }
	 
	 @Test
	 public void setAdventurerWithWrongPatternOrOrientation() {
		//set wrong orientation for adventurer : 
		 this.mapBuilder.setMapConfig(Arrays.asList("C-4-4","A-Jean-2-2-Q-AAA","T-1-1-4"));
		 Throwable thrown1 = assertThrows(IllegalArgumentException.class,()->{
				this.mapBuilder.setAdventurers();
				},"Expect an IllegalArgumentException exception when entering wrong orientation for A entity ");
		 assertEquals("For adventurer, orientation symbols should be in list [N,E,S,O] and move symbols should be in list [A,G,D] No orientation with cardinal point Q"
				 ,thrown1.getMessage(),"NumberFormatException message for adventurer should be personalized");

		//set wrong pattern for adventurer : 
		 this.mapBuilder.setMapConfig(Arrays.asList("C-4-4","A-Jean-2-2-S-QAA","T-1-1-4"));
		 Throwable thrown2 = assertThrows(IllegalArgumentException.class,()->{
				this.mapBuilder.setAdventurers();
				},"Expect an IllegalArgumentException exception when entering wrong pattern for A entity ");
		 assertEquals("For adventurer, orientation symbols should be in list [N,E,S,O] and move symbols should be in list [A,G,D] No move like Q"
				 ,thrown2.getMessage(),"NumberFormatException message for adventurer should be personalized");

	 }
	
	 
	
	 
}
	 


