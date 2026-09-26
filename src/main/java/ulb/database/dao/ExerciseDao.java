package ulb.database.dao;

import org.sqlite.SQLiteErrorCode;
import ulb.exceptions.AlreadyExistException;
import ulb.exceptions.RepositoryException;
import ulb.models.Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercise data access object
 */
public class ExerciseDao implements Dao<Exercise> {


    private final Connection connexion;

    private ExerciseDao() throws RepositoryException {
        this.connexion = DBManager.getInstance().getConnection();
    }

    public static ExerciseDao getInstance() throws RepositoryException {
        return ExerciseDao.ExerciseDaoHolder.getInstance();
    }

    /**
     * Insert new Exercise into db
     */
    @Override
    public int insert(Exercise item) throws RepositoryException, AlreadyExistException {
        int key;
        String request = "INSERT INTO EXERCICE (user_id,name,difficulty,type,description,image_name,image_data, exercise_duration,calories)" +
                " VALUES (?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setInt(1, item.getUserId());
            statement.setString(2, item.getName());
            statement.setInt(3, item.getDifficulty());
            statement.setString(4, item.getType());
            statement.setString(5, item.getDescription());
            statement.setString(6, item.getImageName());
            statement.setBytes(7, item.getImageData());
            if (item.getExerciseDuration() != 0) {
                statement.setInt(8, item.getExerciseDuration());
            } else {
                statement.setInt(8, 0);
            }
            if (item.getCalories() != 0) {
                statement.setInt(9, item.getCalories());
            } else {
                statement.setInt(9, 0);
            }
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
        String request = "DELETE FROM EXERCICE WHERE id_exercice=?";
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setInt(1, key);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(Exercise item) throws RepositoryException, AlreadyExistException {
        String request = "UPDATE EXERCICE SET name=? ,difficulty=? ,type=? ,description=? ,image_data=? ,exercise_duration=? ,calories=? WHERE id_exercice=?";
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setString(1, item.getName());
            statement.setInt(2, item.getDifficulty());
            statement.setString(3, item.getType());
            statement.setString(4, item.getDescription());
            statement.setBytes(5, item.getImageData());
            statement.setInt(6, item.getExerciseDuration());
            statement.setInt(7, item.getCalories());
            statement.setInt(8, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == SQLiteErrorCode.SQLITE_CONSTRAINT.code) {
                throw new AlreadyExistException(e);
            }
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Exercise> selectAll() throws RepositoryException {
        String request = "SELECT * FROM EXERCICE";
        List<Exercise> dtos = new ArrayList<>();
        try(PreparedStatement statement = this.connexion.prepareStatement(request);
                ResultSet rs = statement.executeQuery()){
            addExerciseToList(dtos, rs);
        }catch(SQLException e){
            throw new RepositoryException(e);
        }
        return dtos;
    }

    private void addExerciseToList(List<Exercise> dtos, ResultSet rs) throws SQLException {
        while (rs.next()){
            Exercise ex = new Exercise(rs.getInt("id_exercice"),rs.getInt("user_id"), rs.getString("name"),
                    rs.getString("description"), rs.getString("type"),rs.getInt("difficulty"),
                    rs.getString("image_name"), rs.getBytes("image_data"),rs.getInt("exercise_duration"),rs.getInt("calories"));
            dtos.add(ex);
        }
    }
    /**
     * Retrieves all exercises associated with a specific user ID from the database.
     *
     * @param userId The ID of the user whose exercises are to be retrieved.
     * @return A list of Exercise objects associated with the user.
     * @throws RepositoryException If there is an issue with the database access.
     */
    public List<Exercise> getAllWithId(int userId) throws RepositoryException{
        String request = "SELECT * FROM EXERCICE WHERE user_id = ?";
        List<Exercise> dtos = new ArrayList<>();
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            addExerciseToList(dtos, rs);
        }catch(SQLException e){
            throw new RepositoryException(e);
        }
        return dtos;
    }

    /**
     * SElect all exercices with the specified type
     *
     * @param typeSelected string type to filter
     * @return Exercise list
     * @throws RepositoryException
     */
    public List<Exercise> selectAllByType(String typeSelected, int userId) throws RepositoryException {
        String request = "SELECT * FROM EXERCICE WHERE type = ? AND user_id = ?";
        List<Exercise> dtos = new ArrayList<>();
        try(PreparedStatement statement = this.connexion.prepareStatement(request)){
                statement.setString(1,typeSelected);
                statement.setInt(2,userId);
                ResultSet rs = statement.executeQuery();
            addExerciseToList(dtos, rs);
        }catch(SQLException e){
            throw new RepositoryException(e);
        }
        return dtos;
    }
    /**
     * Selects all exercises for a user that match the specified difficulty level.
     *
     * @param difficultySelected The difficulty level of the exercises to retrieve.
     * @param userId             The ID of the user whose exercises are to be selected.
     * @return A list of Exercise objects that match the difficulty level.
     * @throws RepositoryException If there is an issue with the database access.
     */
    public List<Exercise> selectAllByDifficulty(String difficultySelected, int userId) throws RepositoryException {
        String request = "SELECT * FROM EXERCICE WHERE difficulty = ? AND user_id = ?";
        List<Exercise> dtos = new ArrayList<>();
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setString(1, difficultySelected);
            statement.setInt(2, userId);
            ResultSet rs = statement.executeQuery();
            addExerciseToList(dtos, rs);
        }catch(SQLException e){
            throw new RepositoryException(e);
        }

        return dtos;
    }
    /**
     * Selects all exercises for a user that are associated with the specified muscle.
     *
     * @param muscleSelected The name of the muscle associated with the exercises to retrieve.
     * @param userId         The ID of the user whose exercises are to be selected.
     * @return A list of Exercise objects that are associated with the specified muscle.
     * @throws RepositoryException If there is an issue with the database access.
     */
    public List<Exercise> selectAllByMuscle(String muscleSelected, int userId) throws RepositoryException {
        String request = "SELECT * FROM EXERCICE LEFT JOIN EXERCICE_MUSCLE,MUSCLE WHERE EXERCICE_MUSCLE.id_muscle = MUSCLE.id_muscle AND EXERCICE_MUSCLE.id_exercice = EXERCICE.id_exercice AND MUSCLE.name = ? AND Exercice.user_id = ?";
        List<Exercise> dtos = new ArrayList<>();
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setString(1, muscleSelected);
            statement.setInt(2, userId);
            ResultSet rs = statement.executeQuery();
            addExerciseToList(dtos, rs);
        }catch(SQLException e){
            throw new RepositoryException(e);
        }

