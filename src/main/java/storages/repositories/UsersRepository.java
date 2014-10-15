package storages.repositories;

import storages.dto.UserInfo;

import java.sql.SQLException;

public interface UsersRepository {
    void add(UserInfo user) throws SQLException;
    void findUserByGoogleIdentity(String googleIdentity);
}
