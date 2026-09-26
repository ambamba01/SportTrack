package ulb.controllers;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import ulb.database.repository.ExerciseRepository;
import ulb.exceptions.FXMLException;
import ulb.exceptions.RepositoryException;
import ulb.models.Exercise;
import ulb.models.User;
import ulb.views.FXMLController;
import ulb.views.FxUtils;
import ulb.utils.LogManager;
import ulb.utils.Utils;
import ulb.views.ExerciseListFxController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
/**
 * Controller class for the exercise list view.
 * Extends FXMLController and implements ViewListener from ExerciseListFxController.
 */
public class ExerciseListController extends FXMLController implements ExerciseListFxController.ViewListener {

    private final ExerciseListController.ControllerListener listener;
    private ExerciseListFxController exerciseListFxController;
    private final Stage stage;
    private User currentUser;
    private List<Exercise> listOfExercises;

    /**
     * Constructor for ExerciseListController.
     * Initializes the controller with the stage, current user, and a listener for controller events.
     *
     * @param stage    The stage on which the view will be shown.
     * @param currentUser The current user session.
     * @param listener The listener for controller communication.
     */
    public ExerciseListController(Stage stage, User currentUser, ControllerListener listener) {
        this.listener = listener;
        this.stage = stage;
        this.currentUser = currentUser;
        super.setBundle(this.currentUser.getLanguage());
    }

    /**
     * Displays the exercise list view.
     * Sets the current user, loads the "ListExercise" FXML file, and initializes the controller.
     * Filters the exercises to be displayed and applies the current color mode to the view.
     *
     * @throws FXMLException If there is an issue loading the FXML.
     */
    public void show() throws FXMLException {
        FXMLLoader loader = super.show("ListExercise", this.stage );
        this.exerciseListFxController = loader.getController();
        this.exerciseListFxController.setListener(this);
        getExerciseFiltered(FxUtils.TypeExo.ALL.getValue());
        this.fillAllExerciseName();
        this.exerciseListFxController.changeMode(this.currentUser.getColorMode());
    }

    @Override
    public void askFilteredExercise(String type) {
        this.getExerciseFiltered(type);
        this.fillAllExerciseName();
    }

    @Override
    public void showHome() {
        this.stage.hide();
        listener.showHome();

    }

    @Override
    public void goToAddExercise() {
        this.stage.hide();
        listener.showAddExercise();
    }

    @Override
    public void goToModifyExercise(int indexModifyListView) {
        this.stage.hide();
        listener.showModifyExercise(this.listOfExercises.get(indexModifyListView));
    }

    @Override
    public void deleteExercise(int idExercise) {
        try {
            this.deleteExerciseWithGivenID(listOfExercises.get(idExercise).getId());
            Utils.displayPopup(getBundleString("ExerciseRemove"));
            this.listOfExercises.remove(idExercise);
            this.fillAllExerciseName();
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(super.getBundleString("ExerciseNotRemove"));
        }

    }

    /**
     * Retrieves a list of exercise names filtered by the specified type.
     *
     * @param type The type of exercises to filter by. If "All", all exercises for the current user are retrieved.
     */
    private void getExerciseFiltered(String type) {
        try {
            this.listOfExercises = new ArrayList<>();
            listOfExercises = this.getAllObjectExerciseByTypeWithoutPauses(type);
        } catch (IllegalArgumentException e) {
            Utils.displayPopup(super.getBundleString("WrongArgument"));
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(super.getBundleString("impossibleGettingExercises"));
        }
    }

    /**
     * Retrieves a list of all exercises by type from the repository without the pauses .
     *
     * @param typeSelected String of the type filter
     * @return A list of exercise names to be displayed in the UI.
     */
    private List<Exercise> getAllObjectExerciseByTypeWithoutPauses(String typeSelected) throws RepositoryException {
        ExerciseRepository eRep = new ExerciseRepository();
        if (Objects.equals(typeSelected, FxUtils.TypeExo.ALL.getValue())) {
            return eRep.getAllWithIdWithoutThePauses(this.currentUser.getId());
        } else {
            return eRep.getAllByTypeWithoutThePauses(typeSelected, this.currentUser.getId());
        }
    }

    /**
     * Delete an exercise
     *
     * @param idExercise id of the exercise we want to delete
     */
    private void deleteExerciseWithGivenID(int idExercise) throws RepositoryException {
        ExerciseRepository exerciseRepository = new ExerciseRepository();
        exerciseRepository.remove(idExercise);
    }

    /**
     * Fill the list view with all the programme's name
     */
    private void fillAllExerciseName() {
        List<String> res = new ArrayList<>();
        for (Exercise ex : this.listOfExercises) {
            res.add(ex.getName());
        }
        this.exerciseListFxController.fillListView(res);
    }

    /**
     * Interface for communication with the main application controller. Implementing classes
     * should use this interface to communicate back to the main controller.
     */
    public interface ControllerListener {
        void showHome();

        /**
         * change stage with add programme view;
         */
        void showAddExercise();

        /**
         * Change the stage with modify programme view;
         * int idProgramme
         */
        void showModifyExercise(Exercise exercise);
        // Methods for communication with the main controller can be defined here.
    }
}
