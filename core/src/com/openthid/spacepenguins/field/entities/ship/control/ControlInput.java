package com.openthid.spacepenguins.field.entities.ship.control;

@FunctionalInterface
public interface ControlInput<T extends IOable> {
	public abstract T get();
}