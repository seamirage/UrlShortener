package storages.connection_sources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionSourceImpl implements ConnectionSource {
    public SimpleConnectionSourceImpl(String username, String password, String dbUrl) {
        this.username = username;
        this.password = password;
        this.dbUrl = dbUrl;
    }

    @Override
    public Connection getConnection() throws SQLException {

        return DriverManager.getConnection(dbUrl, username, password);
    }

    private final String username;
    private final String password;
    private final String dbUrl;
}
