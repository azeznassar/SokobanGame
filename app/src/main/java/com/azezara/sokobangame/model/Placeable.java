package com.azezara.sokobangame.model;

public abstract class Placeable {
	protected int x;
	protected int y;
	protected char symbolKey;
	
	public Placeable(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return "" + this.symbolKey;
	}
	
}
