package ulb.models;

import ulb.database.dto.Dto;

/**
 * Model of an exercise
 */
public class Exercise extends Dto {


    private int userId;
    private String name;
    private String description;
    private String type;
    private int difficulty;
    private String imageName;
    private byte[] imageData;
    private int exerciseDuration;
    private int calories;

    private Muscle muscle;


    /**
     * Constructs a new Exercise with specified duration.
     *
     * @param exerciseId       exercise id
     * @param userId           The id of the user.
     * @param name             The name of the exercise.
     * @param description      A brief description of the exercise.
     * @param type             The type of exercise (e.g., cardio, strength).
     * @param difficulty       The difficulty level of the exercise.
     * @param imageName        The name of the image associated with the exercise.
     * @param imageData        The binary data of the image associated with the exercise.
     * @param exerciseDuration The duration of the exercise.
     * @param calories         the calories spent by the exercise
     */
    public Exercise(int exerciseId, int userId, String name, String description, String type, int difficulty, String imageName, byte[] imageData, int exerciseDuration, int calories) {
        super(exerciseId);
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.difficulty = difficulty;
        this.description = description;
        this.imageName = imageName;
        this.imageData = imageData;
        this.exerciseDuration = exerciseDuration;
        this.calories = calories;
    }

    /**
     * Constructs a new Exercise with specified duration.
     *
     * @param userId           The id of the user.
     * @param name             The name of the exercise.
     * @param description      A brief description of the exercise.
     * @param type             The type of exercise (e.g., cardio, strength).
     * @param difficulty       The difficulty level of the exercise.
     * @param imageName        The name of the image associated with the exercise.
     * @param imageData        The binary data of the image associated with the exercise.
     * @param exerciseDuration The duration of the exercise.
     * @param calories         the calories spent by the exercise
     */
    public Exercise(int userId, String name, String description, String type, int difficulty, String imageName, byte[] imageData, int exerciseDuration, int calories) {
        super(-1);
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.difficulty = difficulty;
        this.description = description;
        this.imageName = imageName;
        this.imageData = imageData;
        this.exerciseDuration = exerciseDuration;
        this.calories = calories;
    }

    /**
     * This constructor is used by the tests.ps
     */
    public Exercise() {
        super(-1);
    }


    /**
     * Returns the name of the exercise.
     *
     * @return The name of the exercise.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the type of the exercise.
     *
     * @return The type of the exercise.
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the difficulty level of the exercise.
     *
     * @return The difficulty level.
     */
    public int getDifficulty() {
        return difficulty;
    }

    public void setuserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return super.key;
    }

    /**
     * Returns the description of the exercise.
     *
     * @return The description of the exercise.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the name of the image associated with the exercise.
     *
     * @return The image name.
     */

    public int getUserId() {
        return userId;
    }

    public String getImageName() {
        return imageName;
    }

    public int getExerciseDuration() {
        return exerciseDuration;
    }

    public byte[] getImageData() {
        return imageData;
    }

    /**
     * Gives the calories spent by the exercise
     *
     * @return the quantity of calories spent
     */
    public int getCalories() {
        return calories;
    }

    public void setMuscle(Muscle muscle) {
        this.muscle = muscle;
    }

    public Muscle getMuscle() {
        return muscle;
    }

    public String getMuscleName() {
        return muscle.getName();
    }

    public int getMuscleId() {
        return muscle.getId();
    }

    /**
     * Sets the name of the exercise.
     *
     * @param name The new name of the exercise.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the description of the exercise.
     *
     * @param description The new description of the exercise.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the type of the exercise.
     *
     * @param type The new type of the exercise.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the difficulty level of the exercise.
     *
     * @param difficulty The new difficulty level.
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }



    /**
     * Set multiple datas
     * @param calories new calories
     * @param description new description
     * @param difficulty new difficulty
     * @param type new type
     * @param name new name
     * @param imageData new image data
     * @param duration new duration
     */
    public void setData(int calories, String description, int difficulty, String type, String name, byte[] imageData, int duration) {
        this.calories = calories;
        this.description = description;
        this.difficulty = difficulty;
        this.type = type;
        this.name = name;
        this.imageData = imageData;
        this.exerciseDuration = duration;
    }

    /**
     * Check if all string are valide (not empty or null)
     *
     * @throws IllegalArgumentException
     */
    public void isAllValid() throws IllegalArgumentException {
        this.isValid(this.name, "name");
        this.isValid(this.description, "description");
        this.isValid(this.imageName, "imageName");
    }


}
