package com.openthid.spacepenguins.field.entities.ship.elements;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.openthid.spacepenguins.field.entities.components.ControlIOComponent;
import com.openthid.spacepenguins.field.entities.components.TorqueComponent;
import com.openthid.spacepenguins.field.entities.ship.Part;
import com.openthid.spacepenguins.field.entities.ship.control.IOable;

public class Gyro extends Element {

	private ControlIOComponent controlIOComponent;
	private TorqueComponent torqueComponent;

	private float maxTorque;

	private IOable torque;

	public Gyro(Part part, float maxTorque) {
		super(part, "GYRO", new Array<>(new Integer[]{0}));
		torque = IOable.ZERO;
		this.maxTorque = maxTorque;
		controlIOComponent = ControlIOComponent
				.build("Gyro").apply()
				.apply(this::setTorque).apply("torque")
				.apply().apply();
		torqueComponent = new TorqueComponent(
				part
				.getShip()
				.getRotationComponent(),
				this::getTorque);
	}

	private boolean setTorque(IOable torque) {
		this.torque = torque.makeWithin(-maxTorque, maxTorque);
		if (this.torque.getValue() > 0) {
			updateMask(0, 1);
		} else if (this.torque.getValue() < 0) {
			updateMask(0, 2);
		} else {
			updateMask(0, 0);
		}
		return this.torque.equals(torque);
	}

	private float getTorque() {
		return torque.asFloat();
	}

	@Override
	public Component[] getComponents() {
		return new Component[]{controlIOComponent, torqueComponent};
	}
}