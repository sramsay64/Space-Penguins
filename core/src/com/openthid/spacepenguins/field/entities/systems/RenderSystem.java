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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;
import com.openthid.spacepenguins.field.entities.components.PositionComponent;
import com.openthid.spacepenguins.field.entities.components.RenderedComponent;
import com.openthid.spacepenguins.field.entities.components.SelfRenderedComponent;
import com.openthid.spacepenguins.field.entities.components.TextureComponent;
import com.openthid.util.TriConsumer;

public class RenderSystem extends EntitySystem {

	//	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class); //TODO
	//	private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);

	private ImmutableArray<Entity> renderedEntities;
	private ImmutableArray<Entity> selfRenderedEntities;
	private float worldPosX = 0;
	private float worldPosY = 0;
	private float linZoom = 0;
	/**
	 * A Cache of <pre>Math.exp(linZoom)</pre>
	 */
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
		renderedEntities = engine.getEntitiesFor(Family.all(RenderedComponent.class, PositionComponent.class, TextureComponent.class).get());
		selfRenderedEntities = engine.getEntitiesFor(Family.all(SelfRenderedComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		for (int i = 0; i < renderedEntities.size(); i++) {
			Entity entity = renderedEntities.get(i);
			PositionComponent pos = entity.getComponent(PositionComponent.class);
			Texture texture = entity.getComponent(TextureComponent.class).texture;
			batch.draw(texture,
					projectX(pos.x) - texture.getWidth()*getZoom()/2,
					projectY(pos.y) - texture.getHeight()*getZoom()/2,
					texture.getWidth()*getZoom(),
					texture.getHeight()*getZoom()
				);
		}
		for (int i = 0; i < selfRenderedEntities.size(); i++) {
			Entity entity = selfRenderedEntities.get(i);
			SelfRenderedComponent selfRenderedComponent = entity.getComponent(SelfRenderedComponent.class);
			TriConsumer<Texture, FloatArray, Vector2> biFunCons = (texture, floats, vec) -> {
				float rotationA = floats.items[4];
				float rotationB = floats.items[5];
				batch.draw(texture,
						projectX(floats.items[0] + vec.x*MathUtils.cosDeg(rotationB) - vec.y*MathUtils.sinDeg(rotationB)) - floats.items[2]*getZoom()/2,
						projectY(floats.items[1] + vec.y*MathUtils.cosDeg(rotationB) + vec.x*MathUtils.sinDeg(rotationB)) - floats.items[3]*getZoom()/2,
						(floats.items[2]/2)*getZoom(),
						(floats.items[3]/2)*getZoom(),
						floats.items[2]*getZoom(),
						floats.items[3]*getZoom(),
						1,1, //No scaling
						rotationA + rotationB,
						0,0,
						texture.getWidth(),
						texture.getHeight(),
						false,false
					);
			};
			selfRenderedComponent.consumer.accept(biFunCons);
		}
		batch.end();
	}

	public void setLinZoom(float linZoom) {
		this.linZoom = linZoom;
		this.zoom = (float) Math.exp(linZoom);
	}

	public float getZoom() {
		return zoom;
	}

	public float getLinZoom() {
		return linZoom;
	}

	public void move(int dx, int dy) {
		worldPosX += dx/getZoom();
		worldPosY += dy/getZoom();
	}

	public float projectX(float x) {
		return (x-worldPosX)*getZoom() + screenX/2;
	}

	public float projectY(float y) {
		return (y-worldPosY)*getZoom() + screenY/2;
	}
}