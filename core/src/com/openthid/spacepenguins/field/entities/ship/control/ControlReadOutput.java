package com.openthid.spacepenguins.field.entities.ship.control;

/**
 * An output that can be read from.
 * Pronounced "Control Red Output" not "Control Reed Output"
 */
public interface ControlReadOutput<T extends IOable> extends ControlOutput<T>, ControlInput<T> {

	public static <E extends IOable> ControlReadOutput<E> make(ControlInput<E> input, ControlOutput<E> output) {
		return new ControlReadOutput<E>() {
			@Override
			public E get() {
				return input.get();
			}

			@Override
			public boolean set(E e) {
				return output.set(e);
			}
		};
	}
}