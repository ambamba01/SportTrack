package ulb;

import javafx.application.Application;
import javafx.stage.Stage;
import ulb.controllers.Presenter;
import ulb.exceptions.ConfigManagerException;
import ulb.exceptions.LogManagerException;
import ulb.utils.ConfigManager;
import ulb.utils.LogManager;
import ulb.utils.Utils;

import java.util.logging.Level;

/**
 * Main class
 */
public class Main extends Application {

    private Presenter presenter;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        runLogger();
        runConfig();
        primaryStage.setResizable(false);
        presenter = new Presenter(primaryStage);
        this.presenter.showLogin();
    }

    private void runLogger() {
        try {
            LogManager.getInstance().load("./logs.log");
        } catch (LogManagerException ex) {
            Utils.displayPopup("Impossible to start the application. Please contact the admin.");
            ex.printStackTrace();
        }
    }


    /**
     * Load the db via the configManager
     */
    private void runConfig() {
        try {
            ConfigManager.getInstance().load();
            ConfigManager.getInstance().getProperties("./db.url");
        } catch (ConfigManagerException ex) {
            Utils.displayPopup("Impossible to contact the db. Please contact the admin.");
            LogManager.getInstance().getLogger().log(Level.SEVERE, ex.getMessage());
        }
    }

}
