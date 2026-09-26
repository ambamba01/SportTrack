package models;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestSession extends TestCase {

    private Programme programme;
    private Exercise ex1, ex2;

    @Test
    public void testListExerciseIsEmpty() {
        programme = new Programme(1, "t", 1, "r", 1);
        Session instance = new Session(programme);
        ex1 = new Exercise(1, "pomp1", "pomp", "musculation", 3, "image", new byte[]{10, 10, 10}, 0, 0);
        ex2 = new Exercise(2, "pomp2", "pomp", "musculation", 3, "image", new byte[]{10, 10, 10}, 0, 0);
        assertTrue(instance.listExerciseIsEmpty());
        instance.getExerciseList().add(ex1);
        instance.getExerciseList().add(ex2);
        assertFalse(instance.listExerciseIsEmpty());
    }

    @Test
    public void testGetExerciseList() {
        ex1 = new Exercise(1, "pomp1", "pomp", "musculation", 3, "image", new byte[]{10, 10, 10}, 0, 0);
        ex2 = new Exercise(2, "pomp2", "pomp", "musculation", 3, "image", new byte[]{10, 10, 10}, 0, 0);
        programme = new Programme(1);
        Session instance = new Session(programme);
        List<Exercise> result = instance.getExerciseList();
        List<Exercise> testList = new ArrayList<>();
        result.add(ex1);
        result.add(ex2);
        testList.add(ex1);
        assertNotSame(result, testList);
        testList.add(ex2);
        assertEquals(result, testList);
    }

    @Test
    public void testGetProgramme() {
        programme = new Programme(1);
        Session instance = new Session(programme);
        Programme expectedProgram = new Programme(1);
        Programme result = instance.getProgramme();
        assertEquals(result, expectedProgram);

    }
}