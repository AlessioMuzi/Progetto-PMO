package utils.generics;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;

//Classe di utility che gestisce il caricamento delle immagini, implementata con pattern Singleton
public final class ImageLoader {

	private static ImageLoader        loader;
	private final  Map<String, Image> images;

	//Costruttore
    private ImageLoader() {
        this.images = new HashMap<>();
    }
    
	//Pattern Singleton
	public static ImageLoader getLoader() {
		if (ImageLoader.loader == null)
			ImageLoader.loader = new ImageLoader();
		return ImageLoader.loader;
	}

	//Metodo che restituisce l'immagine voluta
	public Image getImageFromPath(final String path) {
		try {
			if (!this.images.containsKey(path))
				this.images.put(path, new Image(ImageLoader.class.getResourceAsStream("/" + path)));
			return this.images.get(path);
		} catch (final Exception e) {
			System.out.println("Errore durante il caricamento di " + path);
		}
		return null;
	}
}