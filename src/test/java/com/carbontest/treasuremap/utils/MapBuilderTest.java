package com.carbontest.treasuremap.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.carbontest.treasuremap.entity.Adventurer;
import com.carbontest.treasuremap.entity.Mountain;
import com.carbontest.treasuremap.entity.TreasurePlace;
import com.carbontest.treasuremap.entity.utils.Position;
import com.carbontest.treasuremap.utils.interfaces.IMapBuilder;

@SpringBootTest
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
		 assertEquals(new Position(5,4),this.mapBuilder.getEntitiesList().get(0).getPosition(),"Position of borders intersection is not what expected");
		 }
	 
	 @Test
	 public void setMountainsInEntitiesList() {
		 this.mapBuilder.setMountains();
		 assertEquals(new Mountain(1,1),this.mapBuilder.getEntitiesList().get(0),"Mountain is not well initialized");
		 assertEquals(new Mountain(2,2),this.mapBuilder.getEntitiesList().get(1),"Mountain is not well initialized");
	 }
	 
	 @Test
	 public void setAdventurerInEntitiesList() {
		 this.mapBuilder.setAdventurers();
		 assertEquals(new Adventurer("Jean",1,2,"S","DDADADADAGD"),((Adventurer) this.mapBuilder.getEntitiesList().get(0)),"Adventurer is not well initialized");
	 }
	 
	 @Test
	 public void setTreasurePlacesInEntitiesList() {
		 this.mapBuilder.setTreasures();
		 assertEquals(new TreasurePlace(3,2,4),(TreasurePlace) this.mapBuilder.getEntitiesList().get(0),"TreasurePlace is not well initialized");
		 assertEquals(new TreasurePlace(3,3,10),(TreasurePlace) this.mapBuilder.getEntitiesList().get(1),"TreasurePlace is not well initialized");
		 }
	 
	 @Test
	 public void setUnknownTypeEntity() {
		 this.mapBuilder.setMapConfig(Arrays.asList("C-5-4","X-2-2","T-1-1-4"));
		 assertThrows(IllegalArgumentException.class,()->{
				this.mapBuilder.setMountains();
				},"Expect an illegal argument exception when reviewing X entity ");
	 }
	 
	 
	 @ParameterizedTest
	 @CsvSource({
	    	"C-5",
	    	"M-1",
	    	"T-1-1",
	    	"A-Jean-2-2",
	    })
	 public void setIncompleteTypeEntity(String parameterString) {
		 //set incomplete adventurer : 
		 this.mapBuilder.setMapConfig(Arrays.asList(parameterString));
		 assertThrows(ArrayIndexOutOfBoundsException.class,()->{
				this.mapBuilder.setMountains().setTreasures().setAdventurers().buildMapAndEntities();
				},"Expect an ArrayIndexOutOfBounds exception when reviewing incomplete entity ");
		}
	 
	 @ParameterizedTest
     @CsvSource({
    	    "C-5-Y",
    	    "M-1-V",
    	    "T-1-1-Z",
	    	"A-Jean-X-2-S-AAA"
     })
	 public void setEntityWithWrongParametersType(String parameterString) {
		//set wrong type of parameter for adventurer : 
		 this.mapBuilder.setMapConfig(Arrays.asList(parameterString));
		 assertThrows(NumberFormatException.class,()->{
				this.mapBuilder.setMountains().setTreasures().setAdventurers().buildMapAndEntities();
				},"Expect an NumberFormatException exception when entering string instead of int for entity initialization ");
		 }
	 
	 @ParameterizedTest
     @CsvSource({
    	 "A-Jean-2-2-Q-AAA",
    	 "A-Jean-2-2-S-QAA",
     })
	 public void setAdventurerWithWrongPatternOrOrientation(String parameterString) {
		//set wrong orientation for adventurer : 
		 this.mapBuilder.setMapConfig(Arrays.asList("C-4-4","A-Jean-2-2-Q-AAA","T-1-1-4"));
		 assertThrows(IllegalArgumentException.class,()->{
				this.mapBuilder.setAdventurers();
				},"Expect an IllegalArgumentException exception when entering wrong orientation or pattern for Adventurer entity ");
	 }
	
	 
	
	 
}
	 


