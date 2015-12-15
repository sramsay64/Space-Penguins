package com.openthid.spacepenguins.field.entities.ship.elements;

import com.badlogic.ashley.core.Component;
import com.openthid.spacepenguins.field.entities.components.ControlIOComponent;
import com.openthid.spacepenguins.field.entities.components.TorqueComponent;
import com.openthid.spacepenguins.field.entities.ship.Part;
import com.openthid.spacepenguins.field.entities.ship.control.IOable;

public class Gyro extends Element {

	private ControlIOComponent controlIOComponent;
	private TorqueComponent torqueComponent;

	private float maxTorque;

	private IOable mode;

	public Gyro(Part part, float maxTorque) {
		super(part, "GYRO");
		controlIOComponent = ControlIOComponent
				.build().apply()
				.apply(this::setMode).apply("mode")
				.apply().apply();
		torqueComponent = new TorqueComponent(
				part
				.getShip()
				.getRotationComponent(),
				this::getTorque);
	}

	private boolean setMode(IOable mode) {
		this.mode = mode;
		return true;
	}

	private float getTorque() {
		return mode.makeWithin(-maxTorque, maxTorque).asFloat();
	}

	@Override
	public ControlIOComponent getControlIOComponent() {
		return controlIOComponent;
	}

	@Override
	public Component[] getComponents() {
		return new Component[]{controlIOComponent, torqueComponent};
	}
}