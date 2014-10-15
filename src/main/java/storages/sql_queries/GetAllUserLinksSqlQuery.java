package storages.sql_queries;

import storages.connection_sources.ConnectionSource;
import storages.dto.UserLink;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetAllUserLinksSqlQuery extends SqlQuery<List<UserLink>> {
    public GetAllUserLinksSqlQuery(ConnectionSource connectionSource) {
        super(GET_ALL_LINKS_STATEMENT, connectionSource);
    }

    @Override
    protected List<UserLink> readAndConvertResultSet(ResultSet resultSet) throws SQLException {
        List<UserLink> result = new ArrayList<UserLink>();
        while(resultSet.next()) {
            String shortLinkId = resultSet.getString("ShortLinkId");
            String originalUri = resultSet.getString("OriginalUri");
            int userId = resultSet.getInt("UserId");
            result.add(new UserLink(originalUri, shortLinkId, userId));
        }

        return result;
    }

    private static final String GET_ALL_LINKS_STATEMENT = "SELECT * FROM UserLinks";
}