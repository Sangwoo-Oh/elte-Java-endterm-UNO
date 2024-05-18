package uno.util;

import java.lang.Exception;

public class NotEnoughPlayersException extends Exception {
    public NotEnoughPlayersException(int players) {
        super("Only " + players + " players were given");
    };
}
