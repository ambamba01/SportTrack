package ulb.database.repository;

import ulb.database.dao.ExerciseDao;
import ulb.exceptions.AlreadyExistException;
import ulb.exceptions.RepositoryException;
import ulb.models.Exercise;

import java.util.List;

/**
 * Repository pattern to manage the exercise.
 */
public class ExerciseRepository implements Repository<Exercise> {
    private final ExerciseDao exerciseDao;

    /**
     * Getter of instance based on Singleton.
     *
     * @throws RepositoryException if the repository can't access the Dao instance..
     */
    public ExerciseRepository() throws RepositoryException {
        this.exerciseDao = ExerciseDao.getInstance();
    }

    /**
     * This constructor is used by the tests.
     * @param exerciseDao
     * @throws RepositoryException
     */
    public ExerciseRepository(ExerciseDao exerciseDao) throws RepositoryException{
        this.exerciseDao = exerciseDao;
    }

    @Override
    public int add(Exercise item) throws RepositoryException, IllegalArgumentException, AlreadyExistException {
        if (item == null) {
            throw new IllegalArgumentException("No exercise has been given as parameter.");
        }
        item.isAllValid();
        return exerciseDao.insert(item);
    }

    @Override
    public void remove(int key) throws RepositoryException {
        exerciseDao.delete(key);
    }

    @Override
    public List<Exercise> getAll() throws RepositoryException {
        return exerciseDao.selectAll();
    }

    public List<Exercise> getAllWithId(int userId) throws RepositoryException {
        return exerciseDao.getAllWithId(userId);
    }

    /**
     * Get all exercises dto without pause's
     *
     * @param userId given userID
     * @return list of exercises dto
     * @throws RepositoryException throws repository error if something is wrong
     */
    public List<Exercise> getAllWithIdWithoutThePauses(int userId) throws RepositoryException {
        return exerciseDao.getAllWithIdWithoutThePauses(userId);
    }

    /**
     * Get all exercise from a program
     *
     * @param programmeId program id
     * @return list of exercise
     * @throws RepositoryException
     */
    public List<Exercise> getAllExerciseFromProgram(int programmeId) throws RepositoryException {
        return exerciseDao.getAllExerciseFromProgram(programmeId);
    }

    /**
     * Get all exercises dto filtered by the given type
     *
     * @param type   givent type
     * @param userId given userID
     * @return list of exercises dto
     * @throws RepositoryException throws repository error if something is wrong
     */
    public List<Exercise> getAllByType(String type, int userId) throws RepositoryException, IllegalArgumentException {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("The type field is empty");
        }
        return exerciseDao.selectAllByType(type, userId);
    }

    /**
     * Get all exercises dto filtered by the given type without pause's
     *
     * @param type   givent type
     * @param userId given userID
     * @return list of exercises dto
     * @throws RepositoryException throws repository error if something is wrong
     */
    public List<Exercise> getAllByTypeWithoutThePauses(String type, int userId) throws RepositoryException, IllegalArgumentException {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("The type field is empty");
        }
        return exerciseDao.selectAllByTypeWithoutThePauses(type, userId);
    }


    public List<Exercise> getAllByDifficulty(String difficulty, int userId) throws RepositoryException, IllegalArgumentException {
        if (difficulty == null || difficulty.isBlank()) {
            throw new IllegalArgumentException("The difficulty field is empty");
        }
        return exerciseDao.selectAllByDifficulty(difficulty, userId);
    }

    public List<Exercise> getAllByMuscle(String muscle, int userId) throws RepositoryException, IllegalArgumentException {
        if (muscle == null || muscle.isBlank()) {
            throw new IllegalArgumentException("The difficulty field is empty");
        }
        return exerciseDao.selectAllByMuscle(muscle, userId);
    }

    @Override
    public Exercise get(int key) throws RepositoryException {
        return exerciseDao.select(key);
    }

    @Override
    public boolean contains(int key) throws RepositoryException {
        return exerciseDao.select(key) != null;
    }

    /**
     * Update the data exercise into the db
     * @param exercise
     * @throws RepositoryException
     * @throws AlreadyExistException
     */
    public void update(Exercise exercise) throws RepositoryException, AlreadyExistException {
        exerciseDao.update(exercise);
    }
}
