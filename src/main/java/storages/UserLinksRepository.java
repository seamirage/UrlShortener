package storages;

import java.sql.SQLException;

public interface UserLinksRepository {
    void add(UserLink userLink) throws SQLException;
    String getOriginalUri(String shortUri) throws SQLException;
}
