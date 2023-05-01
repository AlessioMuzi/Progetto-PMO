package utils.generics;

//Classe che modella l'area occupata da un entità
public class Area {
		
	private final double width;
	private final double height;
	
	//Costruttore con parametro-copia
	public Area(final Area area) {
		this.width = area.width;
		this.height = area.height;
	}
	
	//Costruttore canonico
	public Area(final double w, final double h) {
		this.width = w;
		this.height = h;
	}
	
	//Metodi getter
	public double getWidth() {
		return this.width;
	}
	
	public double getHeight() {
		return this.height;
	}
}