// Package declaration indicating the location of this file within the project structure.
package controllers;

// Import statements importing necessary JavaFX, model, dto, repository, and view classes. yes

import java.util.logging.Level;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
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

/**
 * Controller class for handling the addition of exercises in the UI.
 * Implements the ExerciseAdditionFxController.ViewListener interface to respond to UI actions.
 */
public class ExerciseAdditionController extends ExerciseManager implements ExerciseAdditionFxController.ViewListener {

    private final ControllerListener listener; // Listener for communication with the main controller.

    private final Stage stage;

    /**
     * Constructor initializing the controller with a stage and a listener for communication.
     *
     * @param stage    The stage on which the view will be displayed.
     * @param listener Listener for communication with the main controller.
     */
    public ExerciseAdditionController(Stage stage, User currentUser, ControllerListener listener) {
        this.listener = listener;
        this.stage = stage;
        this.currentUser = currentUser;
        super.setBundle(this.currentUser.getLanguage());
    }

    /**
     * Show the add an exercise stage
     *
     * @param user current user
     * @throws FXMLException if loading the stage crash
     */
    public void show(User user) throws FXMLException {
        this.currentUser = user;
        FXMLLoader loader = super.show("ExerciseAddition", this.stage);
        super.exerciseManagerFxController = loader.getController();
        this.exerciseManagerFxController.setListener(this);
        this.exerciseManagerFxController.fillMuscleList(getAllStrMuscles());
        this.exerciseManagerFxController.changeMode(this.currentUser.getColorMode());

    }


    /**
     * Handles the request to add a new exercise to the database.
     *
     * @param name        The name of the exercise.
     * @param type        The type of the exercise.
     * @param difficulty  The difficulty level of the exercise.
     * @param description A description of the exercise.
     * @param imageName   The name of the image associated with the exercise.
     * @param imageData   The binary data of the image file.
     * @param strMuscle   The name of the muscle targeted by the exercise.
     */
    @Override
    public void askAddExercise(String name, String type, int difficulty, String description, String imageName,
                               byte[] imageData, String strMuscle, int duration, int calories) {
        super.exercise = new Exercise(currentUser.getId(), name, description, type, difficulty, imageName, imageData, duration, calories);
        super.exercise.setMuscle(super.getCorrectMuscle(strMuscle));
        try {
            if (super.exercise.getMuscle() == null) {
                Utils.displayPopup(super.getBundleString("MuscleNotRecognize"));
            } else {
                this.addExercise(exercise, super.exercise.getMuscleId());
                exerciseManagerFxController.resetAllField();
                DisplayExerciseAdded();
            }
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(super.getBundleString("addingExerciseProblem"));
        } catch (IllegalArgumentException e) {
            Utils.displayPopup(super.getBundleString("badArgument"));
        } catch (AlreadyExistException e) {
            Utils.displayPopup(super.getBundleString("ExerciseAlreadyExist"));
        }
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
     * Displays a dialog confirming the successful addition of an exercise.
     */
    private void DisplayExerciseAdded() {
        Utils.displayPopup(super.getBundleString("ExerciseInsert"));
    }

    /**
     * Add an exercise to the db
     *
     * @param ex exercise to add to the db
     * @param idMuscle muscle id
     * @throws RepositoryException
     */
    private void addExercise(Exercise ex, int idMuscle) throws RepositoryException, AlreadyExistException {
        ExerciseRepository exerciseRepository = new ExerciseRepository();
        ex.setuserId(this.currentUser.getId());
        int exo_id = exerciseRepository.add(ex);
        super.addExerciseMuscle(exo_id, idMuscle);
    }

    /**
     * Interface for communication with the main controller.
     */
    public interface ControllerListener {
        /**
         * Change the stage to the list of exercises view
         */
        void showListExercises();
        // Methods for communication should be defined here.
    }
}
//