package model.entities;

import utils.generics.Location;
import utils.enums.Direction;
import utils.enums.EntityType;

//Classe che gestisce un proiettile sparato da una qualsiasi arma
public class Projectile implements Entity {

	private final EntityType    id = EntityType.PROJECTILE;
	private final EntityType    parentID;
	private final Direction     direction;
	private final double        speed;
	private final int           damage;
	private Location            location;
	private boolean             removable;

	//Costruttore
	public Projectile(final EntityType id, final Location l, final Direction dir, final double s, final int dmg) {
		this.removable = false;
		this.parentID = id;
		this.damage = dmg;
		this.location = l;
		this.speed = s;
		this.direction = dir;
	}

	@Override
	public void update() {	
		this.direction.moveLocation(this.location, this.speed);
		if (this.location.getX() > 2 || this.location.getX() < -0.30 || this.location.getY() > 1.3 || this.location.getY() < -0.30)
			this.removable = true;
	}
	
	//Metodi getter e setter
    public int getDamage() {
        return this.damage;
    }
    
    public EntityType getId() {
        return this.id;
    }

    public EntityType getParentID() {
        return this.parentID;
    }

    public Location getLocation() {
        return this.location;
    }

    public Direction getDirection() {
        return this.direction;
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