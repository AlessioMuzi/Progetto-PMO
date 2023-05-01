package view.menu;

import javafx.scene.image.ImageView;
import utils.generics.ImageLoader;

//Classe view che gestisce l'icona del software
public final class Title {

    private final ImageLoader loader = ImageLoader.getLoader();
    private final ImageView   title = new ImageView(this.loader.getImageFromPath("Menu/title.png"));

    //Costruttore
    Title(final double w, final double h) {
        this.title.setFitWidth(w);
        this.title.setFitHeight(h);
    }

    //Metodo getter
    ImageView getTitle() {
        return this.title;
    }
}