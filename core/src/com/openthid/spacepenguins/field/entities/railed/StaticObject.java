package com.openthid.spacepenguins.field.entities.railed;

import com.openthid.spacepenguins.field.entities.SpaceObject;
import com.openthid.spacepenguins.field.entities.components.BodyComponent;
import com.openthid.spacepenguins.field.entities.components.MassComponent;

public class StaticObject extends SpaceObject {

	private String name;
	private BodyComponent bodyComponent;

	public StaticObject(MassComponent massComponent, BodyComponent bodyComponent, String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public String getStatus() {
		return "";//TODO Orbital Info or something
	}

	@Override
	public float getX() {
		return bodyComponent.getBody().getPosition().x;
	}

	@Override
	public float getY() {
		return bodyComponent.getBody().getPosition().y;
	}
}