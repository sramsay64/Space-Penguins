package com.openthid.spacepenguins.field.entities.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.openthid.spacepenguins.field.entities.components.PositionComponent;
import com.openthid.spacepenguins.field.entities.components.RenderedComponent;
import com.openthid.spacepenguins.field.entities.components.SpriteComponent;

public class RenderSystem extends EntitySystem {

	//	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	//	private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);

	private ImmutableArray<Entity> entities;
	private ViewPort viewPort;

	private Batch batch;

	public RenderSystem(Batch batch, ViewPort viewPort) {
		this.batch = batch;
		this.setViewPort(viewPort);
	}

	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(RenderedComponent.class, PositionComponent.class, SpriteComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			PositionComponent pos = entity.getComponent(PositionComponent.class);
			batch.draw(entity.getComponent(SpriteComponent.class).sprite.getTexture(), pos.x, pos.y);
		}
		batch.end();
	}

	public ViewPort getViewPort() {
		return viewPort;
	}

	public void setViewPort(ViewPort viewPort) {
		this.viewPort = viewPort;
	}

	public static class ViewPort {
		private int xSize;
		private int ySize;
		private int xOffset;
		private int yOffset;

		public ViewPort(int xSize, int ySize, float scale, int xPixels, int yPixels) {
			this.xSize = xSize;
			this.ySize = ySize;
			this.xOffset = xOffset;
			this.yOffset = yOffset;
		}

		public int getxSize() {
			return xSize;
		}

		public int getySize() {
			return ySize;
		}

		public int getxOffset() {
			return xOffset;
		}

		public int getyOffset() {
			return yOffset;
		}
	}
}