        return dtos;
    }

    @Override
    public Exercise select(int key) throws RepositoryException {
        String request = "SELECT * FROM EXERCICE WHERE id_exercice = ?";
        Exercise exercise = null;
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setInt(1, key);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                exercise = new Exercise(rs.getInt("id_exercice"), rs.getInt("user_id"), rs.getString("name"),
                        rs.getString("description"), rs.getString("type"), rs.getInt("difficulty"),
                        rs.getString("image_name"), rs.getBytes("image_data"), rs.getInt("exercise_duration"), rs.getInt("calories"));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return exercise;
    }
    /**
     * Retrieves all exercises for a specific user, excluding any that are categorized as 'Pause'.
     *
     * @param userId The ID of the user whose exercises are to be retrieved.
     * @return A list of Exercise objects, excluding 'Pause' exercises.
     * @throws RepositoryException If there is an issue with the database access.
     */
    public List<Exercise> getAllWithIdWithoutThePauses(int userId) throws RepositoryException {
        String request = "SELECT * FROM EXERCICE WHERE user_id = ? AND name NOT LIKE 'Pause%'";
        List<Exercise> dtos = new ArrayList<>();
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            addExerciseToList(dtos, rs);
        }catch(SQLException e){
            throw new RepositoryException(e);
        }
        return dtos;
    }

    /**
     * Get all exercise linked to a programme
     *
     * @param programmeId programme id
     * @return list of exercise from this programme
     * @throws RepositoryException
     */
    public List<Exercise> getAllExerciseFromProgram(int programmeId) throws RepositoryException {
        String request = "SELECT * FROM EXERCICE as E JOIN EXERCICE_PROGRAMME as EP " +
                "ON EP.id_exercice = E.id_exercice WHERE EP.id_programme = ?";
        List<Exercise> dtos = new ArrayList<>();
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setInt(1, programmeId);
            ResultSet rs = statement.executeQuery();
            addExerciseToList(dtos, rs);
        }catch(SQLException e){
            throw new RepositoryException(e);
        }
        return dtos;
    }
    /**
     * Selects all default 'Pause' exercises for a user from the database.
     * These are identified by a specific type, name pattern, and default image data and name.
     *
     * @param userId The ID of the user whose default pause exercises are to be retrieved.
     * @return A list of Exercise objects that are default pauses.
     * @throws RepositoryException If there is an issue with the database access.
     */
    public List<Exercise> selectAllDefaultPauses(int userId) throws RepositoryException {
        String request = "SELECT * FROM EXERCICE WHERE type = ? AND user_id = ? " +
                "AND name LIKE 'Pause%' AND image_data = ? AND image_name = ? ORDER BY id_exercice ";
        List<Exercise> dtos = new ArrayList<>();
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setString(1, "Pause");
            statement.setInt(2, userId);
            statement.setBytes(3, "0A0A0A".getBytes());
            statement.setString(4, "/");
            ResultSet rs = statement.executeQuery();
            addExerciseToList(dtos, rs);
        }catch(SQLException e){
            throw new RepositoryException(e);
        }
        return dtos;
    }

    /**
     * Selects all exercises for a user that match a specified type, excluding any that are categorized as 'Pause'.
     *
     * @param typeSelected The type of exercises to retrieve.
     * @param userId       The ID of the user whose exercises are to be selected.
     * @return A list of Exercise objects that match the type, excluding 'Pause' exercises.
     * @throws RepositoryException If there is an issue with the database access.
     */
    public List<Exercise> selectAllByTypeWithoutThePauses(String typeSelected, int userId) throws RepositoryException {
        String request = "SELECT * FROM EXERCICE WHERE type = ? AND user_id = ? AND name NOT LIKE 'Pause%'";
        List<Exercise> dtos = new ArrayList<>();
        try (PreparedStatement statement = this.connexion.prepareStatement(request)) {
            statement.setString(1, typeSelected);
            statement.setInt(2, userId);
            ResultSet rs = statement.executeQuery();
            addExerciseToList(dtos, rs);
        }catch(SQLException e){
            throw new RepositoryException(e);
        }
        return dtos;
    }

    private static class ExerciseDaoHolder {
        private static ExerciseDao getInstance() throws RepositoryException {
            return new ExerciseDao();
        }
    }
}
