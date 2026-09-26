package ulb.models;

import ulb.database.dto.Dto;
import ulb.utils.LogManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.logging.Level;

/**
 * User model.
 */
public class User extends Dto {
    private String name;
    private String lastName;
    private String mailAddress;
    private String password;
    private int maxPushUps;
    private int maxPullups;
    private int maxAbs;
    private float maxKmRun;
    private float height;
    private float weight;
    private boolean darkMode;
    private String language = "fr";
    private boolean weightUnit;
    private boolean lengthUnit;

    /**
     * user Dto constructor
     * will hash the password before creating the object
     *
     * @param userId's    id
     * @param name        user's name
     * @param lastName    user's last name
     * @param mailAddress user's address mail
     * @param password user's password
     * @param maxPushUps user's maximum of pump
     * @param maxPullups user's maximum of push up
     * @param maxAbs user's maximum of ab
     * @param maxKmRun user's maximum of km
     * @param height user's height
     * @param weight user's weight
     */
    public User(int userId, String name, String lastName, String mailAddress, String password, int maxPushUps, int maxPullups,
                int maxAbs, float maxKmRun, float height, float weight, Boolean darkMode,String language, Boolean weightUnit,Boolean lengthUnit) {
        super(userId);
        this.name = name;
        this.lastName = lastName;
        this.mailAddress = mailAddress;
        this.password = password;
        this.maxPushUps = maxPushUps;
        this.maxPullups = maxPullups;
        this.maxAbs = maxAbs;
        this.maxKmRun = maxKmRun;
        this.height = height;
        this.weight = weight;
        this.darkMode = darkMode;
        this.language = language;
        this.weightUnit = weightUnit;
        this.lengthUnit = lengthUnit;
    }

    /**
     * User Dto constructor
     * will hash the password before creating the object
     *
     * @param name        user's name
     * @param lastName    user's last name
     * @param mailAddress user's address mail
     * @param password user's password
     * @param maxPushUps user's maximum of pump
     * @param maxPullups user's maximum of push up
     * @param maxAbs user's maximum of ab
     * @param maxKmRun user's maximum of km
     * @param height user's height
     * @param weight user's weight
     */
    public User(String name, String lastName, String mailAddress, String password, int maxPushUps, int maxPullups,
                int maxAbs, float maxKmRun, int height, float weight, Boolean darkMode, String language,Boolean weightUnit,Boolean lengthUnit) {
        super(-1);
        this.name = name;
        this.lastName = lastName;
        this.mailAddress = mailAddress;
        this.password = password;
        this.maxPushUps = maxPushUps;
        this.maxPullups = maxPullups;
        this.maxAbs = maxAbs;
        this.maxKmRun = maxKmRun;
        this.height = height;
        this.weight = weight;
        this.darkMode = darkMode;
        this.language = language;
        this.weightUnit = weightUnit;
        this.lengthUnit = lengthUnit;
    }

    /**
     * This constructor is used for the tests.
     */
    public User(){
        super(-1);
    }

    /**
     * This constructor is used for load data providing by another instance User.
     * @param user
     */
    public User(User user) {
        super(user.getId());
        this.name = user.name;
        this.lastName = user.lastName;
        this.mailAddress = user.mailAddress;
        this.password = user.password;
        this.maxPushUps = user.maxPushUps;
        this.maxPullups = user.maxPullups;
        this.maxAbs = user.maxAbs;
        this.maxKmRun = user.maxKmRun;
        this.height = user.height;
        this.weight = user.weight;
        this.darkMode = user.darkMode;
        this.language = user.language;
        this.weightUnit = user.weightUnit;
        this.lengthUnit = user.lengthUnit;
    }

    public String getLanguage(){return language;}

    public void setLanguage(String newLanguage) {
        this.language = newLanguage;
    }

    public boolean getColorMode(){return darkMode;}

    public void setColorMode(boolean newColorMode) {
        this.darkMode = newColorMode;
    }

    public int getId() {
        return super.key;
    }

    /**
     * Returns the first name of the user.
     *
     * @return The first name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the last name of the user.
     *
     * @return The last name of the user.
     */

    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the email of the user.
     *
     * @return The email of the user.
     */

    public String getMailAddress() {
        return mailAddress;
    }

    /**
     * Returns the password of the user.
     *
     * @return The password of the user.
     */

    public String getPassword() {
        return password;
    }

    /**
     * Returns the maximum number of push-ups that the user can drive.
     *
     * @return The maximum number of push-ups that the user can drive.
     */

    public int getMaxPushUps() {
        return maxPushUps;
    }

    /**
     * Returns the maximum number of pull-ups that the user's tires can withstand.
     *
     * @return The maximum number of pull-ups that the user's tires can withstand.
     */

    public int getMaxPullups() {
        return maxPullups;
    }

    /**
     * Returns the maximum number of abs that the user can carry.
     *
     * @return The maximum number of abs that the user can carry.
     */

