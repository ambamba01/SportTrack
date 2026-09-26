package ulb.controllers;

import javafx.collections.ObservableList;
import ulb.database.repository.ExerciseProgrammeRepository;
import ulb.database.repository.ExerciseRepository;
import ulb.database.repository.MuscleRepository;
import ulb.database.repository.ProgrammeRepository;
import ulb.exceptions.AlreadyExistException;
import ulb.exceptions.RepositoryException;
import ulb.models.*;
import ulb.views.FXMLController;
import ulb.views.FxUtils;
import ulb.utils.LogManager;
import ulb.utils.Utils;
import ulb.views.ProgrammeAdditionFxController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
/**
 * Controller class for ProgrammeManager.
 * Extends FXMLController.
 */
public abstract class ProgrammeManager extends FXMLController {

    protected ProgrammeAdditionFxController programmeManagerFxController;
    protected User currentUser;
    protected Programme programme;
    protected List<String> muscles = new ArrayList<>();


    /**
     * Fill the exercises combobox based on the chosen filter
     *
     * @param filterVal String of the chosen filter
     * */
    protected void changeExercisesList(String filterVal){
        try{
            if (Objects.equals(filterVal,FxUtils.TypeExo.ALL.getValue())){
                this.programmeManagerFxController.fillExerciseList(this.getAllExercises());
            }else if (FxUtils.TypeExo.TYPE_EXO_ARRAY.contains(filterVal)){
                this.programmeManagerFxController.fillExerciseList(
                        this.getAllExercisesByType(filterVal));
            } else if (FxUtils.TypeExo.DIFFICULTY_ARRAY.contains(filterVal)){
                this.programmeManagerFxController.fillExerciseList(
                        this.getAllExercisesByDifficulty(filterVal));
            } else if (muscles.contains(filterVal)){
                this.programmeManagerFxController.fillExerciseList(
                        this.getAllExercisesByMuscle(filterVal));
            }
        } catch (RepositoryException ex) {
            LogManager.getInstance().getLogger().log(Level.WARNING, ex.getMessage());
            Utils.displayPopup(super.getBundleString("impossibleGettingExercises"));
        }
    }

    /**
     * Retrieves a list of all exercises from the repository.
     *
     * @return A list of exercise names to be displayed in the UI.
     */
    private List<String> getAllExercises() throws RepositoryException {
        List<String> ListNameExercises = new ArrayList<>();
        ExerciseRepository eRep = new ExerciseRepository();

        for (Exercise e : eRep.getAllWithId(this.currentUser.getId())) {
            ListNameExercises.add(e.getName());
        }
        return ListNameExercises;
    }

    /**
     * Retrieves a list of all exercises by type from the repository.
     *
     * @param typeSelected String of the type of exercises selected
     * @return A list of exercise names to be displayed in the UI.
     */
    private List<String> getAllExercisesByType(String typeSelected) {
        List<String> ListNameExercises = new ArrayList<>();
        try {
            ExerciseRepository eRep = new ExerciseRepository();
            for (Exercise e : eRep.getAllByType(typeSelected, this.currentUser.getId())) {
                ListNameExercises.add(e.getName());
            }
        } catch (RepositoryException ex) {
            LogManager.getInstance().getLogger().log(Level.WARNING, ex.getMessage());
            Utils.displayPopup(super.getBundleString("impossibleGettingExercises"));
        }
        return ListNameExercises;
    }

    /**
     * Retrieves a list of all exercises by type from the repository.
     *
     * @param difficultySelected String of the difficulty of exercises selected
     * @return A list of exercise names to be displayed in the UI.
     */
    private List<String> getAllExercisesByDifficulty(String difficultySelected) {
        List<String> ListNameExercises = new ArrayList<>();
        try {
            ExerciseRepository eRep = new ExerciseRepository();
            for (Exercise e : eRep.getAllByDifficulty(difficultySelected, this.currentUser.getId())) {
                ListNameExercises.add(e.getName());
            }
        } catch (RepositoryException ex) {
            LogManager.getInstance().getLogger().log(Level.WARNING, ex.getMessage());
            Utils.displayPopup(super.getBundleString("impossibleGettingExercises"));
        }
        return ListNameExercises;
    }

    /**
     * Retrieves a list of all exercises by type from the repository.
     *
     * @param muscleSelected String of the type of exercises selected
     * @return A list of exercise names to be displayed in the UI.
     */
    private List<String> getAllExercisesByMuscle(String muscleSelected) {
        List<String> ListNameExercises = new ArrayList<>();
        try {
            ExerciseRepository eRep = new ExerciseRepository();
            for (Exercise e : eRep.getAllByMuscle(muscleSelected, this.currentUser.getId())) {
                ListNameExercises.add(e.getName());
            }
        } catch (RepositoryException ex) {
            LogManager.getInstance().getLogger().log(Level.WARNING, ex.getMessage());
            Utils.displayPopup(super.getBundleString("impossibleGettingExercises"));
        }
        return ListNameExercises;
    }

    /**
     * Convert a observableList into a list of exerciseProgramme
     *
     * @param chosenExercises observable list of exercise name
     * @return a list of exerciseProgramme
     */
    protected List<ExerciseProgramme> addAllExercisesProgramme(ObservableList<String> chosenExercises) throws RepositoryException {
        ExerciseRepository exerciseRepository = new ExerciseRepository();
        List<Exercise> listExercise = exerciseRepository.getAll();
        return this.programme.getAllChosenExerciseProgrammes(listExercise, chosenExercises);
    }

    /**
     * Get all muscle for filtering
     */
    protected void getAllMuscle(){
        try {
            MuscleRepository mR = new MuscleRepository();
            this.muscles.addAll(Muscle.toString(mR.getAll()));
        } catch (RepositoryException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            Utils.displayPopup(super.getBundleString("MuscleProblem"));
        }
    }

    /**
     * Handle add program to repository
     *
     * @param chosenExercises List of string of exercises chosen by user
     */
    protected void addProgrammeIntoDb(ObservableList<String> chosenExercises) throws RepositoryException, AlreadyExistException {
        ProgrammeRepository programmeRepository = new ProgrammeRepository();
        ExerciseProgrammeRepository exerciseProgrammeRepository = new ExerciseProgrammeRepository();
        int newProgrammeId = programmeRepository.add(this.programme);
        this.programme.setIdProgramme(newProgrammeId);
        for (ExerciseProgramme e : this.addAllExercisesProgramme(chosenExercises)) {
            exerciseProgrammeRepository.add(e);
        }
    }

}
