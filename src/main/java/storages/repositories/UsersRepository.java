package storages.repositories;

import storages.DatabaseException;
import storages.dto.UserInfo;

public interface UsersRepository {
    void add(UserInfo user) throws DatabaseException;
    UserInfo findUserByGoogleIdentity(String googleIdentity) throws DatabaseException;
    UserInfo addUserIfNotExists(String googleIdentity) throws DatabaseException;
}
