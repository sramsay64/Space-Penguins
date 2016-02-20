package com.openthid.spacepenguins.field.entities.components;

import com.badlogic.ashley.core.Component;

@Deprecated
public class MovementComponent implements Component {
	private float velX;
	private float velY;
	private MassComponent massComponent;

	public MovementComponent(float velX, float velY, MassComponent massComponent) {
		this.velX = velX;
		this.velY = velY;
		this.massComponent = massComponent;
	}

	public MovementComponent(float velX, float velY, float mass) {
		this.velX = velX;
		this.velY = velY;
		this.massComponent = new MassComponent(mass);
	}

	public float getVelX() {
		return velX;
	}

	public float getVelY() {
		return velY;
	}

	public MassComponent getMassComponent() {
		return massComponent;
	}

	public void incVelX(float dvX) {
		this.velX += dvX;
	}

	public void incVelY(float dvY) {
		this.velY += dvY;
	}
}