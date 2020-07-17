package com.azezara.sokobangame.model;

public class Target extends Enterable {
	
	public Target(int x, int y) {
		super(x,y);
		this.symbolKey = '+';
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		if (this.hasCrate) {
			return "X";
		} else if (this.hasWorker) {
			return "W";
		} else {
			return "" + this.symbolKey;
		}
	}
}
