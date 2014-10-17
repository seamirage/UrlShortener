package storages.connection_sources;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import storages.DatabaseException;

import java.sql.Connection;
import java.sql.SQLException;

public class PooledConnectionSourceImpl implements ConnectionSource {
    public PooledConnectionSourceImpl(String username, String password, String dbUrl) {
        pooledDataSource = new ComboPooledDataSource();
        pooledDataSource.setUser(username);
        pooledDataSource.setPassword(password);
        pooledDataSource.setJdbcUrl(dbUrl);
    }

    @Override
    public Connection getConnection() throws DatabaseException {
        try {
            return pooledDataSource.getConnection();
        } catch (SQLException e) {
            throw new DatabaseException("Could not initialize connection pool", e);
        }
    }

    ComboPooledDataSource pooledDataSource;
}
