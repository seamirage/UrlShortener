package storages.connection_sources;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionSource {
    Connection getConnection() throws SQLException;
}
