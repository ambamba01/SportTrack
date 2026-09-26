package ulb.models;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class TestExercice extends TestCase {

    @Test
    public void testGetName() {
        byte[] byte_array_instance = new byte[]{10, 10, 10};
        Exercise instance = new Exercise(1, "pomp", "pomp", "musculation", 3, "image", byte_array_instance, 0, 0);
        String expected_value = "pomp";
        String result = instance.getName();
        assertEquals(expected_value, result);
    }

    @Test
    public void testGetDescription() {
        byte[] byte_array_instance = new byte[]{10, 10, 10};
        Exercise instance = new Exercise(1, "pomp", "pomp", "musculation", 3, "image", byte_array_instance, 0, 0);
        String expected_value = "pomp";
        String result = instance.getDescription();
        assertEquals(expected_value, result);
    }

    @Test
    public void testGetImage_name() {
        byte[] byte_array_instance = new byte[]{10, 10, 10};
        Exercise instance = new Exercise(1, "pomp", "pomp", "musculation", 3, "image", byte_array_instance, 0, 0);
        String expected_value = "image";
        String result = instance.getImageName();
        assertEquals(expected_value, result);
    }

    @Test
    public void testGetImage_data() {
        byte[] byte_array_instance = new byte[]{10, 10, 10};
        Exercise instance = new Exercise(1, "pomp", "pomp", "musculation", 3, "image", byte_array_instance, 0, 0);
        byte[] expected_value = new byte[]{10, 10, 10};
        byte[] result = instance.getImageData();
        for (int i = 0; i < byte_array_instance.length; i++) {
            assertEquals(expected_value[i], result[i]);
        }
    }

    @Test
    public void testGetCalories() {
        byte[] byte_array_instance = new byte[]{10, 10, 10};
        Exercise instance = new Exercise(1, "pomp", "pomp", "musculation", 3, "image", byte_array_instance, 10, 20);
        int expectedValue = 20;
        int result = instance.getCalories();
        assertEquals(expectedValue, result);
    }


}