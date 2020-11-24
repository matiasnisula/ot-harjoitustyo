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
    UserDao userDao = new SqliteUserDao(url);
    
    public SqliteUserDaoTest() throws SQLException {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws SQLException {
        
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