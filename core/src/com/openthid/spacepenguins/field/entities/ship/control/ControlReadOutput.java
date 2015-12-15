package com.openthid.spacepenguins.field.entities.ship.control;

/**
 * An output that can be read from.
 * Pronounced "Control Red Output" not "Control Reed Output"
 */
public interface ControlReadOutput extends ControlOutput, ControlInput {
	public static ControlReadOutput make(ControlInput input, ControlOutput output) {
		return new ControlReadOutput() {
			@Override
			public IOable get() {
				return input.get();
			}

			@Override
			public boolean set(IOable e) {
				return output.set(e);
			}
		};
	}
}