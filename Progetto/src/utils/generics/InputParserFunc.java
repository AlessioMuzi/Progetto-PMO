package utils.generics;

import java.util.List;
import java.util.Optional;
import utils.enums.Direction;
import utils.enums.Input;

//Interfaccia funzionale per il parsing degli input
@FunctionalInterface
public interface InputParserFunc {

    //Lambda metodo, restituisce la direzione dopo il parsing e se il giocatore vuole sparare
    Pair<Optional<Direction>, Boolean> parseInputs(List<Input> inputs);
}