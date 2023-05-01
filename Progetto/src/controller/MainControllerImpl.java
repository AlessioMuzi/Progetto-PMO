package controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import utils.generics.*;
import utils.enums.*;
import view.menu.MainView;

//Classe che implementa il controller principale, gestendo input, pause e salvataggio dati
public final class MainControllerImpl implements MainController {
    
    private static final String      FILENAME = "highscores";
    private static final int         NSCORES = 15;
    private final ScoreController    scoreController;
    private final InputParserFunc    inputParser;
    private int                      fps = 60;
    private Pair<String, Integer>    diff = new Pair<>("Normale", 2);
    private Optional<GameLoop>       game;
    private MainView                 view;
    private volatile int             score;

    //Costruttore
    public MainControllerImpl() {
        this.scoreController = new ScoreControllerImpl(FILENAME, NSCORES);
        this.game = Optional.empty();
        this.inputParser = inputs -> {
            final boolean n = inputs.contains(Input.W);
            final boolean s = inputs.contains(Input.S);
            final boolean e = inputs.contains(Input.D);
            final boolean w = inputs.contains(Input.A);
            final boolean shoot = inputs.contains(Input.SPACE);
            Optional<Direction> dir = Optional.empty();
            if (n) {
                if (e)
                    dir = Optional.of(Direction.NE);
                else if (w)
                    dir = Optional.of(Direction.NW);
                else
                    dir = Optional.of(Direction.N);  
            } else if (s) {
                if (e)
                    dir = Optional.of(Direction.SE);
                else if (w)
                    dir = Optional.of(Direction.SW);
                else
                    dir = Optional.of(Direction.S); 
            } else if (e)
                dir = Optional.of(Direction.E);
            else if (w)
                dir = Optional.of(Direction.W);
          
            return new Pair<Optional<Direction>, Boolean>(dir, shoot); };
    }
    
    @Override
    public void startGame() {
        final GameLoop game = new GameLoop(this.fps, this.diff.getSecond(), this, this.view, this.inputParser);
        this.game = Optional.of(game);
        game.start();
    }
    
    @Override
    public void abortGame() {
        if (this.game.isPresent()) {
            this.game.get().abort();
            this.game = Optional.empty();
        }
    }
    
    @Override
    public void pauseGame() {
        if (this.game.isPresent())
            this.game.get().pause();
    }
    
    @Override
    public void resumeGame() {
        if (this.game.isPresent())
            this.game.get().resumeGame();
    }
    
    @Override
    public boolean isPaused() {
        if (!this.game.isPresent())
            return false;
     
        return this.game.get().isInState(GameLoopStatus.PAUSED);
    }
    
    @Override
    public boolean isRunning() {
        if (!this.game.isPresent())
            return false;

        return this.game.get().isInState(GameLoopStatus.RUNNING);
    }
    
    //Metodi che gestiscono i punteggi con eccezioni per effettuare le operazioni in sicurezza
    @Override
    public boolean setPlayerName(final String name) {
        this.scoreController.addScore(new Pair<>(name, this.score));
        try {
            this.scoreController.saveData();
        } catch (IllegalStateException | IOException e) {
            return false;
        }
        return true;
    }
    
    @Override
    public boolean emptyScores() {
        this.scoreController.emptyScores();
        try {
            this.scoreController.saveData();
        } catch (IllegalStateException | IOException e) {
            return false;
        }
        return true;
    }
    
    //Metodi getter e setter
    @Override
    public List<Pair<String, Integer>> getScores() {
        return this.scoreController.getScores();
    }
    
    @Override
    public int getFPS() {
        return this.fps;
    }
    
    @Override
    public String getDifficulty() {
        return this.diff.getFirst();
    }
    
    @Override
    public void setFPS_Diff(final int f, final Pair<String, Integer> d) {
        this.fps = f;
        this.diff = d;
    }
    
    @Override
    public void setView(final MainView v) {
        this.view = v;
    }
    
    @Override
    public void setScore(final int s) {
        this.score = s;
    }
}