package com.openthid.spacepenguins.field.entities.components;

import com.badlogic.ashley.core.Component;

public class MassComponent implements Component {
	public float mass;

	public MassComponent(float mass) {
		this.mass = mass;
	}
}