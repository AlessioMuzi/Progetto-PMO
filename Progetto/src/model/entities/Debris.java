package model.entities;

import utils.generics.Location;
import utils.enums.Direction;
import utils.enums.EntityType;

//Classe che modella i vari tipi di detriti
public class Debris implements Entity {
	
	//Enum che raccoglie i tipi di detriti
	public enum DebrisType {
	    //Esplosione
		EXPLOSION {
			public String getImageName() {
				return "explosion.gif";
			} },
		//Bersaglio colpito
		HIT {
			public String getImageName() {
				return "hit.gif";
			} },
		//Scintille
		SPARK {
			public String getImageName() {
				return "spark.gif";
			} },
		//Asteroide
		ASTEROID {
			public String getImageName() {
				return "asteroid.png";
			} };
		public abstract String getImageName();
	}

	private final EntityType id = EntityType.DEBRIS;
	private final DebrisType type; 
	private Direction        direction = Direction.W; 
	private Location         location; 
	private double           speed; 
	private int              countdown; 
	private boolean          removable; 

	//Costruttore per gli asteroidi
	public Debris(final Location loc, final double s) {
		this.type = DebrisType.ASTEROID;
		this.removable = false;
		this.location = new Location(loc);
		this.speed = s;
		this.countdown = 0;
	}
	
	//Costruttore per gli altri detriti
	public Debris(final DebrisType type, final Location loc, final int cd) {
		this.type = type;
		this.removable = false;
		this.location = new Location(loc);
		this.speed = 0;
		this.countdown = cd;
	}
	
    @Override
    public void update() {           
        if (!this.type.equals(DebrisType.ASTEROID)) {
            this.countdown--;
            if (this.countdown <= 0)
                this.removable = true;
        } else {
            if (location.getX() < -0.30d)
                this.removable = true;
            else if (this.speed > 0)     
                this.direction.moveLocation(this.location, this.speed);  
        }
    }	
	
	//Metodi getter e setter
	public DebrisType getType() {
		return this.type;
	}

    public EntityType getId() {
        return this.id;
    }
	
	public Location getLocation() {
		return this.location;
	}
	
	public void setLocation(final Location loc) {
		this.location = loc;
	}	
	
    public void setRemovable() {
        this.removable = true;  
    }
    
    public boolean toRemove() {
        return this.removable;
    }   
}