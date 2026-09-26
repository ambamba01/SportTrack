package ulb.utils;

import ulb.exceptions.LogManagerException;

import java.io.IOException;
import java.util.logging.*;

/**
 * This class manages the set of logs that appears all along of project.
 */
public class LogManager {


    private static final String FILE = "/config/log.properties";

    private static Logger logger;

    private LogManager() {
        logger = Logger.getLogger(LogManager.class.getName());
    }

    /**
     * Loads the properties from this url.
     *
     * @throws IOException if no file is found.
     */
    public void load(String outputFile) throws LogManagerException {
        try {
            java.util.logging.LogManager.getLogManager().readConfiguration(getClass().getResourceAsStream(FILE));
            Handler fileHandler = new FileHandler(outputFile);
            logger.addHandler(fileHandler);
        } catch (SecurityException | IOException ex) {
            throw new LogManagerException(ex);
        }
    }

    /**
     * Return the logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Returns the instance of the singleton.
     *
     * @return the instance of the singleton.
     */
    public static LogManager getInstance() {
        return LogManager.LogManagerHolder.INSTANCE;
    }

    private static class LogManagerHolder {
        private static final LogManager INSTANCE = new LogManager();
    }
}
