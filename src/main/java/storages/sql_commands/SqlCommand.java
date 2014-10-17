package storages.sql_commands;

import storages.DatabaseException;
import storages.connection_sources.ConnectionSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlCommand {

    public SqlCommand(ConnectionSource connectionSource, String statement) {
        this.connectionSource = connectionSource;
        this.statement = statement;
    }

    public void execute() throws DatabaseException {

        try {
            try (Connection connection = connectionSource.getConnection()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                    addParameters(preparedStatement);
                    preparedStatement.execute();
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    protected void addParameters(PreparedStatement preparedStatement) throws SQLException {

    }

    private ConnectionSource connectionSource;
    private final String statement;
}