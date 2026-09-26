package views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import exceptions.FXMLException;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Abstract base class for controllers that manage FXML views.
 */
public abstract class FXMLController {
    protected ResourceBundle bundle;
    /**
     * Loads an FXML file and displays its associated view on the given stage.
     *
     * @param fileName The name of the FXML file to load (without the .fxml extension).
     * @param stage    The stage on which to display the loaded view.
     * @return The FXMLLoader instance that loaded the view.
     * @throws FXMLException If an IOException occurs during loading.
     */
    public FXMLLoader show(String fileName, Stage stage) throws FXMLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/"+fileName+".fxml"), bundle);

        try {
            loader.load();
        } catch (IOException e) {
            throw new FXMLException(e);
        }
        Parent root = loader.getRoot();
        stage.setScene(new Scene(root));
        stage.setTitle(fileName);
        stage.show();
        return loader;
    }

    /**
     * This function allows to display the screen with the language of the user so the correct language.
     * @param language
     */
    public void setBundle(String language){
        Locale locale = new Locale.Builder().setLanguage(language).build();
        bundle = ResourceBundle.getBundle("bundles."+language, locale);
    }

    /**
     * Get the value from the bundle properties file using the key
     * @param key value's key
     * @return the value linked to the key.
     */
    public String getBundleString(String key) {
        return bundle.getString(key);
    }


}
