package storages.sql_queries;

import storages.connection_sources.ConnectionSource;
import storages.dto.UserInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FindUserByGoogleIdentity extends SqlQuery<UserInfo> {

    private String googleIdentity;

    public FindUserByGoogleIdentity(ConnectionSource connectionSource, String googleIdentity) {
        super(FIND_USER_STATEMENT, connectionSource);
        this.googleIdentity = googleIdentity;
    }

    protected void addParameters(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, googleIdentity);
    }
    @Override
    protected UserInfo readAndConvertResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            String userId = resultSet.getString("UserId");
            String googleIdentity = resultSet.getString("GoogleIdentity");
            return new UserInfo(userId, googleIdentity);
        } else {
            return null;
        }
    }

    private static final String FIND_USER_STATEMENT = "SELECT * FROM Users WHERE GoogleIdentity = ? ";
}
