package views;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import exceptions.UploadException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class allows to manage the screen that permits to create or modify an exercise, by intermediary FXML objects.
 */
public class ExerciseAdditionFxController implements Initializable, Colorable {
    @FXML
    private AnchorPane parent;
    @FXML
    private TextField caloriesAmount;
    @FXML
    private ChoiceBox<String> calorieCalculation;
    @FXML
    private Label title;
    @FXML
    private ChoiceBox formuleTaille;
    @FXML
    private ChoiceBox formulePoids;
    @FXML
    private AnchorPane choixCal;
    @FXML
    private TextField poidsFloat;
    @FXML
    private TextField tailleFloat;
    @FXML
    private ChoiceBox<String> exerciseType;
    @FXML
    private ComboBox<String> usedMuscle;
    @FXML
    private Button chooseFile;
    @FXML
    private Label imagePath;
    @FXML
    private TextField exerciseName;
    @FXML
    private TextArea exerciseDescription;
    private byte[] byteArrayInstance = new byte[]{0};
    @FXML
    private Button addExercise;
    private ViewListener listener;
    private final String[] types = {"Musculation", "Endurance", "Agilité", "Coordination"};
    private final String[] typesCalculeCalorie = {"Fix", "Choix Personnel"};

    private final String[] signes = {"x", "%"};
    private int exerciseDifficulty = 1;
    @FXML
    private ChoiceBox<Integer> duration;
    private final Integer[] durationList = {1, 2, 3, 4, 5, 10, 15, 20, 25, 30};
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
    private Button goBack;
    private String imageName = "";

    private StarSelectionFx starSelection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        calorieCalculation.getItems().addAll(typesCalculeCalorie);
        exerciseType.getItems().addAll(types);
        formuleTaille.getItems().addAll(signes);
        formuleTaille.getSelectionModel().selectFirst();
        formulePoids.getItems().addAll(signes);
        formulePoids.getSelectionModel().selectFirst();
        duration.getItems().addAll(durationList);
        calorieCalculation.getSelectionModel().selectFirst();
        calorieCalculation.setOnAction(actionEvent -> {
            String temp = calorieCalculation.getValue();
            changeCaloriesInputSettings(temp);
        });

        addExercise.setOnAction(actionEvent -> {
            if (allFieldCompleted()) {
                this.exerciseDifficulty = this.starSelection.getDifficulty(Arrays.asList(this.starButtonSVG1, this.starButtonSVG2,
                        this.starButtonSVG3, this.starButtonSVG4, this.starButtonSVG5));
                listener.askAddExercise(exerciseName.getText(),
                        exerciseType.getValue(),
                        exerciseDifficulty,
                        exerciseDescription.getText(),
                        imageName,
                        byteArrayInstance,
                        usedMuscle.getValue(),
                        duration.getValue() * 60,
                        getAmountOfCalories(calorieCalculation.getValue()));
            }
        });

        this.starSelection = new StarSelectionFx(Arrays.asList(this.starButton1, this.starButton2,
                this.starButton3, this.starButton4, this.starButton5),
                Arrays.asList(this.starButtonSVG1, this.starButtonSVG2,
                        this.starButtonSVG3, this.starButtonSVG4, this.starButtonSVG5));

