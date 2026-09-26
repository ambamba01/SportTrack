package controllers;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import exceptions.FXMLException;
import models.Exercise;
import models.Programme;
import models.Session;
import models.User;
import utils.LogManager;

import java.util.Objects;
import java.util.logging.Level;


/**
 * Presenter class managing all the controller and the switch between them
 */

public class Presenter implements LoginController.ControllerListener, ProgrammeModificationController.ControllerListener, ProgrammeController.ControllerListener, ExerciseAdditionController.ControllerListener, ProgrammeAdditionController.ControllerListener, MainMenuController.ControllerListener, RegistrationController.ControllerListener, SessionController.ControllerListener, RunSessionController.ControllerListener, MyProfileController.ControllerListener, ExerciseListController.ControllerListener, ExerciseModificationController.ControllerListener, SettingsController.ControllerListener {


    private final Stage stage;
    private User currentUser;

    /**
     * simple constructor of the controller
     *
     * @param stage primary stage
     */
    public Presenter(Stage stage) {
        this.stage = stage;
        currentUser = null;
    }

    /**
     * Show the stage error
     */
    private void showStageError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur | Error");
        alert.setHeaderText("Erreur d'affichage | Diplay error");
        alert.setContentText("Erreur lors de l'affichage de la page souhaité. Veuillez réessayer. \n" +
                "Error displaying the desired page. Please try again.");
        alert.showAndWait();
    }

    /**
     * Initialize and call the show function of the programme controller
     */
    @Override
    public void showHome() {
        if (currentUser == null) {
            showLogin();
        } else {
            showMainMenu();
        }
    }


    /**
     * Initialize and call the show function of the mainMenu controller
     *
     * @throws Exception trigged when error
     */
    public void showMainMenu(){
        MainMenuController mainMenuController = new MainMenuController(stage, this.currentUser, this);
        try {
            mainMenuController.show();
        } catch (FXMLException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            showStageError();
        }
    }

    /**
     * Initialize and call the show function of the programme controller
     */
    @Override
    public void showProgramme() {
        ProgrammeController programmeController = new ProgrammeController(stage, this.currentUser, this);
        try{
            programmeController.show();
        } catch (FXMLException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            showStageError();
        }
    }


    @Override
    public void showModifyProgramme(Programme programme) {
        ProgrammeModificationController programmeModificationController = new ProgrammeModificationController(stage, this.currentUser, this, programme);
        try{
            programmeModificationController.show();
        } catch (FXMLException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            showStageError();
        }
    }

    /**
     * Initializes and displays the exercise addition view for the current user.
     * This method sets up the exercise addition controller and loads its view.
     */
    @Override
    public void showAddExercise(){
        ExerciseAdditionController exerciseAdditionController = new ExerciseAdditionController(stage, this.currentUser, this);
        try{
            exerciseAdditionController.show(currentUser);
        } catch (FXMLException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            showStageError();
        }
    }

    /**
     * Show the registration view
     */
    public void showRegister(){
        RegistrationController registrationController = new RegistrationController(stage, this);
        try{
            registrationController.show();
        } catch (FXMLException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            showStageError();
        }
    }

    @Override
    public void showSession(){
        SessionController sessionController = new SessionController(stage, this);
        try{
            sessionController.show(currentUser);
        } catch (FXMLException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            showStageError();
        }
    }

    @Override
    public void showRunSession(Session session) {
        RunSessionController runSessionController = new RunSessionController(stage, this, session);
        try{
            runSessionController.show(currentUser);
        } catch (FXMLException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            showStageError();
        }
    }

    @Override
    public void showModifyExercise(Exercise exercise){
        ExerciseModificationController exerciseModificationController = new ExerciseModificationController(stage, this.currentUser, this, exercise);
        try{
            exerciseModificationController.show();
        }catch (FXMLException e){
            LogManager.getInstance().getLogger().log(Level.WARNING,e.getMessage());
            showStageError();
        }
    }

    /**
     * Displays the list of exercises in a new stage.
     * Initializes the ExerciseListController with the current stage, user, and this instance as the listener.
     * Attempts to show the exercise list and handles any FXML loading exceptions.
     */
    public void showListExercises(){
        ExerciseListController exerciseListController = new ExerciseListController(stage, this.currentUser, this);
        try{
            exerciseListController.show();
        } catch (FXMLException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            showStageError();
        }
    }


    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public void showLogin() {
        LoginController loginController = new LoginController(stage, this);
        try {
            loginController.show();
        } catch (FXMLException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            showStageError();
        }
    }

    @Override
    public void showAddProgramme(){
        ProgrammeAdditionController programmeAdditionController = new ProgrammeAdditionController(stage, this.currentUser, this);
        try{
            programmeAdditionController.show();
        } catch (FXMLException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            showStageError();
        }
    }

    @Override
    public void goToMainMenu() {
        showHome();
    }


    @Override
    public void showMyProfile(){
        MyProfileController myProfileController = new MyProfileController(stage, this.currentUser, this);
        try {
            myProfileController.show();
        }
        catch (FXMLException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING,e.getMessage());
            showStageError();
        }
    }

    @Override
    public void showSettings(){
        SettingsController settingsController = new SettingsController(stage, this.currentUser,this);
        try {
            settingsController.show();
        }
        catch (FXMLException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING,e.getMessage());
            showStageError();
        }
    }

}
