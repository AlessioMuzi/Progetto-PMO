package model.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.entities.PowerUp;
import model.entities.PowerUp.Bonus;
import utils.enums.EntityType;

///Classe che genera i vari power up durante la partita
public class PowerUpGenerator extends GeneratorImpl {
	
	private final double speed;
		
	//Costruttore
	public PowerUpGenerator(final int delay, final double s) {
		super(EntityType.POWERUP, delay);
		this.speed = s;
	}

	@Override
	public List<PowerUp> gen() {
		List<PowerUp> powerUps = new ArrayList<>();		
		Random rnd = new Random();	
		
		if (rnd.nextInt(2) == 1) {	
				int bonus = rnd.nextInt(Bonus.values().length);
				Bonus tmp = Bonus.ADD_PROJ;			
				if (bonus == 0)
				    tmp = Bonus.CD;
				else if (bonus == 1)
				    tmp = Bonus.SHIELD;
				else if (bonus == 2)
				    tmp = Bonus.DMG;
				else if (bonus == 3)
				    tmp = Bonus.SPEED;
				else if (bonus == 4)
				    tmp = Bonus.HEAL;
				powerUps.add(new PowerUp(tmp, this.generateRandomLocation(), this.speed));
				this.incrementGenCount();			
		}				
		return powerUps;
	}	
}