package model.entities;

import java.util.List;
import utils.generics.Location;
import utils.enums.Direction;
import utils.enums.EntityType;

//Classe astratta che modella una entità attiva generica
public abstract class LivingEntity implements Entity {

	private Direction  direction; 
	private EntityType id; 
	private Location   location; 
	private int        currentLife;
	private int        maxLife;  
	private double     speed; 
	private double     enemySpeed; 
	private boolean    removable;
	private Weapon     weapon;
		
	//Costruttore
	public LivingEntity(final EntityType id, final int maxHp, final double s) {
	    this.id = id;
	    this.maxLife = maxHp;
	    this.currentLife = maxHp;
	    this.enemySpeed = s;
	    this.speed = s;
	    this.removable = false;
	}
		
	//Metodo che aggiorna la posizione
	protected void updateLocation() {		
		this.direction.moveLocation(this.location, this.speed);	
	}
		
	//Metodo che verifica se l'arma può sparare
	public boolean canShoot() {
		if (this.weapon != null)
			return this.weapon.isReadyToShoot();

		return false;
	}
		
	//Metodo che aziona l'arma
	public List<Projectile> attack() {		
		return this.weapon.shoot(new Location(this.location));
	}
	
	//Metodo per il cooldown dell'arma
	protected void coolDownWeapon() {
		if (this.weapon != null)
			this.weapon.coolDown();			
	}
	
	//Metodo che diminuisce la salute se danneggiati
	public void loseHp(final int dmg) {
		this.currentLife -= dmg;
		if (this.currentLife <= 0) {
			this.currentLife = 0;
			this.removable = true;
		}			
	}
		
	//Metodo che aumenta la salute se viene presa una cura
	public void acquireHp(final int inc) {
		this.currentLife += inc;
		if (this.currentLife > this.maxLife)
			this.currentLife = this.maxLife;
	}
	
	//Metodi getter e setter				
	public int getRemainingLife() {
		return this.currentLife;
	}
		
	public Direction getDirection() {
		return this.direction;	
	}
	
	public Weapon getWeapon() {
		return this.weapon;
	}
	
	public int getMaximumLife() {
		return this.maxLife;
	}
	
    public double getSpeed() {
        return this.speed;
    }
    	
	public Location getLocation() {
		return this.location;
	}
			
	public EntityType getId() {
		return this.id;
	}	
	
    public void setRemovable() {
        this.removable = true;
    }
	
    public void setWeapon(final Weapon w) {
        this.weapon = w;
    }
                
    public void setVelocity(final double s) {
        if ((s / this.enemySpeed) <= 3)
            this.speed = s;      
    }
    
    public void setDirection(final Direction d) {
        this.direction = d;  
    }
    
    public void setLocation(final Location loc) {
        this.location = loc;        
    }	
	
	public boolean toRemove() {
		return this.removable;
	}
}