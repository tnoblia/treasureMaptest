package com.carbontest.treasuremap.entity.factories;

import org.springframework.stereotype.Component;

import com.carbontest.treasuremap.entity.Mountain;
import com.carbontest.treasuremap.entity.factories.interfaces.IEntityFactory;
import com.carbontest.treasuremap.entity.interfaces.IEntity;

@Component
public class MountainFactory implements IEntityFactory{

	 @Override
	    public IEntity createMountain(int xPosition, int yPosition) {
	        return new Mountain(xPosition, yPosition);
	    }

}
