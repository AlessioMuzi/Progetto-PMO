package model.generators;

import java.util.List;
import model.entities.Entity;
import utils.generics.Area;
import utils.enums.EntityType;

//Interfaccia che modella un generatore generico
public interface Generator {

	//Metodo che genera entit� generiche
	List<? extends Entity> gen();
	
	//Metodi che aggiornano il timer per permettere di generare entit� e stabiliscono se � possibile farlo
	void update();
	boolean canGen();
		
	//Metodi getter e setter
	void setGenDelay(final int delay);
	void setGenEntityType(final EntityType type);
	void setGenEntityArea(final Area area);
	int getGenEntitiesCount();
}