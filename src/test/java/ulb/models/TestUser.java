package ulb.models;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class TestUser extends TestCase {

    @Test
    public void testGetAndSetId() {
        User instance = new User(1 , "John", "Doe", "john.doe@example.com", "password", 10, 10, 10, 5.0f, 175, 70.0f,true,"fr",true,true);
        int expected_value = 1;
        int result = instance.getId();
        assertEquals(expected_value, result);

        int new_id = 2;
        instance.setId(new_id);
        assertEquals(new_id, instance.getId());
    }

    @Test
    public void testGetAndSetName() {
        User instance = new User(1, "John", "Doe", "john.doe@example.com", "password", 10, 10, 10, 5.0f, 175, 70.0f,true,"fr",true,true);
        String expected_value = "John";
        String result = instance.getName();
        assertEquals(expected_value, result);

        String new_name = "Jane";
        instance.setName(new_name);
        assertEquals(new_name, instance.getName());
    }

    @Test
    public void testGetAndSetLastName() {
        User instance = new User(1, "John", "Doe", "john.doe@example.com", "password", 10, 10, 10, 5.0f, 175, 70.0f,true,"fr",true,true);
        String expected_value = "Doe";
        String result = instance.getLastName();
        assertEquals(expected_value, result);

        String new_lastName = "Smith";
        instance.setLastName(new_lastName);
        assertEquals(new_lastName, instance.getLastName());
    }

    @Test
    public void testGetAndSetMailAddress() {
        User instance = new User(1, "John", "Doe", "john.doe@example.com", "password", 10, 10, 10, 5.0f, 175, 70.0f,true,"fr",true,true);
        String expected_value = "john.doe@example.com";
        String result = instance.getMailAddress();
        assertEquals(expected_value, result);

        String new_mailAddress = "jane.doe@example.com";
        instance.setMailAddress(new_mailAddress);
        assertEquals(new_mailAddress, instance.getMailAddress());
    }

    @Test
    public void testGetAndSetPassword() {
        User instance = new User(1, "John", "Doe", "john.doe@example.com", "password", 10, 10, 10, 5.0f, 175, 70.0f,true,"fr",true,true);
        String expected_value = "password";
        String result = instance.getPassword();
        assertEquals(expected_value, result);

        String new_password = "newpassword";
        instance.setPassword(new_password);
        assertEquals(new_password, instance.getPassword());
    }

    @Test
    public void testGetAndSetMaxPompe() {
        User instance = new User(1, "John", "Doe", "john.doe@example.com", "password", 10, 10, 10, 5.0f, 175, 70.0f,true,"fr",true,true);
        int expected_value = 10;
        int result = instance.getMaxPushUps();
        assertEquals(expected_value, result);

        int new_maxPompe = 15;
        instance.setMaxPushUps(new_maxPompe);
        assertEquals(new_maxPompe, instance.getMaxPushUps());
    }

    @Test
    public void testGetAndSetMaxTraction() {
        User instance = new User(1, "John", "Doe", "john.doe@example.com", "password", 10, 10, 10, 5.0f, 175, 70.0f,true,"fr",true,true);
        int expected_value = 10;
        int result = instance.getMaxPullups();
        assertEquals(expected_value, result);

        int new_maxTraction = 15;
        instance.setMaxPullups(new_maxTraction);
        assertEquals(new_maxTraction, instance.getMaxPullups());
    }

    @Test
    public void testGetAndSetMaxAbdo() {
        User instance = new User(1, "John", "Doe", "john.doe@example.com", "password", 10, 10, 10, 5.0f, 175, 70.0f,true,"fr",true,true);
        int expected_value = 10;
        int result = instance.getMaxAbs();
        assertEquals(expected_value, result);

        int new_maxAbdo = 15;
        instance.setMaxAbs(new_maxAbdo);
        assertEquals(new_maxAbdo, instance.getMaxAbs());
    }

    @Test
    public void testGetAndSetMaxKmCourseWithMeters() {
        User instance = new User(1, "John", "Doe", "john.doe@example.com", "password", 10, 10, 10, 5.0f, 175, 70.0f,true,"fr",true,true);
        float expected_value = 5.0f;
        float result = instance.getMaxKmRun();
        assertEquals(expected_value, result, 0.01);

        float new_maxKmCourse = 10.0f;
        instance.setMaxKmRun(new_maxKmCourse);
        assertEquals(new_maxKmCourse, instance.getMaxKmRun(), 0.01);
    }

   @Test
   public void testGetMaxKmCourseWithMiles(){
        User instance = new User(1, "John", "Doe", "john.doe@example.com", "password", 10, 10, 10, 10, 170, 80,true,"fr",true,false);
        float expectedValue = (float)(10*1.609);
        float result = instance.getMaxKmRunWithConversion();
        assertEquals(expectedValue,result);
    }

    @Test
    public void testGetAndSetTailleWithMeters() {
        User instance = new User(1, "John", "Doe", "john.doe@example.com", "password", 10, 10, 10, 5.0f, 175, 70.0f,true,"fr",true,true);
        int expected_value = 175;
        float result = instance.getHeight();
        assertEquals(expected_value, result, 0.01);

        int new_taille = 180;
        instance.setHeight(new_taille);
        assertEquals(new_taille, instance.getHeight(), 0.01);
    }

    @Test
    public void testGetHeightWithFeet(){
        User instance = new User(1, "John", "Doe", "john.doe@example.com", "password", 10, 10, 10, 10, 170, 80,true,"fr",false,false);
        float expectedValue = (float)(170/30.48);
        float result = instance.getHeightWithConversion();
        assertEquals(expectedValue,result);

        expectedValue=170;
        assertNotSame(expectedValue,result);
    }

    @Test
    public void testGetAndSetPoidsWithKilos() {
        User instance = new User(1, "John", "Doe", "john.doe@example.com", "password", 10, 10, 10, 5.0f, 175, 70.0f,true,"fr",true,true);
        float expected_value = 70.0f;
        float result = instance.getWeight();
        assertEquals(expected_value, result, 0.01);

        float new_poids = 75.0f;
        instance.setWeight(new_poids);
        assertEquals(new_poids, instance.getWeight(), 0.01);
    }
    @Test
    public void testGetWeightWithPounds(){
        User instance = new User(1, "John", "Doe", "john.doe@example.com", "password", 10, 10, 10, 10, 170, 80,true,"fr",false,false);
        float expectedValue = (float)(80*2.205);
        float result = instance.getWeightWithConversion();
        assertEquals(expectedValue,result);

        expectedValue = 10;
        assertNotSame(expectedValue,result);
    }

    @Test
    public void testIsAllValid() {
        User instance = new User(1, "John", "Doe", "john.doe@example.com", "password", 10, 10, 10, 5.0f, 175, 70.0f,true,"fr",true,true);
        try {
            instance.isAllValid();
        } catch (IllegalArgumentException e) {
            fail();
        }

        instance.setName(null);
        try {
            instance.isAllValid();
            fail();
        } catch (IllegalArgumentException e) {
            // Expected exception.
        }
    }
}
