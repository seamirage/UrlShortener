package storages;

import storages.connection_sources.ConnectionSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLinksSqlBasedRepositoryImpl implements UserLinksRepository {
    public UserLinksSqlBasedRepositoryImpl(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
    }

    @Override
    public void add(UserLink userLink) throws SQLException {
        try (Connection connection = connectionSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LINK_STATEMENT);
            preparedStatement.setString(1, userLink.getShortLinkId());
            preparedStatement.setString(2, userLink.getOriginalUri());
            preparedStatement.setInt(3, userLink.getUserId());

            preparedStatement.execute();
        }
    }

    @Override
    public String getOriginalUri(String shortLinkId) throws SQLException {
        try (Connection connection = connectionSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ORIGINAL_URI_STATEMENT)) {
                preparedStatement.setString(1, shortLinkId);
                try (ResultSet result = preparedStatement.executeQuery()) {
                    if (result.next()) {
                        return result.getString("OriginalUri");
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    private ConnectionSource connectionSource;
    private static final String INSERT_LINK_STATEMENT = "INSERT INTO UserLinks VALUES (?, ?, ?) ";
    private static final String GET_ORIGINAL_URI_STATEMENT = "SELECT OriginalUri FROM UserLinks WHERE ShortLinkId = ? ";
}
