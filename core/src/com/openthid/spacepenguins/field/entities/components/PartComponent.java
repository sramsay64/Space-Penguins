package com.openthid.spacepenguins.field.entities.components;

import com.badlogic.ashley.core.Component;

public class PartComponent implements Component {
	public int heat;
	public int weight;

	public static class PartType {
		boolean filled;
		boolean cracked;
	}

	public static enum PartShape {
		FILLED, HOLLOW;
		
		private int x;
		private int hieght;
	}

	public static enum MaterialType {
		WOOD ( 100,  200,  50,  50, 0, "kenney_physics/Wood elements/elementWood000.png"),
		STONE(6000,  500, 900, 900, 10, "kenney_physics/Stone elements/elementStone000.png"),
		METAL(3000, 2000, 100, 100, 0, "kenney_physics/Metal elements/elementMetal000.png"),
		GLASS(9000,   50,  10,  10, 0, "kenney_physics/Glass elements/elementGlass000.png"),
		FUEL ( 150,  100, 100, 100, 100, "kenney_physics/Explosive elements/elementExplosive000.png");

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