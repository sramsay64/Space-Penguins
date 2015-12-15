package com.openthid.spacepenguins.field.entities.ship.control;

@FunctionalInterface
public interface ControlOutput<T extends IOable> {
	public abstract boolean set(T t);
}