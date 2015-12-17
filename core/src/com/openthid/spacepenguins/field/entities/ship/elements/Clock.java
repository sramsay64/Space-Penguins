package com.openthid.spacepenguins.field.entities.ship.elements;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.openthid.spacepenguins.field.entities.components.ClockComponent;
import com.openthid.spacepenguins.field.entities.components.ControlIOComponent;
import com.openthid.spacepenguins.field.entities.ship.Part;
import com.openthid.spacepenguins.field.entities.ship.control.IOable;

public class Clock extends Element {

	private ControlIOComponent controlIOComponent;
	private ClockComponent clockComponent;
	private float time;

	public Clock(Part part) {
		super(part, "CLOCK", new Array<>(new Integer[]{}));
		controlIOComponent = ControlIOComponent
				.build("Clock", this::getTime).apply("time")
				.apply().apply()
				.apply().apply();
		clockComponent = new ClockComponent(time -> this.time = time);
	}

	public IOable getTime() {
		return IOable.fromFloat(time);
	}

	@Override
	public Component[] getComponents() {
		return new Component[]{controlIOComponent, clockComponent};
	}
}