package com.openthid.spacepenguins.field.entities.ship;

import com.openthid.spacepenguins.field.entities.systems.MovementSystem;

public class RootPart extends Part {

	public RootPart(MovementSystem movementSystem) {
		super(movementSystem, PartType.SOLID, PartShape.SQUARE1x1, MaterialType.META);
	}

	@Override
	public String getTextureFilename() {
		return "kenney/Meta-elements/BEIGE-ROOT.png";
	}
}