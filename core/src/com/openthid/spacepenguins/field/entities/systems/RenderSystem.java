package com.openthid.spacepenguins.field.entities.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.openthid.spacepenguins.field.entities.components.PositionComponent;
import com.openthid.spacepenguins.field.entities.components.RenderedComponent;
import com.openthid.spacepenguins.field.entities.components.TextureComponent;

public class RenderSystem extends EntitySystem {

	//	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	//	private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);

	private ImmutableArray<Entity> entities;
	private float worldPosX = 0;
	private float worldPosY = 0;
	private float zoom = 1;
	private int screenX;
	private int screenY;

	private Batch batch;

	public RenderSystem(Batch batch, int screenX, int screenY) {
		this.batch = batch;
		this.screenX = screenX;
		this.screenY = screenY;
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(RenderedComponent.class, PositionComponent.class, TextureComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			PositionComponent pos = entity.getComponent(PositionComponent.class);
			Texture texture = entity.getComponent(TextureComponent.class).texture;
			batch.draw(texture, projectX(pos.x), projectY(pos.y), texture.getWidth()*zoom, texture.getHeight()*zoom);
		}
		batch.end();
	}

	public void zoom(float diff) {
		zoom = zoom*diff;
	}

	public void move(int dx, int dy) {
		worldPosX += dx/zoom;
		worldPosY += dy/zoom;
	}

	public float projectX(float x) {
		return (x-worldPosX)*zoom + screenX/2;
	}

	public float projectY(float y) {
		return (y-worldPosY)*zoom + screenY/2;
	}
}