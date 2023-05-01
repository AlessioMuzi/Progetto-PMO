package view.menu;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.generics.Pair;
import utils.enums.Difficulty;
import utils.enums.Resolution;
import view.game.GameScreen;
import view.message.GenericMessage;

//Classe view che gestisce le opzioni di gioco
public class Options extends Scene {

    private final List<Pair<String, Integer>>              difficulties = new LinkedList<>();
    private final List<Pair<String, Pair<Double, Double>>> resolutions  = new LinkedList<>();
    private static boolean                                 fullScreen   = false;
    private static Options                                 mainScene    = new Options();
    private static ChoiceBox<String>                       difficult;
    private static ChoiceBox<Integer>                      fps;
    private static ChoiceBox<String>                       resolution;
    private static Stage                                   mainStage;

    //Costruttore
    public Options() {
        super(new StackPane());
        
        this.difficulties.add(new Pair<String, Integer>(Difficulty.EASY.getName(), Difficulty.EASY.getDiff()));
        this.difficulties.add(new Pair<String, Integer>(Difficulty.NORMAL.getName(), Difficulty.NORMAL.getDiff()));
        this.difficulties.add(new Pair<String, Integer>(Difficulty.HARD.getName(), Difficulty.HARD.getDiff()));
        this.difficulties.add(new Pair<String, Integer>(Difficulty.EXTREME.getName(), Difficulty.EXTREME.getDiff()));

        this.resolutions.add(new Pair<>(Resolution.LOWEST.getRes(), new Pair<>(Resolution.LOWEST.getWidth(), Resolution.LOWEST.getHeight())));
        this.resolutions.add(new Pair<>(Resolution.LOW.getRes(), new Pair<>(Resolution.LOW.getWidth(), Resolution.LOW.getHeight())));
        this.resolutions.add(new Pair<>(Resolution.MID.getRes(), new Pair<>(Resolution.MID.getWidth(), Resolution.MID.getHeight())));
        this.resolutions.add(new Pair<>(Resolution.HIGH.getRes(), new Pair<>(Resolution.HIGH.getWidth(), Resolution.HIGH.getHeight())));

        final Text optionsText = new Text("Impostazioni");
        optionsText.setTranslateX(800 / 2 - 130);
        optionsText.setTranslateY(100);
        optionsText.setFont(Font.font(null, FontWeight.BOLD, 72));
        optionsText.setStyle("-fx-fill:  linear-gradient(cyan , dodgerblue)");
        final Pane mainLayout = new Pane();
        final VBox options = new VBox();
        options.setPadding(new Insets(30));
        options.setTranslateX(200);
        options.setTranslateY(250);
        options.setSpacing(50);
        this.setBox();
        final HBox diffLayout = new HBox();
        diffLayout.setSpacing(10);
        final Text diffText = new Text("Difficoltà:");
        diffText.setFill(Color.WHITE);
        this.difficulties.forEach(e -> {difficult.getItems().add(e.getFirst()); });
        diffLayout.getChildren().addAll(diffText, difficult);
        final HBox fpsLayout = new HBox();
        fpsLayout.setSpacing(10);
        final Text fpsText = new Text("FPS:");
        fpsText.setFill(Color.WHITE);
        fps.getItems().addAll(30, 60, 120);
        fpsLayout.getChildren().addAll(fpsText, fps);
        final HBox resLayout = new HBox();
        resLayout.setSpacing(10);
        final Text resText = new Text("Risoluzione:");
        resText.setFill(Color.WHITE);
        this.resolutions.forEach(e -> {resolution.getItems().add(e.getFirst()); });
        resolution.setValue(Resolution.LOW.getRes());
        resLayout.getChildren().addAll(resText, resolution);
        options.getChildren().addAll(resLayout, diffLayout, fpsLayout);
        mainLayout.getChildren().add(options);
        final HBox bottomLayout = new HBox();
        bottomLayout.setPrefWidth(400);
        bottomLayout.setTranslateY(620);
        bottomLayout.setTranslateX(250);
        bottomLayout.setSpacing(30);
        bottomLayout.setPadding(new Insets(20));
        final Button save = new Button("Salva");
        save.setId("button");
        final Button back = new Button("Menu");
        back.setId("button");
        bottomLayout.getChildren().addAll(back, save);
        mainLayout.getChildren().addAll(optionsText, bottomLayout);
        mainLayout.setStyle("-fx-background-image: url(Menu/options.jpeg);" + "-fx-font-family: Arial;" + "-fx-font-size: 18pt;");
        this.setRoot(mainLayout);
        this.getStylesheets().add("button.css");
        back.setOnAction(e -> mainStage.setScene(MainMenu.get(mainStage)));
        save.setOnAction(e -> {this.save(); });
    }

    //Metodo che crea le box di scelta
    private synchronized void setBox() {
        difficult = new ChoiceBox<>();
        fps = new ChoiceBox<>();
        resolution = new ChoiceBox<>();
    }

    //Metodo che salva le impostazioni
    private void save() {
        if (Options.difficult.getValue().equals(Difficulty.EXTREME.getName()))
            MainView.getController().setFPS_Diff(Options.fps.getValue(), this.difficulties.get(this.difficulties.size() - 1));
        else if (Options.difficult.getValue().equals(Difficulty.HARD.getName()))
            MainView.getController().setFPS_Diff(Options.fps.getValue(), this.difficulties.get(this.difficulties.size() - 2));
        else if (Options.difficult.getValue().equals(Difficulty.NORMAL.getName()))
            MainView.getController().setFPS_Diff(Options.fps.getValue(), this.difficulties.get(this.difficulties.size() - 3));
        else
            MainView.getController().setFPS_Diff(Options.fps.getValue(), this.difficulties.get(this.difficulties.size() - 4));
        this.changeResolution();
    }

    //Metodo che modifica la risoluzione dello schermo e gestisce i messaggi
    private void changeResolution() {
        double currentWidth = Resolution.LOW.getWidth(), currentHeight = Resolution.LOW.getHeight();
        for (int i = 0; i < this.resolutions.size(); i++) {
            if (this.resolutions.get(i).getFirst().equals(resolution.getValue())) {
                currentWidth = this.resolutions.get(i).getSecond().getFirst();
                currentHeight = this.resolutions.get(i).getSecond().getSecond();
                final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                double screenWidth = screenSize.getWidth();
                double screenHeight = screenSize.getHeight();
                boolean check = true;
                if (currentWidth > screenWidth || currentHeight > screenHeight)
                    check = false;
                if (currentWidth == screenWidth && currentHeight == screenHeight)
                    Options.fullScreen = true;
                else
                    Options.fullScreen = false;
                if (check) {
                    GenericMessage.display(true, "Successo", "Impostazioni salvate", "Ok");
                    GameScreen.setResolution(currentWidth, currentHeight, Options.fullScreen);
                } else {
                    GenericMessage.display(false, "Errore", "Il tuo schermo è troppo piccolo per questa risoluzione!", "Torna alle impostazioni");
                    resolution.setValue(Resolution.LOW.getRes());
                    break;
                }
            }
        }
    }

    //Metodo che aggiorna l'opzione corrente
    static void update() {
        difficult.setValue(MainView.getController().getDifficulty());
        fps.setValue(MainView.getController().getFPS());
    }

    //Metodi getter
    static Options get(final Stage mainWindow) {
        mainStage = mainWindow;
        mainStage.setWidth(900);
        mainStage.setHeight(750);
        mainStage.setTitle("StarQuest - Impostazioni");
        return mainScene;
    }

    public static boolean isFullScreen() {
        return fullScreen;
    }
}