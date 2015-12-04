package com.openthid.spacepenguins;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kotcrab.vis.ui.VisUI;
import com.openthid.spacepenguins.screens.HomeScreen;
import com.openthid.spacepenguins.screens.TitleScreen;

public class GdxGame extends Game {
	private SpriteBatch batch;
	private Texture img;

	private int width;
	private int height;

	private HomeScreen homeScreen;
	private TitleScreen titleScreen;

	public GdxGame(int width, int height) {
		this.height = height;
		this.width = width;
	}

	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		VisUI.load();
		setScreen(getTitleScreen());
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public Texture getImg() {
		return img;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public HomeScreen getHomeScreen() {
		if (homeScreen == null) {
			homeScreen = new HomeScreen(this);
		}
		return homeScreen;
	}

	public TitleScreen getTitleScreen() {
		if (titleScreen == null) {
			titleScreen = new TitleScreen(this);
		}
		return titleScreen;
	}
}