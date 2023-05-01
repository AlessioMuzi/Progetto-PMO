package utils.enums;

import utils.generics.Location;

//Enum che raccoglie le possibili direzioni delle entità
public enum Direction {
    
	N(0) {
		public void moveLocation(final Location loc, final double s) {
			loc.setY(loc.getY() - s);
		} },
	NE(1) {
		public void moveLocation(final Location loc, final double s) {
			Direction.N.moveLocation(loc, s * 0.7071);
			Direction.E.moveLocation(loc, s * 0.7071);
		} },
	E(2) {
		public void moveLocation(final Location loc, final double s) {
			loc.setX(loc.getX() + s);
		} },
	SE(3) {
		public void moveLocation(final Location loc, final double s) {
			Direction.S.moveLocation(loc, s * 0.7071);
			Direction.E.moveLocation(loc, s * 0.7071);
		} },
	S(4) {
		public void moveLocation(final Location loc, final double s) {
			loc.setY(loc.getY() + s);
		} },
	SW(5) {
		public void moveLocation(final Location loc, final double s) {
			Direction.S.moveLocation(loc, s * 0.7071);
			Direction.W.moveLocation(loc, s * 0.7071);
		} },
	W(6) {
		public void moveLocation(final Location loc, final double s) {
			loc.setX(loc.getX() - s);
		} },
	NW(7) {
		public void moveLocation(final Location loc, final double s) {
			Direction.N.moveLocation(loc, s * 0.7071);
			Direction.W.moveLocation(loc, s * 0.7071);
		} };
    private int index; 
    
	//Costruttore
	Direction(final int i) {
		this.index = i;
	}

	//Metodi che gestiscono lo spostamento generico, a sinistra\destra e di 180° gradi
	public abstract void moveLocation(final Location loc, final double d);

	public Direction moveLeft() {
		return Direction.values()[(this.getIndex() + 1) % Direction.values().length];
	}

	public Direction moveRight() {
		return Direction.values()[(this.getIndex() + 7) % Direction.values().length];
	}

	public Direction flip() {
		return Direction.values()[(this.getIndex() + 4) % Direction.values().length];
	}
	
    //Metodi getter
    public double getRotation() {
        return ((this.getIndex() + 6) % Direction.values().length) * 45.0;
    }

    private int getIndex() {
        return this.index;
    }
}