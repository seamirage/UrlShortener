package storages.repositories;

import storages.DatabaseException;
import storages.connection_sources.ConnectionSource;
import storages.dto.UserLink;
import storages.sql_commands.AddUserLinkSqlCommand;
import storages.sql_queries.GetOriginalUriSqlQuery;

public class UserLinksSqlBasedRepositoryImpl implements UserLinksRepository {
    public UserLinksSqlBasedRepositoryImpl(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
    }

    @Override
    public void add(UserLink userLink) throws DatabaseException {
        AddUserLinkSqlCommand command = new AddUserLinkSqlCommand(connectionSource, userLink);
        command.execute();
    }

    @Override
    public String getOriginalUri(String shortLinkId) throws DatabaseException {
        GetOriginalUriSqlQuery query = new GetOriginalUriSqlQuery(connectionSource, shortLinkId);
        return query.execute();
    }

    private ConnectionSource connectionSource;
}
