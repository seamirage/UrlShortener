package storages.repositories;

import storages.dto.UserInfo;

import java.sql.SQLException;

public interface UsersRepository {
    void add(UserInfo user) throws SQLException;
    UserInfo findUserByGoogleIdentity(String googleIdentity) throws SQLException;
    UserInfo addUserIfNotExists(String googleIdentity) throws SQLException;
}
