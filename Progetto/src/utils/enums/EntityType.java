package utils.enums;

import java.util.Arrays;
import java.util.List;
import model.entities.*;
import utils.generics.Pair;

//Enum che raccoglie i possibili tipi di entità e le loro statistiche di gioco
public enum EntityType {

	SPACESHIP, ENEMY, DEBRIS, POWERUP, PROJECTILE;

	private static List<Pair<String, Integer>> proj = Arrays.asList(new Pair<>("diagonal-green.png", 12), new Pair<>("beam_blue.png", 25), new Pair<>("beam_red.png", 35), new Pair<>("fireball.png", 50));
	private static List<Pair<String, Integer>> dmg = Arrays.asList(new Pair<>("Wave1_", 15), new Pair<>("Wave2_", 30), new Pair<>("Wave3_", 45));
	private static List<Pair<String, Integer>> hp = Arrays.asList(new Pair<>("1.png", 12), new Pair<>("2.png", 24), new Pair<>("3.png", 36), new Pair<>("4.png", 48), new Pair<>("5.png", 60));

	//Metodo che restituisce la corretta immagine da associare al nemico
	public static Pair<String, Double> getImage(final Entity e) {
		final StringBuilder s = new StringBuilder("Entities/");
		double rotation = 0;
		if (e instanceof Projectile) {
			s.append("Projectiles/");
			s.append(EntityType.fileName(((Projectile) e).getDamage(), EntityType.proj));
			rotation = ((Projectile) e).getDirection().getRotation();
		} else if (e instanceof Enemy) {
			s.append("Enemies/");
			s.append(EntityType.fileName(((Enemy) e).getWeapon().getDamage(), EntityType.dmg));
			s.append(EntityType.fileName(((Enemy) e).getMaximumLife(), EntityType.hp));
		} else if (e instanceof Debris) {
			s.append("Debris/");
			s.append(((Debris) e).getType().getImageName());
		} else if (e instanceof PowerUp) {
			s.append("Powerups/");
			s.append(((PowerUp) e).getBonus().getImageName());
		}
		return new Pair<String, Double>(s.toString(), Double.valueOf(rotation));
	}

	//Metodo che gestisce i nomi dei file delle varie entità e i corrispondenti parametri
	private static String fileName(final int value, final List<Pair<String, Integer>> ranges) {
		for (final Pair<String, Integer> p : ranges) {
			if (value <= p.getSecond())
				return p.getFirst();
		}
		return ranges.get(ranges.size() - 1).getFirst();
	}
}