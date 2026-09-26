package ulb.database.repository;

import ulb.database.dao.ExerciseProgrammeDao;
import ulb.exceptions.RepositoryException;
import ulb.models.ExerciseProgramme;

import java.util.List;

/**
 * Repository pattern to manage the exerciseProgramme.
 */
public class ExerciseProgrammeRepository implements Repository<ExerciseProgramme> {

    private final ExerciseProgrammeDao exerciseProgrammeDao;

    /**
     * Getter of instance based on Singleton.
     *
     * @throws RepositoryException if the repository can't access the Dao instance..
     */
    public ExerciseProgrammeRepository() throws RepositoryException {
        this.exerciseProgrammeDao = ExerciseProgrammeDao.getInstance();
    }

    /**
     * This constructor is used by the tests.
     * @param dao
     */
    public ExerciseProgrammeRepository(ExerciseProgrammeDao dao) {
        this.exerciseProgrammeDao = dao;
    }

    @Override
    public int add(ExerciseProgramme item) throws RepositoryException {
        return exerciseProgrammeDao.insert(item);
    }

    @Override
    public void remove(int key) {
        throw new UnsupportedOperationException("The function is not supported");
    }

    /**
     * Remove all exercises that have the id program as key.
     * @param programmeId
     * @throws RepositoryException
     */
    public void removeAllByProgrammeId(int programmeId) throws RepositoryException {
        exerciseProgrammeDao.deleteAllByProgrammeId(programmeId);
    }

    @Override
    public List<ExerciseProgramme> getAll() throws RepositoryException {
        return exerciseProgrammeDao.selectAll();
    }

    @Override
    public ExerciseProgramme get(int key) throws RepositoryException {
        return exerciseProgrammeDao.select(key);
    }

    @Override
    public boolean contains(int key) throws RepositoryException {
        return exerciseProgrammeDao.select(key) != null;
    }
}
