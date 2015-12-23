package com.openthid.spacepenguins.field.entities.ship;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.openthid.spacepenguins.field.entities.components.ControlIOComponent;
import com.openthid.spacepenguins.field.entities.components.EngineComponent;
import com.openthid.spacepenguins.field.entities.ship.control.IOable;
import com.openthid.spacepenguins.field.entities.ship.elements.Element;

public class ShipEngine extends Part {

	private EngineType engineType;
	private EngineElement element;

	public ShipEngine(Ship ship, EngineType engineType, PartRotation partRotation) {
		super(null, null, MaterialType.META, partRotation);
		setup(new Part[0], new Part[0], new Part[0], new Part[0], null);
		this.engineType = engineType;
		element = new EngineElement(ship, this, engineType);
	}

	@Override
	public void setup(Part[] norths, Part[] souths, Part[] easts, Part[] wests, Ship ship) {
		int nonNull = 0;
		for (Part part : norths) {
			if (part != null) nonNull++;
		}
		for (Part part : souths) {
			if (part != null) nonNull++;
		}
		for (Part part : easts) {
			if (part != null) nonNull++;
		}
		for (Part part : wests) {
			if (part != null) nonNull++;
		}
		super.setup(norths, souths, easts, wests, ship);
		if (nonNull > 1) {
			throw new UnsupportedOperationException("Engine has more than one parent part");
		}
	}

	@Override
	protected String getTextureFilename() {
		return "kenney/Engines/" + engineType.getFilename() + ".png";
	}

	@Override
	public void setElement(Element e) {
		throw new UnsupportedOperationException("Cannot set the element of an engine");
	}

	@Override
	public Element getElement() {
		return element;
	}

	public static class EngineElement extends Element {

		private ControlIOComponent ioComponent;
		private EngineComponent engineComponent;
		private EngineType type;

		protected EngineElement(Ship ship, Part part, EngineType type) {
			super(part, "ENGINE", new Array<>(0));
			this.type = type;
			ioComponent = ControlIOComponent.build(ship, "Engine").apply().apply(this::setThrust).apply("thrust").apply().apply();
			engineComponent = new EngineComponent(ship.getShipComponent(), 0, part.getRotation());
//			setThrust(30);
		}

		@Override
		public Component[] getComponents() {
			return new Component[]{ioComponent, engineComponent};
		}

		public boolean setThrust(IOable thrust) {
			boolean b = true;
			float t = thrust.getValue();
			if (t > type.maxThrust) {
				t = type.maxThrust;
				b = false;
			} else if (t < 0) {
				t = 0;
				b = false;
			}
			engineComponent.force = t;
			return b;
		}

		@Override
		public ControlIOComponent getIOComponent() {
			return ioComponent;
		}

		@Override
		public Texture[] getTextures() {
			return new Texture[0];
		}
	}

	public static class EngineType {
		
		public static final EngineType MINI = new EngineType("MINI", 30, 200);
		
		private String filename;
		private float maxThrust;
		private float specificImpulse;

		public EngineType(String filename, float maxThrust, float specificImpulse) {
			this.filename = filename;
			this.maxThrust = maxThrust;
			this.specificImpulse = specificImpulse;
		}
		
		public String getFilename() {
			return filename;
		}
		
		public float getMaxThrust() {
			return maxThrust;
		}
		
		public float getSpecificImpulse() {
			return specificImpulse;
		}
	}
}