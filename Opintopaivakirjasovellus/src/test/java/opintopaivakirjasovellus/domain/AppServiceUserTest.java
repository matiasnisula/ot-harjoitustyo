
package opintopaivakirjasovellus.domain;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import opintopaivakirjasovellus.dao.SqliteTaskDao;
import opintopaivakirjasovellus.dao.SqliteUserDao;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Testaa sovelluslogiikkaluokan AppServicen käyttäjiin liittyviä toimintoja.
 */
public class AppServiceUserTest {
    SqliteUserDao userDao;
    SqliteTaskDao taskDao;
    User loggedIn;
    AppService service;
    String url;
    
    public AppServiceUserTest() throws SQLException {
        try (InputStream input = new FileInputStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            url = properties.getProperty("db.testiUrl");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        userDao = new SqliteUserDao(url);
        taskDao = new SqliteTaskDao(url, userDao);
        service = new AppService(taskDao, userDao);

    }
    
    @Before
    public void setUp() throws SQLException {
        userDao.emptyTables();
    }
    
    @Test
    public void loggingInWorks() throws Exception {
        service.createUser("Matias", "MN");
        assertEquals(true, service.login("MN"));
    }
    @Test
    public void loggingInReturnFalseWhenAlreadyLoggedIn() throws Exception {
        service.createUser("Matias", "MN");
        service.createUser("Matias", "M");
        service.login("MN");
        assertEquals(false, service.login("M"));
    }
    @Test
    public void loggingInReturnFalseWhenUsernameDoesntExists() throws Exception {
        service.createUser("Matias", "MN");
        service.createUser("Matias", "M");
        assertEquals(false, service.login("S"));
    }
    @Test
    public void creatingUserWorksWhenNotLoggedIn() throws Exception {
        service.createUser("Kalle", "K");  
        assertEquals(true,userDao.usernameExists("K"));
    }
    @Test
    public void noTwoSameUsernamesAddedToDatabase() throws Exception {
        service.createUser("Kalle", "K");  
        assertEquals(false, service.createUser("Kalle", "K"));
    }
    @Test
    public void creatingUserDoesntWorkWhenLoggedIn() throws Exception {
        service.createUser("Kalle", "K");
        service.login("K");
        assertEquals(false, service.createUser("Sa", "M"));
    }
    @Test
    public void creatingUserDoesntAddEmptyNameOrUsername() throws Exception {
        assertEquals(false, service.createUser(" ", " "));
    }
    
}
