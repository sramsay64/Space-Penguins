package com.openthid.spacepenguins.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.openthid.spacepenguins.GdxGame;

public class HomeScreen extends BaseScreen {

	public HomeScreen(GdxGame game) {
		super(game);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		getGame().getBatch().begin();
		getGame().getBatch().draw(getGame().getImg(), getGame().getWidth() / 2, 0);
		getGame().getBatch().end();
	}
}