package view.game;

import java.util.List;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import utils.generics.*;
import view.menu.MainView;

//Classe view che disgna le entità sullo schermo di gioco
public class DrawEntities {
    
    private static final double SCROLL = 150 / MainView.getController().getFPS();
    private final ImageLoader   loader;
    private final ImageView     bg1;
    private final ImageView     bg2;
    private double              width;
    private double              height;

    //Costruttore
    public DrawEntities(final double w, final double h) {
        this.width = w;
        this.height = h;
        this.loader = ImageLoader.getLoader();
        this.bg1 = new ImageView(this.loader.getImageFromPath("Menu/background.png"));
        this.bg2 = new ImageView(this.loader.getImageFromPath("Menu/background.png"));
        this.bg1.setFitWidth(this.width + SCROLL);
        this.bg1.setFitHeight(this.height);
        this.bg2.setFitWidth(this.width + SCROLL);
        this.bg2.setFitHeight(this.height);
        this.bg2.relocate(this.width, 0);
    }

    //Metodo che disegna le varie entità assegnate sul corretto panel
    void draw(final Pane screen, final List<Pair<Pair<String, Double>, Location>> entities) {
        screen.getChildren().clear();
        final double x = this.bg1.getLayoutX() - DrawEntities.SCROLL;
        final double x2 = this.bg2.getLayoutX() - DrawEntities.SCROLL;
        if (x <= (-this.width + 1))
            this.bg1.relocate(this.width, 0);
        else
            this.bg1.setLayoutX(x);
        if (x2 <= (-this.width + 1))
            this.bg2.relocate(this.width, 0);
        else
            this.bg2.setLayoutX(x2);
        screen.getChildren().addAll(this.bg1, this.bg2);
        entities.forEach(p -> {
            final ImageView image = new ImageView(this.loader.getImageFromPath(p.getFirst().getFirst()));
            image.setPreserveRatio(true);
            final Area area = p.getSecond().getArea();
            image.setFitHeight(this.height * area.getHeight());
            screen.getChildren().add(image);
            image.setX((p.getSecond().getX() - area.getWidth() / 2) * this.height);
            image.setY((p.getSecond().getY() - area.getHeight() / 2) * this.height);
            if (p.getFirst().getSecond().doubleValue() != 0d)
                image.setRotate(p.getFirst().getSecond().doubleValue()); });
    }
}