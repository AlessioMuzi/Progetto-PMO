package view.menu;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.generics.Pair;
import view.message.*;

//Classe view che gestisce i punteggi
public class Scores extends Scene {

    private static final Scores MAINSCENE = new Scores();
    private static VBox         scoreBox;
    private static Stage        mainStage;

    //Costruttore
    public Scores() {
        super(new StackPane());
        Text text = new Text("Punteggi");
        text.setFont(Font.font(null, FontWeight.BOLD, 46));
        text.setText("Punteggi");
        text.setStyle("-fx-fill: linear-gradient(cyan , dodgerblue)");
        scoreBox = new VBox();
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.setStyle("-fx-font-family: Arial;" + "-fx-text-fill: white !important;" + "-fx-font-size: 18pt;");
        scoreBox.setPadding(new Insets(10));
        final VBox layout = new VBox(10);
        final Button back = new Button("Menu principale");
        final Button reset = new Button("Resetta i punteggi");
        final StackPane bottomLayout = new StackPane();
        final HBox bottomBox = new HBox();
        reset.setId("button");
        back.setId("button");
        bottomLayout.setAlignment(Pos.BOTTOM_CENTER);
        bottomLayout.setPadding(new Insets(0, 0, 100, 0));
        bottomBox.setSpacing(15);
        bottomBox.setAlignment(Pos.BOTTOM_CENTER);
        bottomBox.getChildren().addAll(back, reset);
        bottomLayout.getChildren().add(bottomBox);
        final StackPane mainLayout = new StackPane();
        layout.getChildren().addAll(text, scoreBox);
        layout.setSpacing(10);
        layout.setPadding(new Insets(8));
        layout.setAlignment(Pos.TOP_CENTER);
        mainLayout.getChildren().addAll(layout, bottomLayout);
        mainLayout.setStyle("-fx-background-image: url(Menu/scores.jpg);" + "-fx-font-family: Arial;" + "-fx-font-size: 18pt;");
        this.setRoot(mainLayout);
        this.getStylesheets().add("button.css");
        back.setOnAction(e -> {
            scoreBox.getChildren().clear();
            mainStage.setScene(MainMenu.get(mainStage)); });
        reset.setOnAction(e -> {this.resetScores(); });
    }

    //Metodo che mostra i punteggi
    private static void showScores() {
        final List<Pair<String, Integer>> scores = MainView.getController().getScores();
        final Label noScore = new Label("Nessun punteggio registrato");
        noScore.setStyle("-fx-text-fill: white !important;");
        if (scores.isEmpty())
            scoreBox.getChildren().add(noScore);
        else
            for (int i = 0; i < scores.size(); i++) {
                final Label player = new Label();
                player.setStyle("-fx-font-family: Arial;" + "-fx-text-fill: white !important;" + "-fx-font-size: 18pt;");
                player.setText(scores.get(i).getFirst() + " - " + scores.get(i).getSecond());
                scoreBox.getChildren().add(player);
            }
    }

    //Metodo che resetta i punteggi
    private void resetScores() {
        final Boolean confirm = ConfirmMessage.display("Attenzione", "Sei sicuro di volere resettare i punteggi?");
        if (confirm)
            if (MainView.getController().emptyScores())
                Scores.scoreBox.getChildren().clear();
            else
                GenericMessage.display(false, "Errore", "Si è verificato un errore durante il reset dei punteggi", "Continua");
            mainStage.setScene(Scores.get(mainStage));
    }
    
    //Metodo getter
    public static Scene get(final Stage mainWindow) {
        showScores();
        mainStage = mainWindow;
        mainStage.setWidth(900);
        mainStage.setHeight(750);
        mainStage.setTitle("StarQuest - Punteggi");
        return MAINSCENE;
    }
}