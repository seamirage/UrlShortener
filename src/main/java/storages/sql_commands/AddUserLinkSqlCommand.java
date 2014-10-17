package storages.sql_commands;

import storages.DatabaseException;
import storages.connection_sources.ConnectionSource;
import storages.dto.UserLink;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUserLinkSqlCommand extends SqlCommand {
    public AddUserLinkSqlCommand(ConnectionSource connectionSource, UserLink userLink) {
        super(connectionSource, INSERT_LINK_STATEMENT);
        this.userLink = userLink;
    }

    @Override
    protected void addParameters(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, userLink.getShortLinkId());
        preparedStatement.setString(2, userLink.getOriginalUri());
        preparedStatement.setString(3, userLink.getUserId());
    }

    private static final String INSERT_LINK_STATEMENT = "INSERT INTO UserLinks VALUES (?, ?, ?) ";
    private UserLink userLink;
}

