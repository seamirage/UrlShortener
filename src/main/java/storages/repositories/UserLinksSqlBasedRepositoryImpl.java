package storages.repositories;

import storages.connection_sources.ConnectionSource;
import storages.dto.UserLink;
import storages.sql_commands.AddUserLinkSqlCommand;
import storages.sql_queries.GetOriginalUriSqlQuery;

import java.sql.SQLException;

public class UserLinksSqlBasedRepositoryImpl implements UserLinksRepository {
    public UserLinksSqlBasedRepositoryImpl(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
    }

    @Override
    public void add(UserLink userLink) throws SQLException {
        AddUserLinkSqlCommand command = new AddUserLinkSqlCommand(connectionSource, userLink);
        command.execute();
    }

    @Override
    public String getOriginalUri(String shortLinkId) throws SQLException {
        GetOriginalUriSqlQuery query = new GetOriginalUriSqlQuery(connectionSource, shortLinkId);
        return query.execute();
    }

    private ConnectionSource connectionSource;
}
