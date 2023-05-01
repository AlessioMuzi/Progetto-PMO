package view.game;

import java.util.LinkedList;
import java.util.List;
import javafx.scene.input.KeyCode;
import utils.enums.Input;

//Classe che gestisce gli input del giocatore
public final class InputHandler {

    private static final InputHandler INPUTHANDLER = new InputHandler();
    private boolean      w = false;
    private boolean      s = false;
    private boolean      a = false;
    private boolean      d = false;
    private boolean      space = false;

    //Pattern Singleton
    public static InputHandler getInputHandler() {
        return InputHandler.INPUTHANDLER;
    }

    //Metodo che svuota la lista degli input
    public void emptyList() {
        this.w = false;
        this.s = false;
        this.a = false;
        this.d = false;
        this.space = false;
    }

    //Metodo invocato alla pressione di un tasto
    void press(final KeyCode k) {
        this.process(k, true);
    }

    //Metodo invocato al rilascio di un tasto
    void release(final KeyCode k) {
        this.process(k, false);
    }

    //Metodo che cambia lo status di un tasto
    private void process(final KeyCode k, final boolean action) {
        if (k == KeyCode.W)
            this.w = action;
        else if (k == KeyCode.A)
            this.a = action;
        else if (k == KeyCode.S)
            this.s = action;
        else if (k == KeyCode.D)
            this.d = action;
        else if (k == KeyCode.SPACE)
            this.space = action;
    }
    
    //Metodo getter della lista degli input, richiamato ad ogni frame
    public List<Input> getInputs() {
        final List<Input> inputs = new LinkedList<>();
        if (this.w)
            inputs.add(Input.W);
        if (this.s)
            inputs.add(Input.S);      
        if (this.a) 
            inputs.add(Input.A);        
        if (this.d) 
            inputs.add(Input.D);
        if (this.space) 
            inputs.add(Input.SPACE);
        
        return inputs;
    }
}