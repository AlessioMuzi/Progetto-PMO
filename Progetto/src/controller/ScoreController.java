package controller;

import java.io.IOException;
import java.util.List;
import utils.generics.Pair;

//Interfaccia che modella il gestore dei punteggi
public interface ScoreController {

    //Metodo che aggiunge una nuova coppia nome-punteggio
    void addScore(final Pair<String, Integer> p);

    //Metodo che svuota i punteggi salvati
    void emptyScores();

    //Metodo che salva i dati
    void saveData() throws IllegalStateException, IOException;
    
    //Metodo getter
    List<Pair<String, Integer>> getScores();
}