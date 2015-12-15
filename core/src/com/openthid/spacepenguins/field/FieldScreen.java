package com.openthid.spacepenguins.field;

import java.util.Arrays;
import java.util.HashMap;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.kotcrab.vis.ui.widget.VisSlider;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.openthid.spacepenguins.GdxGame;
import com.openthid.spacepenguins.field.entities.components.MassComponent;
import com.openthid.spacepenguins.field.entities.components.OrbitComponent;
import com.openthid.spacepenguins.field.entities.components.PositionComponent;
import com.openthid.spacepenguins.field.entities.components.RenderedComponent;
import com.openthid.spacepenguins.field.entities.components.RotationComponent;
import com.openthid.spacepenguins.field.entities.components.TextureComponent;
import com.openthid.spacepenguins.field.entities.railed.RailedBody;
import com.openthid.spacepenguins.field.entities.ship.Part;
import com.openthid.spacepenguins.field.entities.ship.Part.MaterialType;
import com.openthid.spacepenguins.field.entities.ship.Part.PartRotation;
import com.openthid.spacepenguins.field.entities.ship.Part.PartShape;
import com.openthid.spacepenguins.field.entities.ship.Part.PartType;
import com.openthid.spacepenguins.field.entities.ship.RootPart;
import com.openthid.spacepenguins.field.entities.ship.Ship;
import com.openthid.spacepenguins.field.entities.ship.ShipGraphBuilder;
import com.openthid.spacepenguins.field.entities.ship.elements.Gyro;
import com.openthid.spacepenguins.field.entities.systems.OrbitSystem;
import com.openthid.spacepenguins.field.entities.systems.RenderSystem;
import com.openthid.spacepenguins.field.entities.systems.RenderSystem.FocusElement;
import com.openthid.spacepenguins.field.entities.systems.RotationSystem;
import com.openthid.spacepenguins.screens.BaseScreen;
import com.openthid.util.FunctionalUtils;

/**
 * Renders the game field
 */
public class FieldScreen extends BaseScreen {

	private HashMap<Integer, Boolean> keyCache;

	private Engine engine;

	private RenderSystem renderSystem;
	private OrbitSystem orbitSystem;
	private RotationSystem rotationSystem;

	private RailedBody mainPlanet;
	private Ship[] ships;
	private VisTextButton[] focusButtons;

	private Stage stage;
	private ScalingViewport viewport;
	
	private VisSlider zoomSlider;

	public FieldScreen(GdxGame game) {
		super(game);
		keyCache = new HashMap<>(10);
		ships = new Ship[0];
		focusButtons = new VisTextButton[0];
		engine = new Engine();
		
		renderSystem = new RenderSystem(getBatch(), getWidth(), getHeight());
		engine.addSystem(renderSystem);
		
		orbitSystem = new OrbitSystem();
		engine.addSystem(orbitSystem);
		
		rotationSystem = new RotationSystem();
		engine.addSystem(rotationSystem);
		
		viewport = new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
		stage = new Stage(viewport, getBatch());
		
		zoomSlider = new VisSlider(0, 100, 0.1f, true);
		zoomSlider.setPosition(20, 20);
		zoomSlider.setSize(50, 500);
		stage.addActor(zoomSlider);
		zoomSlider.addListener(e -> {
			float amount = zoomSlider.getValue();
			renderSystem.setLinZoom(-amount/10);
			return true;
		});
		
		mainPlanet = new RailedBody(new PositionComponent(0, 0), new MassComponent(7.6E8f), "Olmus");
		Entity planetEntity = new Entity()
				.add(new RenderedComponent())
				.add(mainPlanet.getPositionComponent())
				.add(mainPlanet.getMassComponent())
				.add(new TextureComponent(new Texture("Colorful_planets_1/spr_planet01.png")));
		engine.addEntity(planetEntity);
		addFocusElement(mainPlanet);
		
		Ship ship = new Ship(new RootPart(),
				"Test Ship",
				new OrbitComponent(0.8f, 0.1f, 1),
				new PositionComponent(0, 10000),
				new RotationComponent(0, 9)
			);
		Part part = new Part(PartType.HOLLOW, PartShape.SQUARE1x1, MaterialType.WOOD);
		ShipGraphBuilder builder = new ShipGraphBuilder();
		builder
			.add( 1,  0, new Part(PartType.SOLID, PartShape.TRIANGLE, MaterialType.WOOD))
			.add(-1,  0, new Part(PartType.SOLID, PartShape.TRIANGLE, MaterialType.WOOD, PartRotation.QUARTER))
			.add( 0, -1, part)
			.add( 0,  1, new Part(PartType.SOLID, PartShape.CIRCLE, MaterialType.FUEL))
			.setupOn(ship);
		addShip(ship);
		part.setElement(new Gyro(part, 5));
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(new InputMultiplexer(this, stage));
	}

	@Override
	public void render(float dt) {
		if (isKeyDown(Input.Keys.LEFT)) {
			renderSystem.move(-100*dt, 0);
		} else if (isKeyDown(Input.Keys.RIGHT)) {
			renderSystem.move(100*dt, 0);
		}
		if (isKeyDown(Input.Keys.UP)) {
			renderSystem.move(0, 100*dt);
		} else if (isKeyDown(Input.Keys.DOWN)) {
			renderSystem.move(0, -100*dt);
		}
		getEngine().update(dt);
		stage.draw();
	}

	public Engine getEngine() {
		return engine;
	}

	public Ship[] getShips() {
		return ships;
	}

	public void addShip(Ship newShip) {
		ships = Arrays.copyOf(ships, ships.length+1);
		ships[ships.length-1] = newShip;
		
		Array<FocusElement> array = new Array<>();
		array.add(mainPlanet);
		array.addAll(ships);
		
		Entity newShipEntity = new Entity()
				.add(new RenderedComponent())
				.add(newShip.getPositionComponent())
				.add(newShip.getRotationComponent())
				.add(newShip.getShipComponent())
				.add(newShip.getShipComponent().selfRenderedComponent)
				.add(newShip.getOrbitComponent())
				.add(newShip.getMassComponent());
		engine.addEntity(newShipEntity);
		
		addFocusElement(newShip);
	}

	public void addFocusElement(FocusElement element) {
		focusButtons = Arrays.copyOf(focusButtons, focusButtons.length+1);
		focusButtons[focusButtons.length-1] = new VisTextButton(element.getName(), FunctionalUtils.makeChangeListener((event, actor) -> {
			zoomSlider.setValue(0);
			renderSystem.lookAt(element);
		}));
		focusButtons[focusButtons.length-1].setPosition(70, focusButtons.length*30);//TODO Place near objects
		stage.addActor(focusButtons[focusButtons.length-1]);
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
		zoomSlider.setValue(zoomSlider.getValue() + amount);
		return true;
	}

	@Override
	public void resize(int width, int height) {
		viewport.setScreenSize(width, height);
	}
}