package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import database.repository.ExerciseProgrammeRepository;
import database.repository.ExerciseRepository;
import database.repository.ProgrammeRepository;
import exceptions.AlreadyExistException;
import exceptions.FXMLException;
import exceptions.RepositoryException;
import models.ExerciseProgramme;
import models.Programme;
import models.User;
import views.FxUtils;
import utils.LogManager;
import utils.Utils;
import views.ProgrammeAdditionFxController;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


/**
 * Controller class for modifying a programme.
 * Extends ProgrammeManager and implements ViewListener from ProgrammeAdditionFxController.
 */
public class ProgrammeModificationController extends ProgrammeManager implements ProgrammeAdditionFxController.ViewListener {
    private final ControllerListener listener;
    private final Stage stage;


    /**
     * Constructor for ProgrammeModificationController.
     * Initializes the controller with the stage, current user, listener, and programme to be modified.
     *
     * @param stage       The stage on which the view will be shown.
     * @param currentUser The current user session.
     * @param listener    The listener for controller communication.
     * @param programme   The programme to be modified.
     */
    public ProgrammeModificationController(Stage stage, User currentUser, ControllerListener listener, Programme programme) {
        this.listener = listener;
        this.stage = stage;
        this.currentUser = currentUser;
        super.programme = programme;
        super.muscles.add(FxUtils.TypeExo.ALL.getValue());
        super.getAllMuscle();
        super.setBundle(this.currentUser.getLanguage());
    }

    /**
     * Shows the programme addition form with pre-filled data for modification.
     * Sets up the controller and initializes the view components.
     *
     * @throws FXMLException If there is an issue loading the FXML.
     */
    public void show() throws FXMLException {
        FXMLLoader loader = super.show("ProgrammeAddition", this.stage);
        super.programmeManagerFxController = loader.getController();
        super.programmeManagerFxController.setListener(this);
        super.changeExercisesList(FxUtils.TypeExo.ALL.getValue());
        this.programmeManagerFxController.changeMode(this.currentUser.getColorMode());
        super.programmeManagerFxController.renameLabel(super.bundle.getString("modifyProgramTitle"),
                super.bundle.getString("modifyProgramButton"));
        super.programmeManagerFxController.fillAllFields(super.programme.getDifficulty(), super.programme.getDescription()
                , super.programme.getName(), this.getExercisesFromDB());
    }

    @Override
    public void displayIncorrectForm() {
        Utils.displayPopup(super.getBundleString("IncorrectForm"));
    }

    @Override
    public void askAddProgramme(String programmeName, String programmeDescription, int exerciseDifficulty, ObservableList<String> chosenExercises) {
        super.programme.setProgramme(programmeName, programmeDescription, exerciseDifficulty);
        this.addProgramme(chosenExercises);
    }

    /**
     * Adds or updates the programme with the given details and chosen exercises.
     * Calls the method to modify the programme in the repository.
     *
     * @param chosenExercises The exercises chosen by the user to include in the programme.
     */
    private void addProgramme(ObservableList<String> chosenExercises) {
        try {
            this.modifyProgramme(chosenExercises);
            Utils.displayPopup(super.getBundleString("ProgramModified"));
            this.goBack();
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(super.getBundleString("programFormError"));
        } catch (AlreadyExistException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(super.getBundleString("ProgramAlreadyExist"));
        }

    }

    /**
     * Retrieve all the exercise of a programme
     * @return a list of exercise
     */
    private List<String> getExercisesFromDB() {
        List<String> res = new ArrayList<>();
        try {
            ExerciseRepository erep = new ExerciseRepository();
            super.programme.setExercises(
                    erep.getAllExerciseFromProgram(super.programme.getIdProgramme()));
            res = super.programme.getAllExercisesName(super.programme.getExercises());
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(super.getBundleString("impossibleGettingExercises"));
        }
        return res;
    }

    /**
     * Handle add program to repository
     *
     * @param chosenExercises List of string of exercises chosen by user
     */
    private void modifyProgramme(ObservableList<String> chosenExercises) throws RepositoryException, AlreadyExistException {
        ExerciseProgrammeRepository exerciseProgrammeRepository = new ExerciseProgrammeRepository();
        exerciseProgrammeRepository.removeAllByProgrammeId(super.programme.getIdProgramme());
        ProgrammeRepository programmeRepository = new ProgrammeRepository();
        programmeRepository.modifyProgramme(super.programme);
        for (ExerciseProgramme e : super.addAllExercisesProgramme(chosenExercises)) {
            exerciseProgrammeRepository.add(e);
        }
    }

    @Override
    public void goBack() {
        this.stage.hide();
        listener.showProgramme();
    }

    @Override
    public void changeFilterList(String parameter) {
        super.programmeManagerFxController.fillFilterList(parameter, muscles);
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
     * Interface for controller communication.
     * Defines methods for showing the programme list.
     */
    public interface ControllerListener {
        void showProgramme();
    }
}
