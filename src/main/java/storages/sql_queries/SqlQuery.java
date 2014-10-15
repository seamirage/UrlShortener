package storages.sql_queries;

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

    protected void addParameters(PreparedStatement preparedStatement) throws SQLException {

    }

    protected abstract TResult readAndConvertResultSet(ResultSet resultSet) throws SQLException;

    public TResult execute() throws SQLException {
        try (Connection connection = connectionSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                addParameters(preparedStatement);
                try (ResultSet result = preparedStatement.executeQuery()) {
                    return readAndConvertResultSet(result);
                }
            }
        }
    }

    private ConnectionSource connectionSource;
    private final String statement;
}
