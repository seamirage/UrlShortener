package storages.repositories;

import storages.DatabaseException;
import storages.dto.UserLink;

/**
 * Represents a storage of short links.
 */
public interface UserLinksRepository {
    void add(UserLink userLink) throws DatabaseException;

    /**
     * Resolves original url by short link id.
     * @param shortLinkId
     * @return original URL. If original URL was not found, returns null.
     * @throws DatabaseException
     */
    String getOriginalUri(String shortLinkId) throws DatabaseException;
}
