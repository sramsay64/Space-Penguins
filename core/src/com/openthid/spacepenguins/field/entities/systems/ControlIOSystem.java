package com.openthid.spacepenguins.field.entities.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.openthid.spacepenguins.field.entities.components.ShipComponent;

public class ControlIOSystem extends EntitySystem {

	private ImmutableArray<Entity> shipEntities;

	public ControlIOSystem() {
	}

	@Override
	public void addedToEngine(Engine engine) {
		shipEntities = engine.getEntitiesFor(Family.all(ShipComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		shipEntities.forEach(entity -> {
			entity.getComponent(ShipComponent.class).ship.updateProg(deltaTime);
		});
	}
}