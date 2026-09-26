package ulb.models;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class TestModelProgramme extends TestCase {

    @Test
    public void testGetAndSetName() {
        Programme instance = new Programme(1, "TestProgramme", 3, "TestDescription", 1);
        String expected_value = "TestProgramme";
        String result = instance.getName();
        assertEquals(expected_value, result);

        String new_name = "NewTestProgramme";
        instance.setName(new_name);
        assertEquals(new_name, instance.getName());
    }

    @Test
    public void testGetAndSetDifficulty() {
        Programme instance = new Programme(1, "TestProgramme", 3, "TestDescription", 1);
        int expected_value = 3;
        int result = instance.getDifficulty();
        assertEquals(expected_value, result);

        int new_difficulty = 5;
        instance.setDifficulty(new_difficulty);
        assertEquals(new_difficulty, instance.getDifficulty());
    }

    @Test
    public void testIsAllValid() {
        Programme instance = new Programme(1, "TestProgramme", 3, "TestDescription", 1);
        try {
            instance.isAllValid();
        } catch (IllegalArgumentException e) {
            fail("isAllValid method threw an exception when it shouldn't have.");
        }
        instance.setName(null);
        try {
            instance.isAllValid();
            fail("isAllValid method didn't throw an exception when it should have.");
        } catch (IllegalArgumentException e) {
            assertEquals(1, 1);
        }
    }
}
