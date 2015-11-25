package com.openthid.spacepenguins.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.openthid.spacepenguins.GdxGame;

public class BaseScreen extends ScreenAdapter {
	private GdxGame game;

	public BaseScreen(GdxGame game) {
		this.game = game;
	}

	public GdxGame getGame() {
		return game;
	}

	public SpriteBatch getBatch() {
		return getGame().getBatch();
	}
}