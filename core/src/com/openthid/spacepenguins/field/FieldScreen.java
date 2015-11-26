package com.openthid.spacepenguins.field;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.openthid.spacepenguins.GdxGame;
import com.openthid.spacepenguins.field.entities.components.PositionComponent;
import com.openthid.spacepenguins.field.entities.components.RenderedComponent;
import com.openthid.spacepenguins.field.entities.components.SpriteComponent;
import com.openthid.spacepenguins.field.entities.systems.RenderSystem;
import com.openthid.spacepenguins.field.entities.systems.RenderSystem.ViewPort;
import com.openthid.spacepenguins.screens.BaseScreen;

/**
 * Renders the game field
 */
public class FieldScreen extends BaseScreen {

	private Stage stage;

	private Engine engine;

	public FieldScreen(GdxGame game) {
		super(game);
		engine = new Engine();
		engine.addSystem(new RenderSystem(getBatch(), new ViewPort(-100, -100, 0.5f, getWidth(), getHeight())));

		Entity entity = new Entity()
				.add(new RenderedComponent())
				.add(new PositionComponent(0, 0))
				.add(new SpriteComponent(new Sprite(new Texture("Colorful_planets_1/spr_planet01.png"))));
		engine.addEntity(entity);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		getEngine().update(delta);
//		super.render(delta);
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		getGame().getBatch().begin();
//		getGame().getBatch().draw(getGame().getImg(), getGame().getWidth() / 2, 0);
//		getGame().getBatch().end();
	}

	public Engine getEngine() {
		return engine;
	}
}