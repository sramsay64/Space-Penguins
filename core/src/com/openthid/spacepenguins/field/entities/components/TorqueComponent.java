package com.openthid.spacepenguins.field.entities.components;

import java.util.function.Supplier;

import com.badlogic.ashley.core.Component;

public class TorqueComponent implements Component {

	private BodyComponent bodyComponent;
	private Supplier<Float> torqueSupplier;

	public TorqueComponent(BodyComponent bodyComponent, Supplier<Float> torqueSupplier) {
		this.bodyComponent = bodyComponent;
		this.torqueSupplier = torqueSupplier;
	}

	public float getTorque() {
		return torqueSupplier.get();
	}

	public BodyComponent getBodyComponent() {
		return bodyComponent;
	}
}