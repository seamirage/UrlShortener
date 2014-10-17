package storages.repositories;

import storages.DatabaseException;
import storages.dto.UserLink;

public interface UserLinksRepository {
    void add(UserLink userLink) throws DatabaseException;
    String getOriginalUri(String shortUri) throws DatabaseException;
}
