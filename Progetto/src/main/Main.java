package main;

import view.menu.MainView;
import controller.MainController;
import controller.MainControllerImpl; 

//Classe main che esegue il software del videogioco StarQuest
public final class Main {

    //Metodo main
    public static void main(final String[] args) {
        final MainController c = new MainControllerImpl();
        final MainView v = new MainView(c);
        c.setView(v);
        v.startView();
    }
}