package com.openthid.spacepenguins.field.entities.ship.control;

@FunctionalInterface
public interface ControlOutput {
	public abstract boolean set(IOable t);
}