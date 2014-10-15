package storages.repositories;

import storages.dto.UserLink;

import java.sql.SQLException;

public interface UserLinksRepository {
    void add(UserLink userLink) throws SQLException;
    String getOriginalUri(String shortUri) throws SQLException;
}
