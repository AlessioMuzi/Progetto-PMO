package model.levels;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import model.entities.*;
import model.entities.PowerUp.Bonus;
import model.entities.Debris.DebrisType;
import utils.generics.*;
import utils.enums.*;

//Classe che implementa la logica di gioco e racchiude tutte le entità attive
public class GameLogicImpl implements GameLogic {

	private MatchStatus      status = MatchStatus.RUNNING;
	private Optional<String> latestPowerup = Optional.empty();
	private final int        fps;
	private int              score;
	private Level            lvl;
	private static Spaceship player;
	private List<Enemy>      enemies;
	private List<Debris>     debris;
	private List<Projectile> playerProjectiles;
	private List<Projectile> enemyProjectiles;
	private List<Entity>     deadEntities;
	private List<PowerUp>    powerups;
	private final int        hitDuration;
	private final int        explosionDuration;
	private final int        sparkleDuration;
	
	//Costruttore
	public GameLogicImpl(final int fps, final int id, final int diff) {
		this.latestPowerup = Optional.empty();
		this.fps = fps;
		this.score = 0;	
		this.lvl = LevelFactory.createLevel(fps, id, diff);	
		this.enemies = new ArrayList<>();
		this.debris = new ArrayList<>();
		this.powerups = new ArrayList<>();
		this.playerProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();
		this.deadEntities = new ArrayList<>();
		this.hitDuration = fps / 4;
		this.explosionDuration = fps / 2;
		this.sparkleDuration = fps * 2;
		if (GameLogicImpl.player == null || id == 1) {
			final Location loc = new Location(0.1, 0.5, new Area(0.125, 0.0972));
			final Weapon w = new WeaponImpl(EntityType.SPACESHIP, Direction.E, fps, 10, this.lvl.getLevelSpeed() * 2);
			GameLogicImpl.player = new Spaceship(100, this.lvl.getLevelSpeed() * 1.8, loc, Direction.E, 100, w);
		}
	}
	
	@Override
	public void updateUserInputs(final Optional<Direction> dir, final boolean shoot) {
		if (dir.isPresent())
			GameLogicImpl.player.move(dir.get());	
		if (shoot && GameLogicImpl.player.canShoot())
			this.playerProjectiles.addAll(GameLogicImpl.player.attack());
	}
	
