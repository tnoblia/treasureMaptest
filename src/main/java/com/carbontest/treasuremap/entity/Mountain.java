package com.carbontest.treasuremap.entity;

import com.carbontest.treasuremap.entity.base.Entity;

public class Mountain extends Entity{
	
	public Mountain(int xPosition, int yPosition) {
		super(xPosition,yPosition);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || this.getClass()!= obj.getClass()) {
			return false;
		}
		Mountain comparedMountain = (Mountain) obj;
		return comparedMountain.getPosition().equals(this.getPosition());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("Mountain : ");
		sb.append(this.getPosition());
		return sb.toString();
	}
	


}
