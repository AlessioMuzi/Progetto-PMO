package view.menu;

import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import controller.MainController;
import utils.generics.Location;
import utils.generics.Pair;
import utils.enums.Input;
import view.game.*;

//Classe view principale del programma, raccoglie tutta la parte grafica per le interazioni con il controller
public class MainView {

    private static MainController controller;
    private static GameScreen     screen;
    private final  InputHandler   inputHandler = InputHandler.getInputHandler();

    //Costruttore
    public MainView(final MainController c) {
        this.setController(c);
    }

    public void startView() {
        Application.launch(MainWindow.class);
    }

    public List<Input> getInput() {
        return this.inputHandler.getInputs();
    }

    public void draw(final List<Pair<Pair<String, Double>, Location>> entities) {
        Platform.runLater(() -> MainView.screen.drawOnScreen(entities));
    }

    public void updateInfo(final int hp, final int shield, final int score) {
        Platform.runLater(() -> MainView.screen.updateInfo(hp, shield, score));
    }

    public void showText(final int nLevel) {
        Platform.runLater(() -> MainView.screen.won(nLevel));
    }

    public void showText(final String powerUp) {
        Platform.runLater(() -> MainView.screen.powerUp(powerUp));    }

    //Metodi getter e setter
    public static MainController getController() {
        return MainView.controller;
    }
    
    private synchronized void setController(final MainController c) {
        MainView.controller = c;
    }
    
    public static void setGameScreen(final GameScreen s) {
        MainView.screen = s;
    }
}