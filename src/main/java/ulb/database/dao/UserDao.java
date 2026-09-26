package ulb.database.dao;

import org.sqlite.SQLiteErrorCode;
import ulb.exceptions.AlreadyExistException;
import ulb.exceptions.RepositoryException;
import ulb.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * User data access object
 */
public class UserDao implements Dao<User> {

    public static UserDao getInstance() throws RepositoryException {
        return UserDaoHolder.getInstance();
    }

    private final Connection connexion;
    private String request;

    private UserDao() throws RepositoryException {
        this.connexion = DBManager.getInstance().getConnection();
    }

    /**
     * Insert new user into the DB
     *
     * @param item user to insert.
     * @return the id of the created user and -1 if an error occurred
     * @throws RepositoryException if the dao can't insert the element into the db.
     */
    @Override
    public int insert(User item) throws RepositoryException, AlreadyExistException {
        int key;
        String request = "INSERT INTO USER (name,last_name,mail_address,password,max_pompe,max_traction,max_abdo,max_km_course,taille,poids)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?)";
        try(PreparedStatement pstmt = this.connexion.prepareStatement(request)) {
            setUserVariables(item, pstmt);
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

    private void setUserVariables(User item, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1,item.getName());
        pstmt.setString(2,item.getLastName());
        pstmt.setString(3,item.getMailAddress());
        pstmt.setString(4,item.getPassword());
        pstmt.setInt(5,item.getMaxPushUps());
        pstmt.setInt(6,item.getMaxPullups());
        pstmt.setInt(7,item.getMaxAbs());
        pstmt.setFloat(8,item.getMaxKmRun());
        pstmt.setFloat(9,item.getHeight());
        pstmt.setFloat(10,item.getWeight());
    }

    /**
     * Deletes a user from the database based on the given user ID.
     *
     * @param key the user ID
     * @throws RepositoryException if an error occurs while deleting the user
     */
    public void delete(int key) throws RepositoryException {
        request = "DELETE FROM USER WHERE id_user = ?";
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setInt(1,key);
            statement.executeQuery();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    /**
     * Updates the given user in the database.
     *
     * @param user the user to update
     * @throws RepositoryException if an error occurs while updating the user
     */
    public void update(User user) throws RepositoryException, AlreadyExistException {
        request = "UPDATE user set name =?, last_name =?," +
                " mail_address =?, password =?, max_pompe =?, max_traction =?, max_abdo=?, " +
                "max_km_course =?, taille =?, poids =?WHERE id_user=?";
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            setUserVariables(user, statement);
            statement.setInt(11, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == SQLiteErrorCode.SQLITE_CONSTRAINT.code) {
                throw new AlreadyExistException(e);
            }
            throw new RepositoryException(e);
        }
    }

    /**
     * Updates the given user in the database.
     *
     * @param user the user to update
     * @throws RepositoryException if an error occurs while updating the user
     */
    public void updateColorMode(User user) throws RepositoryException {
        request = "UPDATE user set NightMode = ? WHERE id_user=?";
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setBoolean(1, user.getColorMode());
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    /**
     * Returns a list of all users in the database.
     *
     * @return a list of all users in the database
     * @throws RepositoryException if an error occurs while retrieving the users
     */
    public List<User> selectAll() throws RepositoryException {
        throw new UnsupportedOperationException("The function is not supported");
    }

    /**
     * Retrieves a User object from the database based on the provided email and hashed password.
     * This method is typically used for user authentication purposes.
     *
     * @param email          The email address of the user to be retrieved.
     * @param hashedPassword The hashed password associated with the user's account.
     * @return A User object if a match is found; otherwise, null.
     * @throws RepositoryException If there is an issue with the database access.
     */
    public User select(String email, String hashedPassword) throws RepositoryException {
        String request = "SELECT * FROM USER WHERE mail_address = ? AND password = ? ";
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setString(1, email);
            statement.setString(2, hashedPassword);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new User(
                    rs.getInt("id_user"),
                    rs.getString("name"),
                    rs.getString("last_name"),
                    rs.getString("mail_address"),
                    rs.getString("password"),
                    rs.getInt("max_pompe"),
                    rs.getInt("max_traction"),
                    rs.getInt("max_abdo"),
                    rs.getInt("max_km_course"),
                    rs.getFloat("taille"),
                    rs.getFloat("poids"),
                    rs.getBoolean("NightMode"),
                    rs.getString("Language"),
                    rs.getBoolean("mass_unit"),
                    rs.getBoolean("lenght_unit"));
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public User select(int key) {
        throw new UnsupportedOperationException("The function is not supported");
    }

    public boolean contains(String email) throws RepositoryException {
        String request = "SELECT * FROM USER WHERE mail_address = ? ";
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    /**
     * Updates the given user in the database.
     *
     * @param user the user to update
     * @throws RepositoryException if an error occurs while updating the user
     */
    public void updateLanguage(User user) throws RepositoryException {
        request = "UPDATE user set Language = ? WHERE id_user=?";
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setString(1, user.getLanguage());
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
    /**
     * Updates the weight unit preference for a specific user.
     *
     * @param userId      The ID of the user whose preference is being updated.
     * @param preferences The new weight unit preference to be set.
     * @throws RepositoryException If there is an issue updating the database.
     */

    public void setWeightPreferences(int userId, boolean preferences) throws RepositoryException {
        String request = "UPDATE USER SET mass_unit=? WHERE id_user=?";
        try(PreparedStatement statement = this.connexion.prepareStatement(request)){
            statement.setBoolean(1, preferences);
            statement.setInt(2, userId);
            statement.executeUpdate();

        } catch (SQLException e){
            throw new RepositoryException(e);
        }
    }
    /**
     * Updates the length unit preference for a specific user.
     *
     * @param userId      The ID of the user whose preference is being updated.
     * @param preferences The new length unit preference to be set.
     * @throws RepositoryException If there is an issue updating the database.
     */

    public void setSizePreferences(int userId, boolean preferences)throws RepositoryException{
        String request = "UPDATE USER SET lenght_unit=? WHERE id_user=?";
        try(PreparedStatement statement = this.connexion.prepareStatement(request)){
            statement.setBoolean(1, preferences);
            statement.setInt(2, userId);
            statement.executeUpdate();

        } catch (SQLException e){
            throw new RepositoryException(e);
        }
    }

    private static class UserDaoHolder {
        private static UserDao getInstance() throws RepositoryException {
            return new UserDao();
        }
    }

}
