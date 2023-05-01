package model.entities;

import java.util.List;
import utils.generics.Location;

//Interfaccia che modella una generica arma
public interface Weapon {

	//Metodo che gestisce uno sparo
	List<Projectile> shoot(final Location loc);
	
	//Metodo che controlla se un'entità è pronta a sparare
	boolean isReadyToShoot();
	
	//Metodo che gestisce il tempo tra uno sparo e l'altro
	void coolDown();
	
	//Metodi che aumentano il numero, il danno o il cooldown dei proiettili
	void increaseProjectiles();
	void increaseDamage(final int inc);
	void decreaseCooldown(final int dec);
	
	//Metodi getter
	int getProjectilesCount();
	int getDamage();
}