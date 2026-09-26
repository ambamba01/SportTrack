package ulb.database.repository;

import ulb.database.dto.Dto;
import ulb.exceptions.AlreadyExistException;
import ulb.exceptions.RepositoryException;

import java.util.List;

/**
 * /**
 * Repository pattern to manage a resource of the application: a file, a
 * database, a web service.
 *
 * @param <T> an element.
 */
public interface Repository<T extends Dto> {
    /**
     * Add an element to the repository.If the element exists, the repository
     * updates this element.
     *
     * @param item the element to add.
     * @return the element's key, useful when the key is auto-generated.
     * @throws RepositoryException if the repository can't access to the element.
     */
    int add(T item) throws RepositoryException, IllegalArgumentException, AlreadyExistException;

    /**
     * Removes the element of the specific key.
     *
     * @param key key of the element to removes.
     * @throws RepositoryException if the repository can't access to the element.
     */
    void remove(int key) throws RepositoryException;

    /**
     * Returns all the elements of the repository.
     *
     * @return all the elements of the repository.
     * @throws RepositoryException if the repository can't access to the elements.
     */
    List<T> getAll() throws RepositoryException;

    /**
     * Return the element of the repository with the specific key.
     *
     * @param key key of the element.
     * @return the element of the repository with the specific key.
     * @throws RepositoryException if the repository can't access to the element.
     */
    T get(int key) throws RepositoryException;

    /**
     * Returns true if the element exist in the repository and false otherwise.
     * An element is found by this key.
     *
     * @param key key of the element.
     * @return true if the element exist in the repository and false otherwise.
     * @throws RepositoryException if the repository can't access to the element.
     */
    boolean contains(int key) throws RepositoryException;
}
