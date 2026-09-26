package views;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class manages the screen that permits to add a program of exercises according the will of user by intermediary
 * of FXML Objects.
 */
public class ProgrammeAdditionFxController implements Initializable, Colorable {
    @FXML
    private AnchorPane parent;
    @FXML
    private TextArea programmeDescription;
    @FXML
    private TextField programmeName;
    @FXML
    private ListView<String> programmeList;
    @FXML
    private ComboBox<String> exercisesList;
    @FXML
    private ComboBox<String> FilterList;
    @FXML
    private RadioButton radioExercisesType;
    @FXML
    private RadioButton radioExercisesDifficulty;
    @FXML
    private RadioButton radioMuscles;
    @FXML
    private TextField nbRepetition;
    @FXML
    private Button addExercise;
    @FXML
    private Button addProgramme;

    @FXML
    private Button starButton1;
    @FXML
    private SVGPath starButtonSVG1;
    @FXML
    private Button starButton2;
    @FXML
    private SVGPath starButtonSVG2;
    @FXML
    private Button starButton3;
    @FXML
    private SVGPath starButtonSVG3;
    @FXML
    private Button starButton4;
    @FXML
    private SVGPath starButtonSVG4;
    @FXML
    private Button starButton5;
    @FXML
    private SVGPath starButtonSVG5;
    @FXML
    private Button removeExercise;
    @FXML
    private Button goBack;
    @FXML
    private Label Title;
    @FXML
    private RadioButton radioPauseCool;
    @FXML
    private RadioButton radioPauseNormal;
    @FXML
    private RadioButton radioPauseIntense;
    private int exerciseDifficulty = 1;

    private ToggleGroup toggleGroupExercice;
    private ToggleGroup toggleGroupPause;
    private StarSelectionFx starSelection;


    private ViewListener listener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.addExercise.setOnAction(actionEvent -> addExerciseToList(exercisesList.getValue()));
        this.removeExercise.setOnAction(actionEvent -> removeExerciseFromList());
        this.addProgramme.setOnAction(actionEvent -> {
            if (allFieldCompleted()) {
                this.exerciseDifficulty = this.starSelection.getDifficulty(Arrays.asList(this.starButtonSVG1, this.starButtonSVG2,
                        this.starButtonSVG3, this.starButtonSVG4, this.starButtonSVG5));
                listener.askAddProgramme(programmeName.getText(), programmeDescription.getText(), exerciseDifficulty, programmeList.getItems());
            }
        });
        this.FilterList.setOnAction(actionEvent -> listener.changeExercisesList(this.FilterList.getValue()));

        this.radioExercisesType.setOnAction(actionEvent -> updateFilter());
        this.radioExercisesDifficulty.setOnAction(actionEvent -> updateFilter());
        this.radioMuscles.setOnAction(actionEvent -> updateFilter());

        setupRadioButtonExercice();
        setUpRadioButtonPause();


