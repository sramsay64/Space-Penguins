package com.openthid.spacepenguins.field.entities.ship.control;

@FunctionalInterface
public interface ControlInput<T extends Controlable> {
	public abstract T get();
}