package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.List;
import model.entities.*;
import utils.generics.Area;
import utils.generics.Location;
import utils.enums.Direction;
import utils.enums.EntityType;

//Test che verifica il model e la logica di gioco
public class TestGame {
    
	private Weapon    testWeapon = new WeaponImpl(EntityType.SPACESHIP, Direction.E, 10, 5, 1);
	private Spaceship testShip = new Spaceship(100, 0.65, new Location(0.2, 0.4, new Area(0.125, 0.0972)), Direction.E, 100, testWeapon);
	private Enemy     testEnemy = new Enemy(100, 0.1, new Location(0.8, 0.4, new Area(0.125, 0.0972)), Direction.E, testWeapon);
	
	//Test n°1: salute
	@org.junit.Test
	public void testLife1() {
	    testEnemy.acquireHp(10);
		assertTrue(testEnemy.getRemainingLife() == 100);
		testShip.acquireShield(50);
		assertFalse(testShip.getRemainingShield() == 150);
		testShip.acquireHp(50);
		assertTrue(testShip.getRemainingLife() == 100);
		assertFalse(testShip.collideWith(testEnemy));
	}
	
	//Test n°2: salute
	@org.junit.Test
	public void testLife2() {
		assertFalse(testShip.getId().equals(testEnemy.getId()));
		assertTrue(testShip.getId().equals(EntityType.SPACESHIP));
		testShip.setWeapon(new WeaponImpl(testShip.getId(), Direction.E, 10, 5, 1));
		testShip.loseHp(5);
		assertTrue(testShip.getRemainingLife() == 100);
		assertTrue(testShip.getRemainingShield() == 95);
		assertFalse(testShip.toRemove());
		System.out.println(testShip.toString());
		System.out.println(testEnemy.toString());
	}
	
	//Test n°2: attacchi e collisioni
	@org.junit.Test
	public void testShoot() {
	    testShip.setLocation(new Location(0.1, 0.4, new Area(0.125, 0.0972)));
	    testEnemy.setLocation(new Location(1.6, 0.4, new Area(0.125, 0.0972)));
		System.out.println(testShip.toString());
		System.out.println(testEnemy.toString());
		testShip.setWeapon(new WeaponImpl(testShip.getId(), Direction.E, 10, 5, 0.3));
		List<Projectile> proj = testShip.attack();
		proj.get(0).update();
		System.out.println(proj.toString());
		proj.get(0).update();
		System.out.println(proj.toString());
		proj.get(0).update();
		System.out.println(proj.toString());
		proj.get(0).update();
		System.out.println(proj.toString());
		proj.get(0).update();
		System.out.println(proj.toString());
		assertTrue(testEnemy.collideWith(proj.get(0)));
		testEnemy.loseHp(proj.get(0).getDamage());
		assertFalse(testEnemy.getRemainingLife() == 100);
		assertFalse(testEnemy.toRemove());
	}
}