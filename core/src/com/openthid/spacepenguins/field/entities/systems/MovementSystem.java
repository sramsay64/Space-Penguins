package com.openthid.spacepenguins.field.entities.systems;

import java.util.function.Supplier;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.openthid.spacepenguins.field.entities.components.BodyComponent;
import com.openthid.spacepenguins.field.entities.components.EngineComponent;
import com.openthid.spacepenguins.field.entities.components.MassComponent;
import com.openthid.spacepenguins.field.entities.components.MovementComponent;
import com.openthid.spacepenguins.field.entities.components.PositionComponent;

public class MovementSystem extends EntitySystem {

	private ImmutableArray<Entity> movingEntities;
	private ImmutableArray<Entity> bodiedEntities;
	private ImmutableArray<Entity> engineEntities;

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private Supplier<Camera> camSupplier;//For debug only

	/**
	 * Uses gravity of -9.8 ms^-2
	 * @param viewport 
	 */
	public MovementSystem(Supplier<Camera> camSupplier) {
		this(-9.8f, camSupplier);
	}

	public MovementSystem(float gravity, Supplier<Camera> camSupplier) {
		this.camSupplier = camSupplier;
		Box2D.init();
		debugRenderer = new Box2DDebugRenderer();
		world = new World(new Vector2(0, gravity), true); 
	}

	@Override
	public void addedToEngine(Engine engine) {
		movingEntities = engine.getEntitiesFor(Family.all(PositionComponent.class, MovementComponent.class, MassComponent.class).get());
		bodiedEntities = engine.getEntitiesFor(Family.all(BodyComponent.class).get());
		engineEntities = engine.getEntitiesFor(Family.all(EngineComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {//XXX LAND UPDATE
		
		world.step(deltaTime, 6, 2);//LATER fix 1st arg needing to be constant
		debugRenderer.render(world, camSupplier.get().combined);
		
		
		
		
		/*for (int i = 0; i < engineEntities.size(); i++) {
			EngineComponent engineComponent = engineEntities.get(i).getComponent(EngineComponent.class);
			MovementComponent movementComponent = engineComponent.shipComponent.ship.getOrbitComponent();
			
			Vector2 force = new Vector2(0, engineComponent.force).setAngle(engineComponent.shipComponent.ship.getRotationComponent().angle + 90);
			movementComponent.incVelX(force.x / movementComponent.getMassComponent().mass);
			movementComponent.incVelY(force.y / movementComponent.getMassComponent().mass);
		}
		for (int i = 0; i < movingEntities.size(); i++) {
			Entity entity = movingEntities.get(i);
			PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
			MovementComponent movementComponent = entity.getComponent(MovementComponent.class);
			positionComponent.x += movementComponent.getVelX();
			positionComponent.y += movementComponent.getVelY();
			movementComponent.incVelY(-9.8f*deltaTime);
			if (positionComponent.y < -1000) {
				positionComponent.y = -1000;
			}
		}*/
	}

	public World getWorld() {
		return world;
	}
}