package model.levels;

import java.util.List;
import java.util.Optional;
import model.entities.Entity;
import utils.generics.Location;
import utils.enums.Direction;
import utils.enums.MatchStatus;

//Interfaccia che modella la logica di gioco
public interface GameLogic {
	
	//Metodi che aggiornano gli input dell'utente e tutte le entità
	void updateUserInputs(Optional<Direction> dir, boolean shoot);
	void updateAll();
	
	//Metodi getter
	int getLife();
	int getShield();   
	int getScores();
	Location getPlayerLocation(); 
	MatchStatus getMatchStatus();  
	Optional<String> getLatestPowerUp();
	List<Entity> getEntitiesToDraw();
}