package com.openthid.spacepenguins.field.entities.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
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

	public static BodyComponent gen(World world, float x, float y) {
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;
		def.position.set(x, y);
		
		Body body = world.createBody(def);
		
		CircleShape shape = new CircleShape();
		shape.setRadius(10);
		
		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		fDef.density = 0.5f;
		fDef.friction = 0.4f;
		fDef.restitution = 0.6f;
		
		Fixture fixture = body.createFixture(fDef);
		
		shape.dispose();
		return new BodyComponent(body);
	}

	public static BodyComponent genStatic(World world, float x, float y) {
		BodyDef def = new BodyDef();
		def.type = BodyType.StaticBody;
		def.position.set(x, y);
		
		Body body = world.createBody(def);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1000, 10);
		
		FixtureDef fDef = new FixtureDef();
		fDef.shape = shape;
		fDef.density = 0.5f;
		fDef.friction = 0.4f;
		fDef.restitution = 0.6f;
		
		Fixture fixture = body.createFixture(shape, 1);
		
		shape.dispose();
		return new BodyComponent(body);
	}
}