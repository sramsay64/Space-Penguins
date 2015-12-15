package com.openthid.spacepenguins.field.entities.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.openthid.spacepenguins.field.entities.components.ControlIOComponent;

public class ControlIOSystem extends EntitySystem {

	private ImmutableArray<Entity> entities;

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(ControlIOComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		entities.forEach(entity -> {
			ControlIOComponent ioComponent = entity.getComponent(ControlIOComponent.class);
			
		});
	}
}