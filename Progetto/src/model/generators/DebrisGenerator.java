package model.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.entities.Debris;
import utils.enums.EntityType;

//Classe che genera i vari detriti durante la partita
public class DebrisGenerator extends GeneratorImpl {
	
	private final double speed;
	
	//Costruttore
	public DebrisGenerator(final int delay, final double s) {
		super(EntityType.DEBRIS, delay);
		this.speed = s;
	}

	@Override
	public List<Debris> gen() {
		List<Debris> debris = new ArrayList<>();		
		Random rnd = new Random();	
		if (rnd.nextInt(2) == 1) {			
		    debris.add(new Debris(this.generateRandomLocation(), this.speed));
			this.incrementGenCount();			
		}				
		return debris;
	}
}