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

//Classe view che gestisce la creazione di un messaggio a schermo con delle scelte
public final class ConfirmMessage {

    private static boolean confirm;

    //Metodo che mostra il messaggio
    public static boolean display(final String title, final String message) {
        final Stage box = new Stage();
        box.getIcons().add(ImageLoader.getLoader().getImageFromPath("Icons/alert.png"));
        box.setResizable(false);
        box.centerOnScreen();
        box.initModality(Modality.APPLICATION_MODAL);
        box.setTitle(title);
        box.setMinWidth(350);
        final Label label = new Label();
        label.setText(message);
        final Button yes = new Button("Si");
        final Button no = new Button("No");
        yes.setOnAction(e -> {
            confirm = true;
            box.close(); });
        no.setOnAction(e -> {
            confirm = false;
            box.close(); });
        final VBox layout = new VBox(10);
        final HBox button = new HBox(50);
        button.getChildren().addAll(yes, no);
        button.setSpacing(10);
        button.setPadding(new Insets(8));
        button.setAlignment(Pos.CENTER);
        layout.setMinWidth(350);
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);
        final Scene scene = new Scene(layout);
        box.setScene(scene);
        box.showAndWait();

        return confirm;
    }
}