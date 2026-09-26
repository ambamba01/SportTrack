package database.repository;

import database.dao.MuscleDao;
import exceptions.AlreadyExistException;
import exceptions.RepositoryException;
import models.Muscle;

import java.util.List;

/**
 * Repository pattern to manage the muscle.
 */
public class MuscleRepository implements Repository<Muscle> {

    private final MuscleDao muscleDao;

    /**
     * Getter of instance based on Singleton.
     *
     * @throws RepositoryException if the repository can't access the Dao instance.
     */
    public MuscleRepository() throws RepositoryException {
        this.muscleDao = MuscleDao.getInstance();
    }

    /**
     * This constructor is used by the tests.
     * @param dao
     */
    public MuscleRepository(MuscleDao dao) {
        this.muscleDao = dao;
    }

    @Override
    public int add(Muscle item) throws RepositoryException, IllegalArgumentException, AlreadyExistException {
        if (item == null) {
            throw new IllegalArgumentException("No muscle has been given as parameter.");
        }
        return muscleDao.insert(item);
    }

    @Override
    public void remove(int key) throws RepositoryException {
        muscleDao.delete(key);
    }

    @Override
    public List<Muscle> getAll() throws RepositoryException {
        return muscleDao.selectAll();
    }

    @Override
    public Muscle get(int key) throws RepositoryException {
        return muscleDao.select(key);
    }

    @Override
    public boolean contains(int key) throws RepositoryException {
        return muscleDao.select(key) != null;
    }

}
