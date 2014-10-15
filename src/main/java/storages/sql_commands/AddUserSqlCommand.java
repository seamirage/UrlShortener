package storages.sql_commands;

import storages.connection_sources.ConnectionSource;
import storages.dto.UserInfo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class AddUserSqlCommand extends SqlCommand {

    public AddUserSqlCommand(ConnectionSource connectionSource, UserInfo userInfo) {
        super(connectionSource, INSERT_USER_STATEMENT);
        this.userInfo = userInfo;
    }

    @Override
    protected void addParameters(PreparedStatement preparedStatement) throws SQLException {
        String newUserId = UUID.randomUUID().toString();
        preparedStatement.setString(1, newUserId);
        preparedStatement.setString(2, userInfo.getGoogleIdentity());
        userInfo.setUserId(newUserId);
    }

    private static final String INSERT_USER_STATEMENT = "INSERT INTO Users Values (?, ?)";
    private UserInfo userInfo;
}
