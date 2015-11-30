package com.openthid.spacepenguins.field.entities.components;

import com.badlogic.ashley.core.Component;
import com.openthid.spacepenguins.field.entities.Part;

public class PartComponent implements Component {
	public Part part;
	public TextureComponent textureComponent;

	public PartComponent(Part part) {
		this.part = part;
		this.textureComponent = new TextureComponent(part.getTexture());
	}
}