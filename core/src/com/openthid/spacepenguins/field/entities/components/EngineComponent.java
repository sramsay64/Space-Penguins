package com.openthid.spacepenguins.field.entities.components;

import com.badlogic.ashley.core.Component;

public class EngineComponent implements Component {

	public ShipComponent shipComponent;
	/**
	 * Force in Newtons
	 */
	public float force;
	/**
	 * angle in degrees
	 */
	public float angle;

	public EngineComponent(ShipComponent shipComponent, float force, float angle) {
		this.shipComponent = shipComponent;
		this.force = force;
		this.angle = angle;
	}
}