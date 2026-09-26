package ulb.views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class permits to manage the will of the user to switch the color mode between dark and light mode. Moreover,
 * this class manages the language of application thanks to the switch between French and English languages.
 */
public class SettingsFxController implements Initializable, Colorable {
    @FXML
    private Button goBack;
    @FXML
    private Button visualizationMode;

    @FXML
    public ChoiceBox LanguageChoice;
    @FXML
    public Button weightUnitChosen;
    @FXML
    public Button lengthUnitChosen;

    @FXML
    private AnchorPane parent;

    private ViewListener listener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.goBack.setOnAction(actionEvent -> listener.goBack());
        this.visualizationMode.setOnAction(actionEvent -> listener.changeColors());
        this.LanguageChoice.getItems().add("fr");
        this.LanguageChoice.getItems().add("en");
        this.LanguageChoice.setOnAction(actionEvent -> listener.changeLanguage(this.LanguageChoice.getValue().toString()));
        this.lengthUnitChosen.setOnAction(actionEvent -> {
            listener.askUpdateLenghtUnitPreferences();
        });
        this.weightUnitChosen.setOnAction(actionEvent -> {
            listener.askUpdateWeightUnitPreferences();
        });
    }


    /**
     * This function permit to the controller of settings to be able to check or to be informed any changes of the view
     * of settings.
     * @param listener
     */
    public void setListener(ViewListener listener){this.listener = listener;}

    /**
     * Changes the text of the weight button
     * If true, the chosen unit are kilos
     * If false, the chosen unit are pounds
     * @param unit the chosen unit
     */
    public void changeWeightButtonText(Boolean unit){
        if(unit){
            weightUnitChosen.setText("Kilogramme");
        }else{
            weightUnitChosen.setText("Pounds");
        }
    }

    /**
     * Changes the text of the length button
     * If true, the chosen unit are meters
     * If false, the chosen unit are feet's
     * @param unit the chosen unit
     */
    public void changeLengthButtonText(Boolean unit){
        if(unit){
            lengthUnitChosen.setText("Mètre");
        }else{
            lengthUnitChosen.setText("Feet");
        }
    }

    @Override
    public void changeMode(boolean darkMode) {
        FxUtils.changeMode(darkMode, this.parent);
    }

    public interface ViewListener {

        /**
         * Change stage to the main menu view
         */
        void goBack();
        void changeColors();
        void changeLanguage(String language);

        /**
         * Allows to modify the preferences of weight unit
         */
        void askUpdateWeightUnitPreferences();
        
         /**
         * Allows to modify the preferences of length unit
         */
        void askUpdateLenghtUnitPreferences();
    }
}
