package com.openthid.spacepenguins.field.entities.components;

import java.util.Arrays;

import com.badlogic.ashley.core.Component;
import com.openthid.spacepenguins.field.entities.ship.control.IOable;
import com.openthid.util.FunctionalUtils;

public class IOBufferComponent implements Component {
	public IOable[] data;

	public IOBufferComponent(int size) {
		this(FunctionalUtils.applyMany(new IOable[size], a -> Arrays.fill(a, IOable.ZERO)));
	}

	public IOBufferComponent(IOable... data) {
		this.data = data;
	}
}