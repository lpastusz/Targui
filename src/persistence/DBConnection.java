package persistence;

import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.Connection;

public class DBConnection { 

    // database URL
    /*
    private static final String dbURL = "jdbc:mysql://www.db4free.net:3306/targui?zeroDateTimeBehavior=convertToNull";
    private static final String username = "lukas412";
    private static final String password = "000000";
    */
    
    private static final String dbURL = "jdbc:mysql://localhost/test_targui?zeroDateTimeBehavior=convertToNull";
    private static final String username = "root";
    private static final String password = "";
    
    private Connection connection;
    public DBConnection() {
        try {
            connection = (Connection) DriverManager.getConnection(dbURL, username, password);

        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, sqlException.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }

    }

    public void closeConnection() {
        try {
            connection.close();
        } // handle exceptions closing statement and connection
        catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, sqlException.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}