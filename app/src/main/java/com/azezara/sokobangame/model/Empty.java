package com.azezara.sokobangame.model;

public class Empty extends Enterable {
	public Empty(int x, int y) {
		super(x,y);
		this.symbolKey = '.';
		this.x = x;
		this.y = y;
	}

	public String toString() {
		if (this.hasCrate) {
			return "x";
		} else if (this.hasWorker) {
			return "w";
		} else {
			return "" + this.symbolKey;
		}
	}

}
