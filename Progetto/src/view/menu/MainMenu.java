package view.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import view.game.*;

//Classe view che gestisce il menù principale
public final class MainMenu extends Scene {

    private static final MainMenu MAINSCENE = new MainMenu();
    private static Stage          mainStage;
    private final Button          nuovaPartita = new Button("Nuova partita");
    private final Button          punteggi = new Button("Punteggi");
    private final Button          impostazioni = new Button("Impostazioni");
    private final Button          esci = new Button("Esci");

    //Costruttore
    private MainMenu() {
        super(new StackPane(), 1400, 800);
        final StackPane titleBox = new StackPane();
        final Title title = new Title(500, 250);
        titleBox.setAlignment(Pos.TOP_CENTER);
        titleBox.getChildren().add(title.getTitle());
        titleBox.setPadding(new Insets(60));
        final HBox buttonBox = new HBox(nuovaPartita, punteggi, impostazioni, esci);
        buttonBox.setPrefWidth(250);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(100));
        this.nuovaPartita.setMinWidth(buttonBox.getPrefWidth());
        this.nuovaPartita.setId("button");
        this.nuovaPartita.setOnAction(e -> {
            InputHandler.getInputHandler().emptyList();
            MainView.getController().startGame();
            final GameScreen screen = new GameScreen();
            MainView.setGameScreen(screen);
            mainStage.setScene(screen.get(mainStage));
            mainStage.setFullScreen(screen.isFullScreen()); });
        this.punteggi.setMinWidth(buttonBox.getPrefWidth());
        this.punteggi.setId("button");
        this.punteggi.setOnAction(e -> {mainStage.setScene(Scores.get(MainMenu.mainStage)); });
        this.impostazioni.setMinWidth(buttonBox.getPrefWidth());
        this.impostazioni.setId("button");
        this.impostazioni.setOnAction(e -> {
            mainStage.setScene(Options.get(MainMenu.mainStage));
            Options.update(); });
        this.esci.setMinWidth(buttonBox.getPrefWidth());
        ClosureHandler.getClosureHandler();
        this.esci.setOnAction(e -> ClosureHandler.closeProgram(mainStage));
        this.esci.setId("button");
        final StackPane layout = new StackPane();
        layout.getChildren().addAll(titleBox, buttonBox);
        layout.setStyle("-fx-background-image: url(/Menu/lost-vikings.jpg);" + "-fx-background-size: cover;" + "-fx-background-color: white;" + "-fx-font-size: 15pt;" + "-fx-font-family: space_invaders;");
        this.setRoot(layout);
        this.getStylesheets().add("button.css");
        Font.loadFont(getClass().getResourceAsStream("/Font/Starcraft-Normal.ttf"), 16);
    }

    //Metodo getter
    public static MainMenu get(final Stage mainWindow) {
        mainStage = mainWindow;
        mainStage.setFullScreen(false);
        mainStage.setWidth(1400);
        mainStage.setHeight(800);
        mainStage.centerOnScreen();
        mainStage.setTitle("StarQuest - Menu");
        return MAINSCENE;
    }
}