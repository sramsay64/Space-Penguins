package com.openthid.spacepenguins.field.entities;

import com.openthid.spacepenguins.field.entities.components.PositionComponent;

public interface FocusElement {
	public PositionComponent getPositionComponent();

	public default float getX() {
		return getPositionComponent().x;
	}

	public default float getY() {
		return getPositionComponent().y;
	}
	
	public String getName();

	public String getStatus();
}