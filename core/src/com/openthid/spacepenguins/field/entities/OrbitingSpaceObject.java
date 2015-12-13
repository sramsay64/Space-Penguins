package com.openthid.spacepenguins.field.entities;

import com.openthid.spacepenguins.field.entities.components.OrbitComponent;

public abstract class OrbitingSpaceObject extends SpaceObject {
	public abstract OrbitComponent getOrbit();
}