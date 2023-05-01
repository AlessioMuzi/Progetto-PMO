package view.game;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import utils.generics.Location;
import utils.generics.Pair;
import view.menu.*;
import view.message.*;

//Classe view che gestisce lo schermo di gioco durante la partita
public class GameScreen extends Scene {

    private Stage              mainStage;
    private static double      constantWidth  = 1;
    private static double      constantHeight = 1;
    private static double      inGameWidth    = 1280;
    private static double      inGameHeight   = 768;
    private final Group        root           = new Group();
    private final Pane         background     = new Pane();
    private final DrawEntities drawHandler    = new DrawEntities(inGameWidth, inGameHeight);
    private final HBox         statsBox       = new HBox();
    private final Button       pause          = new Button("Pausa");
    private final Label        hpLabel        = new Label();
    private final Label        shieldLabel    = new Label();
    private final Label        scoreLabel     = new Label();

    //Costruttore
    public GameScreen() {
        super(new StackPane());
        final HBox pauseBox = new HBox();
        pause.setId("button");
        pause.setDefaultButton(false);
        pause.setFocusTraversable(false);
        pause.setOnAction(e -> {this.pause(); });
        pauseBox.getChildren().addAll(pause);
        pauseBox.setSpacing(10);
        pauseBox.setAlignment(Pos.TOP_CENTER);
        pauseBox.setPadding(new Insets(10, 0, 0, 0));
        final HBox layout = new HBox();
        final VBox topBox = new VBox();
        topBox.getChildren().addAll(pauseBox, layout);
        topBox.getStylesheets().add("button.css");
        layout.setPadding(new Insets(5, 15, 15, 15));
        layout.setSpacing(4);

        this.hpLabel.setTextFill(Color.GREEN);
        this.shieldLabel.setTextFill(Color.BLUE);
        this.scoreLabel.setTextFill(Color.YELLOW);

        final VBox infoBox = new VBox();
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setSpacing(2);
        statsBox.getChildren().addAll(this.hpLabel, this.shieldLabel);
        infoBox.getChildren().addAll(statsBox, this.scoreLabel);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setStyle("-fx-font: 18px Tahoma;" + "-fx-fill: linear-gradient(from 0% 90% to 100% 200%, repeat, aqua 0%, green 110%);" + "-fx-border-radius: 30;" + "-fx-border-width: 2;");
        statsBox.setPadding(new Insets(0, 4, 0, 4));
        layout.getChildren().add(infoBox);
        topBox.setStyle("-fx-background-image: url(Menu/info.png);" + "-fx-border-radius: 0 0 25 0;" + "-fx-opacity: 0.80;" + "-fx-background-radius: 0 0 25 0;");
        this.root.getChildren().addAll(this.background, topBox);
        this.getInput();
        this.resize();
        this.setRoot(this.root);
    }

