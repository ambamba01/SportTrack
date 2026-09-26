package controllers;

import database.repository.ExerciseMuscleRepository;
import database.repository.MuscleRepository;
import exceptions.RepositoryException;
import models.Exercise;
import models.ExerciseMuscle;
import models.Muscle;
import models.User;
import utils.LogManager;
import utils.Utils;
import views.ExerciseAdditionFxController;
import views.FXMLController;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
/**
 * Abstract base class for managing exercise-related operations.
 * Extends the FXMLController to utilize its methods for FXML view management.
 */
public abstract class ExerciseManager extends FXMLController {

    protected User currentUser;
    protected Exercise exercise;
    protected ExerciseAdditionFxController exerciseManagerFxController; // Controller for the exercise addition view.
    protected List<Muscle> muscles;

    /**
     * Fetches all muscles from the model to populate the muscle list in the UI.
     *
     * @return A list of muscle names for UI selection.
     */
    protected List<String> getAllStrMuscles() {
        List<String> res = new ArrayList<>();
        try {
            this.muscles = this.getAllMuscles();
            for (Muscle m : this.muscles) {
                res.add(m.getName());
            }
        } catch (RepositoryException ex) {
            LogManager.getInstance().getLogger().log(Level.WARNING, ex.getMessage());
            Utils.displayPopup(super.getBundleString("MuscleProblem"));
        }
        return res;
    }

    /**
     * Get all the muscle from db
     *
     * @return a list of muscle
     * @throws RepositoryException
     */
    private List<Muscle> getAllMuscles() throws RepositoryException {
        MuscleRepository eRep = new MuscleRepository();
        return eRep.getAll();
    }

    /**
     * Retrieves the database ID for a muscle based on its name.
     *
     * @param muscle The name of the muscle.
     * @return The database ID of the muscle.
     */
    protected Muscle getCorrectMuscle(String muscle) {
        for (Muscle m : this.muscles) {
            if (m.getName().equals(muscle)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Add a link between an exercise and its working muscle
     *
     * @param exo_id    exercise id
     * @param muscle_id muscle id
     * @throws RepositoryException
     */
    protected void addExerciseMuscle(int exo_id, int muscle_id) throws RepositoryException {
        ExerciseMuscle exM = new ExerciseMuscle(exo_id, muscle_id);
        ExerciseMuscleRepository exerciseMuscleRepository = new ExerciseMuscleRepository();
        exerciseMuscleRepository.add(exM);
    }

    /**
     * Retrieves the muscle associated with a given exercise ID.
     * Queries the ExerciseMuscleRepository to get the ExerciseMuscle object,
     * then uses the MuscleRepository to fetch the corresponding Muscle.
     *
     * @param exerciseId The ID of the exercise for which the muscle is to be retrieved.
     * @return The Muscle object associated with the exercise ID.
     * @throws RepositoryException If there is an issue accessing the repositories.
     */
    protected Muscle getMuscleFromExerciseId(int exerciseId) throws RepositoryException {
        ExerciseMuscleRepository exerciseMuscleRepository = new ExerciseMuscleRepository();
        ExerciseMuscle exerciseMuscle = exerciseMuscleRepository.getMuscleForExercise(exerciseId);
        MuscleRepository muscleRepository = new MuscleRepository();
        return muscleRepository.get(exerciseMuscle.getMuscleId());
    }

}
