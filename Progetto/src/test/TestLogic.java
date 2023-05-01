package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import utils.generics.*;
import utils.enums.Input;
import view.menu.MainView;
import controller.*;

//Test che verifica la logica di gioco
public class TestLogic {
    private volatile int var;

    //Test n°1: avvio e pausa
    @Test
    public void testStartAndPause() {
        this.var = 0;
        final GameLoop gl = new GameLoop(10, 2, this.dummyController, this.dummyView, this.dummyParser);
        gl.start();
        try {
            Thread.sleep(1000);
        } catch (final InterruptedException e) {
            Assert.assertTrue("Interrupted", false);
        }
        Assert.assertTrue("Draw method is not called", this.var > 0);
        gl.abort();
        final int lastVar = this.var;
        try {
            Thread.sleep(1000);
        } catch (final InterruptedException e) {
            Assert.assertTrue("Interrupted", false);
        }
        System.out.println("Test1: " + lastVar + " when stopped, now is " + this.var);
        Assert.assertTrue("GameLoop not stopped", this.var <= lastVar + 1);
    }

    //Test n°2: framerate
    @Test
    public void testFPS() {
        this.var = 0;
        final GameLoop gl = new GameLoop(60, 2, this.dummyController, this.dummyView, this.dummyParser);
        gl.start();
        try {
            Thread.sleep(10000);
        } catch (final InterruptedException e) {
            Assert.assertTrue("Interrupted", false);
        }
        gl.abort();
        final double x = (double) (600 - Math.abs(600 - this.var)) / 6;
        System.out.println("Test2: expected 600, got " + this.var + " (" + x + "% accuracy)");
        Assert.assertTrue("Not enough accurate", x > 90);
    }

    //Test n°3: pausa e ripresa
    @Test
    public void testPauseAndUnpause() {
        this.var = 0;
        final GameLoop gl = new GameLoop(50, 2, this.dummyController, this.dummyView, this.dummyParser);
        gl.start();
        try {
            Thread.sleep(1000);
        } catch (final InterruptedException e) {
            Assert.assertTrue("Interrupted", false);
        }
        System.out.print("Test3: " + this.var + " before pause, ");
        gl.pause();
        try {
            Thread.sleep(1000);
        } catch (final InterruptedException e) {
            Assert.assertTrue("Interrupted", false);
        }
        System.out.print(this.var + " after pause,");
        Assert.assertTrue("GameLoop not paused", this.var < 55);
        gl.resumeGame();
        try {
            Thread.sleep(1000);
        } catch (final InterruptedException e) {
            Assert.assertTrue("Interrupted", false);
        }
        gl.abort();
        System.out.println(" resumed, finally is " + this.var);
        Assert.assertTrue("GameLoop not resumed", this.var >= 100);
    }

    private final MainController dummyController = new MainController() {

        @Override
        public void startGame() {
        }

        @Override
        public void resumeGame() {
        }

        @Override
        public void pauseGame() {
        }
        
        @Override
        public void abortGame() {
        }
        
        @Override
        public boolean emptyScores() {
            return true;
        }

        @Override
        public boolean isRunning() {
            return false;
        }

        @Override
        public boolean isPaused() {
            return false;
        }

        @Override
        public List<Pair<String, Integer>> getScores() {
            return null;
        }
        
        @Override
        public int getFPS() {
            return 0;
        }

        @Override
        public String getDifficulty() {
            return null;
        }

        @Override
        public void setScore(final int score) {
        }
        
        @Override
        public boolean setPlayerName(final String s) {
            return true;
        }

        @Override
        public void setFPS_Diff(final int fps, final Pair<String, Integer> diff) {
        }

        @Override
        public void setView(final MainView v) {
        }
    };
    
    private final MainView dummyView = new MainView(dummyController) {

        @Override
        public void startView() {
        }

        @Override
        public void draw(final List<Pair<Pair<String, Double>, Location>> entities) {
            TestLogic.this.var++;
        }

        @Override
        public void updateInfo(final int hp, final int shields, final int score) {
        }

        @Override
        public void showText(final int nLevel) {
        }

        @Override
        public void showText(final String powerUp) {
        }
        
        @Override
        public List<Input> getInput() {
            return new ArrayList<>();
        }
    };

    private final InputParserFunc dummyParser = inputs -> new Pair<>(Optional.empty(), Boolean.FALSE);
}