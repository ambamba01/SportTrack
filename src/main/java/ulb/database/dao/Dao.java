package database.dao;

import database.dto.Dto;
import exceptions.AlreadyExistException;
import exceptions.RepositoryException;

import java.util.List;

/**
 * Data access object of a resource (file, database, web service).
 *
 * @param <T> item of the resource.
 */
public interface Dao<T extends Dto> {

    /**
     * Inserts an element into the resource.
     *
     * @param item item to insert.
     * @return the element's key, useful when the key is auto-generated.
     * @throws RepositoryException if the resource can't be accessed.
     */
    int insert(T item) throws RepositoryException, AlreadyExistException;

    /**
     * Deletes the item of the specific key from the resource.
     *
     * @param key key of the element to delete.
     * @throws RepositoryException if the resource can't be accessed.
     */
    void delete(int key) throws RepositoryException;

    /**
     * Update an element of the resource. The search of the element is based on
     * its key.
     *
     * @param item item to update.
     * @throws RepositoryException if the resource can't be accessed.
     */
    void update(T item) throws RepositoryException, AlreadyExistException;

    /**
     * Returns all the elements of the resource. This method can be long.
     *
     * @return all the elements of the resource.
     * @throws RepositoryException if the resource can't be accessed.
     */
    List<T> selectAll() throws RepositoryException;

    /**
     * Returns an element based on its key.
     *
     * @param key key of the element to select.
     * @return an element based on its key.
     * @throws RepositoryException if the resource can't be accessed.
     */
    T select(int key) throws RepositoryException;
}
