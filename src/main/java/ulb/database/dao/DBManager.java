package ulb.database.dao;

import ulb.exceptions.RepositoryException;
import ulb.utils.ConfigManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages the database connection.
 * Utilizes the Singleton pattern to ensure only one instance of the database manager is used.
 */
class DBManager {

    private Connection connection;

    private DBManager() {
    }

    /**
     * Provides the single instance of Connection.
     * If the connection is not already established, it creates one using the JDBC URL from the configuration manager.
     *
     * @return The single Connection instance to the database.
     * @throws RepositoryException If the connection to the database cannot be established.
     */
    protected Connection getConnection() throws RepositoryException {
        String jdbcUrl = "jdbc:sqlite:" + ConfigManager.getInstance().getProperties("db.url");
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(jdbcUrl);
            } catch (SQLException ex) {
                throw new RepositoryException("Connection failed: " + ex.getMessage());
            }
        }
        return connection;
    }

    /**
     * Provides access to the singleton instance of DBManager.
     *
     * @return The single instance of DBManager.
     */
    static protected DBManager getInstance() {
        return DBManagerHolder.INSTANCE;
    }

    private static class DBManagerHolder {

        private static final DBManager INSTANCE = new DBManager();
    }
}
