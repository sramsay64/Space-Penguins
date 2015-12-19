package com.openthid.spacepenguins.field.entities.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.openthid.spacepenguins.field.entities.components.ClockComponent;

public class UserInputSystem extends EntitySystem {

	private ImmutableArray<Entity> entities;

	public UserInputSystem() {
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(ClockComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		entities.forEach(entity -> {
		});
	}
}