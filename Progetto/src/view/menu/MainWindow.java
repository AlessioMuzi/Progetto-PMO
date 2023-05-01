package view.menu;

import javafx.application.Application;
import javafx.stage.Stage;
import utils.generics.ImageLoader;
import view.game.ClosureHandler;

//Classe view che gestisce la finestra principale
public class MainWindow extends Application {

    private final Stage mainWindow = new Stage();

    //Metodo che dà inizio a tutto ciò che riguarda la grafica (immagini, JavaFX...)
    public void start(final Stage stage) {
        this.mainWindow.getIcons().add(ImageLoader.getLoader().getImageFromPath("Icons/Logo.png"));
        this.mainWindow.setWidth(1400);
        this.mainWindow.setHeight(800);
        this.mainWindow.setTitle("StarQuest");
        this.mainWindow.centerOnScreen();
        this.mainWindow.setResizable(false);
        this.mainWindow.setOnCloseRequest(e -> {
            e.consume();
            if (MainView.getController().isRunning())
                MainView.getController().pauseGame();
            ClosureHandler.closeProgram(this.mainWindow); });
        this.mainWindow.setScene(MainMenu.get(this.mainWindow));
        this.mainWindow.show();
    }
}