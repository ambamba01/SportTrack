package database.repository;

import database.dao.ExerciseMuscleDao;
import exceptions.RepositoryException;
import models.ExerciseMuscle;

import java.util.List;

/**
 * Repository pattern to manage the ExerciseMuscle.
 */
public class ExerciseMuscleRepository implements Repository<ExerciseMuscle> {
    private final ExerciseMuscleDao exerciseMuscleDao;

    /**
     * Getter of instance based on Singleton.
     *
     * @throws RepositoryException if the repository can't access the Dao instance..
     */
    public ExerciseMuscleRepository() throws RepositoryException {
        this.exerciseMuscleDao = ExerciseMuscleDao.getInstance();
    }

    /**
     * This constructor is used by the tests.
     * @param dao dao of ExerciseMuscle
     */
    public ExerciseMuscleRepository(ExerciseMuscleDao dao) {
        this.exerciseMuscleDao = dao;
    }

    @Override
    public int add(ExerciseMuscle item) throws RepositoryException {
        return exerciseMuscleDao.insert(item);
    }

    @Override
    public void remove(int key) throws RepositoryException {
        exerciseMuscleDao.delete(key);
    }

    @Override
    public List<ExerciseMuscle> getAll() throws RepositoryException {
        return exerciseMuscleDao.selectAll();
    }

    @Override
    public ExerciseMuscle get(int key) throws RepositoryException {
        return exerciseMuscleDao.select(key);
    }

    @Override
    public boolean contains(int key) throws RepositoryException {
        return exerciseMuscleDao.select(key) != null;
    }

    /**
     * get the muscle linked to a exercise
     * @param exerciseId the exercise id
     * @return an ExerciseMuscle object
     * @throws RepositoryException
     */
    public ExerciseMuscle getMuscleForExercise(int exerciseId) throws RepositoryException {
        return exerciseMuscleDao.selectMuscleWithExId(exerciseId);
    }

    public void removeLink(int exerciseId, int muscleId) throws RepositoryException {
        exerciseMuscleDao.removeLink(exerciseId, muscleId);
    }
}
