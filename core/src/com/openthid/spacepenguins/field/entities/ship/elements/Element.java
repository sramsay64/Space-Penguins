package com.openthid.spacepenguins.field.entities.ship.elements;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.openthid.spacepenguins.field.entities.ship.Part;
import com.openthid.spacepenguins.field.entities.ship.control.ControlIOComponent;

public class Element {

	private static HashMap<String, Texture> textureCache = new HashMap<>(7);

	private Part part;
	private ControlIOComponent controlIOComponent;

	public Element(Part part, ControlIOComponent controlIOComponent) {
		this.part = part;
		this.controlIOComponent = controlIOComponent;
	}

	public Part getPart() {
		return part;
	}

	public ControlIOComponent getControlIOComponent() {
		return controlIOComponent;
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