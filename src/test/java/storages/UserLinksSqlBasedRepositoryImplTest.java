package storages;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import storages.connection_sources.ConnectionSource;
import storages.connection_sources.SimpleConnectionSourceImpl;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class UserLinksSqlBasedRepositoryImplTest {

    @Before
    public void SetUp() throws SQLException {
        connectionSource = new SimpleConnectionSourceImpl(USER, PASS, DB_URL);
        executeStatement("CREATE table UserLinks" +
                "(" +
                "    ShortLinkId char(8)," +
                "    OriginalUri varchar(2048)," +
                "    UserId int" +
                ");");
    }

    @After
    public void TearDown() throws SQLException {
       executeStatement("DROP table UserLinks");
    }

    @Test
    public void addUserLinkTest() throws Exception {
        UserLinksRepository repository = new UserLinksSqlBasedRepositoryImpl(connectionSource);

        repository.add(new UserLink("http://original/com", "short_01", 1));

        String original = repository.getOriginalUri("short_01");
        assertEquals("http://original/com", original);
    }

    private void executeStatement(String statement) throws SQLException {
        Connection conn = connectionSource.getConnection();
        conn.createStatement().execute(statement);
        conn.close();
    }

    private static final String USER = "root";
    private static final String PASS = "root";
    private static final String DB_URL = "jdbc:h2:mem:TestDB;DB_CLOSE_DELAY=-1";
    private ConnectionSource connectionSource;
}