package com.openthid.spacepenguins.field.entities.components;

import com.badlogic.ashley.core.Component;

public class OrbitComponent implements Component {
	private float velX;
	private float velY;
	private MassComponent massComponent;
	private OrbitComponent next;

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

	public float getVelX() {
		return velX;
	}

	public float getVelY() {
		return velY;
	}

	public MassComponent getMassComponent() {
		return massComponent;
	}

	public OrbitComponent getNext() {
		return next;
	}

	public void incVelX(float dvX) {
		this.velX += dvX;
		this.next = null;
	}

	public void incVelY(float dvY) {
		this.velY += dvY;
		this.next = null;
	}

	public void setNext(OrbitComponent next) {
		this.next = next;
	}

	public OrbitComponent makeNext() {
		OrbitComponent next;
		return next;
	}
}