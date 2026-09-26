package models;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class TestMuscle extends TestCase {

    @Test
    public void testTestGetName() {
        Muscle instance = new Muscle("biceps");
        String expected_value = "biceps";
        String result = instance.getName();
        assertEquals(expected_value, result);
    }

    @Test
    public void testGetId() {
        Muscle instance = new Muscle(1, "Biceps");
        int expected_value = 1;
        int result = instance.getId();
        assertEquals(expected_value, result);
    }
}
