package com.openthid.spacepenguins.field.entities.components;

import com.badlogic.ashley.core.Component;

public class OrbitComponent implements Component {
	public float velX;
	public float velY;
	public MassComponent massComponent;

	public OrbitComponent(float velX, float velY, MassComponent massComponent) {
		this.velX = velX;
		this.velY = velY;
		this.massComponent = massComponent;
	}

	public OrbitComponent(float velX, float velY, float mass) {
		this.velX = velX;
		this.velY = velY;
		this.massComponent = new MassComponent(mass);
	}
}