package ulb.models;

import ulb.database.dto.Dto;

/**
 * Represents the association between an exercise and a muscle.
 * Extends Dto to include common data transfer object properties.
 */
public class ExerciseMuscle extends Dto {

    private final int exerciseId;
    private final int muscleId;

    /**
     * Constructs an ExerciseMuscle object with specified exercise and muscle IDs.
     *
     * @param exerciseId The ID of the exercise.
     * @param muscleId   The ID of the muscle associated with the exercise.
     */
    public ExerciseMuscle(int exerciseId, int muscleId) {
        super(-1);
        this.exerciseId = exerciseId;
        this.muscleId = muscleId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public int getMuscleId() {
        return muscleId;
    }

}
