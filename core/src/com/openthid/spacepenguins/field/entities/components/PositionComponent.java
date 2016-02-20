package com.openthid.spacepenguins.field.entities.components;

import com.badlogic.ashley.core.Component;

@Deprecated
public class PositionComponent implements Component {
	public float x;
	public float y;

	public PositionComponent(float x, float y) {
		this.x = x;
		this.y = y;
	}
}