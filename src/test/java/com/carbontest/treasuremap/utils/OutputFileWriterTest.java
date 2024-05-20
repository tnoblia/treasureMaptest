package com.carbontest.treasuremap.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.carbontest.treasuremap.entity.Adventurer;
import com.carbontest.treasuremap.entity.BordersIntersection;
import com.carbontest.treasuremap.entity.Mountain;
import com.carbontest.treasuremap.entity.TreasurePlace;
import com.carbontest.treasuremap.entity.base.IEntity;
import com.carbontest.treasuremap.utils.interfaces.IOutputFileWriter;

@SpringBootTest
public class OutputFileWriterTest {
	
	@Autowired
	private IOutputFileWriter outputFileWriter;
	
	@Autowired
	private ResourceLoader resourceLoader;
	 
	 @BeforeEach
	public void testSetup() {
		 
		 List<IEntity> entitiesList = new ArrayList<>();
		 
		 BordersIntersection borderIntersections = new BordersIntersection(3,3);
		 Adventurer adventurer = new Adventurer("Jeannot", 1, 0, "S", "");
		 adventurer.earnTreasure(2);
		 Adventurer adventurer2 =new Adventurer("Jeannette", 2, 1, "N", "");
		 adventurer2.earnTreasure(3);
		 Mountain mountain = new Mountain( 1, 1);
		 TreasurePlace treasurePlace = new TreasurePlace( 0, 0,10);
		 
		 entitiesList.add(borderIntersections);
		 entitiesList.add(adventurer);
		 entitiesList.add(mountain);
		 entitiesList.add(treasurePlace);
		 entitiesList.add(adventurer2);
		 
		 outputFileWriter.setEntitiesList(entitiesList);
	 }
	 
	 @Test
	 public void StringBuildingTest() {
		 String BACK_TO_LINE =  System.lineSeparator();
		 assertEquals("C - 3 - 3" + BACK_TO_LINE
		 		+ "M - 1 - 1" + BACK_TO_LINE
		 		+ "T - 0 - 0 - 10" + BACK_TO_LINE
		 		+ "A - Jeannot - 1 - 0 - S - 2" + BACK_TO_LINE
		 		+ "A - Jeannette - 2 - 1 - N - 3" + BACK_TO_LINE,
		 		outputFileWriter.finalMapParamsToString(),
		 		"String of map is not what expected");
	 }
	 
	 @Test
	 public void writeFileTest() {
		 outputFileWriter.writeFile("output-test.txt");
		 Resource resource = null;
		 try{
	        	resource = resourceLoader.getResource("output-test.txt");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		 assertNotNull(resource,"File is not produced");
		 
	 }
}