    public int getMaxAbs() {
        return maxAbs;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public float getMaxKmRun() {
        return maxKmRun;
    }

    /**
     * Returns the maximum number of km that the user can drive.
     *
     * @return The maximum number of km that the user can drive.
     */
    public float getMaxKmRunWithConversion() {
        if(this.lengthUnit){
            return maxKmRun;
        }else{
            return (float)(maxKmRun * 1.609);
        }

    }

    /**
     * Returns the height of the user.
     *
     * @return The height of the user.
     */
    public float getHeightWithConversion() {
        if(this.lengthUnit){
            return height;
        }else{
            return (float)(height/30.48);
        }
    }

    /**
     * returns the weight of the user.
     *
     * @return the weight of the user.
     */
    public float getWeightWithConversion() {
        if(this.weightUnit){
            return weight;
        }else{
            return (float)(weight*2.205);
        }

    }

    /**
     * Returns the preferences of weight unit
     * true if the unit chosen is grams
     * false if the unit chosen are pounds
     * @return True or false based on user preferences
     */
    public boolean isKiloWeightUnit(){
        return weightUnit;
    }

    /**
     * Returns the preferences of length unit
     * true if the unit chosen are meters
     * false if the unit chosen are foots
     * @return True or false based on user preferences
     */
    public boolean isMetreLengthUnit(){
        return lengthUnit;
    }
    /**
     * Sets the unique identifier of the user.
     *
     * @param userId The unique identifier of the user.
     */
    public void setId(int userId) {
        super.key = userId;
    }

    /**
     * Sets the first name of the user.
     *
     * @param name the first name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the email of the user.
     *
     * @param mailAddress the email of the user.
     */
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password of the user.
     **/
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the maximum number of push-ups that the user can drive.
     * @param maxPushUps the maximum number of push-ups that the user can drive.
     */

    public void setMaxPushUps(int maxPushUps) {
        this.maxPushUps = maxPushUps;
    }

    /**
     * Sets the maximum number of pull-ups that the user's tires can withstand.
     * @param maxPullups the maximum number of pull-ups that the user's tires can withstand.
     */

    public void setMaxPullups(int maxPullups) {
        this.maxPullups = maxPullups;
    }

    /**
     * Sets the maximum number of abs that the user can carry.
     * @param maxAbs the maximum number of abs that the user can carry.
     */

    public void setMaxAbs(int maxAbs) {
        this.maxAbs = maxAbs;
    }

    /**
     * Sets the maximum number of km that the user can drive.
     * @param maxKmRun the maximum number of km that the user can drive.
     */

    public void setMaxKmRun(float maxKmRun) {
        this.maxKmRun = maxKmRun;
    }

    /**
     * Sets the height of the user.
     * @param height the height of the user.
     */

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Sets the weight of the user.
     * @param weight the weight of the user.
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setLengthUnit(boolean lengthUnit) {
        this.lengthUnit = lengthUnit;
    }

    public void setWeightUnit(boolean weightUnit) {
        this.weightUnit = weightUnit;
    }

    /**
     * This function checks if the name, lastname, password and mail address are not empty by intermediary of isValid
     * function Dto.
     * @throws IllegalArgumentException
     */
    public void isAllValid() throws IllegalArgumentException {
        this.isValid(this.name, "name");
        this.isValid(this.lastName, "lastname");
        this.isValid(this.password, "password");
        this.isValid(this.mailAddress, "mailaddress");
    }

    /**
     * Check the password.
     * @param password
     * @return
     */
    public boolean checkPassword(String password) {
        return Objects.equals(this.password, password);
    }

    /**
     * Set all the user data
     * @param newLastName new last name
     * @param newName new first name
     * @param newMailAddress new mail address
     * @param newHeight new height
     * @param newWeight new weight
     * @param newMaxKmRun new max KM run
     * @param newMaxPushUps new max pushup
     * @param newMaxPullUps new max pull up
     * @param newMaxAbs new max abs
     * @param hashedPassword hashed password
     */
    public void updateUser(String newLastName, String newName, String newMailAddress, float newHeight,
                                     float newWeight, float newMaxKmRun, int newMaxPushUps, int newMaxPullUps,
                                     int newMaxAbs, String hashedPassword){
        this.lastName = newLastName;
        this.name = newName;
        this.mailAddress = newMailAddress;
        this.password = hashedPassword;
        if(!isKiloWeightUnit()){
            this.weight = (float)(newWeight/2.205);
        }else{
            this.weight =newWeight;
        }
        if(!isMetreLengthUnit()){
            this.height = (int)(newHeight*30.48);
            this.maxKmRun = (float)(newMaxKmRun/1.609);
        }else{
            this.height = newHeight;
            this.maxKmRun = newMaxKmRun;
        }
        this.maxPushUps = newMaxPushUps;
        this.maxPullups = newMaxPullUps;
        this.maxAbs = newMaxAbs;
    }

    /**
     * Calculate the calories depending on the user's formula
     *
     * @return the calories rounded up
     */
    public int getCaloriesPersonalChoice(float weight, String weightFactor, float height, String heightFactor) {
        float convertedWeight = this.getWeightWithConversion();
        float convertedHeight = this.getHeightWithConversion();
        if (weightFactor.matches("x"))
            if (heightFactor.matches("%"))
                return Math.round((convertedWeight * weight) + (convertedHeight / height));
            else
                return Math.round((convertedWeight * weight) + (convertedHeight * height));
        else if (heightFactor.matches("%"))
            return Math.round((convertedWeight / weight) + (convertedHeight / height));
        else
            return Math.round((convertedWeight / weight) + (convertedHeight * height));
    }


    /**
     * Hash the given password before storing into db
     *
     * @param password given password
     * @return hashed password
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            LogManager.getInstance().getLogger().log(Level.WARNING, e.getMessage());
            return null;
        }
    }
}
