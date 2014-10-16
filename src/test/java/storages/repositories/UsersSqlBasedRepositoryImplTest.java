package storages.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import storages.dto.UserInfo;
import storages.sql_queries.FindUserByGoogleIdentity;
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
        FindUserByGoogleIdentity query = new FindUserByGoogleIdentity(connectionSource, googleIdentity);

        UserInfo user = respository.findUserByGoogleIdentity(googleIdentity);

        assertNull(user);
    }

    private UsersSqlBasedRepositoryImpl respository;
    private static final String googleIdentity = "google_Id";
}
