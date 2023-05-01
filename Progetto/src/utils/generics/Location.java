package utils.generics;

//Classe che modella la posizione centrale di un'entità in un piano cartesiano
public class Location {
	
	private double x;
	private double y;
	private Area   area;
	
	//Costruttore con un parametro-copia
	public Location(final Location loc) {
		this.x = loc.x;
		this.y = loc.y;
		this.area = new Area(loc.area);
	}
		
	//Costruttore canonico
	public Location(final double x, final double y, final Area area) {		
		this.x = x;
		this.y = y;
		this.area = area;
	}
	
	//Metodi getter e setter
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public Area getArea() {
		return this.area;
	}
	
	public void setX(final double x) {
		this.x = x;
	}
	
	public void setY(final double y) {
		this.y = y;
	}
	
	public void setArea(final Area a) {
		this.area = a;
	}
}