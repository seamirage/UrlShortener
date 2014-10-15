package storages.repositories;

import org.junit.Before;
import storages.connection_sources.ConnectionSource;
import storages.connection_sources.SimpleConnectionSourceImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class SqlRepositoriesBaseTest {

    @Before
    public void SetUp() throws SQLException {
        connectionSource = new SimpleConnectionSourceImpl("username", "pwd", dbUrl);
    }

    protected void executeStatement(String statement) throws SQLException {
        Connection conn = connectionSource.getConnection();
        conn.createStatement().execute(statement);
        conn.close();
    }

    private static final String dbUrl = "jdbc:h2:mem:TestDB;DB_CLOSE_DELAY=-1";
    protected ConnectionSource connectionSource;
}
