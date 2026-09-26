package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import exceptions.AlreadyExistException;
import exceptions.FXMLException;
import exceptions.RepositoryException;
import models.Programme;
import models.User;
import views.FxUtils;
import utils.LogManager;
import utils.Utils;
import views.ProgrammeAdditionFxController;

import java.util.logging.Level;


/**
 * Controller responsible for handling the programme addition UI and interactions within the application.
 * It connects the programme addition view with the underlying model, facilitating the addition of new programmes.
 */
public class ProgrammeAdditionController extends ProgrammeManager implements ProgrammeAdditionFxController.ViewListener {

    private final ControllerListener listener;

    private final Stage stage;


    /**
     * Initializes a new ProgrammeAdditionController with a given stage and listener.
     *
     * @param stage    The primary stage of the application to which the programme addition UI will be set.
     * @param listener The listener that facilitates communication between this controller and others.
     */
    public ProgrammeAdditionController(Stage stage, User currentUser, ControllerListener listener) {
        this.listener = listener;
        this.stage = stage;
        super.currentUser = currentUser;
        super.programme = new Programme(super.currentUser.getId());
        super.muscles.add(FxUtils.TypeExo.ALL.getValue());
        super.getAllMuscle();
        super.setBundle(this.currentUser.getLanguage());
    }

    /**
     * Initialize and show the fxml to the stage
     *
     * @throws FXMLException throws an exception if error
     */
    public void show() throws FXMLException {
        FXMLLoader loader = super.show("ProgrammeAddition", this.stage );
        super.programme = new Programme(this.currentUser.getId());
        super.programmeManagerFxController = loader.getController();
        super.programmeManagerFxController.setListener(this);
        super.changeExercisesList(FxUtils.TypeExo.ALL.getValue());
        this.programmeManagerFxController.changeMode(this.currentUser.getColorMode());
    }


    @Override
    public void displayIncorrectForm() {
        Utils.displayPopup(super.getBundleString("IncorrectForm"));
    }

    /**
     * Handles the request to add a new programme based on the provided details.
     * This method will be implemented to process the addition of a new programme with specified attributes.
     *
     * @param programmeName        The name of the new programme.
     * @param programmeDescription The description of the new programme.
     * @param exerciseDifficulty   The difficulty level of the exercises included in the new programme.
     * @param chosenExercises      A list of exercise names to be included in the new programme.
     */
    @Override
    public void askAddProgramme(String programmeName, String programmeDescription, int exerciseDifficulty, ObservableList<String> chosenExercises) {
        super.programme.setProgramme(programmeName, programmeDescription, exerciseDifficulty);
        addProgramme(chosenExercises);
    }

    /**
     * Fill the exercises combobox based on the chosen filter
     *
     * @param filterVal String of the chosen filter
     */
    @Override
    public void changeExercisesList(String filterVal) {
        super.changeExercisesList(filterVal);
    }

    /**
     * Fill the filter combobox based on the chosen parameter
     *
     * @param parameter Chosen parameter
     */
    @Override
    public void changeFilterList(String parameter){
        super.programmeManagerFxController.fillFilterList(parameter, muscles);
    }

    @Override
    public void goBack() {
        this.stage.hide();
        listener.showProgramme();
    }

    /**
     * Handle insert program into the Model and display if error
     *
     * @param chosenExercises List of string of chosen exercises by user
     */
    private void addProgramme(ObservableList<String> chosenExercises) {
        try {
            this.addProgrammeIntoDb(chosenExercises);
            programmeManagerFxController.resetAllField();
            Utils.displayPopup(super.getBundleString("ProgramAdded"));
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(super.getBundleString("programFormError"));
        } catch (AlreadyExistException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(super.getBundleString("ProgramAlreadyExist"));
        }
    }

    /**
     * Interface defining listener methods for the ProgrammeAdditionController.
     * This allows for communication between this controller and the main application or other controllers.
     */
    public interface ControllerListener {
        /**
         * Change stage to the main menu view
         */
        void showProgramme();
    }
}
