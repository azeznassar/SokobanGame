package com.azezara.sokobangame.model;

public abstract class Enterable extends Placeable {
	protected Worker theWorker;
	protected Crate theCrate;
	protected boolean hasCrate;
	protected boolean hasWorker;
	
	public Enterable(int x, int y) {
		super(x, y);
	}
	
	public void addWorker(Worker worker) {
		this.theWorker = worker;
		this.hasWorker = true;
	}

	public void addCrate(Crate crate) {
		this.theCrate = crate;
		this.hasCrate = true;
	}
	
	public void updateCrate() {
		this.theCrate.x = this.x;
		this.theCrate.y = this.y;
	}
	
	public void removeCrate() {
		this.theCrate = null;
		this.hasCrate = false;
	}
	
	public void updateWorker() {
		this.theWorker.x = this.x;
		this.theWorker.y = this.y;
	}
	
	public void removeWorker() {
		this.theWorker = null;
		this.hasWorker = false;
	}
}
