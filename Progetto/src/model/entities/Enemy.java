package model.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import utils.generics.Location;
import utils.enums.Direction;
import utils.enums.EntityType;

//Classe che descrive le astronavi nemiche
public class Enemy extends LivingEntity {
	
	//Costruttore
	public Enemy(final int maxHp, final double speed, final Location loc, final Direction dir, final Weapon w) {
		super(EntityType.ENEMY, maxHp, speed);
		this.setLocation(new Location(loc));
		this.setDirection(dir);
		this.setWeapon(w);
	}
	
	@Override
	public void update() {				
		this.coolDownWeapon();
		this.generateRandomMovement();
		this.updateLocation();
		this.boundaryControl();
	}
	
	//Metodo che genera un movimento casuale
	private void generateRandomMovement() {
		Random rnd = new Random();	
		if (rnd.nextDouble() < 0.0070) {
			int dir = rnd.nextInt(2);
			if (dir == 0)
				this.setDirection(this.getDirection().moveLeft());
			else if (dir == 1)
				this.setDirection(this.getDirection().moveRight());	
		}
	}
	
	//Metodo che controlla che i nemici non escano dai limiti
	public void boundaryControl() {				
		if (this.getLocation().getX() < -0.3) {
			this.getLocation().setX(-0.25);
			this.setDirection(this.getRandomDirection(this.getDirection()));
		}
		if (this.getLocation().getY() > 1.3) {
			this.getLocation().setY(1.25);
			this.setDirection(this.getRandomDirection(this.getDirection()));
		}
		if (this.getLocation().getY() < -0.3) {
			this.getLocation().setY(-0.25);
			this.setDirection(this.getRandomDirection(this.getDirection()));
		}
		if (this.getLocation().getX() > 2) {
			this.getLocation().setX(1.95);
			this.setDirection(this.getRandomDirection(this.getDirection()));
		}
	}
	
	//Metodo getter
	Direction getRandomDirection(final Direction dir) {	
		List<Direction> directions = new ArrayList<Direction>(Arrays.asList(Direction.values()));
		directions.remove(dir);			
		Random rnd = new Random();
		
		return directions.get(rnd.nextInt(Direction.values().length - 1));	
	}
}