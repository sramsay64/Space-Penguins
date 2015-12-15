package com.openthid.spacepenguins.field.entities.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.openthid.spacepenguins.field.entities.components.RotationComponent;
import com.openthid.spacepenguins.field.entities.components.TorqueComponent;

public class RotationSystem extends EntitySystem {

	private ImmutableArray<Entity> rotatingEntities;
	private ImmutableArray<Entity> torquingEntities;

	@Override
	public void addedToEngine(Engine engine) {
		rotatingEntities = engine.getEntitiesFor(Family.all(RotationComponent.class).get());
		torquingEntities = engine.getEntitiesFor(Family.all(TorqueComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		torquingEntities.forEach(entity -> {
			TorqueComponent torqueComponent = entity.getComponent(TorqueComponent.class);
			torqueComponent.getRotationComponent().spin += torqueComponent.getTorque()*deltaTime;
		});
		rotatingEntities.forEach(entity -> {
			RotationComponent component = entity.getComponent(RotationComponent.class);
			component.angle += component.spin*deltaTime;
		});
	}

}