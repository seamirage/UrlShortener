package storages;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import storages.connection_sources.ConnectionSource;
import storages.connection_sources.SimpleConnectionSourceImpl;
import storages.sql_queries.GetAllUserLinksSqlQuery;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserLinksSqlBasedRepositoryImplTest {

    @Before
    public void SetUp() throws SQLException {
        connectionSource = new SimpleConnectionSourceImpl("username", "pwd", dbUrl);
        executeStatement("CREATE table UserLinks (" +
                "    ShortLinkId char(8)," +
                "    OriginalUri varchar(2048)," +
                "    UserId int" +
                ");");
        repository = new UserLinksSqlBasedRepositoryImpl(connectionSource);
    }

    @After
    public void TearDown() throws SQLException {
       executeStatement("DROP table UserLinks");
    }

    @Test
    public void addLinkTest() throws URISyntaxException, SQLException {
        repository.add(new UserLink(originalUri, shortLinkId, 1));

        List<UserLink> allLinks = new GetAllUserLinksSqlQuery(connectionSource).execute();
        assertEquals(1, allLinks.size());
        UserLink addedLink = allLinks.get(0);
        CheckUserLink(addedLink, originalUri, shortLinkId, 1);
    }

    @Test
    public void getOriginalUri_WhenItExistsTest() throws SQLException {
        repository.add(new UserLink(originalUri, shortLinkId, 1));

        String original = repository.getOriginalUri(shortLinkId);

        assertEquals(originalUri, original);
    }

    @Test
    public void getOriginalUri_WhenItDoesNotExistsTest() throws SQLException {
        repository.add(new UserLink(originalUri, shortLinkId, 1));

        String original = repository.getOriginalUri("not_existing");

        assertNull(original);
    }

    private void executeStatement(String statement) throws SQLException {
        Connection conn = connectionSource.getConnection();
        conn.createStatement().execute(statement);
        conn.close();
    }

    private void CheckUserLink(UserLink addedLink, String expectedOriginalUri1, String expectedShortLinkId, int expectedUserId) {
        assertEquals(expectedOriginalUri1, addedLink.getOriginalUri());
        assertEquals(expectedShortLinkId, addedLink.getShortLinkId());
        assertEquals(expectedUserId, addedLink.getUserId());
    }

    private ConnectionSource connectionSource;
    private UserLinksRepository repository;
    private static final String dbUrl = "jdbc:h2:mem:TestDB;DB_CLOSE_DELAY=-1";
    private static final String originalUri = "https%3A%2F%2Fgoogle.ru%2Fsearch";
    private static final String shortLinkId = "short_01";
}