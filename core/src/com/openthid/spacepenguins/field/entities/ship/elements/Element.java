package com.openthid.spacepenguins.field.entities.ship.elements;

import java.util.HashMap;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.openthid.spacepenguins.field.entities.ship.Part;
import com.openthid.util.StringUtils;

public abstract class Element {

	private static HashMap<String, Texture> textureCache = new HashMap<>(7);

	private Part part;

	private Texture[] textures;

	private String typeName;
	private Array<Integer> textureMasks;

	protected Element(Part part, String typeName, Array<Integer> textureMasks) {
		this.part = part;
		this.typeName = typeName;
		this.textureMasks = textureMasks;
	}

	public Part getPart() {
		return part;
	}

	public abstract Component[] getComponents();

	protected String getType() {
		return typeName;
	}

	protected Texture getTexture(String key) {
		if (!textureCache.containsKey(key)) {
			textureCache.put(key, new Texture(key));
			Gdx.app.log("LAZY", "Loaded new part texture '" + key + "'");
		}
		return textureCache.get(key);
	}

	public Texture[] getTextures() {
		if (textures == null) {
			String baseName = "kenney/Tech-Parts/" + part.getPartShape().toString() + "/" + getType() + "/";
			textures = new Texture[textureMasks.size+1];
			textures[0] = getTexture(baseName + "Main.png");
			for (int i = 0; i < textureMasks.size; i++) {
				textures[i+1] = getTexture(baseName + "Mask-" + StringUtils.alphabet[i] + "-" + textureMasks.get(i) + ".png");
			}
		}
		return textures;
	}

	protected void updateMask(int i, int value) {
		textureMasks.set(i, value);
		textures[i+1] = getTexture("kenney/Tech-Parts/" + part.getPartShape().toString() + "/" + getType() + "/" + "Mask-" + StringUtils.alphabet[i] + "-" + textureMasks.get(i) + ".png");
	}
}