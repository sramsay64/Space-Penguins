package com.openthid.spacepenguins.field.entities.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.openthid.spacepenguins.field.entities.components.ClockComponent;

public class ClockSystem extends EntitySystem {

	private ImmutableArray<Entity> entities;

	private float time;

	public ClockSystem() {
		time = 0;
	}

	@Override
	public void addedToEngine(Engine engine) {
		time = 0;
		entities = engine.getEntitiesFor(Family.all(ClockComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		time += deltaTime;
		entities.forEach(entity -> {
			entity.getComponent(ClockComponent.class).setTime(time);
		});
	}

	public float getTime() {
		return time;
	}
}