	@Override
	public void updateAll() {
		GameLogicImpl.player.update();
		this.lvl.update();
		this.enemies.forEach((x) -> {
			x.update();
			if (this.enemyShoot(x))
				this.enemyProjectiles.addAll(x.attack()); });
		this.playerProjectiles.forEach(x -> x.update());
		this.enemyProjectiles.forEach(x -> x.update());
		this.debris.forEach(x -> x.update());
		this.powerups.forEach(x -> x.update());
		if (this.playerProjectiles.size() > 0) {
			this.playerProjectiles.forEach(x -> {
				if (x.toRemove())
					this.deadEntities.add(x);  }); }
		if (this.enemyProjectiles.size() > 0) {
			this.enemyProjectiles.forEach(x -> {
				if (x.toRemove())
					this.deadEntities.add(x); }); }
		if (this.debris.size() > 0) {
			this.debris.forEach(x -> {
				if (x.toRemove())
					this.deadEntities.add(x); }); }
		if (this.powerups.size() > 0) {
			this.powerups.forEach(x -> {
				if (x.toRemove())
					this.deadEntities.add(x); }); }
		//Gestione delle collisioni
        List<Debris> debris = new ArrayList<Debris>();
        //Proiettili con entità nemiche
        if ((this.enemies.size() > 0) && (this.playerProjectiles.size() > 0)) {
            this.playerProjectiles.stream()
            .filter(x -> !x.toRemove())
            .forEach(x -> this.enemies.stream()
                    .filter(y -> !y.toRemove())
                    .forEach(y -> {
                        if (x.collideWith(y) && !this.deadEntities.contains(x)) {
                            y.loseHp(x.getDamage());
                            x.setRemovable();
                            if (y.toRemove()) {
                                debris.add(new Debris(DebrisType.EXPLOSION, y.getLocation(), this.explosionDuration));
                                this.deadEntities.add(y);
                                this.score += 10;
                            } else
                                debris.add(new Debris(DebrisType.HIT, y.getLocation(), this.hitDuration));
                            this.deadEntities.add(x);
                    } }));
        }
        //Proiettili nemici con il giocatore
        if (this.enemyProjectiles.size() > 0) {
            this.enemyProjectiles.stream()
            .filter(x -> !x.toRemove())
            .forEach(x -> {
                if (x.collideWith(GameLogicImpl.player)) {
                    GameLogicImpl.player.loseHp(x.getDamage());
                    x.setRemovable();
                    if (GameLogicImpl.player.toRemove()) {
                        debris.add(new Debris(DebrisType.EXPLOSION, GameLogicImpl.player.getLocation(), this.explosionDuration));
                        this.status = MatchStatus.LOST;
                        this.deadEntities.add(GameLogicImpl.player);
                    } else
                        debris.add(new Debris(DebrisType.HIT, GameLogicImpl.player.getLocation(), this.hitDuration));
                    this.deadEntities.add(x);
                } });
        }
        //Collisioni tra astronavi
        if ((this.enemies.size() > 0) && !GameLogicImpl.player.toRemove()) {
            this.enemies.stream()
            .filter(x -> !x.toRemove())
            .forEach(x -> {
                if (x.collideWith(GameLogicImpl.player)) {
                    GameLogicImpl.player.loseHp(20);
                    x.loseHp(20);
                    if (GameLogicImpl.player.toRemove()) {
                        debris.add(new Debris(DebrisType.EXPLOSION, GameLogicImpl.player.getLocation(), this.explosionDuration));
                        this.status = MatchStatus.LOST;
                        this.deadEntities.add(GameLogicImpl.player);
                    } else
                        debris.add(new Debris(DebrisType.HIT, GameLogicImpl.player.getLocation(), this.hitDuration));
                    if (x.toRemove()) {
                        debris.add(new Debris(DebrisType.EXPLOSION, x.getLocation(), this.explosionDuration));
                        this.deadEntities.add(x);
                    } else
                        debris.add(new Debris(DebrisType.HIT, x.getLocation(), this.hitDuration));
                } });
        }
        //Proiettili con asteroidi
        if ((this.debris.size() > 0) && (this.playerProjectiles.size() > 0)) {
            this.playerProjectiles.stream()
            .filter(x -> !x.toRemove())
            .forEach(x -> this.debris.stream()
                    .filter(y -> !y.toRemove())
                    .filter(y -> y.getType().equals(DebrisType.ASTEROID))
                    .forEach(y -> {
                        if (x.collideWith(y) && !this.deadEntities.contains(x) && !this.deadEntities.contains(y)) {                         
                            x.setRemovable();
                            y.setRemovable();
                            debris.add(new Debris(DebrisType.EXPLOSION, x.getLocation(), this.explosionDuration));
                            this.deadEntities.add(x);
                            this.deadEntities.add(y);                           
                    } }));
        }
        //Giocatore con powerup
        if ((this.powerups.size() > 0) && !GameLogicImpl.player.toRemove()) {
            this.powerups.stream()
            .filter(x -> !x.toRemove())
            .forEach(x -> {
                if (x.collideWith(GameLogicImpl.player)) {          
                    x.applyPowerup(GameLogicImpl.player);
                    x.setRemovable();
                    debris.add(new Debris(DebrisType.SPARK, GameLogicImpl.player.getLocation(), this.sparkleDuration));
                    this.latestPowerup = Optional.of(x.getBonus().getString());                     
                    this.deadEntities.add(x);
                } });
        }           
        //Giocatore con asteroidi
        if ((this.debris.size() > 0) && !GameLogicImpl.player.toRemove()) {
            this.debris.stream()
            .filter(x -> !x.toRemove())
            .filter(x -> x.getType().equals(DebrisType.ASTEROID))
            .forEach(x -> {
                if (x.collideWith(GameLogicImpl.player)) {
                    GameLogicImpl.player.loseHp(10);
                    x.setRemovable();
                    if (GameLogicImpl.player.toRemove()) {
                        debris.add(new Debris(DebrisType.EXPLOSION, GameLogicImpl.player.getLocation(), this.explosionDuration));
                        this.status = MatchStatus.LOST;
                        this.deadEntities.add(GameLogicImpl.player);
                    } else
                        debris.add(new Debris(DebrisType.HIT, GameLogicImpl.player.getLocation(), this.hitDuration));
                    this.deadEntities.add(x);
                } });
        }   
        this.debris.addAll(debris);
		this.lvl.spawn(this.enemies, this.debris, this.powerups);
		if ((this.enemies.size() <= 0) && this.lvl.playerWin() && this.status.equals(MatchStatus.RUNNING))
			this.status = MatchStatus.WON;
	}

