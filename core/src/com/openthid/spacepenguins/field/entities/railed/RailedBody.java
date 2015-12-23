package com.openthid.spacepenguins.field.entities.railed;

import com.openthid.spacepenguins.field.entities.SpaceObject;
import com.openthid.spacepenguins.field.entities.components.MassComponent;
import com.openthid.spacepenguins.field.entities.components.PositionComponent;
import com.openthid.util.OrbitalCalc;

public class RailedBody extends SpaceObject {

	private PositionComponent positionComponent;
	private MassComponent massComponent;

	private String name;

	public RailedBody(PositionComponent positionComponent, MassComponent massComponent, String name) {
		this.positionComponent = positionComponent;
		this.massComponent = massComponent;
		this.name = name;
	}

	@Override
	public PositionComponent getPositionComponent() {
		return positionComponent;
	}

	public MassComponent getMassComponent() {
		return massComponent;
	}

	@Override
	public String getName() {
		return name;
	}

	public String getStatus() {
		return "";//TODO Orbital Info or something
	}

	@Override
	public OrbitalCalc getOrbitCalc() {
		return null;
	}
}