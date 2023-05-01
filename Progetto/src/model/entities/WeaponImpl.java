package model.entities;

import java.util.ArrayList;
import java.util.List;
import utils.generics.Area;
import utils.generics.Location;
import utils.enums.Direction;
import utils.enums.EntityType;

//Classe che gestisce le funzioni dell'arma, implementata con pattern Factory
public class WeaponImpl implements Weapon {

	private final EntityType   parentId;
	private final Direction    direction;
	private int                damage; 
	private int                cooldownTime;
	private int                actualCooldown;
	private int                projCount;
	private double             projSpeed;
		
	//Costruttore
	public WeaponImpl(final EntityType id, final Direction dir, final int cd, final int dmg, final double s) {
		this.parentId = id;
		this.direction = dir;
		this.damage = dmg;
		this.projSpeed = s;
		this.projCount = 1;
		this.cooldownTime = cd;
		this.actualCooldown = 0;
	}
	
	@Override
	public List<Projectile> shoot(final Location loc) {
		this.actualCooldown = this.cooldownTime;
		Location newLoc = new Location(loc);
		newLoc.setArea(new Area(loc.getArea().getWidth() * 0.5, loc.getArea().getHeight() * 0.5));
		List<Projectile> projectiles = new ArrayList<Projectile>();		
		List<Direction> projectilesDir = new ArrayList<Direction>();
				
		projectilesDir.add(this.direction);
		if (this.projCount % 2 == 0 || this.projCount > 5)
		    projectilesDir.add(this.direction.flip());
		if (this.projCount > 2) {
		    projectilesDir.add(this.direction.moveLeft());
		    projectilesDir.add(this.direction.moveRight());
			if (this.projCount > 4) {
			    projectilesDir.add(this.direction.moveLeft().flip());
			    projectilesDir.add(this.direction.moveRight().flip());
				if (this.projCount > 6)
				    projectilesDir.add(this.direction.moveLeft().moveLeft());
					if (this.projCount == 8)
					    projectilesDir.add(this.direction.moveRight().moveRight());
			}
		}
		projectilesDir.stream().forEach(x -> {projectiles.add(new Projectile(this.parentId, new Location(newLoc), x, this.projSpeed, this.damage)); });
		return projectiles;		
	}
	
	@Override
	public boolean isReadyToShoot() {
		return this.actualCooldown == 0;
	}
	
	@Override
	public void coolDown() {
		if (this.actualCooldown > 0)
			this.actualCooldown--;		
	}
	
	@Override
	public void increaseProjectiles() {
		if (this.projCount < 8)
			this.projCount++;
	}
	
	@Override
	public void increaseDamage(final int inc) {
		if (this.damage + inc <= 60)
			this.damage += inc;
	}
	
	@Override
	public void decreaseCooldown(final int dec) {
		if (this.cooldownTime - dec >= 0)
			this.cooldownTime -= dec;
	}
	
	@Override
	public int getDamage() {
	    return this.damage;
	}
	
	@Override
	public int getProjectilesCount() {
		return this.projCount;
	}	
}