	//Metodo che gestisce i proiettili dei nemici
	private boolean enemyShoot(final Enemy e) {
		if (e.canShoot()) {
			final Random rnd = new Random();
			final double proj = rnd.nextDouble();
			if (proj < (((1 / this.enemies.size()) + 0.2) / this.fps))
				return true;
		}
		return false;
	}

    //Metodi getter
	@Override
	public int getLife() {
		return GameLogicImpl.player.getRemainingLife();
	}

	@Override
	public int getShield() {
		return GameLogicImpl.player.getRemainingShield();
	}

	@Override
	public Location getPlayerLocation() {
		return GameLogicImpl.player.getLocation();
	}

	@Override
	public int getScores() {
		int s = this.score;
		this.score = 0;
		return s;
	}

	@Override
	public MatchStatus getMatchStatus() {
		return this.status;
	}
	
	@Override
	public Optional<String> getLatestPowerUp() {
		Optional<String> up = Optional.empty();
		if (this.latestPowerup.isPresent()) {			
			if (GameLogicImpl.player.getWeapon().getProjectilesCount() >= 8 && this.latestPowerup.get().contains(Bonus.ADD_PROJ.getString()))
			    up = Optional.of("Arma potenziata al massimo!");
			else if ((GameLogicImpl.player.getSpeed() / this.lvl.getLevelSpeed()) > 3d && this.latestPowerup.get().contains(Bonus.SPEED.getString()))
			    up = Optional.of("Motori potenziati al massimo!");
			else if ((GameLogicImpl.player.getWeapon().getDamage()) > 60 && this.latestPowerup.get().contains(Bonus.DMG.getString()))
			    up = Optional.of("Danno potenziato al massimo!");
			else
			    up = this.latestPowerup;
			this.latestPowerup = Optional.empty();
		}
		return up;
	}
	
	   @Override
	    public List<Entity> getEntitiesToDraw() {
	        if (!this.status.equals(MatchStatus.RUNNING))
	            return new ArrayList<>();
	        this.updateAll();
	        this.deadEntities.forEach(x -> {
	            if (x.getId().equals(EntityType.SPACESHIP))
	                this.status = MatchStatus.LOST;
	            else if (x.getId().equals(EntityType.ENEMY))
	                this.enemies.remove(x);
	            else if (x.getId().equals(EntityType.DEBRIS))
	                this.debris.remove(x);
	            else if (x.getId().equals(EntityType.POWERUP))
	                this.powerups.remove(x);
	            else if (x.getId().equals(EntityType.PROJECTILE)) {
	                final Projectile p = (Projectile) x;
	                if (p.getParentID().equals(EntityType.SPACESHIP))
	                    this.playerProjectiles.remove(p);
	                else
	                    this.enemyProjectiles.remove(p);
	            }  });
	        this.deadEntities = new ArrayList<>();
	        final List<Entity> entities = new ArrayList<>();
	        entities.addAll(this.enemies);
	        entities.addAll(this.debris);
	        entities.addAll(this.powerups);
	        entities.addAll(this.playerProjectiles);
	        entities.addAll(this.enemyProjectiles);

	        return entities;
	    }
}