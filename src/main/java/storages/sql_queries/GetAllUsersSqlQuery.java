package storages.sql_queries;

import storages.connection_sources.ConnectionSource;
import storages.dto.UserInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetAllUsersSqlQuery extends SqlQuery<List<UserInfo>> {

    public GetAllUsersSqlQuery(ConnectionSource connectionSource) {
        super(GET_ALL_USERS, connectionSource);
    }

    @Override
    protected List<UserInfo> readAndConvertResultSet(ResultSet resultSet) throws SQLException {
        List<UserInfo> result = new ArrayList<UserInfo>();

        while(resultSet.next()) {
            String userId = resultSet.getString("UserId");
            String googleIdentity = resultSet.getString("GoogleIdentity");
            result.add(new UserInfo(userId, googleIdentity));
        }

        return result;
    }

    private static final String GET_ALL_USERS = "SELECT * FROM Users";
}
