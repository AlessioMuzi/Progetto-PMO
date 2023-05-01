package model.entities;

import utils.generics.Location;
import utils.enums.EntityType;

//Interfaccia che modella una generica entità
public interface Entity {
				
	//Metodo che verifica se avviene una collisione
	default Boolean collideWith(final Entity otherEntity) {
		if (otherEntity.getLocation().equals(this.getLocation()))
			return true;		
		double a = Math.abs(otherEntity.getLocation().getX() - this.getLocation().getX());
		double b = Math.abs(otherEntity.getLocation().getY() - this.getLocation().getY());
		if (a < (otherEntity.getLocation().getArea().getWidth() + this.getLocation().getArea().getWidth()) / 2 && b < (otherEntity.getLocation().getArea().getHeight() + this.getLocation().getArea().getHeight()) / 2)
			return true;

		return false;
	}
		
	//Metodo che aggiorna l'entità
	void update();
	
	//Metodi getter e setter
    Location getLocation();
    EntityType getId();
    void setLocation(final Location loc);	
	void setRemovable();
	boolean toRemove();
}