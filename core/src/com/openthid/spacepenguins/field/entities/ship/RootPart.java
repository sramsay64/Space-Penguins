package com.openthid.spacepenguins.field.entities.ship;

public class RootPart extends Part {

	public RootPart() {
		super(PartType.SOLID, PartShape.SQUARE1x1, MaterialType.META);
	}

	@Override
	public String getTextureFilename() {
		return "kenney/Meta-elements/BEIGE-ROOT.png";
	}
}