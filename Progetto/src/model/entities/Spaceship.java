package model.entities;

import utils.generics.Location;
import utils.enums.Direction;
import utils.enums.EntityType;

//Classe che descrive l'astronave comandata dal giocatore
public class Spaceship extends LivingEntity {
    
    private int currentShield; 
    private int maxShield;
    
	//Costruttore
	public Spaceship(final int maxHp, final double s, final Location loc, final Direction dir, final int maxShield, final Weapon w) {
	    super(EntityType.SPACESHIP, maxHp, s);
		this.currentShield = maxShield; 
		this.maxShield = maxShield;
		this.setLocation(new Location(loc));
		this.setDirection(dir);
		this.setWeapon(w); 
	}
	
	@Override
    public void update() { 
        coolDownWeapon();           
    }	  
	    	
    @Override
    public void loseHp(final int dmg) {
         super.loseHp(this.loseShield(dmg));             
     }
    
	//Metodo che muove il giocatore nella direzione voluta
	public void move(final Direction dir) {	
		this.setDirection(dir);			
		this.updateLocation();
		this.boundaryControl();
	}

	//Metodo che controlla che il giocatore non esca dai confini
	private void boundaryControl() {	
		if (this.getLocation().getX() < 0.05)
			this.getLocation().setX(0.05);
		if (this.getLocation().getY() > 0.91)
			this.getLocation().setY(0.91);
		if (this.getLocation().getY() < 0.05)
			this.getLocation().setY(0.05);
		if (this.getLocation().getX() > 1.7)
			this.getLocation().setX(1.7);
	}
	
    //Metodo che aumenta lo scudo se viene riparato
    public void acquireShield(final int inc) {           
        this.currentShield += inc;
        if (this.currentShield > this.maxShield)
            this.currentShield = this.maxShield;
    }
    
    //Metodo che diminuisce lo scudo se colpiti
    public int loseShield(final int dmg) {
        int shield = currentShield;
        int actualDmg = 0;        
        currentShield -= dmg;
       
        if (shield != currentShield && currentShield < 0) { 
            actualDmg = Math.abs(currentShield);
            currentShield = 0;
        } else if (shield != currentShield && currentShield > 0) 
            return 0;
          else if (shield == currentShield && currentShield == 0)
            return dmg;
               
        return actualDmg;
    }
	    
    //Metodi getter e setter
	public int getRemainingShield() {
		return this.currentShield;
	}
	
    public void setShield(final int max) {
        this.maxShield = max;
    }
}