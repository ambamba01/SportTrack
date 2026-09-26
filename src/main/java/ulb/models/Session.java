package ulb.models;

import java.util.List;

/**
 * This class represents the model of Session object. The data like the program are stored in this class.
 */
public class Session {


    private final Programme programme;

    private int pointerPosition = 0;

    private Exercise currentExercise;

    /**
     * This is the constructor of session. Its parameter will permit to obtain the list of exercises used for
     * the session.
     * @param programme
     */
    public Session(Programme programme) {
        this.programme = programme;
    }

    /**
     * Start a session of exercises
     */
    public void startSession(){
        this.currentExercise = this.programme.getExercises().getFirst();
        this.pointerPosition = 0;
    }

    /**
     * Come back to the previous exercise in the session.
     */
    public void getPrevious() {
        if (pointerPosition > 0) {
            pointerPosition--;
        }
    }

    /**
     * This function allows to reference the new exercise to become the current exercise.
     */
    public void updateCurrentExercise() {
        this.currentExercise = this.programme.getExercises().get(pointerPosition);
    }

    /**
     * Checks if we are in the last exercise of the session
     * @return
     */
    public boolean isLastExercise() {
        return ((pointerPosition != 0 && currentExercise == null) ||
                pointerPosition >= this.programme.getExercises().size() - 1 || pointerPosition < 0);
    }


    public void getNext() {
        pointerPosition++;
    }

    public String getCurrentExerciseTitle() {
        return currentExercise.getName() + " (" + (pointerPosition + 1) + "/" + this.programme.getExercises().size() + ")";
    }

    public String getCurrentExerciseDescription() {
        return this.currentExercise.getDescription();
    }

    public int getCurrentExerciseDuration() {
        return this.currentExercise.getExerciseDuration();
    }

    public byte[] getCurrentExerciseImage() {
        return this.currentExercise.getImageData();
    }

    /**
     * Checks tf the list of exercises providing by the program is empty
     * @return
     */
    public boolean listExerciseIsEmpty() {
        return this.programme.getExercises().isEmpty();
    }

    public List<Exercise> getExerciseList() {
        return this.programme.getExercises();
    }

    public Programme getProgramme() {
        return programme;
    }


}

