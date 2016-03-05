package com.openthid.spacepenguins.field.entities.ship;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.openthid.spacepenguins.field.entities.components.ControlIOComponent;
import com.openthid.spacepenguins.field.entities.components.EngineComponent;
import com.openthid.spacepenguins.field.entities.ship.control.IOable;
import com.openthid.spacepenguins.field.entities.ship.elements.Element;
import com.openthid.spacepenguins.field.entities.systems.MovementSystem;

public class ShipEngine extends Part {

	private EngineType engineType;
	private EngineElement element;

	public ShipEngine(MovementSystem movementSystem, Ship ship, EngineType engineType, PartRotation partRotation) {
		super(movementSystem, null, null, MaterialType.META, partRotation);
		setup(new Part[0], new Part[0], new Part[0], new Part[0], null);
		this.engineType = engineType;
		element = new EngineElement(ship, this, engineType);
	}

	/**
	 * Performs basic sanity checks to check that the engine is only attached to a part in the right side.
	 * Then runs {@link Part.setup}
	 * @throws UnsupportedOperationException If the parent part is attached to the wrong side
	 */
	@Override
	public void setup(Part[] norths, Part[] souths, Part[] easts, Part[] wests, Ship ship) throws UnsupportedOperationException {
		int nonNullN = 0;
		int nonNullS = 0;
		int nonNullE = 0;
		int nonNullW = 0;
		for (Part part : norths) {
			if (part != null) nonNullN++;
		}
		for (Part part : souths) {
			if (part != null) nonNullS++;
		}
		for (Part part : easts) {
			if (part != null) nonNullE++;
		}
		for (Part part : wests) {
			if (part != null) nonNullW++;
		}
		switch (getPartRotation()) {
		case NONE://Parent part should be north
			if (nonNullN == 1 && nonNullS == 0 && nonNullE == 0 && nonNullW == 0) {
				super.setup(norths, souths, easts, wests, ship);
			}
			return;
		case HALF://Parent part should be south
			if (nonNullN == 0 && nonNullS == 1 && nonNullE == 0 && nonNullW == 0) {
				super.setup(norths, souths, easts, wests, ship);
			}
			return;
		case QUARTER://Parent part should be 
			if (nonNullN == 0 && nonNullS == 0 && nonNullE == 0 && nonNullW == 1) {
				super.setup(norths, souths, easts, wests, ship);
			}
			return;
		case THREEQUARTER://Parent part should be 
			if (nonNullN == 0 && nonNullS == 0 && nonNullE == 1 && nonNullW == 0) {
				super.setup(norths, souths, easts, wests, ship);
			}
			return;
		}
		throw new UnsupportedOperationException("incorrect placement of parent part (or multiple parent parts)");
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