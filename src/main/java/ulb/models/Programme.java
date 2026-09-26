package models;

import javafx.collections.ObservableList;
import database.dto.Dto;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a program workout.
 */
public class Programme extends Dto {


    /**
     * name field
     */
    private String name;

    /**
     * difficulty field
     */
    private int difficulty;

    private String description;
    
    private final int userId;

    private final List<ExerciseProgramme> exerciseProgrammes;

    private List<Exercise> SelectedExercise;


    /**
     * default constructor
     */
    public Programme(int userId) {
        super(-1);
        this.userId = userId;
        this.exerciseProgrammes = new ArrayList<>();
        this.SelectedExercise = new ArrayList<>();
    }

    /**
     * constructor with parameters
     *
     * @param name       name of the programme
     * @param difficulty difficulty of the programme
     */
    public Programme(int idProgramme, String name, int difficulty, String description, int userId) {
        super(idProgramme);
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.difficulty = difficulty;
        this.exerciseProgrammes = new ArrayList<>();
        this.SelectedExercise = new ArrayList<>();
    }

    /**
     * constructor with parameters
     *
     * @param name       name of the programme
     * @param difficulty difficulty of the programme
     */
    public Programme(String name, int difficulty, String description, int userId) {
        super(-1);
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.difficulty = difficulty;
        this.exerciseProgrammes = new ArrayList<>();
        this.SelectedExercise = new ArrayList<>();
    }

    /**
     * getter for idProgramme field
     *
     * @return idProgramme field
     */
    public int getIdProgramme() {
        return super.key;
    }

    /**
     * setter for idProgramme field
     *
     * @param idProgramme new value for idProgramme field
     */
    public void setIdProgramme(int idProgramme) {
        super.key = idProgramme;
    }

    /**
     * getter for name field
     *
     * @return name field
     */
    public String getName() {
        return name;
    }

    /**
     * setter for name field
     *
     * @param name new value for name field
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the class with multiple values
     *
     * @param name        programme name
     * @param description programme description
     * @param difficulty  programe difficulty
     */
    public void setProgramme(String name, String description, int difficulty) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
    }

    /**
     * getter for difficulty field
     *
     * @return difficulty field
     */
    public int getDifficulty() {
        return difficulty;
    }

    public String getDescription() {
        return description;
    }

    public int getUserId() {
        return userId;
    }

    public void setExercises(List<Exercise> e) {
        this.SelectedExercise = e;
    }

    public List<Exercise> getExercises() {
        return this.SelectedExercise;
    }


    /**
     * Get all chosen exercises
     *
     * @param chosenExercises List of string of chosen exercises by the user
     */
    private List<Exercise> getAllChosenExercises(List<Exercise> exerciseList, ObservableList<String> chosenExercises) {
        List<Exercise> res = new ArrayList<>();
        List<String> temp = getAllExercisesName(exerciseList);
        for (String e : chosenExercises.stream().toList()) {
            res.add(exerciseList.get(temp.indexOf(e)));
        }
        return res;
    }

    /**
     * This function get the list of exercises of chosen program.
     * @param exerciseList
     * @param chosenExercises
     * @return
     */
    public List<ExerciseProgramme> getAllChosenExerciseProgrammes(List<Exercise> exerciseList, ObservableList<String> chosenExercises) {
        int pos = 0;
        for (Exercise ed : this.getAllChosenExercises(exerciseList, chosenExercises)) {
            pos++;
            this.exerciseProgrammes.add(new ExerciseProgramme(super.key, ed.getId(), pos));
        }
        return this.exerciseProgrammes;
    }

    /**
     * setter for difficulty field
     *
     * @param difficulty new value for difficulty field
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * convert the given exercise list into a list of exercise name.
     *
     * @param ex list of exercises
     * @return A list of exercise names to be displayed in the UI.
     */
    public List<String> getAllExercisesName(List<Exercise> ex) {
        List<String> res = new ArrayList<>();
        for (Exercise e : ex) {
            res.add(e.getName());
        }
        return res;
    }

    /**
     * Check if all string are valide (not empty or null)
     *
     * @throws IllegalArgumentException
     */
    public void isAllValid() throws IllegalArgumentException {
        this.isValid(this.name, "name");
        this.isValid(this.description, "description");
    }


}
