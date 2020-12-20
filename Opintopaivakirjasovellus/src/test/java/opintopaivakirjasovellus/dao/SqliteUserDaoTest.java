
package opintopaivakirjasovellus.dao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import opintopaivakirjasovellus.domain.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
/* Käyttäjiin liittyvien tietokantatoimintojen testiluokka.
* 
 */

public class SqliteUserDaoTest {
    String url;
    SqliteUserDao userDao;
    public SqliteUserDaoTest() throws SQLException {
        try (InputStream input = new FileInputStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            url = properties.getProperty("db.testiUrl");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        userDao = new SqliteUserDao(url);
    }
    
    @Before
    public void setUp() throws SQLException {
        userDao.emptyTables();
    }
    

    @Test
    public void addUserToDatabaseWorks() throws SQLException, Exception {
        userDao.saveUser(new User("Pekka", "pekka8"));
        assertEquals(true, userDao.usernameExists("pekka8"));
    }
    @Test
    public void addUserThrowsExpectionWhenUserAddedTwice() {
        try {
            userDao.saveUser(new User("Pekka", "pekka8"));
            userDao.saveUser(new User("Pekka", "pekka8"));
        } catch (SQLException e) {
            assertEquals(true, e.getMessage().equals("Käyttäjän lisäys epäonnistui"));
        }
    }
    @Test
    public void getAllReturnsAllUsers() throws SQLException {
        userDao.saveUser(new User("Pekka", "pekka8"));
        userDao.saveUser(new User("Pekka", "pekka9"));
        userDao.saveUser(new User("Pekka", "pekka4"));
        List<User> users = userDao.getAll();
        assertEquals(3, users.size());
    }
    @Test
    public void usernameExistsReturnFalseWhenDoesntExists() throws SQLException {
        userDao.saveUser(new User("Pekka", "pekka8"));
        assertEquals(false, userDao.usernameExists("ma"));
    }
    @Test
    public void getUserIdWorksWhenUserExists() throws SQLException {
        userDao.saveUser(new User("Pekka", "pekka8"));
        userDao.saveUser(new User("Pekka", "pekka4"));
        assertEquals(1, userDao.getUserId("pekka8"));
    }
    @Test
    public void getUserIdReturns0WhenTableIsEmpty() throws SQLException {
        assertEquals(0, userDao.getUserId("pekka8"));
    }
    @Test
    public void findByUsernameWorks() throws SQLException {
        userDao.saveUser(new User("Pekka", "pekka8"));
        userDao.saveUser(new User("Pekka", "pekka4"));
        userDao.saveUser(new User("M", "N"));
        userDao.saveUser(new User("S", "P"));
        assertEquals(true, userDao.findByUsername("pekka4").getName().equals("Pekka"));
    }
    @Test 
    public void findByUserNameReturnsNullIfUserDoesntExists() throws SQLException {
        assertEquals(null, userDao.findByUsername("M"));
    }
    
}