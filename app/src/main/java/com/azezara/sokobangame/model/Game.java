package com.azezara.sokobangame.model;

import java.util.ArrayList; 
import java.util.List;

public class Game {
	//Attributes
	protected int levelCount = 0;
	protected ArrayList<String> levelNames = new ArrayList<String>();
	public Level currentLevel = null;
	
	//Methods
	public int getLevelCount() { 
	    return levelCount;
	}
	
	public String getCurrentLevelName() {
		if (this.currentLevel != null) {
			return this.currentLevel.getName();
		} else {
			return "no levels";
		}
	}
	
	public List<String> getLevelNames() {
			return this.levelNames;
	}
	
	protected int[] getWorkerPoint() {
		Placeable[][] placeArray = this.currentLevel.allPlaceables;
		for (int x = 0; x < this.currentLevel.height; x ++) { 
			for (int y = 0; y < this.currentLevel.width; y ++) {
				if(placeArray[x][y].toString() == "W" || placeArray[x][y].toString() == "w") {
					int[] workerPoint = new int[] {x, y};
					return workerPoint;
				}
			}
		}
		return null;
	}
	
	protected void setCompletedCount() {
		this.currentLevel.completedCount = 0;
		for (int x = 0; x < this.currentLevel.height; x ++) { 
			for (int y = 0; y < this.currentLevel.width; y ++) {
				if(this.currentLevel.allPlaceables[x][y].toString() == "X") {
					this.currentLevel.completedCount++;
				}
			}
		}
	}
	
	private Placeable dest(Direction direction, int step) {
		int[] workerPoint = this.getWorkerPoint();
		Placeable destPlaceable = null;
		switch (direction) {
		case UP:
			destPlaceable = this.currentLevel.allPlaceables[workerPoint[0] - step][workerPoint[1]];
			break;
		case DOWN:
			destPlaceable = this.currentLevel.allPlaceables[workerPoint[0] + step][workerPoint[1]];
			break;
		case LEFT:
			destPlaceable = this.currentLevel.allPlaceables[workerPoint[0]][workerPoint[1] - step];
			break;
		case RIGHT:
			destPlaceable = this.currentLevel.allPlaceables[workerPoint[0]][workerPoint[1] + step];
			break;
		}
		return destPlaceable;
	}
	
	public String toString() {
		if (this.currentLevel != null) {
			return this.currentLevel.toString();
		} else {
			return "no levels";
		}
	}
	
	public void addLevel(String levelName, int width, int depth, String levelMapString) {
		Level level = new Level(levelName, width, depth, levelMapString);
		this.levelCount += 1;
		this.currentLevel = level;
		this.levelNames.add(levelName);
	}

	public void move(Direction direction) {
		int[] workerPoint = this.getWorkerPoint();
		Placeable theWorkerPlaceable = this.currentLevel.allPlaceables[workerPoint[0]][workerPoint[1]];
		Enterable theWorkerEnterable = (Enterable) theWorkerPlaceable;
		Placeable workerPlaceableDest = this.dest(direction, 1);
		
		if (workerPlaceableDest instanceof Empty || workerPlaceableDest instanceof Target) {
			Enterable workerDest = (Enterable) workerPlaceableDest;
			
			if (workerDest.hasCrate) {
				Placeable cratePlaceableDest = this.dest(direction, 2);
				
				if (cratePlaceableDest instanceof Empty || cratePlaceableDest instanceof Target) {
					Enterable crateDest = (Enterable) cratePlaceableDest;
					
					if (!crateDest.hasCrate) {
						workerDest.addWorker(theWorkerEnterable.theWorker);
						workerDest.updateWorker();
						theWorkerEnterable.removeWorker();
						crateDest.addCrate(workerDest.theCrate);
						crateDest.updateCrate();
						workerDest.removeCrate();
						this.currentLevel.moveCount++;
						return;
					}
				}
			} else {
				workerDest.addWorker(theWorkerEnterable.theWorker);
				workerDest.updateWorker();
				theWorkerEnterable.removeWorker();
				this.currentLevel.moveCount++;
				this.setCompletedCount();
		        return;
		  }
		}
	}
	
}