        this.goBack.setOnAction(actionEvent -> listener.goBack());
        chooseFile.setOnAction(actionEvent -> listener.askToChooseFile());
    }

    private int getAmountOfCalories(String calculationType) {
        return switch (calculationType) {
            case "Fix" -> Integer.parseInt(caloriesAmount.getText());
            case "Choix Personnel" -> listener.getCalChoixPers(Float.parseFloat(poidsFloat.getText()),
                    this.formulePoids.getValue().toString(), Float.parseFloat(tailleFloat.getText()),
                    this.formuleTaille.getValue().toString());
            default -> 0;
        };
    }

    private void changeCaloriesInputSettings(String calculationType) {
        if (calculationType != null) {
            switch (calculationType) {
                case "Fix":
                    caloriesAmount.setDisable(false);
                    caloriesAmount.setVisible(true);
                    choixCal.setDisable(true);
                    choixCal.setVisible(false);
                    break;
                case "Choix Personnel":
                    caloriesAmount.setDisable(true);
                    caloriesAmount.setVisible(false);
                    choixCal.setDisable(false);
                    choixCal.setVisible(true);
                    break;
            }
        }
    }


    /**
     * Check if all field are completed
     *
     * @return true if yes
     */
    private boolean allFieldCompleted() {
        if (this.exerciseName.getText().isBlank()
                || this.exerciseDescription.getText().isBlank()
                || this.byteArrayInstance.length == 0
                || this.imagePath.getText().isBlank()
                || this.exerciseDifficulty > 5
                || this.exerciseDifficulty < 1
                || this.exerciseType.getSelectionModel().isEmpty()
                || this.usedMuscle.getSelectionModel().isEmpty()
                || this.duration.getSelectionModel().isEmpty()
                || checkIfCaloriesAmountIsNotANumber()) {
            listener.displayIncorrectForm();
            return false;
        } else {
            return true;
        }

    }

    /**
     * Checks if the field Calorie amount was properly filled
     * the purpose with the i variable is to check if the calories field was indeed used with real numbers
     *
     * @return true if the field was not correctly filled otherwise false
     */
    private boolean checkIfCaloriesAmountIsNotANumber() {
        if(calorieCalculation.getValue().equals("Choix Personnel") && !this.tailleFloat.getText().isBlank() &&
                !this.poidsFloat.getText().isBlank()){return false;}
        try{
            if (!this.caloriesAmount.getText().isBlank()){
                Integer.parseInt(caloriesAmount.getText());
                return false;
            }
        } catch (Exception e) {
            caloriesAmount.clear();
            caloriesAmount.setPromptText("Compléter votre champs de calorie correctement !");
        }
        return true;
    }

    /**
     * Fill the muscl combo box with the given list
     *
     * @param muscles list of muscle name
     */
    public void fillMuscleList(List<String> muscles) {
        usedMuscle.getItems().addAll(muscles);
        new AutoCompleteComboBoxListener<>(this.usedMuscle);

    }

    /**
     * This function allows to ExerciseAdditionController and ExerciseModificationController to become the listener of
     * ExerciseAdditionFxController.
     * @param listener
     */
    public void setListener(ViewListener listener) {
        this.listener = listener;
    }

    /**
     * Reset all fields to its initial value
     */
    public void resetAllField() {
        this.exerciseType.getSelectionModel().clearSelection();
        this.usedMuscle.getSelectionModel().clearSelection();
        this.imagePath.setText("");
        this.exerciseName.clear();
        this.exerciseDescription.clear();
        this.starSelection.changeDifficultySelection(1, Arrays.asList(this.starButtonSVG1, this.starButtonSVG2,
                this.starButtonSVG3, this.starButtonSVG4, this.starButtonSVG5));
        this.byteArrayInstance = new byte[]{0};
        this.imageName = "";
        this.duration.getSelectionModel().clearSelection();
        this.calorieCalculation.getSelectionModel().clearSelection();
        this.caloriesAmount.setText("");
        this.poidsFloat.setText("");
        this.tailleFloat.setText("");
        this.changeCaloriesInputSettings("Fix");
    }

    /**
     * Allow the user to select an image from it system
     */
    public void pickAnImage() throws UploadException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.jpg"));
        File f = fc.showOpenDialog(null);
        imagePath.setText(f.getAbsolutePath());

        File file = new File(imagePath.getText());
        this.imageName = file.getName();
        try (FileInputStream fis = new FileInputStream(imagePath.getText())) {
            this.byteArrayInstance = new byte[fis.available()];
            fis.read(this.byteArrayInstance);
        } catch (IOException e) {
            throw new UploadException(e);
        }
    }

    /**
     *
     * @param stringTitle
     * @param stringButton
     */
    public void renameLabels(String stringTitle, String stringButton) {
        this.title.setText(stringTitle);
        this.addExercise.setText(stringButton);
    }

    /**
     * This function allows to display the actual data of the exercise for modification.
     * @param name
     * @param description
     * @param exerciseDuration
     * @param calories
     * @param type
     * @param difficulty
     * @param muscleModelName
     * @param imageName
     * @param imageData
     */
    public void fillAllFields(String name, String description, int exerciseDuration, int calories, String type, int difficulty, String muscleModelName, String imageName, byte[] imageData) {
        this.exerciseDescription.setText(description);
        this.exerciseName.setText(name);
        this.starSelection.changeDifficultySelection(difficulty, Arrays.asList(this.starButtonSVG1, this.starButtonSVG2,
                this.starButtonSVG3, this.starButtonSVG4, this.starButtonSVG5));
        this.caloriesAmount.setText(String.valueOf(calories));
        this.duration.getSelectionModel().select(this.duration.getItems().indexOf(exerciseDuration));
        this.exerciseType.setValue(type);
        this.usedMuscle.setValue(muscleModelName);
        this.imagePath.setText("image stocké : " + imageName);
        this.imageName = imageName;
        this.byteArrayInstance = imageData;
    }

    @Override
    public void changeMode(boolean darkMode) {
        FxUtils.changeMode(darkMode, this.parent);
    }

    public interface ViewListener {

        /**
         * Function inserting the new exercises into the db
         *
         * @param name        exercise name
         * @param type        exercise type
         * @param difficulty  exercise difficulty
         * @param description exercise description
         * @param imageName   image name
         * @param imageData   image byte array
         * @param muscle      muscle name
         * @param duration    exercise duration
         */
        void askAddExercise(String name, String type, int difficulty, String description, String imageName,
                            byte[] imageData, String muscle, int duration, int calories);

        /**
         * allow the user to select a image from it system
         */
        void askToChooseFile();

        /**
         * Display a message dialog informing the incorectness of the form
         */
        void displayIncorrectForm();

        /**
         * Change the view to main menu view
         */
        void goBack();


        int getCalChoixPers(float poids, String formulePoids, float taille, String formuleTaille);
    }

}


