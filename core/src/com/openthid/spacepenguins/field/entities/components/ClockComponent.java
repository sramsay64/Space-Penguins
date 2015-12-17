package com.openthid.spacepenguins.field.entities.components;

import java.util.function.Consumer;

import com.badlogic.ashley.core.Component;

public class ClockComponent implements Component {

	private Consumer<Float> consumer;

	public ClockComponent(Consumer<Float> consumer) {
		this.consumer = consumer;
	}

	public void setTime(float time) {
		consumer.accept(time);
	}
}