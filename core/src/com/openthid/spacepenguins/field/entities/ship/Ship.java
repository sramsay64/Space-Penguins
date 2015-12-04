package com.openthid.spacepenguins.field.entities.ship;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;
import com.openthid.spacepenguins.field.entities.components.MassComponent;
import com.openthid.spacepenguins.field.entities.components.OrbitComponent;
import com.openthid.spacepenguins.field.entities.components.PositionComponent;
import com.openthid.spacepenguins.field.entities.components.SelfRenderedComponent;
import com.openthid.spacepenguins.field.entities.components.ShipComponent;
import com.openthid.util.TriConsumer;

public class Ship {

	private Part rootPart;

	private ShipComponent shipComponent;
	private OrbitComponent orbitComponent;
	private PositionComponent positionComponent;

	public Part getRootPart() {
		return rootPart;
	}

	public Ship(Part rootPart, OrbitComponent orbitComponent, PositionComponent positionComponent) {
		this.rootPart = rootPart;
		this.orbitComponent = orbitComponent;
		this.positionComponent = positionComponent;
	}

	public void selfRender(TriConsumer<Texture, FloatArray, Vector2> biFunCons) {
		rootPart.traverseFromHere((part, vec) -> (i) -> {
			Vector2 toCenter = new Vector2(vec).scl(70);
			FloatArray floatArray = FloatArray.with(
					positionComponent.x,// + toCenter.x,
					positionComponent.y,// + toCenter.y,
					part.getTexture().getWidth(),
					part.getTexture().getHeight(),
					part.getRotation(),
					10
				);
			biFunCons.accept(part.getTexture(), floatArray, toCenter);
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
}