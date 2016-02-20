package com.openthid.spacepenguins.field.entities.components;

import com.badlogic.ashley.core.Component;

@Deprecated
public class RotationComponent implements Component {

	/**
	 * Angle in degrees
	 */
	public float angle;

	/**
	 * speed in degrees per second
	 */
	public float spin;

	public RotationComponent(float angle, float spin) {
		this.angle = angle;
		this.spin = spin;
	}
}