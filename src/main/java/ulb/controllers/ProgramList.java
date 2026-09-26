package ulb.controllers;

import ulb.database.repository.ProgrammeRepository;
import ulb.exceptions.RepositoryException;
import ulb.models.Programme;
import ulb.models.User;
import ulb.utils.LogManager;
import ulb.utils.Utils;
import ulb.views.FXMLController;
import ulb.views.FxUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

/**
 * This class contains two methods that will go retrieve the names of programmes of user in the db thanks to
 * programmeRepository.
 */
public abstract class ProgramList extends FXMLController {
    protected List<Programme> programmes;

    protected User currentUser;

    /**
     * This function retrieves the name of programmes that have been saved, previously, by the user thanks to identifier
     * and the type of programme.
     * @param s filter
     * @return
     */
    protected List<String> getProgrammesName(String s) {
        List<String> res = new ArrayList<>();
        try {
            this.programmes = this.getProgrammeByType(s, this.currentUser.getId());
            for (Programme p : this.programmes) {
                res.add(p.getName());
            }
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(e.getMessage());
        }
        return res;
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
    private List<Programme> getProgrammeByType(String type, int userId) throws IllegalArgumentException, RepositoryException {
        ProgrammeRepository rep = new ProgrammeRepository();
        if (Objects.equals(type, FxUtils.TypeExo.ALL.getValue())) {
            return rep.getAll(userId);
        } else {
            return rep.getAllByType(type, userId);
        }
    }

}
