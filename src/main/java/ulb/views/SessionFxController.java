package views;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.layout.AnchorPane;
/**
 * This class permits to display the names of programmes on the listview thanks to the type chooses by the user on the
 * choice box.
 */
public class SessionFxController implements Initializable, Colorable {
    @FXML
    private AnchorPane parent;

    @FXML
    private ListView<String> listViewProg;
    @FXML
    private ChoiceBox<String> exerciseType;
    @FXML
    private Label selectedProg;
    @FXML
    private Button runSession;
    @FXML
    private Button goBack;
    private ViewListener listener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedProg.textProperty().bind(listViewProg.getSelectionModel().selectedItemProperty());
        fillCBox(FxUtils.TypeExo.TYPE_EXO_ARRAY);
        exerciseType.setOnAction(actionEvent -> listener.askFilteredProgrammes(exerciseType.getValue()));
        this.runSession.setOnAction(actionEvent -> {
            if (!this.listViewProg.getSelectionModel().isEmpty()) {
                listener.runSession(this.listViewProg.getSelectionModel().getSelectedIndex());
            }
        });
        this.goBack.setOnAction(actionEvent -> listener.showHome());
    }

    /**
     * This function fill the choice box that displays the type of exercise like bodybuilding, stamina,...
     * @param listExercisesType
     */
    public void fillCBox(List<String> listExercisesType) {
        exerciseType.setItems(FXCollections.observableList(listExercisesType));
        exerciseType.setValue("All");
    }

    /**
     * this function fill the list view of the session view with the programmes that contain the type of exercise
     * chooses by user.
     * @param programmes
     */
    public void fillListView(List<String> programmes) {
        listViewProg.getItems().clear();
        listViewProg.setItems(FXCollections.observableList(programmes));
    }

    @Override
    public void changeMode(boolean darkMode) {
        FxUtils.changeMode(darkMode, this.parent);
    }

    /**
     * This function permit to the controller of session to be able to check or to be informed any changes of the view
     * of session.
     * @param listener
     */
    public void setListener(ViewListener listener) {
        this.listener = listener;
    }


    public interface ViewListener {
        void runSession(int selectedProgram);

        void askFilteredProgrammes(String s);

        void showHome();

    }
}

