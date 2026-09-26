package ulb.database.repository;

import ulb.database.dao.ProgrammeDao;
import ulb.exceptions.AlreadyExistException;
import ulb.exceptions.RepositoryException;
import ulb.models.Programme;

import java.util.List;

/**
 * Repository pattern to manage the programme.
 */
public class ProgrammeRepository implements Repository<Programme> {

    private final ProgrammeDao programmeDao;

    /**
     * Getter of instance based on Singleton.
     *
     * @throws RepositoryException if the repository can't access the Dao instance..
     */
    public ProgrammeRepository() throws RepositoryException {
        this.programmeDao = ProgrammeDao.getInstance();
    }

    /**
     * This constructor is used by the tests.
     * @param dao
     */
    public ProgrammeRepository(ProgrammeDao dao){
        this.programmeDao = dao;
    }

    @Override
    public int add(Programme item) throws RepositoryException, IllegalArgumentException, AlreadyExistException {
        if (item == null) {
            throw new IllegalArgumentException("No programme has been given as parameter.");
        }
        item.isAllValid();
        return programmeDao.insert(item);
    }

    @Override
    public void remove(int key) throws RepositoryException {
        programmeDao.delete(key);
    }

    @Override
    public List<Programme> getAll() throws RepositoryException {
        return programmeDao.selectAll();
    }

    /**
     * Get all programme dto filtered by programme having the given type and userId
     *
     * @param userId user id
     * @return list of programme dot
     * @throws RepositoryException
     */
    public List<Programme> getAll(int userId) throws RepositoryException {
        return programmeDao.selectAll(userId);
    }

    /**
     * Get all programme dto filtered by programme having the given type and userId
     *
     * @param type   givent type
     * @param userId givent user id
     * @return list of exercise dto
     */
    public List<Programme> getAllByType(String type, int userId) throws RepositoryException, IllegalArgumentException {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("The type field is empty");
        }
        return programmeDao.selectAllByType(type, userId);
    }

    /**
     * Modify a given program inside the db
     *
     * @param programme program to modify
     * @throws RepositoryException
     */
    public void modifyProgramme(Programme programme) throws RepositoryException, AlreadyExistException {
        programmeDao.update(programme);
    }

    @Override
    public Programme get(int key) throws RepositoryException {
        return programmeDao.select(key);
    }

    @Override
    public boolean contains(int key) {
        throw new UnsupportedOperationException("The function is not supported");
    }
}
