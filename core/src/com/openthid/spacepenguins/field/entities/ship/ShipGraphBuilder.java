package com.openthid.spacepenguins.field.entities.ship;

import java.util.Comparator;
import java.util.TreeMap;

import com.badlogic.gdx.math.Vector2;

public class ShipGraphBuilder {

	private TreeMap<Vector2, Part> map;

	public ShipGraphBuilder() {
		map = new TreeMap<>(new Comparator<Vector2>() {
			public int compare(Vector2 a, Vector2 b) {
				if (a.x == b.x && a.y == b.y) {
					return 0;
				}
				//Vectors Form a Poset so this is bad math
				//We should however follow the law: compare(a, b) = - compare(b, a)
				if (a.x != b.x) {
					if (a.x > b.x) {
						return 1;
					}
					return -1;
				} else {
					if (a.y > b.y) {
						return 1;
					}
					return -1;
				}
			};
		});
	}

	public Part get(Vector2 positionFromRoot) {
		if (map.containsKey(positionFromRoot)) {
			return map.get(positionFromRoot);
		}
		return null;
	}

	public Part get(float x, float y) {
		return get(new Vector2(x, y));
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
		map.put(positionFromRoot, part);
		return this;//For method chaining
	}

	public void setupOn(Part rootPart) {
//		rootPart.setup(new Part[]{get(0,1)}, new Part[]{get(0,-1)}, new Part[]{get(1,0)}, new Part[]{get(-1,0)});
		rootPart.traverseFromHere((part, pos) -> (i) -> {
			part.setup(
					new Part[]{getNorthOf(pos)},
					new Part[]{getSouthOf(pos)},
					new Part[]{getEastOf(pos)},
					new Part[]{getWestOf(pos)}
				);
		});
	}
}