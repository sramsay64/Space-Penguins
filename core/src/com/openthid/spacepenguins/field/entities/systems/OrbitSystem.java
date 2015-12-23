package com.openthid.spacepenguins.field.entities.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.openthid.spacepenguins.field.entities.components.EngineComponent;
import com.openthid.spacepenguins.field.entities.components.MassComponent;
import com.openthid.spacepenguins.field.entities.components.OrbitComponent;
import com.openthid.spacepenguins.field.entities.components.PositionComponent;

public class OrbitSystem extends EntitySystem {

	private ImmutableArray<Entity> orbitingEntities;
	private ImmutableArray<Entity> massedEntities;
	private ImmutableArray<Entity> engineEntities;

	private float gravitationalConstant;

	private int preCalcSize;

	/**
	 * Uses gravitational constant of 6.6742E-5 and a pre-calculation size of 10
	 */
	public OrbitSystem() {
		this(6.6742E-5f, 10);
	}

	public OrbitSystem(float gravitationalConstant, int preCalcSize) {
		this.gravitationalConstant = gravitationalConstant;
		this.preCalcSize = preCalcSize;
	}

	@Override
	public void addedToEngine(Engine engine) {
		orbitingEntities = engine.getEntitiesFor(Family.all(PositionComponent.class, OrbitComponent.class, MassComponent.class).get());
		massedEntities = engine.getEntitiesFor(Family.all(PositionComponent.class, MassComponent.class).exclude(OrbitComponent.class).get());
		engineEntities = engine.getEntitiesFor(Family.all(EngineComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		for (int i = 0; i < engineEntities.size(); i++) {
			EngineComponent engineComponent = engineEntities.get(i).getComponent(EngineComponent.class);
			OrbitComponent shipOrbit = engineComponent.shipComponent.ship.getOrbitComponent();
			
			Vector2 force = new Vector2(0, engineComponent.force).setAngle(engineComponent.shipComponent.ship.getRotationComponent().angle + 90);
			shipOrbit.incVelX(force.x / shipOrbit.getMassComponent().mass);
			shipOrbit.incVelY(force.y / shipOrbit.getMassComponent().mass);
		}
		for (int i = 0; i < orbitingEntities.size(); i++) {
			Entity entity = orbitingEntities.get(i);
			OrbitComponent orbitComponent = entity.getComponent(OrbitComponent.class);
			PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
			positionComponent.x += orbitComponent.getVelX();
			positionComponent.y += orbitComponent.getVelY();
			Vector2 gravity = gravityAt(positionComponent.x, positionComponent.y);
			orbitComponent.incVelX(-gravity.x);
			orbitComponent.incVelY(-gravity.y);
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