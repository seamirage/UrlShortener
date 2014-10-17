package storages.connection_sources;

import storages.DatabaseException;

import java.sql.Connection;

public interface ConnectionSource {
    Connection getConnection() throws DatabaseException;
}
