package com.openthid.spacepenguins.field.entities.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
//import com.openthid.spacepenguins.field.entities.components.ControlIOComponent;
import com.openthid.spacepenguins.field.entities.components.ShipComponent;
//import com.openthid.spacepenguins.field.entities.ship.control.ShipProg;

public class ControlIOSystem extends EntitySystem {

//	private ShipProg shipProg; 
//	private ImmutableArray<Entity> ioComponents;
	private ImmutableArray<Entity> shipEntities;

	public ControlIOSystem() {
	}

	@Override
	public void addedToEngine(Engine engine) {
//		ioComponents = engine.getEntitiesFor(Family.all(ControlIOComponent.class).get());
		shipEntities = engine.getEntitiesFor(Family.all(ShipComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		shipEntities.forEach(entity -> {
			entity.getComponent(ShipComponent.class).ship.updateProg(deltaTime);
		});
	}
}