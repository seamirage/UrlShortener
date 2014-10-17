package storages.sql_queries;

import storages.DatabaseException;
import storages.connection_sources.ConnectionSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SqlQuery<TResult> {
    public SqlQuery(String statement, ConnectionSource connectionSource) {
        this.statement = statement;
        this.connectionSource = connectionSource;
    }

    public TResult execute() throws DatabaseException {
        try (Connection connection = connectionSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                addParameters(preparedStatement);
                try (ResultSet result = preparedStatement.executeQuery()) {
                    return readAndConvertResultSet(result);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    protected void addParameters(PreparedStatement preparedStatement) throws SQLException {

    }

    protected abstract TResult readAndConvertResultSet(ResultSet resultSet) throws SQLException;

    private ConnectionSource connectionSource;
    private final String statement;
}
