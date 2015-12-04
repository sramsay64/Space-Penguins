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

	//	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	//	private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);

	private ImmutableArray<Entity> renderedEntities;
	private ImmutableArray<Entity> selfRenderedEntities;
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
					projectX(pos.x) - texture.getWidth()*zoom/2,
					projectY(pos.y) - texture.getHeight()*zoom/2,
					texture.getWidth()*zoom,
					texture.getHeight()*zoom
				);
		}
		for (int i = 0; i < selfRenderedEntities.size(); i++) {
			Entity entity = selfRenderedEntities.get(i);
			SelfRenderedComponent selfRenderedComponent = entity.getComponent(SelfRenderedComponent.class);
			TriConsumer<Texture, FloatArray, Vector2> biFunCons = (texture, floats, vec) -> {
				float rotationA = 0;
				float rotationB = 0;
				if (floats.items.length >= 5) {
					rotationA = floats.items[4];
					if (floats.items.length >= 6) {
						rotationB = floats.items[5];
					}
				}
//				float k = vec.len();
//				float g = (float) (k*Math.sin(rotationB*MathUtils.degreesToRadians - vec.angle()));
//				float h = (float) (k*Math.cos(rotationB*MathUtils.degreesToRadians - vec.angle()));
//				Gdx.app.log("TEMP", texture.toString() + " | " + k + " | " + g + " | " + h + " | " + vec);
//				if (vec.y == -1 && vec.x == 0) {
//					batch.draw(texture,
//							projectX(floats.items[0]) - floats.items[2]*zoom/2,
//							projectY(floats.items[1]) - floats.items[3]*zoom/2,
//							(floats.items[2])*zoom/2,
//							(floats.items[3])*zoom/2,
//							floats.items[2]*zoom,
//							floats.items[3]*zoom,
//							1,1, //No scaling
//							rotationA,// + rotationB,
//							0,0,
//							texture.getWidth(),
//							texture.getHeight(),
//							false,false
//						);
//				} else
				batch.draw(texture,
						projectX(floats.items[0] + vec.x*MathUtils.cosDeg(rotationB) - vec.y*MathUtils.sinDeg(rotationB)) - floats.items[2]*zoom/2,
						projectY(floats.items[1] + vec.y*MathUtils.cosDeg(rotationB) + vec.x*MathUtils.sinDeg(rotationB)) - floats.items[3]*zoom/2,
						(floats.items[2]/2)*zoom,
						(floats.items[3]/2)*zoom,
						floats.items[2]*zoom,
						floats.items[3]*zoom,
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