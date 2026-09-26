package ulb.views;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for managing the exercise programme selection view in a fitness application.
 * This view allows users to filter and select exercise programmes based on their type.
 */
public class ProgrammeFxController implements Initializable, Colorable {
    @FXML
    private AnchorPane parent;
    @FXML
    private ChoiceBox<String> listTypes; // ChoiceBox for selecting the type of exercise programmes.

    @FXML
    private ListView<String> programmeList; // ListView to display a list of exercises.

    @FXML
    private Button goBack;

    @FXML
    private Button addProgramme;

    @FXML
    private Button updateProgramme;

    @FXML
    private Button deleteProgramme;

    private ViewListener listener; // Listener interface for interaction with other parts of the application.

    /**
     * Initializes the controller class. Automatically called after the fxml file has been loaded.
     * Populates the choice box with default exercise types.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if not known.
     * @param resourceBundle The resources used to localize the root object, or null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillChoiceBox(new ArrayList<>(FxUtils.TypeExo.TYPE_EXO_ARRAY));
        listTypes.setOnAction(actionEvent -> listener.askFilteredProgrammes(listTypes.getValue()));
        this.goBack.setOnAction(actionEvent -> listener.showHome());
        this.addProgramme.setOnAction(actionEvent -> listener.goToaddProgramme());
        this.updateProgramme.setOnAction(actionEvent -> {
            if (programmeList.getSelectionModel().getSelectedIndices().size() == 1) {
                listener.goToModifyProgramme(getSelectedProgrammeIndex());
            }
        });
        this.deleteProgramme.setOnAction(actionEvent -> {
            if (programmeList.getSelectionModel().getSelectedIndices().size() == 1) {
                listener.deleteProgramme(getSelectedProgrammeIndex());
            }
        });
    }

    /**
     * Fills the ChoiceBox with available types of exercise programmes.
     * The first option is always "All", indicating no filter is applied.
     *
     * @param listExercisesType List of strings representing the types of exercise programmes.
     */
    public void fillChoiceBox(List<String> listExercisesType) {
        listTypes.setItems(FXCollections.observableList(listExercisesType));
        listTypes.setValue("All");
    }

    @Override
    public void changeMode(boolean darkMode) {
        FxUtils.changeMode(darkMode, this.parent);
    }

    private int getSelectedProgrammeIndex() {
        return this.programmeList.getSelectionModel().getSelectedIndex();

    }

    /**
     * Fills the ListView with exercise programmes. Can be used to display programmes
     * filtered by the selected type.
     *
     * @param programmes List of strings representing the names of exercise programmes.
     */
    public void fillListView(List<String> programmes) {
        programmeList.getItems().clear();
        programmeList.setItems(FXCollections.observableList(programmes));
    }


    /**
     * Sets the listener for this view, allowing communication with other components of the application.
     *
     * @param listener The listener to be associated with this view.
     */
    public void setListener(ViewListener listener) {
        this.listener = listener;
    }

    /**
     * Interface for communication between the controller and other parts of the application,
     * such as the model or other controllers. This interface defines methods for requesting
     * filtered lists of exercise programmes.
     */
    public interface ViewListener {
        /**
         * Requests a list of exercise programmes filtered by the specified type.
         *
         * @param type The type of exercise programmes to filter by.
         */
        void askFilteredProgrammes(String type);

        /**
         * Change the stage with the main menu view
         */
        void showHome();

        /**
         * Change the stage with the add programme view
         */
        void goToaddProgramme();

        /**
         * Change the stage with the modify programme view
         */
        void goToModifyProgramme(int indexModifyListView);

        /**
         * delete the selected programme
         *
         * @param idProgramme the list index of the selected programme
         */
        void deleteProgramme(int idProgramme);

    }
}
