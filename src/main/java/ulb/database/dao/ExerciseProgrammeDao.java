package database.dao;

import exceptions.RepositoryException;
import models.ExerciseProgramme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ExerciseProgrammeDao data access object
 */
public class ExerciseProgrammeDao implements Dao<ExerciseProgramme> {

    private final Connection connexion;

    private ExerciseProgrammeDao() throws RepositoryException {
        this.connexion = DBManager.getInstance().getConnection();
    }

    public static ExerciseProgrammeDao getInstance() throws RepositoryException {
        return ExerciseProgrammeDao.ExerciseProgrammeDaoHolder.getInstance();
    }

    @Override
    public int insert(ExerciseProgramme item) throws RepositoryException {
        int key;
        if (item == null) {
            throw new RepositoryException("No ExerciseProgramme has been given");
        }
        String request = "INSERT INTO EXERCICE_PROGRAMME (id_programme,id_exercice,position)" +
                " VALUES (?,?,?)";
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setInt(1, item.getIdProgramme());
            statement.setInt(2, item.getIdExercise());
            statement.setInt(3, item.getPosition());
            statement.executeUpdate();
            key = statement.getGeneratedKeys().getInt(1);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return key;
    }

    @Override
    public void delete(int key) {
        throw new UnsupportedOperationException("The function is not supported");
    }

    public void deleteAllByProgrammeId(int programmeId) throws RepositoryException {
        String request = "DELETE FROM EXERCICE_PROGRAMME WHERE id_programme = ?";
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setInt(1, programmeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(ExerciseProgramme item) {
        throw new UnsupportedOperationException("The function is not supported");
    }

    @Override
    public List<ExerciseProgramme> selectAll() {
        throw new UnsupportedOperationException("The function is not supported");
    }

    @Override
    public ExerciseProgramme select(int key) {
        throw new UnsupportedOperationException("The function is not supported");
    }

    private static class ExerciseProgrammeDaoHolder {
        private static ExerciseProgrammeDao getInstance() throws RepositoryException {
            return new ExerciseProgrammeDao();
        }
    }
}
