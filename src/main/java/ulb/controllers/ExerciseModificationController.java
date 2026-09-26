package controllers;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import database.repository.ExerciseMuscleRepository;
import database.repository.ExerciseRepository;
import exceptions.AlreadyExistException;
import exceptions.FXMLException;
import exceptions.RepositoryException;
import exceptions.UploadException;
import models.Exercise;
import models.User;
import utils.LogManager;
import utils.Utils;
import views.ExerciseAdditionFxController;
import java.util.logging.Level;
/**
 * Controller class for modifying an exercise.
 * Extends ExerciseManager and implements ViewListener from ExerciseAdditionFxController.
 */
public class ExerciseModificationController extends ExerciseManager implements ExerciseAdditionFxController.ViewListener {
    private final ControllerListener listener;
    private final Stage stage;

    /**
     * Constructor for ExerciseModificationController.
     * Initializes the controller with the stage, current user, listener, and exercise to be modified.
     *
     * @param stage    The stage on which the view will be shown.
     * @param currentUser The current user session.
     * @param listener The listener for controller communication.
     * @param exercise The exercise to be modified.
     */
    public ExerciseModificationController(Stage stage, User currentUser, ControllerListener listener, Exercise exercise) {
        this.listener = listener;
        this.stage = stage;
        super.currentUser = currentUser;
        super.exercise = exercise;
        super.setBundle(this.currentUser.getLanguage());
    }

    /**
     * Displays the exercise addition form with pre-filled data for modification.
     * Sets up the controller and initializes the view components.
     *
     * @throws FXMLException If there is an issue loading the FXML.
     */
    public void show() throws FXMLException {
        FXMLLoader loader = super.show("ExerciseAddition", this.stage);
        this.exerciseManagerFxController = loader.getController();
        this.exerciseManagerFxController.setListener(this);
        this.exerciseManagerFxController.renameLabels(super.bundle.getString("modifyExerciseTitle"),
                super.bundle.getString("modifyExerciseButton"));
        this.exerciseManagerFxController.fillMuscleList(getAllStrMuscles());
        this.getMuscleForExercise(super.exercise.getId());
        this.exerciseManagerFxController.fillAllFields(exercise.getName(), exercise.getDescription(),
                exercise.getExerciseDuration() / 60, exercise.getCalories(), exercise.getType(), exercise.getDifficulty(),
                exercise.getMuscleName(), exercise.getImageName(), exercise.getImageData());
        this.exerciseManagerFxController.changeMode(this.currentUser.getColorMode());
    }

    /**
     * Fetch the muscle used by the exercise
     *
     * @param exerciseId id of the exercise we want to modify
     */
    private void getMuscleForExercise(int exerciseId) {
        try {
            super.exercise.setMuscle(super.getMuscleFromExerciseId(exerciseId));
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(super.getBundleString("MuscleProblem"));
        }

    }

    /**
     * Call's the private method to start the update sequence
     *
     * @param name        exercise name
     * @param type        exercise type
     * @param difficulty  exercise difficulty
     * @param description exercise description
     * @param imageName   image name
     * @param imageData   image byte array
     * @param muscle      muscle name
     * @param duration    exercise duration
     * @param calories    exercise calories
     */
    @Override
    public void askAddExercise(String name, String type, int difficulty, String description, String imageName, byte[] imageData, String muscle, int duration, int calories) {
        modifyExercise(name, type, difficulty, description, imageData, muscle, duration, calories);

    }

    /**
     * Triggers the file picker for selecting an image.
     */
    @Override
    public void askToChooseFile() {
        try {
            super.exerciseManagerFxController.pickAnImage();
        } catch (UploadException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(super.getBundleString("UploadImageError"));
        }
    }

    /**
     * Display a message dialog informing the incorrectness of the form
     */
    @Override
    public void displayIncorrectForm() {
        Utils.displayPopup(super.getBundleString("IncorrectForm"));
    }

    @Override
    public void goBack() {
        this.stage.hide();
        listener.showListExercises();
    }

    @Override
    public int getCalChoixPers(float weight, String weightFactor, float height, String heightFactor) {
        return currentUser.getCaloriesPersonalChoice(weight, weightFactor, height, heightFactor);
    }

    /**
     * Execute the sequence to update an exercise
     *
     * @param name        exercise name
     * @param type        exercise type
     * @param difficulty  exercise difficulty
     * @param description exercise description
     * @param imageData   image byte array
     * @param muscleName  muscle name
     * @param duration    exercise duration
     * @param calories    exercise calories
     */
    private void modifyExercise(String name, String type, int difficulty, String description, byte[] imageData, String muscleName, int duration, int calories) {
        setExerciseToModify(name, type, difficulty, description, imageData, duration, calories);
        manageMuscleForExercise(exercise, muscleName);
        this.updateExercise(exercise);

    }

    /**
     * Allows to update the exercise
     */
    private void updateExercise(Exercise ex) {
        try {
            ExerciseRepository exerciseRepository = new ExerciseRepository();
            exerciseRepository.update(ex);
            displayExerciseModified();
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
        } catch (AlreadyExistException e) {
            Utils.displayPopup(super.getBundleString("ExerciseAlreadyExist"));
        }
    }

    /**
     * Allows to manage the linking process of a muscle to an exercise if it is changed
     *
     * @param exercise   the exercise to be linked
     * @param muscleName name of the choosen muscle
     */
    private void manageMuscleForExercise(Exercise exercise, String muscleName) {
        try {
            if (!exercise.getMuscleName().equals(muscleName)) {
                this.removeLinkBetweenMuscleAndExercise(super.exercise.getId(), super.exercise.getMuscleId());
                super.exercise.setMuscle(getCorrectMuscle(muscleName));
                super.addExerciseMuscle(exercise.getId(), super.exercise.getMuscleId());
            }
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(super.getBundleString("ExerciseMuscleProblem"));
        }
    }

    /**
     * From the db remove the link between the exercise and the muscle
     *
     * @param exerciseId exercise id
     * @param muscleId muscle id
     */
    private void removeLinkBetweenMuscleAndExercise(int exerciseId, int muscleId) throws RepositoryException {
        ExerciseMuscleRepository exerciseMuscleRepository = new ExerciseMuscleRepository();
        exerciseMuscleRepository.removeLink(exerciseId, muscleId);

    }

    /**
     * Displays a dialog confirming the successful modification of an exercise.
     */
    private void displayExerciseModified() {
        Utils.displayPopup(super.getBundleString("ExerciseModified"));
    }


    /**
     * Allows to set the exercise with new data
     *
     * @param name        exercise name
     * @param type        exercise type
     * @param difficulty  exercise difficulty
     * @param description exercise description
     * @param imageData   image byte array
     * @param duration    exercise duration
     * @param calories    exercise calories
     */
    private void setExerciseToModify(String name, String type, int difficulty, String description,
                                     byte[] imageData, int duration, int calories) {
        this.exercise.setData(calories, description, difficulty, type, name, imageData, duration);
    }

    public interface ControllerListener {
        void showListExercises();
    }
}
