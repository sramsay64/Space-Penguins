package com.openthid.spacepenguins.field.entities.ship.control;

@FunctionalInterface
public interface ControlInput {
	public abstract IOable get();
}