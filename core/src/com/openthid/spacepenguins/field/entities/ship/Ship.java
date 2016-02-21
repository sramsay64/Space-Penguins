package com.openthid.spacepenguins.field.entities.ship;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.openthid.spacepenguins.field.entities.SpaceObject;
import com.openthid.spacepenguins.field.entities.components.BodyComponent;
import com.openthid.spacepenguins.field.entities.components.ControlIOComponent;
import com.openthid.spacepenguins.field.entities.components.SelfRenderedComponent;
import com.openthid.spacepenguins.field.entities.components.ShipComponent;
import com.openthid.spacepenguins.field.entities.ship.control.ControlInput;
import com.openthid.spacepenguins.field.entities.ship.control.ControlOutput;
import com.openthid.spacepenguins.field.entities.ship.control.ShipProg;
import com.openthid.util.TriConsumer;

public class Ship extends SpaceObject {

	private Part rootPart;
	private ShipProg prog;
	private ShipProgInterface shipProgInterface;

	private ShipComponent shipComponent;
	private BodyComponent bodyComponent;

	private String name;
	private String status = "";

	public Ship(Part rootPart, String name, BodyComponent bodyComponent) {
		this.rootPart = rootPart;
		this.name = name;
		this.bodyComponent = bodyComponent;
	}

	private void selfRender(TriConsumer<Texture, FloatArray, Vector2> triConsumer) {
		rootPart.traverseFromHere((part, vec, i) -> {
			Vector2 toCenter = new Vector2(vec).scl(70);
			FloatArray floatArray = FloatArray.with(
					bodyComponent.getBody().getPosition().x,
					bodyComponent.getBody().getPosition().y,
					part.getTexture().getWidth(),
					part.getTexture().getHeight(),
					part.getRotation(),
					bodyComponent.getBody().getAngle()*MathUtils.radiansToDegrees
				);
			triConsumer.accept(part.getTexture(), floatArray, toCenter);
			if (part.getElement() != null) {
				Texture[] elementTextures = part.getElement().getTextures();
				for (int j = 0; j < elementTextures.length; j++) {
					triConsumer.accept(elementTextures[j], floatArray, toCenter);
				}
			}
		});
	}

	public ShipComponent getShipComponent() {
		if (shipComponent == null) {
			shipComponent = new ShipComponent(this, new SelfRenderedComponent(this::selfRender));
		}
		return shipComponent;
	}

	public SelfRenderedComponent getSelfRenderedComponent() {
		return shipComponent.selfRenderedComponent;
	}

	public BodyComponent getBodyComponent() {
		return bodyComponent;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public float getX() {
		return bodyComponent.getBody().getPosition().x;
	}

	@Override
	public float getY() {
		return bodyComponent.getBody().getPosition().y;
	}

	public ShipProg getProg() {
		return prog;
	}

	public Part getRootPart() {
		return rootPart;
	}

	public ShipProgInterface getInterface() {
		if (shipProgInterface == null) {
			shipProgInterface = new ShipProgInterface();
		}
		return shipProgInterface;
	}

	public Ship setProg(ShipProg prog) {
		this.prog = prog;
		return this;
	}

	public void updateProg(float deltaTime) {
		if (prog != null) {
			prog.update(deltaTime);
		}
	}

	public class ShipProgInterface {
		protected ShipProgInterface() {
		}

		public ControlInput getI(String nameSpace, String key) {
			Array<ControlInput> outs = new Array<>(1);
			rootPart.traverseFromHere((part, vec, i) -> {
				if (part.getElement() != null) {
					ControlIOComponent ioComponent = part.getElement().getIOComponent();
					if (ioComponent.getNameSpace().equals(nameSpace)) {
						outs.add(ioComponent.getInput(key));
					}
					
				}
			});
			if (outs.size > 0) {
				return outs.first();
			} else {
				return null;
			}
		}

		public ControlOutput getO(String nameSpace, String key) {
			Array<ControlOutput> outs = new Array<>(1);
			rootPart.traverseFromHere((part, vec, i) -> {
				if (part.getElement() != null) {
					ControlIOComponent ioComponent = part.getElement().getIOComponent();
					if (ioComponent.getNameSpace().equals(nameSpace)) {
						outs.add(ioComponent.getOutput(key));
					}
				}
			});
			if (outs.size > 0) {
				return outs.first();
			} else {
				return null;
			}
		}
	
		public void status(String str) {
			status = str;
		}
	}
}