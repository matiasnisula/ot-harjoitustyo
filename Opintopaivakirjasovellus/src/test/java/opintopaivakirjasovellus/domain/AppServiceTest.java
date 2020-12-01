/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opintopaivakirjasovellus.domain;

import java.sql.SQLException;
import opintopaivakirjasovellus.dao.SqliteTaskDao;
import opintopaivakirjasovellus.dao.SqliteUserDao;
import opintopaivakirjasovellus.dao.TaskDao;
import opintopaivakirjasovellus.dao.UserDao;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AppServiceTest {
    TaskDao taskDao;
    UserDao userDao;
    User loggedIn;
    AppService service;
    String url = "jdbc:sqlite:testi.db";
    
    public AppServiceTest() throws SQLException {
       
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws SQLException {
        userDao = new SqliteUserDao(url);
        taskDao = new SqliteTaskDao(url, userDao);
        service = new AppService(taskDao, userDao);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void loggingInWorks() throws Exception {
        service.createUser("Matias", "MN");
        assertEquals(true, service.login("MN"));
    }
    @Test
    public void creatingUserWorksWhenNotLoggedIn() throws Exception {
        service.createUser("Kalle", "K");  
        assertEquals(true,userDao.usernameExists("K"));
    }
    @Test
    public void creatingTaskWorksWhenLoggedIn() throws Exception {
        service.createUser("Ma", "M");
        service.login("M");
        assertEquals(true, service.createTask("Testaus"));
    }
    
    
}
