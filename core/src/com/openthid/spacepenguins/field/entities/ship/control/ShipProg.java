package com.openthid.spacepenguins.field.entities.ship.control;

import com.openthid.spacepenguins.field.entities.ship.Ship.ShipProgInterface;

public abstract class ShipProg {

	private ShipProgInterface shipProgInterface;

	public ShipProg(ShipProgInterface shipProgInterface) {
		this.shipProgInterface = shipProgInterface;
	}

	protected ShipProgInterface getShipProgInterface() {
		return shipProgInterface;
	}

	public abstract void update(float deltaTime);
}