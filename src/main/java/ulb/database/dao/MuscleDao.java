package ulb.database.dao;

import org.sqlite.SQLiteErrorCode;
import ulb.exceptions.AlreadyExistException;
import ulb.exceptions.RepositoryException;
import ulb.models.Muscle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Muscle data access object
 */
public class MuscleDao implements Dao<Muscle> {

    public static MuscleDao getInstance() throws RepositoryException {
        return MuscleDaoHolder.getInstance();
    }

    private final Connection connexion;

    private MuscleDao() throws RepositoryException {
        this.connexion = DBManager.getInstance().getConnection();
    }

    /**
     * Insert new muscle into the DB
     *
     * @param item muscle to insert.
     * @return the id of the created muscle and -1 if an error occurred
     * @throws RepositoryException if the dao can't insert the element into the db.
     */
    @Override
    public int insert(Muscle item) throws RepositoryException, AlreadyExistException {
        int key;
        String request = "INSERT INTO MUSCLE (name)" + "VALUES (?)";
        try (PreparedStatement pstmt = this.connexion.prepareStatement(request)) {
            pstmt.setString(1, item.getName());
            pstmt.executeUpdate();
            key = pstmt.getGeneratedKeys().getInt(1);
        } catch (SQLException e) {
            if (e.getErrorCode() == SQLiteErrorCode.SQLITE_CONSTRAINT.code) {
                throw new AlreadyExistException(e);
            }
            throw new RepositoryException(e);
        }
        return key;
    }

    /**
     * Delete a muscle from the DB
     *
     * @param key the id of the muscle to delete.
     * @throws RepositoryException if the dao can't delete the element from the db.
     */
    @Override
    public void delete(int key) throws RepositoryException {
        String request = "DELETE FROM MUSCLE WHERE id_muscle = ?";
        try (PreparedStatement pstmt = this.connexion.prepareStatement(request)) {
            pstmt.setInt(1, key);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    /**
     * Update a muscle name in the DB
     *
     * @param item the Muscle with the new info.
     * @throws RepositoryException if the dao can't update the element in the db.
     */
    @Override
    public void update(Muscle item) throws RepositoryException, AlreadyExistException {
        String request = "UPDATE MUSCLE SET name = ? WHERE id_muscle = ?";
        try (PreparedStatement pstmt = this.connexion.prepareStatement(request)) {
            pstmt.setString(1, item.getName());
            pstmt.setInt(2, item.getId());
        } catch (SQLException e) {
            if (e.getErrorCode() == SQLiteErrorCode.SQLITE_CONSTRAINT.code) {
                throw new AlreadyExistException(e);
            }
            throw new RepositoryException(e);
        }
    }

    /**
     * Select all muscles in the DB
     *
     * @return the list of MuscleDTO.
     * @throws RepositoryException if the dao can't update the element in the db.
     */
    @Override
    public List<Muscle> selectAll() throws RepositoryException {
        List<Muscle> muscles = new ArrayList<>();
        String request = "SELECT * FROM MUSCLE";
        try (PreparedStatement pstmt = this.connexion.prepareStatement(request)) {
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    Muscle muscle = new Muscle(resultSet.getInt("id_muscle"), resultSet.getString("name"));
                    muscles.add(muscle);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return muscles;
    }

    /**
     * Select one muscles in the DB
     *
     * @param key the id of the muscle to select.
     * @return the MuscleDTO selected.
     * @throws RepositoryException if the dao can't update the element in the db.
     */
    @Override
    public Muscle select(int key) throws RepositoryException {
        Muscle muscle = null;
        String request = "SELECT * FROM MUSCLE WHERE id_muscle = ?";
        try (PreparedStatement pstmt = this.connexion.prepareStatement(request)) {
            pstmt.setInt(1, key);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    muscle = new Muscle(resultSet.getInt("id_muscle"), resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return muscle;
    }

    private static class MuscleDaoHolder {
        private static MuscleDao getInstance() throws RepositoryException {
            return new MuscleDao();
        }
    }
}
