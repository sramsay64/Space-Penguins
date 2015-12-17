package com.openthid.spacepenguins.field.entities.ship;

import java.util.Arrays;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.openthid.spacepenguins.field.entities.ship.elements.Element;
import com.openthid.util.FunctionalUtils;

public class ShipGraphBuilder {

	private TreeMap<Vector2, Part> partMap;
	private TreeMap<Vector2, Function<Part, Element>> elementMap;

	public ShipGraphBuilder() {
		partMap = new TreeMap<>(FunctionalUtils.VECTOR2COMPARATOR);
		elementMap = new TreeMap<>(FunctionalUtils.VECTOR2COMPARATOR);
	}

	private Function<Part, Element> getElementSupplier(Vector2 positionFromRoot) {
		if (elementMap.containsKey(positionFromRoot)) {
			return elementMap.get(positionFromRoot);
		}
		return null;
	}

	private Part get(Vector2 positionFromRoot) {
		if (partMap.containsKey(positionFromRoot)) {
			return partMap.get(positionFromRoot);
		}
		return null;
	}

	private Part getFrom(Vector2 pos, int x, int y) {
		return get(new Vector2(pos).add(x, y));
	}

	private Part getNorthOf(Vector2 pos) {
		return getFrom(pos, 0, 1);
	}

	private Part getSouthOf(Vector2 pos) {
		return getFrom(pos, 0, -1);
	}

	private Part getEastOf(Vector2 pos) {
		return getFrom(pos, 1, 0);
	}

	private Part getWestOf(Vector2 pos) {
		return getFrom(pos, -1, 0);
	}

	public ShipGraphBuilder add(int x, int y, Part part) {
		return add(new Vector2(x, y), part);
	}

	public ShipGraphBuilder add(Vector2 positionFromRoot, Part part) {
		partMap.put(positionFromRoot, part);
		return this;//For method chaining
	}

	public ShipGraphBuilder add(int x, int y, Function<Part, Element> elementSupplier) {
		return add(new Vector2(x, y), elementSupplier);
	}

	public ShipGraphBuilder add(Vector2 positionFromRoot, Function<Part, Element> elementSupplier) {
		elementMap.put(positionFromRoot, elementSupplier);
		return this;//For method chaining
	}

	public void setupOn(Ship ship, Consumer<Entity> entityConsumer) {
		ship.getRootPart().traverseFromHere((part, pos, i) -> {
			part.setup(
					new Part[]{getNorthOf(pos)},
					new Part[]{getSouthOf(pos)},
					new Part[]{getEastOf(pos)},
					new Part[]{getWestOf(pos)},
					ship
				);
			if (elementMap.containsKey(pos)) {
				part.setElement(getElementSupplier(pos).apply(part));
				Entity elementEntity = new Entity();
				Arrays.stream(part.getElement().getComponents()).forEach(component -> {
					elementEntity.add(component);
				});
				entityConsumer.accept(elementEntity);
			}
		});
	}
}