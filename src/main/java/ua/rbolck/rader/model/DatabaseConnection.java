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

    private static final Logger log = Logger.getLogger(DatabaseConnection.class);

    private static DatabaseConnection instance;

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            return instance = new DatabaseConnection();
        } else return instance;
    }

    private DatabaseConnection() {
        try {
            Context ctx = new InitialContext();
            this.dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/rader");
            this.connection = getConnection();
            log.info("Opened connection");
        } catch (NamingException e) {
            log.error("NamingException while creating DatabaseConnection", e);
        } catch (SQLException e) {
            log.error("SQLException while creating DatabaseConnection", e);
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null) {
            log.info("Open new connection");
            connection = this.dataSource.getConnection();
        }
        log.info("Connection was been");
        return connection;
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            log.error("SQLException while closing DatabaseConnection", e);
        }
        log.info("Closed connection");
    }

    public static String sanitize(String string) {
        return string
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;");
    }
}

