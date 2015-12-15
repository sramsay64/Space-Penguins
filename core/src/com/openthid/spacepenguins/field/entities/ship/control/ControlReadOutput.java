package com.openthid.spacepenguins.field.entities.ship.control;

/**
 * An output that can be read from
 */
public interface ControlReadOutput<T extends Controlable> extends ControlOutput<T>, ControlInput<T> {

	public static <E extends Controlable> ControlReadOutput<E> make(ControlInput<E> input, ControlOutput<E> output) {
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