package Core;



import java.io.IOException;
import java.util.logging.*;

public class GameLogger {

    public static Logger LOGGER;

    static {
        try {
            LOGGER = Logger.getLogger("GAME_LOGGER");
            LOGGER.setUseParentHandlers(false);

            // Handler fichier
            FileHandler fileHandler = new FileHandler("game.log", true);
            fileHandler.setFormatter(new GameLogFormatter());

            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.INFO);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private GameLogger() {}
}

