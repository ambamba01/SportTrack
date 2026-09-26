package models;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class TestExerciceMuscle extends TestCase {

    @Test
    public void testGetExerciceId() {
        ExerciseMuscle instance = new ExerciseMuscle(1, 2);
        int expected_value = 1;
        int result = instance.getExerciseId();
        assertEquals(expected_value, result);
    }

    @Test
    public void testGetMuscleId() {
        ExerciseMuscle instance = new ExerciseMuscle(1, 2);
        int expected_value = 2;
        int result = instance.getMuscleId();
        assertEquals(expected_value, result);
    }
}
