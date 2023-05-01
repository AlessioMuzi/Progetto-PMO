package model.levels;

//Classe che crea il livello usando il pattern Factory
public final class LevelFactory {
    
	static Level createLevel(final int fps, final int id, final int diff) {
		final int enemies = 5 * (2 * id + diff);
		final int maxWave = diff + (id - 1) / 2;
		final int enemyDelay = (int) ((9 - 0.36666 * Math.min(10, id)) * fps / diff);
		final int debrisDelay = 6 * fps / (diff * id);
		final int powerupDelay = (int) ((7.5 + diff * id) * fps);
		final double speed = (0.135 + 0.015 * id) / fps;
		final Level lvl = new Level(enemies, speed, maxWave, enemyDelay, debrisDelay, powerupDelay);
		lvl.getEnemyGenerator().setEntityWeaponCooldown((int) ((1 - 0.1 * Math.min(5, id / 2)) * fps));
		lvl.getEnemyGenerator().setEntityDamageRange(5 * id, 10 * id);
		lvl.getEnemyGenerator().setEntityHpRange(5 * id, 5 + 10 * id);
		lvl.getEnemyGenerator().setEntitySpeedRange(speed, speed * 1.5);
		
		return lvl;
	}
}