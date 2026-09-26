package database.dao;

import exceptions.RepositoryException;
import models.ExerciseMuscle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * ExerciseMuscle data access object
 */
public class ExerciseMuscleDao implements Dao<ExerciseMuscle> {

    private final Connection connexion;

    public static ExerciseMuscleDao getInstance() throws RepositoryException {
        return ExerciseMuscleDao.ExerciseMuscleDaoHolder.getInstance();
    }

    private ExerciseMuscleDao() throws RepositoryException {
        this.connexion = DBManager.getInstance().getConnection();
    }

    /**
     * Insert's the link between a muscle and an exercise
     *
     * @param item ExerciseMuscle object containing the exercise and muscle to link
     * @return key, 1 means that the insertion was successful, otherwise 0
     */
    @Override
    public int insert(ExerciseMuscle item) throws RepositoryException {
        int key;
        if (item == null) {
            throw new RepositoryException("No ExerciseDTO has been given");
        }
        String request = "INSERT INTO EXERCICE_MUSCLE (id_muscle,id_exercice)" +
                " VALUES (?,?)";
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setInt(1, item.getMuscleId());
            statement.setInt(2, item.getExerciseId());
            statement.executeUpdate();
            key = statement.getUpdateCount();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return key;
    }

    @Override
    public void delete(int key) {
        throw new UnsupportedOperationException("The function is not supported");
    }

    @Override
    public void update(ExerciseMuscle item) {
        throw new UnsupportedOperationException("The function is not supported");
    }

    @Override
    public List<ExerciseMuscle> selectAll() {
        throw new UnsupportedOperationException("The function is not supported");
    }

    @Override
    public ExerciseMuscle select(int key) {
        throw new UnsupportedOperationException("The function is not supported");
    }

    /**
     * Selects the muscle associated with a specific exercise ID from the EXERCICE_MUSCLE table.
     *
     * @param exerciseId The ID of the exercise for which the muscle association is to be retrieved.
     * @return An ExerciseMuscle object containing the IDs of the exercise and associated muscle, or null if not found.
     * @throws RepositoryException If there is an issue with the database access.
     */
    public ExerciseMuscle selectMuscleWithExId(int exerciseId) throws RepositoryException {
        String request = "SELECT * FROM EXERCICE_MUSCLE WHERE id_exercice=?";
        ExerciseMuscle exerciseMuscle = null;
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setInt(1, exerciseId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                exerciseMuscle = new ExerciseMuscle(rs.getInt("id_exercice"),
                        rs.getInt("id_muscle"));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return exerciseMuscle;
    }
    /**
     * Removes the link between an exercise and a muscle from the EXERCICE_MUSCLE table.
     *
     * @param exerciseId The ID of the exercise for which the muscle association is to be removed.
     * @param muscleId   The ID of the muscle to be disassociated from the exercise.
     */
    public void removeLink(int exerciseId, int muscleId) throws RepositoryException {
        String request = "DELETE FROM EXERCICE_MUSCLE WHERE id_exercice=? AND id_muscle=?";
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setInt(1, exerciseId);
            statement.setInt(2, muscleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private static class ExerciseMuscleDaoHolder {
        private static ExerciseMuscleDao getInstance() throws RepositoryException {
            return new ExerciseMuscleDao();
        }
    }
}
