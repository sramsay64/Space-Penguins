package com.openthid.util;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * A lazy font system
 */
public class FontService {
	/**
	 * The Ubuntu Medium font
	 */
	public static final FontService ubuntuMedium = new FontService("ubuntu-font-family/Ubuntu-M.ttf", "ubuntuMedium");

	private String name;
	private FreeTypeFontGenerator generator;
	private HashMap<Integer, BitmapFont> cache;

	private FontService(String filename, String name) {
		generator = new FreeTypeFontGenerator(Gdx.files.internal(filename));
		this.name = name;
		cache = new HashMap<>();
		Gdx.app.log("LAZY", "Loaded new font '" + name + "'");
	}

	/**
	 * Lazily generates a bitmap font
	 * @param size The size to render the font at
	 */
	public BitmapFont getFont(int size) {
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		if (!cache.containsKey(size)) {
			cache.put(size, generator.generateFont(parameter));
			Gdx.app.log("LAZY", "Generated font '" + name + "' at size " + size);
		}
		return cache.get(size);
	}
}