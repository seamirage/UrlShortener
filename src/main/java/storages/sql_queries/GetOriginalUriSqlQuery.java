package storages.sql_queries;

import storages.connection_sources.ConnectionSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetOriginalUriSqlQuery extends SqlQuery<String> {
    public GetOriginalUriSqlQuery(ConnectionSource connectionSource, String shortLinkId) {
        super(GET_ORIGINAL_URI_STATEMENT, connectionSource);
        this.shortLinkId = shortLinkId;
    }

    protected void addParameters(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, shortLinkId);
    }

    @Override
    protected String readAndConvertResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getString("OriginalUri");
        } else {
            return null;
        }
    }

    private static final String GET_ORIGINAL_URI_STATEMENT = "SELECT OriginalUri FROM UserLinks WHERE ShortLinkId = ? ";
    private final String shortLinkId;
}