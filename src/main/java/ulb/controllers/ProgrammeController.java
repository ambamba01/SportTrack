package ulb.controllers;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import ulb.database.repository.ProgrammeRepository;
import ulb.exceptions.FXMLException;
import ulb.exceptions.RepositoryException;
import ulb.models.Programme;
import ulb.models.User;
import ulb.utils.LogManager;
import ulb.utils.Utils;
import ulb.views.FxUtils;
import ulb.views.ProgrammeFxController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Controller for managing the programme view. This class is responsible for initializing the view with
 * the data specific to the current user and handling interactions between the view and the model.
 */
public class ProgrammeController extends ProgramList implements ProgrammeFxController.ViewListener {

    private final ControllerListener listener;
    private ProgrammeFxController programmeFxController;
    private Stage stage;
    private ProgrammeRepository rep;


    /**
     * Constructs a ProgrammeController.
     *
     * @param stage    The primary stage for the application, used to display the programme scene.
     * @param listener An instance of ControllerListener to facilitate communication between this controller
     *                 and the main application controller.
     */
    public ProgrammeController(Stage stage, User currentUser, ControllerListener listener) {
        this.listener = listener;
        this.stage = stage;
        super.currentUser = currentUser;
        super.setBundle(this.currentUser.getLanguage());
        try {
            this.rep = new ProgrammeRepository();
        }catch (RepositoryException e){
            LogManager.getInstance().getLogger().log(Level.WARNING,e.getMessage());
            Utils.displayPopup("Erreur en vérifiant la connexion.");
        }
    }

    /**
     * Initializes and displays the programme view for the specified user.
     *
     * @throws FXMLException If there is an error loading the view or initializing data.
     */

    public void show() throws FXMLException {
        FXMLLoader loader = super.show("Programme", this.stage );
        this.programmeFxController = loader.getController();
        this.programmeFxController.setListener(this);
        getProgrammeFiltered(FxUtils.TypeExo.ALL.getValue());
        this.fillAllProgrammesName();
        this.programmeFxController.changeMode(this.currentUser.getColorMode());
    }

    /**
     * Retrieves a list of programme names filtered by the specified type.
     *
     * @param type The type of programmes to filter by. If "All", all programmes for the current user are retrieved.
     */
    private void getProgrammeFiltered(String type) {
        try {
            this.programmes = this.getProgrammeByType(type, this.currentUser.getId());
        } catch (IllegalArgumentException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(super.getBundleString("WrongArgument"));
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(super.getBundleString("programGetError"));
        }
    }

    @Override
    public void askFilteredProgrammes(String s) {
        this.getProgrammeFiltered(s);
        this.fillAllProgrammesName();
    }

    @Override
    public void showHome() {
        this.stage.hide();
        listener.showHome();
    }

    @Override
    public void goToaddProgramme() {
        this.stage.hide();
        listener.showAddProgramme();
    }

    @Override
    public void goToModifyProgramme(int indexProgrammeListView) {
        this.stage.hide();
        listener.showModifyProgramme(this.programmes.get(indexProgrammeListView));
    }

    @Override
    public void deleteProgramme(int programmeListIndex) {
        Programme programme = this.programmes.get(programmeListIndex);
        try {
            this.delete(programme);
            Utils.displayPopup(super.getBundleString("programRemoved"));
            this.programmes.remove(programmeListIndex);
            this.fillAllProgrammesName();
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(super.getBundleString("programRemovedError"));
        }

    }

    /**
     * Delete a program through the repository
     *
     * @throws RepositoryException raised if there is an issue with the repository
     */
    private void delete(Programme p) throws RepositoryException{
        rep.remove(p.getIdProgramme());
    }

    /**
     * Request through the repository the list of the programs filtered by type
     *
     * @param type:   the type of exercises to filter the program
     * @param userId: the id of the current user
     * @return the list of program corresponding
     * @throws IllegalArgumentException raised if type or userId are not the right type
     * @throws RepositoryException      raised if there is an issue with the repository
     */
    private List<Programme> getProgrammeByType(String type, int userId) throws IllegalArgumentException, RepositoryException{
        if (Objects.equals(type, "All")){
            return rep.getAll(userId);
        } else {
            return rep.getAllByType(type, userId);
        }
    }

    /**
     * Fill the list view with all the programme's name
     */
    private void fillAllProgrammesName() {
        List<String> res = new ArrayList<>();
        for (Programme ex : this.programmes) {
            res.add(ex.getName());
        }
        this.programmeFxController.fillListView(res);
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
        void showAddProgramme();

        /**
         * Change the stage with modify programme view;
         * int idProgramme
         */
        void showModifyProgramme(Programme programme);
        // Methods for communication with the main controller can be defined here.
    }
}
