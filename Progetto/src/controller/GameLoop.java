package controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import model.levels.GameLogic;
import model.levels.GameLogicImpl;
import utils.generics.*;
import utils.enums.*;
import view.menu.MainView;

//Classe che modella il thread GameLoop, il quale sincronizza Model e View
public class GameLoop extends Thread {

    private final long              tick;
    private final int               fps;
    private final int               diff;
    private final InputParserFunc   inputParser;
    private final MainView          view;
    private final MainController    cont;
    private volatile GameLogic      model;
    private volatile int            score;
    private volatile int            nLevel;
    private volatile GameLoopStatus status;

    //Costruttore
    public GameLoop(final int fps, final int d, final MainController c, final MainView v, final InputParserFunc p) {
        this.status = GameLoopStatus.READY;
        this.fps = fps;
        this.tick = 1000 / fps;
        this.nLevel = 1;
        this.score = 0;
        this.diff = d;
        this.model = new GameLogicImpl(fps, this.nLevel, this.diff);
        this.view = v;
        this.cont = c;
        this.inputParser = p;
    }

    //Metodo run eseguito all'inizio di una partita, quando il thread viene avviato
    @Override
    public void run() {
        this.setState(GameLoopStatus.RUNNING);
        int timer = 0;
        while (!this.isInState(GameLoopStatus.ABORTED)) {
            if (this.isInState(GameLoopStatus.RUNNING)) {
                if (this.model.getMatchStatus() == MatchStatus.RUNNING) {
                    final long startTime = System.currentTimeMillis();
                    timer++;
                    if (timer > this.fps) {
                        timer = 0;
                        final Optional<String> up = this.model.getLatestPowerUp();
                        if (up.isPresent())
                            this.view.showText(up.get());
                    }
                    this.score += GameLoop.this.model.getScores() * this.diff / 2;
                    final List<Pair<Pair<String, Double>, Location>> toDraw = new LinkedList<>();
                    final Location loc = this.model.getPlayerLocation();
                    toDraw.add(new Pair<>(new Pair<>("Entities/Player/Spaceship.png", 0d), new Location(loc)));
                    if (this.model.getShield() > 0) {
                        final Area a = new Area(loc.getArea().getWidth() * 2, loc.getArea().getHeight() * 2);
                        toDraw.add(new Pair<>(new Pair<>("Entities/Player/Shield.png", 0d), new Location(loc.getX() + a.getWidth() / 10, loc.getY(), a)));
                    }
                    this.model.getEntitiesToDraw().forEach(e -> {toDraw.add(new Pair<>(EntityType.getImage(e), e.getLocation())); });
                    final Thread t = new Thread() {
                        @Override
                        public void run() {
                            GameLoop.this.view.draw(toDraw);
                            GameLoop.this.view.updateInfo(GameLoop.this.model.getLife(), GameLoop.this.model.getShield(), GameLoop.this.score);
                        }
                    };
                    t.start();
                    final Pair<Optional<Direction>, Boolean> action = this.inputParser.parseInputs(this.view.getInput());
                    this.model.updateUserInputs(action.getFirst(), action.getSecond());
                    this.model.updateAll();
                    try {
                        if (this.model.getLife() <= 0)
                            this.setState(GameLoopStatus.ABORTED);
                        t.join();
                        final long timeSpent = System.currentTimeMillis() - startTime;
                        if (timeSpent < this.tick) {
                            Thread.sleep(this.tick - timeSpent);
                        }
                    } catch (final InterruptedException e) {
                        this.setState(GameLoopStatus.ABORTED);
                    }
                } else if (this.model.getMatchStatus() == MatchStatus.LOST) {
                    this.setState(GameLoopStatus.ABORTED);
                } else {
                    this.view.showText(this.nLevel);
                    this.nLevel++;
                    this.model = new GameLogicImpl(this.fps, this.nLevel, this.diff);
                }
            } else
                try {
                    Thread.sleep(500);
                } catch (final InterruptedException e) {
                    this.setState(GameLoopStatus.ABORTED);
                }
        }
        this.cont.setScore(this.score);
        this.cont.abortGame();
    }
    
    //Metodo che controlla lo stato corrente
    public synchronized boolean isInState(final GameLoopStatus s) {
        return this.status == s;
    }

    //Metodi che forzano la terminazione o mettono\terminano la pausa
    public void abort() {
        this.setState(GameLoopStatus.ABORTED);
    }

    public void pause() {
        if (this.isInState(GameLoopStatus.RUNNING))
            this.setState(GameLoopStatus.PAUSED);
    }

    public void resumeGame() {
        if (this.isInState(GameLoopStatus.PAUSED))
            this.setState(GameLoopStatus.RUNNING);
    }

    //Metodo setter    
    private synchronized void setState(final GameLoopStatus s) {
        this.status = s;
    }
}