package model.generators;

import java.util.Random;
import utils.generics.Area;
import utils.generics.Location;
import utils.enums.EntityType;

//Classe astratta che modella il generico generatore
public abstract class GeneratorImpl implements Generator {

    private int                 genDelay;
    private int                 genCount;
	private int                 countdown;
	private EntityType          type;
	private Area                area;

	//Costruttore
	public GeneratorImpl(final EntityType t, final int d) {
	    this.genDelay = d;
	    this.genCount = 0;
	    this.area = new Area(0.125, 0.0972);
		this.type = t;
		this.countdown = 0;
	}
					
	@Override
	public void update() {
		this.countdown++;
		if (this.countdown >= this.genDelay)
			this.countdown = 0;
	}
	
	@Override
	public boolean canGen() {
		return this.countdown == 0;
	}
	
	//Metodo che aumenta il counter di entità generate
    protected void incrementGenCount() {
        this.genCount++;
    }
        
    //Metodo che crea una posizione randomica in cui generare
    protected Location generateRandomLocation() {
        Random rnd = new Random();
        double x = 1.8 + 0.2 * rnd.nextDouble();
        double y = 0.15 + 0.70 * rnd.nextDouble();
        return new Location(x, y, this.area);
    }

	//Metodi getter e setter
    public int getGenEntitiesCount() {
        return this.genCount;
    }      
       
    protected Area getArea() {
        return this.area;
    }
    
    protected EntityType getType() {
        return this.type;
    }
		
	public void setGenDelay(final int d) {
		this.genDelay = d;
	}
	
	public void setGenEntityType(final EntityType t) {
		this.type = t;
	}
	
	public void setGenEntityArea(final Area a) {		
		this.area = a;
	}
}