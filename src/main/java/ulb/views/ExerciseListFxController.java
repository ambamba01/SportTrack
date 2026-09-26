package views;

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
 * This class manages the screen that displays the set of ancient exercises of the user already created.
 */
public class ExerciseListFxController implements Initializable, Colorable {
    @FXML
    private AnchorPane parent;
    @FXML
    private ChoiceBox<String> listTypes;
    @FXML
    private ListView<String> exercisesList;
    @FXML
    private Button addExercise;
    @FXML
    private Button updateExercise;
    @FXML
    private Button deleteExercise;
    @FXML
    private Button goBack;

    private ViewListener listener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillChoiceBox(new ArrayList<>(FxUtils.TypeExo.TYPE_EXO_ARRAY));
        listTypes.setOnAction(actionEvent -> listener.askFilteredExercise(listTypes.getValue()));
        this.goBack.setOnAction(actionEvent -> listener.showHome());
        this.addExercise.setOnAction(actionEvent -> listener.goToAddExercise());
        this.updateExercise.setOnAction(actionEvent -> {
            if (exercisesList.getSelectionModel().getSelectedIndices().size() == 1) {
                listener.goToModifyExercise(getSelectedExerciseIndex());
            }
        });
        this.deleteExercise.setOnAction(actionEvent -> {
            if (exercisesList.getSelectionModel().getSelectedIndices().size() == 1) {
                listener.deleteExercise(getSelectedExerciseIndex());
            }
        });
    }

    /**
     * This function allows to put all type of exercises that exists in the application.
     * @param listExercisesType
     */
    public void fillChoiceBox(List<String> listExercisesType) {
        this.listTypes.setItems(FXCollections.observableList(listExercisesType));
        listTypes.setValue("All");
    }

    private int getSelectedExerciseIndex() {
        return this.exercisesList.getSelectionModel().getSelectedIndex();

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
     * This function refreshes and after completes the "list view" with the exercises filtered previously thanks to,
     * partially, the choice box above.
     * @param exercises
     */
    public void fillListView(List<String> exercises) {
        exercisesList.getItems().clear();
        exercisesList.setItems(FXCollections.observableList(exercises));
    }

    @Override
    public void changeMode(boolean darkMode) {
        FxUtils.changeMode(darkMode, this.parent);
    }


    public interface ViewListener {
        /**
         * Requests a list of exercises filtered by the specified type.
         *
         * @param type The type of exercises to filter by.
         */
        void askFilteredExercise(String type);

        /**
         * Change the stage with the main menu view
         */
        void showHome();

        /**
         * Change the stage with the add exercise view
         */
        void goToAddExercise();

        /**
         * Change the stage with the modify exercise view
         */
        void goToModifyExercise(int indexModifyListView);

        /**
         * delete the selected exercise
         *
         * @param idExercise the list index of the selected exercise
         */
        void deleteExercise(int idExercise);
    }
}
