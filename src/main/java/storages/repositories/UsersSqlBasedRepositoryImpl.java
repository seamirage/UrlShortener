package storages.repositories;

import storages.DatabaseException;
import storages.connection_sources.ConnectionSource;
import storages.dto.UserInfo;
import storages.sql_commands.AddUserSqlCommand;
import storages.sql_queries.FindUserByGoogleIdentity;

public class UsersSqlBasedRepositoryImpl implements UsersRepository {
    public UsersSqlBasedRepositoryImpl(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
    }

    @Override
    public void add(UserInfo user) throws DatabaseException {
        AddUserSqlCommand command = new AddUserSqlCommand(connectionSource, user);
        command.execute();
    }

    @Override
    public UserInfo findUserByGoogleIdentity(String googleIdentity) throws DatabaseException {
        FindUserByGoogleIdentity query = new FindUserByGoogleIdentity(connectionSource, googleIdentity);
        return query.execute();
    }

    @Override
    public UserInfo addUserIfNotExists(String googleIdentity) throws DatabaseException {
        UserInfo user = findUserByGoogleIdentity(googleIdentity);
        if (null == user) {
            user = new UserInfo(googleIdentity);
            add(user);
        }

        return user;
    }

    private ConnectionSource connectionSource;
}
