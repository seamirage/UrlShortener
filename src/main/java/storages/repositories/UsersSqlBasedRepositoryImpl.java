package storages.repositories;

import storages.connection_sources.ConnectionSource;
import storages.dto.UserInfo;
import storages.sql_commands.AddUserSqlCommand;
import storages.sql_queries.FindUserByGoogleIdentity;

import java.sql.SQLException;

public class UsersSqlBasedRepositoryImpl implements UsersRepository {
    public UsersSqlBasedRepositoryImpl(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
    }

    @Override
    public void add(UserInfo user) throws SQLException {
        AddUserSqlCommand command = new AddUserSqlCommand(connectionSource, user);
        command.execute();
    }

    @Override
    public void findUserByGoogleIdentity(String googleIdentity) {
        FindUserByGoogleIdentity query = new FindUserByGoogleIdentity(connectionSource, googleIdentity);
    }

    private ConnectionSource connectionSource;
}
