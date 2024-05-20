package com.carbontest.treasuremap.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.carbontest.treasuremap.entity.Adventurer;
import com.carbontest.treasuremap.entity.BordersIntersection;
import com.carbontest.treasuremap.entity.Mountain;
import com.carbontest.treasuremap.entity.TreasurePlace;
import com.carbontest.treasuremap.entity.base.IEntity;
import com.carbontest.treasuremap.entity.utils.Position;
import com.carbontest.treasuremap.enums.EntityType;
import com.carbontest.treasuremap.utils.interfaces.IOutputFileWriter;

@Component
public class OutputFileWriter implements IOutputFileWriter{
	
	private List<IEntity> entitiesList;

	public String finalMapParamsToString() {
		String SEPARATOR = " - ";
		String BACK_TO_LINE = System.lineSeparator();
		StringBuilder mapParams = new StringBuilder("");
		StringBuilder adventurersParams = new StringBuilder("");
		StringBuilder treasurePlacesParams = new StringBuilder("");
		StringBuilder mountainsParams = new StringBuilder("");
		StringBuilder finalParams = new StringBuilder("");
		for(IEntity entity:this.getEntitiesList()) {
			if(entity instanceof BordersIntersection) {
				mapParams.append(EntityType.CARTE.getType());
				mapParams.append(SEPARATOR);
				mapParams.append(entity.getXPosition());
				mapParams.append(SEPARATOR);
				mapParams.append(entity.getYPosition());
				mapParams.append(BACK_TO_LINE);
			} else if(entity instanceof Mountain) {
				mountainsParams.append(EntityType.MONTAGNE.getType());
				mountainsParams.append(SEPARATOR);
				mountainsParams.append(entity.getXPosition());
				mountainsParams.append(SEPARATOR);
				mountainsParams.append(entity.getYPosition());
				mountainsParams.append(BACK_TO_LINE);
			}else if(entity instanceof TreasurePlace) {
				treasurePlacesParams.append(EntityType.TRESOR.getType());
				treasurePlacesParams.append(SEPARATOR);
				treasurePlacesParams.append(entity.getXPosition());
				treasurePlacesParams.append(SEPARATOR);
				treasurePlacesParams.append(entity.getYPosition());
				treasurePlacesParams.append(SEPARATOR);
				treasurePlacesParams.append(((TreasurePlace)entity).getNumberTreasures());
				treasurePlacesParams.append(BACK_TO_LINE);
			}else if(entity instanceof Adventurer) {
				adventurersParams.append(EntityType.AVENTURIER.getType());
				adventurersParams.append(SEPARATOR);
				adventurersParams.append(((Adventurer)entity).getName());
				adventurersParams.append(SEPARATOR);
				adventurersParams.append(entity.getXPosition());
				adventurersParams.append(SEPARATOR);
				adventurersParams.append(entity.getYPosition());
				adventurersParams.append(SEPARATOR);
				adventurersParams.append(((Adventurer)entity).getOrientation().getCardinalPoint());
				adventurersParams.append(SEPARATOR);
				adventurersParams.append(((Adventurer)entity).getNumberTreasures());
				adventurersParams.append(BACK_TO_LINE);
			}
		}
		finalParams.append(mapParams.toString());
		finalParams.append(mountainsParams.toString());
		finalParams.append(treasurePlacesParams.toString());
		finalParams.append(adventurersParams.toString());
		return finalParams.toString();
	}
	
	public void writeFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(this.finalMapParamsToString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public List<IEntity> getEntitiesList() {
		return entitiesList;
	}

	public void setEntitiesList(List<IEntity> entitiesList) {
		this.entitiesList = entitiesList;
	}

}
