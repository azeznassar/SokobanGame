package com.azezara.sokobangame.model;

public class Level {
	protected int targetCount = 0;
	protected int width;
	protected int height;
	protected String name;
	protected int moveCount;
	protected int completedCount = 0;
	protected Placeable [][] allPlaceables;
	protected String levelMapString;

	
	public Level(String levelName, int width, int height, String levelMapString) {
		this.name = levelName;
		this.width = width;
		this.height = height;
		this.levelMapString = levelMapString;
		this.allPlaceables = new Placeable[height][width];
		this.putPlaceablesInArray();
		this.setTargetCount();
	}
	
	protected Placeable makePlaceable(int x, int y, char symbol) {
		if (symbol == '.') {
			Empty placeable = new Empty(x, y);
			return placeable;
		} else if (symbol == 'x') {
			Empty placeable = new Empty(x, y);
			placeable.addCrate(new Crate(x, y));
			return placeable;
		} else if(symbol == 'w' || symbol == 'W') {
			Empty placeable = new Empty(x, y);
			placeable.addWorker(new Worker(x, y));
			return placeable;
		} else if (symbol == '+') {
			Target placeable = new Target(x, y);
			return placeable;
		} else {
			Wall placeable = new Wall(x, y);
			return placeable;
		}
	}
	
	protected void putPlaceablesInArray() {
		int index = 0;
		for (int x = 0; x < this.height; x ++) {
			for (int y = 0; y < this.width; y ++) {
				char symbol = this.levelMapString.charAt(index);
				Placeable newTile = this.makePlaceable(x, y, symbol);
				this.allPlaceables[x][y] = newTile;
				index ++;
			}
		}
	}
	
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getMoveCount() {
		return this.moveCount;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getCompletedCount() {
		return this.completedCount;
	}
	
	public void setTargetCount() {
		for (int x = 0; x < this.height; x ++) {
			for (int y = 0; y < this.width; y ++) { 
				if (this.allPlaceables[x][y].symbolKey == '+') {
					this.targetCount++;
				}
			}
		}
	}
	
	public int getTargetCount() {
		return this.targetCount;
	}
	
	public String getAllPlaceables() {
		String formattedString = "";
		for (int x = 0; x < this.height; x ++) { 
			for (int y = 0; y < this.width; y ++) { 
				Placeable theSymbol = this.allPlaceables[x][y];
				formattedString += theSymbol.toString();
			}
			formattedString += "\n";
		}
		return formattedString;	
	}
	
	public String toString() {
		String aNewLine = "\n";
		return getName() + aNewLine + getAllPlaceables() + "move " + getMoveCount() + aNewLine + "completed " + getCompletedCount() + " of " + getTargetCount() + aNewLine;
	}
}
