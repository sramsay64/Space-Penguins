package com.openthid.spacepenguins.field;

import java.util.HashMap;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.openthid.spacepenguins.GdxGame;
import com.openthid.spacepenguins.field.entities.components.MassComponent;
import com.openthid.spacepenguins.field.entities.components.OrbitComponent;
import com.openthid.spacepenguins.field.entities.components.PositionComponent;
import com.openthid.spacepenguins.field.entities.components.RenderedComponent;
import com.openthid.spacepenguins.field.entities.components.TextureComponent;
import com.openthid.spacepenguins.field.entities.ship.Part;
import com.openthid.spacepenguins.field.entities.ship.Part.MaterialType;
import com.openthid.spacepenguins.field.entities.ship.Part.PartRotation;
import com.openthid.spacepenguins.field.entities.ship.Part.PartShape;
import com.openthid.spacepenguins.field.entities.ship.Part.PartType;
import com.openthid.spacepenguins.field.entities.ship.RootPart;
import com.openthid.spacepenguins.field.entities.ship.Ship;
import com.openthid.spacepenguins.field.entities.ship.ShipGraphBuilder;
import com.openthid.spacepenguins.field.entities.systems.OrbitSystem;
import com.openthid.spacepenguins.field.entities.systems.RenderSystem;
import com.openthid.spacepenguins.screens.BaseScreen;

/**
 * Renders the game field
 */
public class FieldScreen extends BaseScreen {

	private HashMap<Integer, Boolean> keyCache;

	private Engine engine;

	private RenderSystem renderSystem;
	private OrbitSystem orbitSystem;

	public FieldScreen(GdxGame game) {
		super(game);
		keyCache = new HashMap<>(10);
		engine = new Engine();
		
		renderSystem = new RenderSystem(getBatch(), getWidth(), getHeight());
		engine.addSystem(renderSystem);
		
		orbitSystem = new OrbitSystem();
		engine.addSystem(orbitSystem);

		Entity entity = new Entity()
				.add(new RenderedComponent())
				.add(new PositionComponent(0, 0))
				.add(new MassComponent(7.6E8f))
				.add(new TextureComponent(new Texture("Colorful_planets_1/spr_planet01.png")));
		engine.addEntity(entity);
		
		Ship ship = new Ship(new RootPart(), new OrbitComponent(0.8f, 0.1f, 1), new PositionComponent(0, 100));
		ShipGraphBuilder builder = new ShipGraphBuilder();
		builder
			.add( 1,  0, new Part(PartType.SOLID, PartShape.TRIANGLE, MaterialType.WOOD))
			.add(-1,  0, new Part(PartType.SOLID, PartShape.TRIANGLE, MaterialType.WOOD, PartRotation.QUARTER))
			.add( 0,  1, new Part(PartType.SOLID, PartShape.CIRCLE, MaterialType.FUEL))
			.setupOn(ship.getRootPart());
		
		Entity testShip = new Entity()
				.add(new RenderedComponent())
				.add(ship.getPositionComponent())
				.add(ship.getShipComponent())
				.add(ship.getShipComponent().selfRenderedComponent)
//				.add(ship.getOrbitComponent())
				.add(ship.getMassComponent());
		engine.addEntity(testShip);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		if (isKeyDown(Input.Keys.LEFT)) {
			renderSystem.move(-1, 0);
		} else if (isKeyDown(Input.Keys.RIGHT)) {
			renderSystem.move(1, 0);
		}
		if (isKeyDown(Input.Keys.UP)) {
			renderSystem.move(0, 1);
		} else if (isKeyDown(Input.Keys.DOWN)) {
			renderSystem.move(0, -1);
		}
		getEngine().update(delta);
	}

	public Engine getEngine() {
		return engine;
	}

	@Override
	public boolean keyDown(int keycode) {
		keyCache.put(keycode, true);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		keyCache.put(keycode, false);
		return true;
	}

	public boolean isKeyDown(int keycode) {
		if (!keyCache.containsKey(keycode)) {
			return false;
		}
		return keyCache.get(keycode);
	}

	@Override
	public boolean scrolled(int amount) {
		renderSystem.zoom((float) Math.pow(2.0, ((float) -amount)/10));
		return true;
	}
}