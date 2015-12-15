package com.openthid.spacepenguins.field.entities.ship.control;

/**
 * A type of value that can be controlled or read
 */
public class IOable {

	private float value;

	public static IOable fromBool(boolean b) {
		if (b) {
			return new IOable(1);
		}
		return new IOable(0);
	}

	public static IOable fromNat(int n) {
		return new IOable(Math.abs(n));
	}

	public static IOable fromInt(int i) {
		return new IOable(i);
	}

	public static IOable fromFloat(float f) {
		return new IOable(f);
	}

	private IOable(float value) {
		this.value = value;
	}

	public float getValue() {
		return value;
	}

	public boolean asBool() {
		return getValue() == 0;
	}

	public int asInt() {
		return Math.round(getValue());
	}

	public int asNat() {
		return Math.abs(asInt());
	}

	public float asFloat() {
		return getValue();
	}

	public IOable add(IOable a) {
		return new IOable(getValue() + a.getValue());
	}

	public IOable mul(IOable a) {
		return new IOable(getValue() * a.getValue());
	}

	public IOable neg() {
		return new IOable(-getValue());
	}

	public IOable inv() {
		return new IOable(1/getValue());
	}

	public IOable neg(IOable a) {
		return new IOable(getValue() - a.getValue());
	}

	public IOable div(IOable a) {
		return new IOable(getValue() / a.getValue());
	}
}