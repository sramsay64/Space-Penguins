package com.openthid.spacepenguins.field.entities.components;

import java.util.function.Supplier;

import com.badlogic.ashley.core.Component;

public class TorqueComponent implements Component {

	private RotationComponent rotationComponent;
	private Supplier<Float> torqueSupplier;

	public TorqueComponent(RotationComponent rotationComponent, Supplier<Float> torqueSupplier) {
		this.rotationComponent = rotationComponent;
		this.torqueSupplier = torqueSupplier;
	}

	public RotationComponent getRotationComponent() {
		return rotationComponent;
	}

	public float getTorque() {
		return torqueSupplier.get();
	}
}