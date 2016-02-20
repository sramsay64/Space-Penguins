package com.openthid.spacepenguins.field.entities;

import com.openthid.spacepenguins.field.entities.components.MovementComponent;

public abstract class OrbitingSpaceObject extends SpaceObject {
	public abstract MovementComponent getOrbit();
}