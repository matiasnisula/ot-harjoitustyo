/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opintopaivakirjasovellus.domain;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import opintopaivakirjasovellus.dao.SqliteTaskDao;
import opintopaivakirjasovellus.dao.SqliteUserDao;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author matnisul
 */
public class AppServiceTaskTest {
    SqliteTaskDao taskDao;
    SqliteUserDao userDao;
    User loggedIn;
    AppService service;
    String url;
    
    public AppServiceTaskTest() throws SQLException {
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
    public void setUp() throws Exception {
        userDao.emptyTables();
        taskDao.emptyTables();
        service.createUser("Ma", "M");
        service.login("M");
    }


    @Test
    public void creatingTaskWorksWhenLoggedIn() throws Exception {
        assertEquals(true, service.createTask("Testaus"));
    }
    @Test
    public void creatingTaskDoesntWorkWhenNotLoggedIn() throws Exception {
        service.logOut();
        assertEquals(false, service.createTask("Testaus"));
    }
    @Test
    public void creatingTaskDoesntWorkWhenTaskAlreadyExists() throws Exception {
        service.createTask("Testaus");
        assertEquals(false, service.createTask("Testaus"));
    }
    @Test
    public void creatingTaskDoesntWorkWhenTasknameIsEmpty() throws Exception {
        assertEquals(false, service.createTask(" "));
    }
    @Test
    public void addingTimeToTaskWorks() throws Exception {
        service.createTask("Testaus");
        service.addTimeUsed("Testaus", 2);
        service.createTask("Ohjelmointi");
        service.addTimeUsed("Ohjelmointi", 4);
        assertEquals(2, service.getTimeUsedOneTask("Testaus"));   
    }
    @Test
    public void addingTimeToTaskDoesntAddNegativeInteger() throws Exception {
        service.createTask("Testaus");
        service.addTimeUsed("Testaus", 2);
        service.addTimeUsed("Testaus", -2);
        assertEquals(2, service.getTimeUsedOneTask("Testaus"));   
    }
    @Test
    public void addingTimeReturn0WhenNotLoggedIn() throws Exception {
        service.createTask("Testaus");
        service.addTimeUsed("Testaus", 2);
        service.addTimeUsed("Testaus", 2);
        service.logOut();
        assertEquals(0, service.getTimeUsedOneTask("Testaus"));   
    }
    
    @Test
    public void getTimeUsedAllTasksWorks() throws Exception {
        service.createTask("Testaus");
        service.addTimeUsed("Testaus", 2);
        service.createTask("Ohjelmointi");
        service.addTimeUsed("Ohjelmointi", 6);
        assertEquals(8, service.getTimeUsedAllTasks());
    }
    @Test
    public void getAllTaskWorks() throws Exception {
        service.createTask("Testaus");
        service.createTask("Ohjelmointi");
        List<Task> tasks = service.getAll();
        assertEquals(2, tasks.size());
    }
    @Test
    public void getAllTaskWorksWhenNoTasks() throws Exception {
        List<Task> tasks = service.getAll();
        assertEquals(0, tasks.size());
    }
    @Test
    public void getAllTaskWorksWhenNotLoggedIn() throws Exception {
        service.createTask("Testi");
        service.logOut();
        List<Task> tasks = service.getAll();
        assertEquals(0, tasks.size());
    }
    @Test
    public void getHistoryOneTaskWorks() throws Exception {
        service.createTask("Testaus");
        service.createTask("Ohjelmointi");
        service.addTimeUsed("Ohjelmointi", 2);
        service.addTimeUsed("Ohjelmointi", 3);
        service.addTimeUsed("Ohjelmointi", 4);
        List<Task> tasks = service.getHistoryOneTask("Ohjelmointi");
        assertEquals(3, tasks.size());   
    }
    @Test
    public void deleteTaskWorks() throws Exception {
        service.createTask("Testaus");
        service.createTask("Ohjelmointi");
        service.deleteTask("Ohjelmointi");
        assertEquals(null, service.getTask("Ohjelmointi"));
    }
    @Test
    public void getTaskWorksWhenTaskExists() throws Exception {
        service.createTask("Testaus");
        assertEquals("Testaus", service.getTask("Testaus").getName());
    }
    @Test
    public void getTaskWorksWhenTaskDoesntExists() throws Exception {
        service.createTask("Testaus");
        assertEquals(null, service.getTask("Ohjelmointi"));
    }
    
}
