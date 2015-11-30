package com.openthid.spacepenguins;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * A lazy font system
 */
public class FontService {
	public static final FontService ubuntuMedium = new FontService("ubuntu-font-family/Ubuntu-M.ttf", "ubuntuMedium");

	private String name;
	private FreeTypeFontGenerator generator;
	private HashMap<Integer, BitmapFont> cache;

	public FontService(String filename, String name) {
		generator = new FreeTypeFontGenerator(Gdx.files.internal(filename));
		this.name = name;
		cache = new HashMap<>();
		Gdx.app.log("LAZY", "Loaded new font '" + name + "'");
	}

	public BitmapFont getFont(int size) {
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		if (!cache.containsKey(size)) {
			cache.put(size, generator.generateFont(parameter));
			Gdx.app.log("LAZY", "Generating font '" + name + "' at size " + size);
		}
		return cache.get(size);
	}
}