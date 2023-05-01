package model.entities;

import utils.generics.Location;
import utils.enums.Direction;
import utils.enums.EntityType;

//Classe che modella i power up del giocatore
public class PowerUp implements Entity {
	
    private static final double SPEED = 1.30;
    private static final int    SHIELD = 20;
    private static final int    LIFE = 15;
    private static final int    DAMAGE = 2;
    private static final int    COOLDOWN = 2;
    
	//Enum che raccoglie i possibili power ups
	public enum Bonus {
	    //Aumento del numero di proiettili
		ADD_PROJ {
			public String getImageName() {
				return "addprojectile.png";
			} },
		//Diminuzione del cooldown di sparo
		CD {
			public String getImageName() {
				return "cooldown.png";
			} },
		//Aumento danni
		DMG {
			public String getImageName() {
				return "damage.png";
			} },
		//Cura il giocatore
		HEAL {
			public String getImageName() {
				return "heal.png";
			} },
		//Ripara lo scudo del giocatore
		SHIELD {
			public String getImageName() {
				return "shield.png";
			} },
		//Aumenta la velocità del giocatore
		SPEED {
			public String getImageName() {
				return "speed.png";
			} };
		
		//Metodo che restituisce la descrizione del power up
		public String getString() {
			if (this.equals(Bonus.ADD_PROJ))
				return "Armi potenziate!";
			else if (this.equals(Bonus.CD))
				return "Rateo di fuoco aumentato!";
			else if (this.equals(Bonus.DMG))
				return "Danno aumentato!";
			else if (this.equals(Bonus.HEAL))
				return "Scafo riparato!";
			else if (this.equals(Bonus.SHIELD))
				return "Scudi ricaricati!";
			else
				return "Velocita' aumentata!";			
		}
		public abstract String getImageName();
	}
	private final EntityType  id = EntityType.POWERUP; 
	private final Bonus       bonus; 
	private final double      speed;
	private Direction         direction = Direction.W; 
	private Location          location; 
	private boolean           removable; 

	//Costuttore
	public PowerUp(final Bonus b, final Location loc, final double s) {
		this.bonus = b;
		this.location = new Location(loc);
		this.speed = s;
		this.removable = false;
	}
	
	//Metodo che applica il powerup
	public void applyPowerup(final Spaceship player) {
		if (this.bonus.equals(Bonus.ADD_PROJ))
			player.getWeapon().increaseProjectiles();
		else if (this.bonus.equals(Bonus.CD))
			player.getWeapon().decreaseCooldown(COOLDOWN);
		else if (this.bonus.equals(Bonus.DMG))
			player.getWeapon().increaseDamage(DAMAGE);
		else if (this.bonus.equals(Bonus.HEAL))
			player.acquireHp(LIFE);
		else if (this.bonus.equals(Bonus.SHIELD))
			player.acquireShield(SHIELD);
		else
			player.setVelocity(player.getSpeed() * SPEED);
	}
	
    @Override
    public void update() {
        if (location.getX() < -0.30d)
           this.removable = true;
        else
           this.direction.moveLocation(this.location, this.speed);     
    }
	
	//Metodi getter e setter
	public Bonus getBonus() {
		return this.bonus;		
	}
	
    public EntityType getId() {
        return this.id;
    }
	
    public Location getLocation() {
        return this.location;
    }
	
	public void setRemovable() {
		this.removable = true;
	}
	
	public void setLocation(final Location loc) {
		this.location = loc;
	}
	
    public boolean toRemove() {
        return this.removable;
    }
}