        this.programmeList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.starSelection = new StarSelectionFx(Arrays.asList(this.starButton1, this.starButton2,
                this.starButton3, this.starButton4, this.starButton5),
                Arrays.asList(this.starButtonSVG1, this.starButtonSVG2,
                        this.starButtonSVG3, this.starButtonSVG4, this.starButtonSVG5));
        this.goBack.setOnAction(actionEvent -> listener.goBack());
    }

    private void setupRadioButtonExercice() {
        this.toggleGroupExercice = new ToggleGroup();
        radioExercisesType.setToggleGroup(toggleGroupExercice);
        radioExercisesType.setUserData("type");
        radioExercisesDifficulty.setToggleGroup(toggleGroupExercice);
        radioExercisesDifficulty.setUserData("difficulty");
        radioMuscles.setToggleGroup(toggleGroupExercice);
        radioMuscles.setUserData("muscle");
    }

    private void setUpRadioButtonPause() {
        this.toggleGroupPause = new ToggleGroup();
        radioPauseCool.setToggleGroup(toggleGroupPause);
        radioPauseCool.setUserData("Pause 1min");
        radioPauseNormal.setToggleGroup(toggleGroupPause);
        radioPauseNormal.setUserData("Pause 30s");
        radioPauseIntense.setToggleGroup(toggleGroupPause);
        radioPauseIntense.setUserData("Pause 15s");
    }

    private void updateFilter() {
        showFilterList();
        listener.changeExercisesList("All");
    }


    /**
     * Fill the comboBox with the list of exercises, we add the element Pause to it as well as it is not represented
     * as en exercise in the database but is an easy and stupid way to make it work.
     *
     * @param exercises the list of exercises name
     */
    public void fillExerciseList(List<String> exercises) {
        this.exercisesList.getItems().clear();
        this.exercisesList.getItems().addAll(exercises);
        new AutoCompleteComboBoxListener<>(this.exercisesList);
    }

    /**
     * Show the filter list based on the chosen parameter
     */
    public void showFilterList() {
        String test = (String) toggleGroupExercice.getSelectedToggle().getUserData();
        this.listener.changeFilterList(test);
    }


    /**
     * Fill the combobox with the list of filters based on parameter chosen
     *
     * @param parameter String of the chosen parameter
     */
    public void fillFilterList(String parameter, List<String> muscleArray) {
        this.FilterList.getItems().clear();
        if (!parameter.isBlank()){
            switch (parameter) {
                case "type" -> this.FilterList.getItems().addAll(FxUtils.TypeExo.TYPE_EXO_ARRAY);
                case "difficulty" -> this.FilterList.getItems().addAll(FxUtils.TypeExo.DIFFICULTY_ARRAY);
                case "muscle" -> this.FilterList.getItems().addAll(muscleArray);
            }
        }
        new AutoCompleteComboBoxListener<>(this.FilterList);
    }


    /**
     * Add exercise to the list.
     *
     * @param exercise single exercise
     */
    private void addExerciseToList(String exercise) {
        if (exercise == null || exercise.isBlank()) {
            return;
        }
        try {
            int repetitions = Integer.parseInt(nbRepetition.getText());
            for (int i = 0; i < repetitions; i++) {
                this.programmeList.getItems().add(exercise);
                if (toggleGroupPause.getSelectedToggle() != null) {
                    String pauseType = (String) toggleGroupPause.getSelectedToggle().getUserData();
                    this.programmeList.getItems().add(pauseType);
                }
            }
            if (toggleGroupPause.getSelectedToggle() != null) {
                toggleGroupPause.getSelectedToggle().setSelected(false);
            }
        } catch (NumberFormatException e) {
            this.nbRepetition.clear();
            this.nbRepetition.setPromptText("Ceci n'est pas un nombre !");
        }
    }

    /**
     * Remove all exercices from the list
     */
    private void removeExerciseFromList() {
        ObservableList<Integer> indices = this.programmeList.getSelectionModel().getSelectedIndices().sorted();
        for (int k = indices.size() - 1; k >= 0; k--) {
            this.programmeList.getItems().remove((int) indices.get(k));
        }
    }

    /**
     * This function refreshes the screen that displayed the data of new program in the goal to permit to the user
     * to create an another program afterwards.
     */
    public void resetAllField() {
        this.programmeName.clear();
        this.programmeDescription.clear();
        this.starSelection.changeDifficultySelection(1, Arrays.asList(this.starButtonSVG1, this.starButtonSVG2,
                this.starButtonSVG3, this.starButtonSVG4, this.starButtonSVG5));
        this.programmeList.getItems().clear();
        this.exercisesList.getSelectionModel().clearSelection();
        this.FilterList.getSelectionModel().clearSelection();
        this.nbRepetition.clear();
    }

    /**
     * Use to check if all the fields are completed
     */
    private boolean allFieldCompleted() {
        if (this.programmeName.getText().isBlank() || this.programmeDescription.getText().isBlank() ||
                this.exerciseDifficulty > 5 || this.exerciseDifficulty < 1
                || this.programmeList.getItems().isEmpty()) {
            listener.displayIncorrectForm();
            return false;
        } else {
            return true;
        }

    }

    /**
     * rename all the label for modification view
     */
    public void renameLabel(String stringTitle, String stringButton) {
        this.Title.setText(stringTitle);
        this.addProgramme.setText(stringButton);
    }

    @Override
    public void changeMode(boolean darkMode) {
        FxUtils.changeMode(darkMode, this.parent);
    }

    /**
     * Fill all the fields with the selected program data (only for modification view)
     *
     * @param difficulty    program difficulty
     * @param description   program description
     * @param name          program name
     * @param exercicesName list of selected exercise name
     */
    public void fillAllFields(int difficulty, String description, String name, List<String> exercicesName) {
        this.programmeDescription.setText(description);
        this.programmeName.setText((name));
        this.starSelection.changeDifficultySelection(difficulty, Arrays.asList(this.starButtonSVG1, this.starButtonSVG2,
                this.starButtonSVG3, this.starButtonSVG4, this.starButtonSVG5));
        this.programmeList.getItems().clear();
        this.programmeList.getItems().addAll(exercicesName);

    }

    /**
     * This function allows to ProgrammeAdditionController to become the listener.
     * @param listener
     */
    public void setListener(ViewListener listener) {
        this.listener = listener;
    }

    public interface ViewListener {
        /**
         * Display a popup to inform that the form is not correct
         */
        void displayIncorrectForm();

        /**
         * Ask the controller to add a program inside the db.
         *
         * @param programmeName        program name
         * @param programmeDescription programe description
         * @param exerciseDifficulty   exercise difficulty
         * @param chosenExercises      chosen exercise index on the list.
         */
        void askAddProgramme(String programmeName, String programmeDescription, int exerciseDifficulty, ObservableList<String> chosenExercises);

        /**
         * Change stage to the main menu view
         */
        void goBack();

        void changeFilterList(String parameter);

        void changeExercisesList(String filterVal);
    }
}
