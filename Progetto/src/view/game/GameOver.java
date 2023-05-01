package view.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.generics.ImageLoader;
import view.menu.*;
import view.message.*;

//Classe view che gestisce la schermata di game over
public class GameOver extends Scene {

    private static final GameOver MAINSCENE = new GameOver();
    private static Stage          mainStage;
    private final TextField       name = new TextField();
    private final Button          enter = new Button("Invio");
    private final Label           saved = new Label("Punteggio salvato");

    //Costruttore
    public GameOver() {
        super(new StackPane());
        final VBox mainLayout = new VBox();
        mainLayout.setAlignment(Pos.CENTER);
        final ImageView gameOver = new ImageView(ImageLoader.getLoader().getImageFromPath("Menu/gameover.png"));
        gameOver.setFitWidth(557);
        gameOver.setFitHeight(372);
        this.name.setFocusTraversable(false);
        this.enter.setFocusTraversable(false);
        final Label insert = new Label("Inserisci il tuo nome: ");
        insert.setStyle("-fx-font-family: Arial;" + "-fx-text-fill: white !important;" + "-fx-font-size: 18pt;");
        this.enter.setId("button");
        this.enter.setOnAction(e -> {
            if (this.checkName()) {
                this.name.setDisable(true);
                this.enter.setDisable(true);
                this.saved.setTextFill(Color.GREEN);
                this.saved.setVisible(true);
                if (!MainView.getController().setPlayerName(name.getText()))
                    GenericMessage.display(true, "Successo", "Punteggio salvato correttamente", "Continua");  } });
        final HBox insertLayout = new HBox();
        insertLayout.setAlignment(Pos.CENTER);
        insertLayout.setSpacing(5);
        insertLayout.getChildren().addAll(insert, name, enter);
        insertLayout.setPadding(new Insets(30, 0, 10, 0));
        final Button retry = new Button("Riprova");
        retry.setFocusTraversable(false);
        retry.setId("button");
        retry.setOnAction(e -> {
            this.resetSaved();
            final GameScreen screen = new GameScreen();
            MainView.setGameScreen(screen);
            mainStage.setScene(screen.get(mainStage));
            MainView.getController().startGame(); });
        final Button exit = new Button("Esci");
        exit.setFocusTraversable(false);
        final Button menu = new Button("Menu");
        menu.setFocusTraversable(false);
        menu.setId("button");
        menu.setOnAction(e -> {
            this.resetSaved();
            mainStage.setScene(MainMenu.get(mainStage)); });
        exit.setId("button");
        exit.setOnAction(e -> {
            this.resetSaved();
            ClosureHandler.getClosureHandler();
            ClosureHandler.closeProgram(mainStage); }); 
        final Button scores = new Button("Punteggi");
        scores.setFocusTraversable(false);
        scores.setId("button");
        scores.setOnAction(e -> {
            this.resetSaved();
            mainStage.setScene(Scores.get(mainStage)); });
        final HBox bottomLayout = new HBox();
        bottomLayout.setPadding(new Insets(150, 0, 10, 0));
        bottomLayout.setSpacing(25);
        bottomLayout.setAlignment(Pos.BOTTOM_CENTER);
        bottomLayout.getChildren().addAll(retry, menu, scores, exit);
        saved.setVisible(false);
        saved.setTextFill(Color.GREEN);

        mainLayout.getChildren().addAll(gameOver, insertLayout, saved, bottomLayout);
        mainLayout.setStyle("-fx-background-color: black;" + "-fx-font-size: 18pt;" + "-fx-text-fill: white !important;" + "-fx-font-family: space_invaders;");
        mainLayout.getStylesheets().add("button.css");
        this.setRoot(mainLayout);
    }

    //Metodo che controlla l'inserimento del nome
    private boolean checkName() {
        if (this.name.getText().isEmpty()) {
            this.saved.setText("Inserisci un nome valido");
            this.saved.setTextFill(Color.RED);
            this.saved.setVisible(true);
            return false;
        }
        return true;
    }

    //Metodo che resetta lo stato dei bottoni
    private void resetSaved() {
        this.enter.setDisable(false);
        this.name.setDisable(false);
        this.saved.setVisible(false);
        this.name.setText("");
    }

    //Metodo getter
    public static Scene get(final Stage mainWindow) {
        mainStage = mainWindow;
        mainStage.setFullScreen(false);
        mainStage.setWidth(800);
        mainStage.setHeight(800);
        mainStage.centerOnScreen();
        mainStage.setTitle("StarQuest - Game Over");
        return MAINSCENE;
    }
}