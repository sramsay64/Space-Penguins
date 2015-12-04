package com.openthid.spacepenguins.field.entities.ship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Part {

	private static HashMap<String, Texture> textureCache = new HashMap<>(275);

	private PartType partType;
	private PartShape partShape;
	private MaterialType materialType;
	private PartRotation partRotation;

	private Part[] norths = {null};
	private Part[] souths = {null};
	private Part[] easts = {null};
	private Part[] wests = {null};
	private Ship ship;

	public Part(PartType partType, PartShape partShape, MaterialType materialType) {
		this(partType, partShape, materialType, PartRotation.NONE);
	}

	public Part(PartType partType, PartShape partShape, MaterialType materialType, PartRotation partRotation) {
		this.partType = partType;
		this.partShape = partShape;
		this.materialType = materialType;
		this.partRotation = partRotation;
	}

	public String getPartFilename() {
		if (materialType == MaterialType.META) {
			throw new RuntimeException("Incorrect Use of MaterialType.META");
		}
		return materialType.getFilename() + partShape.toString() + "-" + partType.toString() + ".png";
	}

	public Texture getTexture() {
		String key = getPartFilename();
		if (!textureCache.containsKey(key)) {
			textureCache.put(key, new Texture(key));
			Gdx.app.log("LAZY", "Loaded new part texture '" + key + "'");
		}
		return textureCache.get(key);
	}

	public Part[] getNorths() {
		return norths;
	}

	public Part[] getSouths() {
		return souths;
	}

	public Part[] getEasts() {
		return easts;
	}

	public Part[] getWests() {
		return wests;
	}

	public Ship getShip() {
		return ship;
	}

	public PartType getPartType() {
		return partType;
	}

	public PartShape getPartShape() {
		return partShape;
	}

	public MaterialType getMaterialType() {
		return materialType;
	}

	public float getRotation() {
		return partRotation.getRotationDegrees();
	}

	public void setup(Part[] norths, Part[] souths, Part[] easts, Part[] wests) {
		if (norths != null) {this.norths = norths;}
		if (souths != null) {this.souths = souths;}
		if (easts  != null) {this.easts  =  easts;}
		if (wests  != null) {this.wests  =  wests;}
	}

	public void traverseFromHere(BiFunction<Part, Vector2, Consumer<Integer>> action) {
		traverse(action, new ArrayList<>(), new Vector2(), 0);
	}

	public void traverse(BiFunction<Part, Vector2, Consumer<Integer>> action, ArrayList<Part> traversed, Vector2 poistionFromRoot, int distanceFromRoot) {
		action.apply(this, poistionFromRoot).accept(distanceFromRoot);
		for (int i = 0; i < norths.length; i++) {
			if (norths[i] != null && !traversed.contains(norths[i])) {
				traversed.add(norths[i]);
				norths[i].traverse(action, traversed, new Vector2(poistionFromRoot).add(0, 1)/*TODO Upgrade for large parts*/, distanceFromRoot+1);
			}
		}
		for (int i = 0; i < souths.length; i++) {
			if (souths[i] != null && !traversed.contains(souths[i])) {
				traversed.add(souths[i]);
				souths[i].traverse(action, traversed, new Vector2(poistionFromRoot).add(0, -1), distanceFromRoot+1);
			}
		}
		for (int i = 0; i < easts.length; i++) {
			if (easts[i] != null && !traversed.contains(easts[i])) {
				traversed.add(easts[i]);
				easts[i].traverse(action, traversed, new Vector2(poistionFromRoot).add(1, 0), distanceFromRoot+1);
			}
		}
		for (int i = 0; i < wests.length; i++) {
			if (wests[i] != null && !traversed.contains(wests[i])) {
				traversed.add(wests[i]);
				wests[i].traverse(action, traversed, new Vector2(poistionFromRoot).add(-1, 0), distanceFromRoot+1);
			}
		}
	}

	public static enum PartType {
		SOLID(true, false, false),
		HOLLOW(false, false, false),
		HOLLOWDAMAGED(true, true, false),
		SOLIDDAMAGED(true, true, false),
		SOLIDCRACKED(true, true, true);

		private boolean filled;
		private boolean damaged;
		private boolean cracked;
		
		private PartType(boolean filled, boolean damaged, boolean cracked) {
			this.filled = filled;
			this.damaged = damaged;
			this.cracked = cracked;
		}
		
		public boolean isFilled() {
			return filled;
		}
		
		public boolean isDamaged() {
			return damaged;
		}
		
		public boolean isCracked() {
			return cracked;
		}
	}

	public static enum PartShape {
		CIRCLE(1,1,false),
		TRIANGLE(1,1,false),
		DOUBLETRIANGLE(2,1,false),
		SQUARE1x1(1,1,true),
		SQUARE2x1(2,1,true),
		SQUARE3x1(3,1,true),
		SQUARE3x2(3,2,true),
		SQUARE2x2(2,2,true),
		SQUARE1x2(1,2,true),
		SQUARE1x3(1,3,true),
		SQUARE2x3(2,3,true);
		
		private int width;
		private int hieght;
		private boolean square;
		
		private PartShape(int width, int hieght, boolean square) {
			this.width = width;
			this.hieght = hieght;
			this.square = square;
		}
		
		public int getWidth() {
			return width;
		}
		
		public int getHieght() {
			return hieght;
		}
		
		public boolean isSquare() {
			return square;
		}
	}

	public static enum MaterialType {
		META (9000,  500, 1000, 100, 100, null),
		WOOD ( 100,  200,   50,  50, 30, "kenney/Wood-elements/"),
		STONE(6000,  500,  900, 900, 10, "kenney/Stone-elements/"),
		METAL(3000, 2000,  100, 100, 500, "kenney/Metal-elements/"),
		GLASS(9000,   50,   10,  10, 100, "kenney/Glass-elements/"),
		FUEL ( 150,  100,  100, 100, 100, "kenney/Explosive-elements/");

		private int heatTolerance;
		private int impactTolerance;
		private int stretchTolerance;
		private int weight;
		private int cost;
		private String filename;
		
		private MaterialType(int heatTolerance, int impactTolerance, int stretchTolerance, int weight, int cost, String filename) {
			this.heatTolerance = heatTolerance;
			this.impactTolerance = impactTolerance;
			this.stretchTolerance = stretchTolerance;
			this.weight = weight;
			this.cost = cost;
			this.filename = filename;
		}
		
		public int getHeatTolerance() {
			return heatTolerance;
		}

		public int getImpactTolerance() {
			return impactTolerance;
		}

		public int getStretchTolerance() {
			return stretchTolerance;
		}

		public int getWeight() {
			return weight;
		}
		
		public int getCost() {
			return cost;
		}

		public String getFilename() {
			return filename;
		}
	}

	public static enum PartRotation {
		NONE(0),
		QUARTER(90),
		HALF(180),
		THREEQUARTER(270);
		
		private float rotationDegrees;

		private PartRotation(float rotationDegrees) {
			this.rotationDegrees = rotationDegrees;
		}

		public float getRotationDegrees() {
			return rotationDegrees;
		}
	}
}