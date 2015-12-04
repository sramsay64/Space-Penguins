package com.openthid.spacepenguins.field.entities.components;

import com.badlogic.ashley.core.Component;
import com.openthid.spacepenguins.field.entities.ship.Ship;

public class ShipComponent implements Component {
	public Ship ship;
	public SelfRenderedComponent selfRenderedComponent;

	public ShipComponent(Ship ship, SelfRenderedComponent selfRenderedComponent) {
		this.ship = ship;
		this.selfRenderedComponent = selfRenderedComponent;
	}
}