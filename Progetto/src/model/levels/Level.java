package model.levels;

import java.util.List;
import model.entities.Debris;
import model.entities.Enemy;
import model.entities.PowerUp;
import model.generators.*;

//Classe che gestisce tutta la logica del livello di gioco
public class Level {
	
	private final int        enemyCount;
	private final double     speed;	
	private EnemyGenerator   enemyGenerator;
	private DebrisGenerator  debrisGenerator;
	private PowerUpGenerator powerupGenerator;
	
	//Costruttore
	public Level(final int c, final double s, final int maxWave, final int enemyDelay, final int debrisDelay, final int powerupDelay) {
		this.enemyCount = c;
		this.speed = s;		
		enemyGenerator = new EnemyGenerator(this.enemyCount, maxWave, enemyDelay);
		debrisGenerator = new DebrisGenerator(debrisDelay, s);
		powerupGenerator = new PowerUpGenerator(powerupDelay, s);
	}
	
	//Metodo che controlla se il giocatore ha vinto
	public boolean playerWin() {
		return this.enemyCount <= this.enemyGenerator.getGenEntitiesCount();
	}
	
	//Metodo che aggiorna i generatori
	public void update() {
		this.enemyGenerator.update();
		this.debrisGenerator.update();
		this.powerupGenerator.update();
	}
		
	//Metodo che genera le entità
	public void spawn(final List<Enemy> enemies, final List<Debris> debris, final List<PowerUp> powerups) {
		if (this.enemyGenerator.canGen())
			enemies.addAll(this.enemyGenerator.gen());
		if (this.debrisGenerator.canGen())
			debris.addAll(this.debrisGenerator.gen());
		if (this.powerupGenerator.canGen())
		    powerups.addAll(this.powerupGenerator.gen());
	}

	//Metodi getter
	public double getLevelSpeed() {
		return this.speed;
	}	
	
    public EnemyGenerator getEnemyGenerator() {
        return this.enemyGenerator;
    }
 
    public DebrisGenerator getDebrisGenerator() {
        return this.debrisGenerator;
    }
 
    public PowerUpGenerator getPowerUpGenerator() {
       return this.powerupGenerator;
    }
}