package models;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class TestExerciseProgramme extends TestCase {

    @Test
    public void testGetIdProgramme() {
        ExerciseProgramme instance = new ExerciseProgramme(1, 2, 3, 4);
        int expected_value = 2;
        int result = instance.getIdProgramme();
        assertEquals(expected_value, result);
    }

    @Test
    public void testGetIdExercise() {
        ExerciseProgramme instance = new ExerciseProgramme(1, 2, 3, 4);
        int expected_value = 3;
        int result = instance.getIdExercise();
        assertEquals(expected_value, result);
    }


    @Test
    public void testGetPosition() {
        ExerciseProgramme instance = new ExerciseProgramme(1, 2, 3, 4);
        int expected_value = 4;
        int result = instance.getPosition();
        assertEquals(expected_value, result);
    }
}
