package com.openthid.spacepenguins.field.entities.ship;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.openthid.spacepenguins.field.entities.SpaceObject;
import com.openthid.spacepenguins.field.entities.components.ControlIOComponent;
import com.openthid.spacepenguins.field.entities.components.MassComponent;
import com.openthid.spacepenguins.field.entities.components.OrbitComponent;
import com.openthid.spacepenguins.field.entities.components.PositionComponent;
import com.openthid.spacepenguins.field.entities.components.RotationComponent;
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
	private OrbitComponent orbitComponent;
	private PositionComponent positionComponent;
	private RotationComponent rotationComponent;

	private String name;

	public Part getRootPart() {
		return rootPart;
	}

	public Ship(Part rootPart, String name, OrbitComponent orbitComponent, PositionComponent positionComponent, RotationComponent rotationComponent) {
		this.rootPart = rootPart;
		this.name = name;
		this.orbitComponent = orbitComponent;
		this.positionComponent = positionComponent;
		this.rotationComponent = rotationComponent;
	}

	private void selfRender(TriConsumer<Texture, FloatArray, Vector2> triConsumer) {
		rootPart.traverseFromHere((part, vec, i) -> {
			Vector2 toCenter = new Vector2(vec).scl(70);
			FloatArray floatArray = FloatArray.with(
					positionComponent.x,
					positionComponent.y,
					part.getTexture().getWidth(),
					part.getTexture().getHeight(),
					part.getRotation(),
					rotationComponent.angle
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

	public OrbitComponent getOrbitComponent() {
		return orbitComponent;
	}

	public MassComponent getMassComponent() {
		return orbitComponent.massComponent;
	}

	public PositionComponent getPositionComponent() {
		return positionComponent;
	}

	public RotationComponent getRotationComponent() {
		return rotationComponent;
	}

	public String getName() {
		return name;
	}

	public ShipProg getProg() {
		return prog;
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

	public ShipProgInterface getInterface() {
		if (shipProgInterface == null) {
			shipProgInterface = new ShipProgInterface();
		}
		return shipProgInterface;
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
	}
}