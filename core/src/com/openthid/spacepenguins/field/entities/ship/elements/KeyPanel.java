package com.openthid.spacepenguins.field.entities.ship.elements;

import java.util.function.IntFunction;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.openthid.spacepenguins.field.entities.components.ControlIOComponent;
import com.openthid.spacepenguins.field.entities.ship.Part;
import com.openthid.spacepenguins.field.entities.ship.control.IOable;

public class KeyPanel extends Element {

	private ControlIOComponent controlIOComponent;
	private IntFunction<Boolean> keyFunction;

	public KeyPanel(Part part, IntFunction<Boolean> keyFunction) {
		super(part, "KEYPANEL", new Array<>(new Integer[]{}));
		controlIOComponent = ControlIOComponent
				.build(part.getShip(), "KeyPanel", this::getQ, this::getW, this::getE, this::getA, this::getS, this::getD).apply("q", "w", "e", "a", "s", "d")
				.apply().apply()
				.apply().apply();
		this.keyFunction = keyFunction;
	}

	public IOable getQ() {
		return IOable.fromBool(keyFunction.apply(Input.Keys.Q));
	}

	public IOable getW() {
		return IOable.fromBool(keyFunction.apply(Input.Keys.W));
	}

	public IOable getE() {
		return IOable.fromBool(keyFunction.apply(Input.Keys.E));
	}

	public IOable getA() {
		return IOable.fromBool(keyFunction.apply(Input.Keys.A));
	}

	public IOable getS() {
		return IOable.fromBool(keyFunction.apply(Input.Keys.S));
	}

	public IOable getD() {
		return IOable.fromBool(keyFunction.apply(Input.Keys.D));
	}

	@Override
	public Component[] getComponents() {
		return new Component[]{controlIOComponent};
	}

	@Override
	public ControlIOComponent getIOComponent() {
		return controlIOComponent;
	}
}