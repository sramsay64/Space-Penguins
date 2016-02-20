package com.openthid.spacepenguins.field.entities.systems;

import java.util.function.Supplier;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class MovementSystem extends EntitySystem {

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
	}

	@Override
	public void update(float deltaTime) {//XXX LAND UPDATE
		world.step(deltaTime, 6, 2);//LATER fix 1st arg needing to be constant
		debugRenderer.render(world, camSupplier.get().combined);
	}

	public World getWorld() {
		return world;
	}
}