package model.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.entities.Enemy;
import model.entities.Weapon;
import model.entities.WeaponImpl;
import utils.enums.Direction;
import utils.enums.EntityType;

//Classe che genera i vari nemici durante la partita
public class EnemyGenerator extends GeneratorImpl {
    
    private int                 maxWave;
    private int                 maxEnemyCount;
	private int                 minHp = 1;
	private int                 maxHp = 1;
	private int                 minDamage = 1;
	private int                 maxDamage = 10;
	private double              minSpeed = 1;
	private double              maxSpeed = 1;
	private int                 weaponCooldown = 30;
	
    //Costruttore
    public EnemyGenerator(final int limit, final int max, final int delay) {
        super(EntityType.ENEMY, delay);
        this.maxEnemyCount = limit;
        this.maxWave = max;
    }

	@Override
	public List<Enemy> gen() {
		List<Enemy> enemies = new ArrayList<>();
		Random rnd = new Random();	
		int toSpawn = rnd.nextInt(this.maxWave) + 1;
		for (int i = 0; i < toSpawn; i++) {	
			if (this.getGenEntitiesCount() < this.maxEnemyCount) {			
				int hp = this.minHp + rnd.nextInt(this.maxHp - this.minHp + 1);
				double speed = this.minSpeed + (this.maxSpeed - this.minSpeed) * rnd.nextDouble();			
				int dmg = this.minDamage + rnd.nextInt(this.maxDamage - this.minDamage + 1);	
				Direction dir = null;			
				int tmp = rnd.nextInt(3);				
				if (tmp == 0)
					dir = Direction.SW;
				else if (tmp == 1)
					dir = Direction.W;
				else
					dir = Direction.NW;
				Weapon w = new WeaponImpl(this.getType(), dir, this.weaponCooldown, dmg, this.maxSpeed * 1.5);
				enemies.add(new Enemy(hp, speed, this.generateRandomLocation(), dir, w));
				this.incrementGenCount();			
			} else
			    return enemies;
		}				
		return enemies;
	}
	
    //Metodi setter
	public void setEntityHpRange(final int min, final int max) {
		this.minHp = min;
		this.maxHp = max;	
	}
		
	public void setEntitySpeedRange(final double min, final double max) {
		this.minSpeed = min;
		this.maxSpeed = max;	
	}
	
	public void setEntityDamageRange(final int min, final int max) {
		this.minDamage = min;
		this.maxDamage = max;
	}
	
	public void setEntityWeaponCooldown(final int cd) {
		this.weaponCooldown = cd;	
	}
}