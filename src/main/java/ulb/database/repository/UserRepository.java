package database.repository;

import database.dao.UserDao;
import exceptions.AlreadyExistException;
import exceptions.RepositoryException;
import models.User;

import java.util.List;


/**
 * Repository pattern to manage the user.
 */
public class UserRepository implements Repository<User> {

    private final UserDao userDao;

    /**
     * Getter of instance based on Singleton.
     *
     * @throws RepositoryException if the repository can't access the Dao instance..
     */
    public UserRepository() throws RepositoryException {
        this.userDao = UserDao.getInstance();
    }

    /**
     * This constructor take as parameter a UserDao for the test.
     * @param userDao
     */
    public UserRepository(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public int add(User item) throws RepositoryException, IllegalArgumentException, AlreadyExistException {
        if (item == null) {
            throw new IllegalArgumentException("No User has been given as parameter.");
        }
        item.isAllValid();
        return userDao.insert(item);
    }

    @Override
    public void remove(int key) throws RepositoryException {
        userDao.delete(key);
    }

    @Override
    public List<User> getAll() throws RepositoryException {
        return userDao.selectAll();
    }

    @Override
    public User get(int key) throws RepositoryException {
        return userDao.select(key);
    }

    /**
     * Check if a given email address and hashPassword is present inside the db
     *
     * @param email          user's email address
     * @param hashedPassword user's hashed password
     * @return if found return the user.
     * @throws RepositoryException
     * @throws IllegalArgumentException
     */
    public User exist(String email, String hashedPassword) throws RepositoryException, IllegalArgumentException {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("The email field is empty");
        } else if (hashedPassword == null || hashedPassword.isBlank()) {
            throw new IllegalArgumentException("The email field is empty");
        }
        return userDao.select(email, hashedPassword);
    }

    @Override
    public boolean contains(int key) throws RepositoryException {
        return userDao.select(key) != null;
    }

    /**
     * Check if a given email address is already present inside the db
     *
     * @param email given email address
     * @return true if found
     * @throws RepositoryException
     * @throws IllegalArgumentException
     */
    public boolean contains(String email) throws RepositoryException, IllegalArgumentException {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("The email field is empty");
        }
        return userDao.contains(email);
    }

    /**
     * update the user inside the db
     *
     * @param user given user.
     * @throws RepositoryException
     */
    public void update(User user) throws RepositoryException, AlreadyExistException {
        userDao.update(user);
    }
    public void setSizePreferences(int userId, boolean preferences) throws RepositoryException {
        userDao.setSizePreferences(userId, preferences);
    }

    public void setWeightPreferences(int userId, boolean preferences) throws RepositoryException{
        userDao.setWeightPreferences(userId, preferences);
    }

    /**
     * update the user color mode inside the db
     *
     * @param user given user.
     * @throws RepositoryException
     */
    public void updateColorMode(User user) throws RepositoryException {
        userDao.updateColorMode(user);
    }

    /**
     * Update the user language mode inside the db
     * @param user
     * @throws RepositoryException
     * @throws IllegalArgumentException
     */
    public void updateLanguage(User user) throws RepositoryException, IllegalArgumentException {
        if (user.getLanguage() == null || user.getLanguage().isBlank()) {
            throw new IllegalArgumentException("The language field is empty");
        }
        userDao.updateLanguage(user);
    }
}
