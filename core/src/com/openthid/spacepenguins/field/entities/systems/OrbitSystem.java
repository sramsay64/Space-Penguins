package com.openthid.spacepenguins.field.entities.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.openthid.spacepenguins.field.entities.components.MassComponent;
import com.openthid.spacepenguins.field.entities.components.OrbitComponent;
import com.openthid.spacepenguins.field.entities.components.PositionComponent;

public class OrbitSystem extends EntitySystem {

	private ImmutableArray<Entity> orbitingEntities;
	private ImmutableArray<Entity> massedEntities;

	private float gravitationalConstant;

	/**
	 * Uses gravitational constant of 6.6742E-5
	 */
	public OrbitSystem() {
		this(6.6742E-5f);
	}

	public OrbitSystem(float gravitationalConstant) {
		this.gravitationalConstant = gravitationalConstant;
	}

	@Override
	public void addedToEngine(Engine engine) {
		orbitingEntities = engine.getEntitiesFor(Family.all(PositionComponent.class, OrbitComponent.class, MassComponent.class).get());
		massedEntities = engine.getEntitiesFor(Family.all(PositionComponent.class, MassComponent.class).exclude(OrbitComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		for (int i = 0; i < orbitingEntities.size(); i++) {
			Entity entity = orbitingEntities.get(i);
			OrbitComponent orbitComponent = entity.getComponent(OrbitComponent.class);
			PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
			positionComponent.x += orbitComponent.velX;
			positionComponent.y += orbitComponent.velY;
			Vector2 gravity = gravityAt(positionComponent.x, positionComponent.y);
			orbitComponent.velX -= gravity.x;
			orbitComponent.velY -= gravity.y;
		}
	}

	private Vector2 gravityAt(float x, float y) {
		Vector2 sum = new Vector2(0, 0);
		for (int i = 0; i < massedEntities.size(); i++) {
			PositionComponent positionComponent = massedEntities.get(i).getComponent(PositionComponent.class);
			MassComponent massComponent = massedEntities.get(i).getComponent(MassComponent.class);
			float dx = x-positionComponent.x;
			float dy = y-positionComponent.y;
			float r2 = (dx*dx + dy*dy);
			float acc = (gravitationalConstant*massComponent.mass/((float) Math.pow(r2, 1.5)));
			sum.add(acc*dx, acc*dy);
		}
		return sum;
	}
}