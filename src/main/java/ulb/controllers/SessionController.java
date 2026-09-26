package controllers;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import database.repository.ExerciseRepository;
import exceptions.FXMLException;
import exceptions.RepositoryException;
import models.Programme;
import models.Session;
import models.User;
import utils.LogManager;
import utils.Utils;
import views.FxUtils;
import views.SessionFxController;

import java.util.logging.Level;

/**
 * This class permits to pass information between SessionFxController and the service layer, in this case,
 * ExerciseRepository. It filters the names of programmes, permits to them to be selected and to run the session with
 * the selected program.
 */
public class SessionController extends ProgramList implements SessionFxController.ViewListener {

    private final SessionController.ControllerListener listener;
    private SessionFxController sessionFxController;
    private final Stage stage;

    private ExerciseRepository erep;


    /**
     * Constructor of the Session controller
     *
     * @param stage    the primary stage
     * @param listener Controller listener
     */
    public SessionController(Stage stage, SessionController.ControllerListener listener) {
        this.listener = listener;
        this.stage = stage;
        try {
            this.erep = new ExerciseRepository();
        }catch (RepositoryException e){
            LogManager.getInstance().getLogger().log(Level.WARNING,e.getMessage());
            Utils.displayPopup(super.getBundleString("loginError"));
        }
    }

    /**
     * This Constructor is used to obtain the stage that will permit to display the screen, the repository of exercise
     * for to be able to connect with the db and the presenter that will act as a listener.
     * @param stage
     * @param rep
     * @param listener
     */
    public SessionController(Stage stage, SessionController.ControllerListener listener, ExerciseRepository rep) {
        this.listener = listener;
        this.stage = stage;
        this.erep = rep;
    }

    /**
     * This function shows the session view with names of programmes and connects SessionController and
     * SessionFxController.
     * @param user
     * @throws FXMLException
     */
    public void show(User user) throws FXMLException {
        super.currentUser = user;
        super.setBundle(this.currentUser.getLanguage());
        FXMLLoader loader = super.show("Session", this.stage );
        this.sessionFxController = loader.getController();
        this.sessionFxController.setListener(this);
        this.sessionFxController.fillListView(this.getProgrammesName(FxUtils.TypeExo.ALL.getValue()));
        this.sessionFxController.changeMode(this.currentUser.getColorMode());
    }

    /**
     * Allows to run a session
     *
     * @param selectedProgram id of the selected program
     */
    @Override
    public void runSession(int selectedProgram) {
        Programme program = checkForCorrectProgram(selectedProgram);
        if (program == null) {
            Utils.displayPopup(super.getBundleString("sessionNotcharged"));
            return;
        }
        Session session = new Session(program);
        getExercisesFromDB(session.getProgramme());
        if (session.listExerciseIsEmpty()) {
            Utils.displayPopup(super.getBundleString("sessionNoExercise"));
            return;
        }
        this.stage.hide();
        listener.showRunSession(session);
    }

    /**
     * Retrieve all the exercise of a programme
     */
    private void getExercisesFromDB(Programme program) {
        try{
            program.setExercises(erep.getAllExerciseFromProgram(program.getIdProgramme()));
        }catch(RepositoryException e){
            LogManager.getInstance().getLogger().log(Level.WARNING,e.getMessage());
            Utils.displayPopup(super.getBundleString("ExercisesError"));
        }
    }

    private Programme checkForCorrectProgram(int selectedProgramIndex) {
        return super.programmes.get(selectedProgramIndex);
    }

    @Override
    public void askFilteredProgrammes(String s) {
        this.sessionFxController.fillListView(super.getProgrammesName(s));
    }

    @Override
    public void showHome() {
        this.stage.hide();
        listener.showHome();
    }

    public interface ControllerListener {
        void showRunSession(Session session);

        void showHome();
    }
}