package com.carbontest.treasuremap.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carbontest.treasuremap.entity.interfaces.IEntity;
import com.carbontest.treasuremap.utils.interfaces.IAdventureLauncher;
import com.carbontest.treasuremap.utils.interfaces.IConfigLoader;
import com.carbontest.treasuremap.utils.interfaces.IMapBuilder;

@Service
public class TreasureService implements ITreasureService{
	
	private IMapBuilder mapBuilder;
	private IAdventureLauncher adventureLauncher;
	private IConfigLoader configLoader;

	public TreasureService(@Autowired IConfigLoader configLoader,
							@Autowired IMapBuilder mapBuilder,
							@Autowired IAdventureLauncher adventureLauncher) {
		this.configLoader = configLoader;
		this.mapBuilder = mapBuilder;
		this.adventureLauncher = adventureLauncher;
		
	}
	
	 public void executeGame() {
		try {
			//Load map parameters from map.cfg
			List<String> mapConfig=this.configLoader.getMapParameters();
			
			//build map and entities.
			this.mapBuilder.setMapConfig(mapConfig);
			List<IEntity> entitiesList = this.mapBuilder.setMountains()
														.setTreasures().setAdventurers()
														.getEntitiesList();
			Map<String,Integer> mapLimits = this.mapBuilder.setMapLimitsFromConfig()
															.getMapLimits();
			
			//Load the entities in adventureLauncher
			this.adventureLauncher.setEntitiesList(entitiesList);
			this.adventureLauncher.setMapXSize(mapLimits.get("XSize"));
			this.adventureLauncher.setMapYSize(mapLimits.get("YSize"));
			
			//Launch the adventures and write final file
			this.adventureLauncher.launchAdventures();
			this.adventureLauncher.writeFileWithFinalPlan();
			
		}catch (Exception e){
			e.printStackTrace();
		}
				
	}
	

}
