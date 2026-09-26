package views;

import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class allows to store multiple functions that have not attributed class. These functions will be used throughout
 * the project.
 */
public class FxUtils {


    /**
     * Enumeration of exercises types. This enumeration of exercises permits to project to be able to filter the
     * exercises between them.
     */
    public enum  TypeExo{
        ALL("All"),
        MUSCULATION("Musculation"),
        ENDURANCE("Endurance"),
        AGILITE("Agilité"),
        COORDINATION("Coordination");

        private final String value;

        public static final ArrayList<String> TYPE_EXO_ARRAY = new ArrayList<>(Arrays.asList("All", "Musculation", "Endurance", "Agilité", "Coordination", "Pause"));
        public static final ArrayList<String> DIFFICULTY_ARRAY = new ArrayList<>(Arrays.asList("All","1","2","3","4","5"));

        TypeExo(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * This function is a regular expression that checks if the user have puts a correct way/custom for the email
     * address.
     * @param mail
     * @return
     */
    public static boolean validateEmail(String mail) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return mail.matches(emailRegex);
    }

    /**
     * This function is a regular expression that checks if the user have puts a correct way/custom for the password.
     * @param pwd
     * @return
     */
    public static boolean validatePassword(String pwd) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[/\\-.@!?])[A-Za-z\\d/\\-.@!?]{7,}$";
        return pwd.matches(passwordRegex);
    }

    /**
     * This function checks if the string parameter is finally an integer.
     * @param valueStr
     * @return
     */
    public static boolean validateNumericField(String valueStr) {
        try{
            Integer.parseInt(valueStr);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * This function checks if the string is finally a float.
     * @param valueStr
     * @return
     */
    public static boolean validateFloatField(String valueStr) {
        try{
            Float.parseFloat(valueStr);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * This function checks if all fields of the registration form are completed.
     * @param name
     * @param firstName
     * @param email
     * @param password
     * @param size
     * @param weight
     * @param travelingKilometers
     * @param pumps
     * @param pullUps
     * @param abs
     * @return
     */
    public static boolean isAllUserRegistrationFieldsValid(String name, String firstName,String email, String password,
                                   String size, String weight, String travelingKilometers, String pumps, String pullUps,
                                   String abs){
        return !name.isBlank() && !firstName.isBlank() && validateEmail(email)&& validatePassword(password)
                && validateFloatField(size) && validateFloatField(weight) && validateNumericField(abs) && validateNumericField(pullUps)
                && validateFloatField(travelingKilometers) && validateNumericField(pumps);
    }

    /**
     * This function checks if all fields of the registration form are completed with the correct type.
     * @param name
     * @param firstName
     * @param email
     * @param password
     * @param size
     * @param weight
     * @param travelingKilometers
     * @param pumps
     * @param pullUps
     * @param abs
     * @return
     */
    public static boolean isAllUserRegistrationFieldsCompleted(String name, String firstName, String email, String password,
                                                               String size, String weight,String travelingKilometers,
                                                               String pumps, String pullUps, String abs){
        return !name.isBlank() && !firstName.isBlank() && !email.isBlank() &&
                !password.isBlank() && !size.isBlank() &&
                !weight.isBlank() && !travelingKilometers.isBlank() && !pumps.isBlank() &&
                !pullUps.isBlank() && !abs.isBlank();
    }

    /**
     * Applies a stylesheet to the parent AnchorPane based on the current mode.
     * Removes the light mode stylesheet and applies the dark mode stylesheet if darkMode is true,
     * and vice versa.
     *
     * @param darkMode Indicates whether the dark mode stylesheet should be applied.
     * @param parent   The AnchorPane to which the stylesheet will be applied.
     */
    public static void changeMode(boolean darkMode, AnchorPane parent) {
        if (darkMode){
            parent.getStylesheets().remove("/styles/lightMode.css");
            parent.getStylesheets().add("/styles/darkMode.css");
        } else {
            parent.getStylesheets().remove("/styles/darkMode.css");
            parent.getStylesheets().add("/styles/lightMode.css");
        }
    }

}