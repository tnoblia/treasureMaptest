package com.carbontest.treasuremap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carbontest.treasuremap.entity.interfaces.IEntity;
import com.carbontest.treasuremap.utils.interfaces.IAdventureLauncher;
import com.carbontest.treasuremap.utils.interfaces.IConfigLoader;
import com.carbontest.treasuremap.utils.interfaces.IMapBuilder;
import com.carbontest.treasuremap.utils.interfaces.IOutputFileWriter;

@Service
public class TreasureService implements ITreasureService{
	
	private IMapBuilder mapBuilder;
	private IAdventureLauncher adventureLauncher;
	private IConfigLoader configLoader;
	private IOutputFileWriter outputFileWriter;

	public TreasureService(@Autowired IConfigLoader configLoader,
							@Autowired IMapBuilder mapBuilder,
							@Autowired IAdventureLauncher adventureLauncher,
							@Autowired IOutputFileWriter outputFileWriter) {
		this.configLoader = configLoader;
		this.mapBuilder = mapBuilder;
		this.adventureLauncher = adventureLauncher;
		this.outputFileWriter = outputFileWriter;
	}
	
	 public void executeGame() {
		try {
			//Load map parameters from map.cfg
			List<String> mapConfig=this.configLoader.getMapParameters();
			
			//build map and entities.
			this.mapBuilder.setMapConfig(mapConfig);
			List<IEntity> entitiesList = this.mapBuilder.setMountains()
														.setTreasures().setAdventurers()
														.buildMapAndEntities();
			
			//Load the entities in adventureLauncher
			this.adventureLauncher.setEntitiesList(entitiesList);
			
			//Launch the adventures and write final file
			this.adventureLauncher.launchAdventures();
			
			this.outputFileWriter.setEntitiesList(this.adventureLauncher.getEntitiesList());
			this.outputFileWriter.writeFileWithFinalPlan();
			
			
		}catch (Exception e){
			e.printStackTrace();
		}
				
	}
	

}
