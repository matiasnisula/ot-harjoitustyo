import java.sql.*;
import opintopaivakirjasovellus.dao.*;
import opintopaivakirjasovellus.domain.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SqliteUserDaoTest {
    String url = "jdbc:sqlite:testi.db";
    SqliteUserDao userDao;
    public SqliteUserDaoTest() throws SQLException {
        userDao = new SqliteUserDao(url);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws SQLException {
        userDao.emptyTables();
    }
    
    @After
    public void tearDown() {
    }
    @Test
    public void addUserToDatabaseWorks() throws SQLException, Exception {
        userDao.addUser(new User("Pekka", "pekka8"));
        assertEquals(true, userDao.usernameExists("pekka8"));
    }

    
}