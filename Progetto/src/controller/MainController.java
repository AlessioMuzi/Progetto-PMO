package controller;

import java.util.List;
import utils.generics.Pair;
import view.menu.MainView;

//Interfaccia che modella il controller principale
public interface MainController {

    //Metodi per iniziare, terminare forzatamente, mettere in pausa e riprendere la partita
    void startGame();
    void abortGame();
    void pauseGame();
    void resumeGame();
    
    //Metodo che svuota la lista dei punteggi
    boolean emptyScores();

    //Metodi che controllano se la partita è in pausa\in corso
    boolean isPaused();
    boolean isRunning();
    
    //Metodi getter e setter
    int getFPS();
    String getDifficulty();
    List<Pair<String, Integer>> getScores();
    void setScore(int s);
    void setView(MainView v);
    void setFPS_Diff(int fps, Pair<String, Integer> diff);
    boolean setPlayerName(String name);
}