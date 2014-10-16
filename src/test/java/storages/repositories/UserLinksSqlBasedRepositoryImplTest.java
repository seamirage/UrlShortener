package storages.repositories;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import storages.dto.UserLink;
import storages.sql_queries.GetAllUserLinksSqlQuery;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserLinksSqlBasedRepositoryImplTest extends SqlRepositoriesBaseTest {

    @Before
    public void SetUp() throws SQLException {
        super.SetUp();
        executeStatement("CREATE table UserLinks (" +
                "    ShortLinkId char(8)," +
                "    OriginalUri varchar(2048)," +
                "    UserId char(36)" +
                ");");
        repository = new UserLinksSqlBasedRepositoryImpl(connectionSource);
    }

    @After
    public void TearDown() throws SQLException {
       executeStatement("DROP table UserLinks");
    }

    @Test
    public void addLinkTest() throws URISyntaxException, SQLException {
        repository.add(new UserLink(originalUri, shortLinkId, userId));

        List<UserLink> allLinks = new GetAllUserLinksSqlQuery(connectionSource).execute();
        assertEquals(1, allLinks.size());
        UserLink addedLink = allLinks.get(0);
        CheckUserLink(addedLink, originalUri, shortLinkId, userId);

        System.out.print(UUID.randomUUID());
    }

    @Test
    public void getOriginalUri_WhenItExistsTest() throws SQLException {
        repository.add(new UserLink(originalUri, shortLinkId, userId));

        String original = repository.getOriginalUri(shortLinkId);

        assertEquals(originalUri, original);
    }

    @Test
    public void getOriginalUri_WhenItDoesNotExistsTest() throws SQLException {
        repository.add(new UserLink(originalUri, shortLinkId, userId));

        String original = repository.getOriginalUri("not_existing");

        assertNull(original);
    }

    private void CheckUserLink(UserLink addedLink, String expectedOriginalUri1, String expectedShortLinkId, String expectedUserId) {
        assertEquals(expectedOriginalUri1, addedLink.getOriginalUri());
        assertEquals(expectedShortLinkId, addedLink.getShortLinkId());
        assertEquals(expectedUserId, addedLink.getUserId());
    }

    private static final String originalUri = "https%3A%2F%2Fgoogle.ru%2Fsearch";
    private static final String shortLinkId = "short_01";
    private static final String userId = "user_id";
    protected UserLinksRepository repository;
}

