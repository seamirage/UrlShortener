package storages.repositories;

import storages.DatabaseException;
import storages.dto.UserInfo;

/**
 * Represents a storage of users information.
 */
public interface UsersRepository {
    void add(UserInfo user) throws DatabaseException;
    UserInfo findUserByGoogleIdentity(String googleIdentity) throws DatabaseException;

    /**
     * Adds new user in storage, if user with same googleIdentity does not exists yet.
     * @param googleIdentity userId provided by Google
     * @return existing user, if such user is already exists, else new user
     * @throws DatabaseException
     */
    UserInfo addUserIfNotExists(String googleIdentity) throws DatabaseException;
}
