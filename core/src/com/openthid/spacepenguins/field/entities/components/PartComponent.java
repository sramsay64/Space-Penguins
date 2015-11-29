package com.openthid.spacepenguins.field.entities.components;

import com.badlogic.ashley.core.Component;

public class PartComponent implements Component {

	public PartType partType;
	public PartShape partShape;
	public MaterialType materialType;

	public String getPartFilename() {
		String x = null;
		switch (partShape) {
		case CIRCLE:
			break;
		case DOUBLETRIANGLE:
			break;
		case SQUARE1x1:
			break;
		case SQUARE1x2:
			break;
		case SQUARE1x3:
			break;
		case SQUARE2x1:
			break;
		case SQUARE2x2:
			break;
		case SQUARE2x3:
			break;
		case SQUARE3x1:
			break;
		case SQUARE3x2:
			break;
		case TRIANGLE:
			break;
		}
		return materialType.getFilename() + x + ".png";
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
		WOOD ( 100,  200,  50,  50, 0, "kenney_physics/Wood elements/elementWood"),
		STONE(6000,  500, 900, 900, 10, "kenney_physics/Stone elements/elementStone"),
		METAL(3000, 2000, 100, 100, 0, "kenney_physics/Metal elements/elementMetal"),
		GLASS(9000,   50,  10,  10, 0, "kenney_physics/Glass elements/elementGlass"),
		FUEL ( 150,  100, 100, 100, 100, "kenney_physics/Explosive elements/elementExplosive");

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