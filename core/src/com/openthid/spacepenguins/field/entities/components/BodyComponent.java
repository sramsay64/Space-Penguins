package com.openthid.spacepenguins.field.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyComponent implements Component {

	private Body body;

	public BodyComponent(Body body) {
		this.body = body;
	}

	public Body getBody() {
		return body;
	}

	/**
	 * @param world {@link Box2D} {@link World} to create the Body within
	 * @param xPos The x position of the body in the {@link World}
	 * @param yPos The y position of the body in the {@link World}
	 * @return A new {@link BodyComponent} with a new {@link Body} 
	 */
	public static BodyComponent gen(World world, float xPos, float yPos) {
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;
		def.position.set(xPos, yPos);
		
		Body body = world.createBody(def);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(32, 32);
		
		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		fDef.density = 0.5f;
		fDef.friction = 0.4f;
		fDef.restitution = 0.6f;
		body.createFixture(fDef);
		
		shape.dispose();
		return new BodyComponent(body);
	}

	/**
	 * @param world {@link Box2D} {@link World} to create the Body within
	 * @param xPos The x position of the body in the {@link World}
	 * @param yPos The y position of the body in the {@link World}
	 * @return A new {@link BodyComponent} with a new {@link Body} 
	 */
	public static BodyComponent genStatic(World world, float x, float y) {
		BodyDef def = new BodyDef();
		def.type = BodyType.StaticBody;
		def.position.set(x, y);
		
		Body body = world.createBody(def);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1000, 10);
		
		body.createFixture(shape, 1);
		
		shape.dispose();
		return new BodyComponent(body);
	}
}