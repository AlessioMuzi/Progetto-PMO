package view.message;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.generics.ImageLoader;

//Classe view di un box generico con messaggio
public final class GenericMessage {

    //Metodo che mostra il generico messaggio a schermo
    public static void display(final boolean type, final String title, final String message, final String buttonMessage) {
        final Stage stage = new Stage();
        if (type == false)
            stage.getIcons().add(ImageLoader.getLoader().getImageFromPath("Icons/error.png"));
        else
            stage.getIcons().add(ImageLoader.getLoader().getImageFromPath("Icons/success.png"));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(350);
        final Label label = new Label();
        label.setText(message);
        final Button confirm = new Button("Continua");
        confirm.setOnAction(e -> {stage.close(); });
        final VBox layout = new VBox(10);
        layout.setMinWidth(350);
        final HBox button = new HBox(50);
        button.getChildren().addAll(confirm);
        button.setSpacing(10);
        button.setPadding(new Insets(8));
        button.setAlignment(Pos.CENTER);
        layout.setMinWidth(350);
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);
        final Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }
}