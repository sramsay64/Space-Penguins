package com.openthid.spacepenguins.field.entities.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.openthid.spacepenguins.field.entities.components.RotationComponent;

public class RotationSystem extends EntitySystem {

	private ImmutableArray<Entity> rotatingEntities;

	@Override
	public void addedToEngine(Engine engine) {
		rotatingEntities = engine.getEntitiesFor(Family.all(RotationComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		rotatingEntities.forEach(entity -> {
			RotationComponent component = entity.getComponent(RotationComponent.class);
			component.angle += component.spin*deltaTime;
		});
	}

}