    //Metodo che gestisce gli input utente
    private void getInput() {
        final InputHandler inputHandler = InputHandler.getInputHandler();
        inputHandler.emptyList();
        this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.BACK_SPACE) {
                MainView.getController().pauseGame();
                final Boolean confirm = ConfirmMessage.display("Attenzione", "Sei sicuro di voler tornare al menu?");
                if (confirm) {
                    MainView.getController().abortGame();
                    InputHandler.getInputHandler().emptyList();
                    this.mainStage.setScene(MainMenu.get(this.mainStage));
                } else {
                    InputHandler.getInputHandler().emptyList();
                    MainView.getController().resumeGame();
                }
            } else if (event.getCode() == KeyCode.P) {
                this.pause();
            } else if (event.getCode() == KeyCode.ESCAPE) {
                MainView.getController().pauseGame();
                ClosureHandler.getClosureHandler();
                ClosureHandler.closeProgram(this.mainStage);
            }
            inputHandler.press(event.getCode()); });
        this.addEventFilter(KeyEvent.KEY_RELEASED, event -> {inputHandler.release(event.getCode()); });
    }

    //Metodo che gestisce pausa e ripresa del gioco
    private void pause() {
        if (MainView.getController().isPaused()) {
            InputHandler.getInputHandler().emptyList();
            MainView.getController().resumeGame();
            this.pause.setText("Pausa");
        } else {
            MainView.getController().pauseGame();
            this.pause.setText("Go!!!");
        }
    }

    //Metodo che disegna su schermo la lista di entità attive assegnate
    public void drawOnScreen(final List<Pair<Pair<String, Double>, Location>> entities) {
        this.drawHandler.draw(this.background, entities);
    }

    //Metodo che aggiorna le informazioni del giocatore
    public void updateInfo(final int hp, final int shield, final int score) {
        if (hp <= 0)
            this.mainStage.setScene(GameOver.get(this.mainStage));
        else
            if (hp <= 20)
                hpLabel.setTextFill(Color.RED);
            else if (hp <= 50)
                hpLabel.setTextFill(Color.ORANGE);
            else
                hpLabel.setTextFill(Color.GREEN);
          
            if (shield <= 20)
                shieldLabel.setTextFill(Color.RED);
            else if (shield <= 50)
                shieldLabel.setTextFill(Color.ORANGE);
            else
                shieldLabel.setTextFill(Color.BLUE);
            scoreLabel.setTextFill(Color.YELLOW);
            
            hpLabel.setText("PUNTI SALUTE: " + Integer.toString(hp));
            shieldLabel.setText("SCUDI: " + Integer.toString(shield));
            scoreLabel.setText("PUNTEGGIO: " + Integer.toString(score));
    }

    //Metodo invocato quando un livello viene completato
    public void won(final int nLevel) {
        final StackPane pane = new StackPane();
        final Label text = new Label();
        text.setStyle("-fx-text-fill: linear-gradient(cyan , dodgerblue);" + "-fx-font-family: Starcraft;");
        pane.setPrefSize(800 * constantWidth, 250 * constantHeight);
        pane.setAlignment(Pos.CENTER);
        text.setFont(Font.font(null, FontWeight.BOLD, 48 * constantWidth));
        text.setVisible(true);
        text.setText("Livello " + nLevel + " completato");
        pane.getChildren().add(text);
        pane.setLayoutX((GameScreen.inGameWidth / 2) - ((800 * constantWidth) / 2));
        pane.setLayoutY((GameScreen.inGameHeight / 2) - ((250 * constantHeight) / 2));
        this.root.getChildren().add(pane);
        this.showText(text);
    }

    //Metodo che mostra il power up appena preso
    public void powerUp(final String powerUp) {
        final StackPane pane = new StackPane();
        final Label text = new Label();
        text.setStyle("-fx-text-fill: linear-gradient(cyan , dodgerblue);" + "-fx-font-family: Starcraft;");
        pane.setPrefSize(800 * constantWidth, 160 * constantHeight);
        pane.setAlignment(Pos.CENTER);
        text.setFont(Font.font(null, FontWeight.BOLD, 35 * constantWidth));
        text.setVisible(true);
        text.setText(powerUp);
        pane.getChildren().add(text);
        pane.setLayoutX((GameScreen.inGameWidth / 2) - ((800 * constantWidth) / 2));
        this.root.getChildren().add(pane);
        this.showText(text);
    }

    //Metodo utility che mostra del testo a schermo per un certo periodo di tempo
    private void showText(final Label text) {
        final Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> text.setVisible(false));
            }  }, 2000, 1);
    }

    //Metodo che gestisce il ridimensionamento delle entità a schemro
    private void resize() {
        this.statsBox.setMinWidth((280 * constantWidth));
        this.statsBox.setMaxSize((280 * constantWidth), (50 * constantHeight));
        this.statsBox.setMinHeight((50 * constantHeight));
        this.statsBox.setSpacing(12 * constantWidth);
        this.scoreLabel.setFont(Font.font(null, FontWeight.BOLD, 18 * constantWidth));
        this.hpLabel.setFont(Font.font(null, FontWeight.BOLD, 18 * constantWidth));
        this.shieldLabel.setFont(Font.font(null, FontWeight.BOLD, 18 * constantWidth));
        this.pause.setPrefSize(110 * constantWidth, 25 * constantHeight);
        this.pause.setOnMouseEntered(e -> this.pause.setFont(Font.font(15 * constantHeight)));
        this.pause.setFont(Font.font(15 * constantHeight));
    }
    
    //Metodi getter e setter
    public boolean isFullScreen() {
        return Options.isFullScreen();
    }
    
    public GameScreen get(final Stage mainWindow) {
        this.mainStage = mainWindow;
        this.mainStage.setWidth(GameScreen.inGameWidth);
        this.mainStage.setHeight(GameScreen.inGameHeight);
        this.mainStage.centerOnScreen();
        this.mainStage.setTitle("StarQuest");
        this.mainStage.setFullScreen(Options.isFullScreen());
        return this;
    }
    
    public static synchronized void setResolution(final double w, final double h, final boolean fullScreen) {
        inGameWidth = w;
        inGameHeight = h;
        constantWidth = GameScreen.inGameWidth / 1280;
        constantHeight = GameScreen.inGameHeight / 768;
    }
}