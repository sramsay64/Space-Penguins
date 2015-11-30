package com.openthid.spacepenguins.field.entities;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Part {

	private static HashMap<String, Texture> textureCache = new HashMap<>(275);

	private PartType partType;
	private PartShape partShape;
	private MaterialType materialType;

	private Part north = null;
	private Part south = null;
	private Part east = null;
	private Part west = null;

	public Part(PartType partType, PartShape partShape, MaterialType materialType) {
		this.partType = partType;
		this.partShape = partShape;
		this.materialType = materialType;
	}

	public String getPartFilename() {
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

	public Part getNorth() {
		return north;
	}

	public Part getSouth() {
		return south;
	}

	public Part getEast() {
		return east;
	}

	public Part getWest() {
		return west;
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

	public void setNorth(Part north) {
		this.north = north;
	}

	public void setSouth(Part south) {
		this.south = south;
	}

	public void setEast(Part east) {
		this.east = east;
	}

	public void setWest(Part west) {
		this.west = west;
	}

	public void setPartType(PartType partType) {
		this.partType = partType;
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
		WOOD ( 100,  200,  50,  50, 0, "kenney/Wood-elements/"),
		STONE(6000,  500, 900, 900, 10, "kenney/Stone-elements/"),
		METAL(3000, 2000, 100, 100, 0, "kenney/Metal-elements/"),
		GLASS(9000,   50,  10,  10, 0, "kenney/Glass-elements/"),
		FUEL ( 150,  100, 100, 100, 100, "kenney/Explosive-elements/");

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
}