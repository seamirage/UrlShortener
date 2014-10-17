package storages.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import storages.dto.UserInfo;
import storages.sql_queries.GetAllUsersSqlQuery;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class UsersSqlBasedRepositoryImplTest extends SqlRepositoriesBaseTest {
    @Before
    public void SetUp() throws SQLException {
        super.SetUp();
        executeStatement("CREATE table Users (" +
                "    UserId char(36)," +
                "    GoogleIdentity varchar(512)" +
                ");");
        respository = new UsersSqlBasedRepositoryImpl(connectionSource);
    }

    @After
    public void TearDown() throws SQLException {
        executeStatement("DROP table Users");
    }

    @Test
    public void addUserTest() throws SQLException {
        UserInfo user = new UserInfo(googleIdentity);
        respository.add(user);

        List<UserInfo> allUsers = new GetAllUsersSqlQuery(connectionSource).execute();
        assertEquals(1, allUsers.size());
        assertNotNull(user.getUserId());
        assertNotEquals("", user.getUserId());
        assertEquals(googleIdentity, user.getGoogleIdentity());
    }

    @Test
    public void findUserByIdentity_WhenItExistsTest() throws SQLException {
        respository.add(new UserInfo(googleIdentity));

        UserInfo user = respository.findUserByGoogleIdentity(googleIdentity);

        assertEquals(googleIdentity, user.getGoogleIdentity());
    }

    @Test
    public void findUserByIdentity_WhenItDoesNotExistsTest() throws SQLException {
        UserInfo user = respository.findUserByGoogleIdentity(googleIdentity);

        assertNull(user);
    }

    @Test
    public void whenUserIsNew_StoreHim_AndReturnUserInfo() throws SQLException {
        UserInfo user = respository.addUserIfNotExists(googleIdentity);

        assertEquals(googleIdentity, user.getGoogleIdentity());
        assertNotNull(user.getUserId());
    }

    @Test
    public void whenUserAlreadyExists_ReturnUserInfo() throws SQLException {
        UserInfo added = new UserInfo(googleIdentity);
        respository.add(added);

        UserInfo actualUser = respository.addUserIfNotExists(googleIdentity);

        assertEquals(added.getUserId(), actualUser.getUserId());
    }

    private UsersSqlBasedRepositoryImpl respository;
    private static final String googleIdentity = "google_Id";
}
