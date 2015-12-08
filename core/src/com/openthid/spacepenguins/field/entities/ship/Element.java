package com.openthid.spacepenguins.field.entities.ship;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Element {

	private static HashMap<String, Texture> textureCache = new HashMap<>(7);

	private Part part;

	public Element(Part part) {
		this.part = part;
	}

	public Part getPart() {
		return part;
	}

	protected String getTextureFilename() {
		return null;//TODO
	}

	public Texture getTexture() {
		String key = getTextureFilename();
		if (!textureCache.containsKey(key)) {
			textureCache.put(key, new Texture(key));
			Gdx.app.log("LAZY", "Loaded new part texture '" + key + "'");
		}
		return textureCache.get(key);
	}

	public enum ElementType {
		GYRO,
		CURCIT;
	}
}