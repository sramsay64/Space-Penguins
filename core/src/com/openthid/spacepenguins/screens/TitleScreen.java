package com.openthid.spacepenguins.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.openthid.spacepenguins.FontService;
import com.openthid.spacepenguins.GdxGame;

public class TitleScreen extends BaseScreen {

	public TitleScreen(GdxGame game) {
		super(game);
	}

	@Override
	public void render(float delta) {//TODO

		super.render(delta);
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		getBatch().begin();
		getBatch().draw(getGame().getImg(), getGame().getWidth() / 2, 0);
		FontService.ubuntuMedium.getFont(20).draw(getGame().getBatch(), "Hello World", 100, 100);
		getBatch().end();
	}
}