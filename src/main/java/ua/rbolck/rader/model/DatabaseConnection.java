package ua.rbolck.rader.model;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection implements Closeable {


    private DataSource dataSource;
    private Connection connection;

    //TODO implement logger
    private static final Logger log = Logger.getLogger(DatabaseConnection.class);

    private static DatabaseConnection instance = new DatabaseConnection();

    public static DatabaseConnection getInstance() {
        return instance;
    }


    private DatabaseConnection() {
        try {
            Context ctx = new InitialContext();
            this.dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/rader");
            this.connection = this.dataSource.getConnection();
            System.out.println("Opened connection");
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return this.connection;
    }

    @Override
    public void close() throws IOException {

        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Closed connection");
    }
}

