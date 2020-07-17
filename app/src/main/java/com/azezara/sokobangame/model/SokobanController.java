package com.azezara.sokobangame.model;

import java.util.ArrayList;

public class SokobanController {
	public Game game;

	public void runLevel(String levelName, String levelSrc) {
		this.game = new Game();
		game.addLevel(levelName, 6, 5, levelSrc);
	}

	public String getLevelInfo() {
		game.setCompletedCount();
		String levelInfo = String.format("Move Count: %s | Number of targets: %s | Count of crates on targets: %s",
											game.currentLevel.getMoveCount(), game.currentLevel.getTargetCount(), game.currentLevel.getCompletedCount());

		return levelInfo;
	}

	public int[] getPlayerLocation() {
		int[] worker = this.game.getWorkerPoint();

		return worker;
	}

	public ArrayList getView() {
		Level currentLevel = this.game.currentLevel;
		ArrayList<String> view = new ArrayList<String>();
		int z = 0;
		for(int x = 0; x < currentLevel.getHeight(); x ++) {
			for (int y = 0; y < currentLevel.getWidth(); y ++) {
				switch (currentLevel.allPlaceables[x][y].toString()) {
					case (".") : {
						view.add("emptytile");
						break;
					}
					case("+") : {
						view.add("targettile");
						break;
					}
					case("W") :
					case("w") : {
						view.add("playertile");
						break;
					}
					case("x") :
					case("X") : {
						//view[z] = "cratetile";
						view.add("cratetile");
						break;
					}
					case("#"):  {
						view.add("walltile");
						break;
					}
				}
			}
		}

		return view;
	}

}
