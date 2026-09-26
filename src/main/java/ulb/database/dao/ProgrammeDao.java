package ulb.database.dao;

import org.sqlite.SQLiteErrorCode;
import ulb.exceptions.AlreadyExistException;
import ulb.exceptions.RepositoryException;
import ulb.models.Programme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Programme data access object
 */
public class ProgrammeDao implements Dao<Programme> {

    private final Connection connexion;

    private ProgrammeDao() throws RepositoryException {
        this.connexion = DBManager.getInstance().getConnection();
    }

    public static ProgrammeDao getInstance() throws RepositoryException {
        return ProgrammeDao.ProgrammeDaoHolder.getInstance();
    }

    @Override
    public int insert(Programme item) throws RepositoryException, AlreadyExistException {
        int key;
        String request = "INSERT INTO PROGRAMME (name,description,difficulty,user_id)" +
                " VALUES (?,?,?,?)";
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setInt(3, item.getDifficulty());
            statement.setInt(4, item.getUserId());
            statement.executeUpdate();
            key = statement.getGeneratedKeys().getInt(1);
        } catch (SQLException e) {
            if (e.getErrorCode() == SQLiteErrorCode.SQLITE_CONSTRAINT.code) {
                throw new AlreadyExistException(e);
            }
            throw new RepositoryException(e);
        }

        return key;
    }

    @Override
    public void delete(int key) throws RepositoryException {
        String request = "DELETE FROM PROGRAMME WHERE id_programme = ?";
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setInt(1, key);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(Programme item) throws RepositoryException, AlreadyExistException {
        String request = "UPDATE PROGRAMME SET name=? ,description=? ,difficulty=? WHERE id_programme=?";
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setInt(3, item.getDifficulty());
            statement.setInt(4, item.getIdProgramme());
            statement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == SQLiteErrorCode.SQLITE_CONSTRAINT.code) {
                throw new AlreadyExistException(e);
            }
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Programme> selectAll() {
        throw new UnsupportedOperationException("The function is not supported");
    }

    /**
     * SElect all programmes with the specified user id
     *
     * @param userId to filter
     * @return Exercise list
     * @throws RepositoryException
     */
    public List<Programme> selectAll(int userId) throws RepositoryException {
        String request = "SELECT * FROM PROGRAMME WHERE PROGRAMME.user_id = ?";
        List<Programme> dtos = new ArrayList<>();
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Programme ex = new Programme(rs.getInt("id_programme"), rs.getString("name"),
                        rs.getInt("difficulty"), rs.getString("description"), userId);
                dtos.add(ex);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dtos;
    }

    /**
     * SElect all programmes with the specified type and userID
     *
     * @param type   string type to filter
     * @param userId id of the user
     * @return Exercise list
     * @throws RepositoryException
     */
    public List<Programme> selectAllByType(String type, int userId) throws RepositoryException {
        String request = "SELECT DISTINCT PROGRAMME.id_programme, PROGRAMME.name, PROGRAMME.difficulty, PROGRAMME.description FROM PROGRAMME " +
                "JOIN EXERCICE_PROGRAMME ON PROGRAMME.id_programme = EXERCICE_PROGRAMME.id_programme " +
                "JOIN EXERCICE ON EXERCICE_PROGRAMME.id_exercice = EXERCICE.id_exercice " +
                "WHERE EXERCICE.type = ? AND PROGRAMME.user_id = ?";
        List<Programme> dtos = new ArrayList<>();
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setString(1, type);
            statement.setInt(2, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Programme ex = new Programme(rs.getInt("id_programme"), rs.getString("name"),
                        rs.getInt("difficulty"), rs.getString("description"), userId);
                dtos.add(ex);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dtos;
    }

    @Override
    public Programme select(int key) throws RepositoryException {
        String request = "SELECT * FROM PROGRAMME WHERE PROGRAMME.id_programme = ?";
        Programme res = null;
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setInt(1, key);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                res = new Programme(rs.getInt("id_programme"), rs.getString("name"),
                        rs.getInt("difficulty"), rs.getString("description"), rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return res;
    }

    private static class ProgrammeDaoHolder {
        private static ProgrammeDao getInstance() throws RepositoryException {
            return new ProgrammeDao();
        }
    }


}
