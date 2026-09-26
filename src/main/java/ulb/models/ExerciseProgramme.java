package models;

import database.dto.Dto;

/**
 * This class represents the model of exerciseProgram object.
 */
public class ExerciseProgramme extends Dto {

    private final int idProgramme;
    private final int idExercise;
    private final int position;

    /**
     * Constructor for programmeDto.
     *
     * @param idProgrammeExercise Unique identifier of the association between the program and the exercise.
     * @param idProgramme         Identifier of the program to which this exercise belongs.
     * @param idExercise          Identifier of the exercise concerned.
     * @param position            Position of this exercise in the program execution order.
     */
    public ExerciseProgramme(int idProgrammeExercise, int idProgramme, int idExercise, int position) {
        super(idProgrammeExercise);
        this.idProgramme = idProgramme;
        this.idExercise = idExercise;
        this.position = position;
    }

    /**
     * Constructor to create an ExerciseProgramDto object without a unique association identifier.
     *
     * @param idProgramme Identifier of the program to which this exercise belongs.
     * @param idExercise  Identifier of the exercise concerned.
     * @param position    Position of this exercise in the program execution order.
     */
    public ExerciseProgramme(int idProgramme, int idExercise, int position) {
        super(-1);
        this.idProgramme = idProgramme;
        this.idExercise = idExercise;
        this.position = position;
    }

    public int getIdProgramme() {
        return idProgramme;
    }

    public int getIdExercise() {
        return idExercise;
    }

    public int getPosition() {
        return position;
    }
}
