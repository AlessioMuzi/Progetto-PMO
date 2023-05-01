package view.game;

import javafx.stage.Stage;
import view.message.ConfirmMessage;
import view.menu.MainView;

//Classe che gestisce la corretta chiusura del software
public final class ClosureHandler {

    private static final ClosureHandler HANDLER = new ClosureHandler();

    //Metodo che gestisce la chiusura del software
    public static void closeProgram(final Stage mainWindow) {
        final Boolean confirm = ConfirmMessage.display("Attenzione", "Sei sicuro di volere chiudere il gioco?");
        if (MainView.getController().isPaused()) {
            if (confirm) {
                MainView.getController().abortGame();
                System.exit(0);
                mainWindow.close();
            } else
                MainView.getController().resumeGame();
        }
        if (confirm) {
            System.exit(0);
            mainWindow.close();
        }
    }
    
    //Metodo getter
    public static ClosureHandler getClosureHandler() {
        return ClosureHandler.HANDLER;
    }
}