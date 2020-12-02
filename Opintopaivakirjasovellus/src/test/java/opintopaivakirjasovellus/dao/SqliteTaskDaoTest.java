/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opintopaivakirjasovellus.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;
import opintopaivakirjasovellus.domain.Task;
import opintopaivakirjasovellus.domain.User;

public class SqliteTaskDaoTest {
    String url = "jdbc:sqlite:testi.db";
    SqliteUserDao userDao;
    SqliteTaskDao taskDao;
    
    public SqliteTaskDaoTest() throws Exception {
        userDao = new SqliteUserDao(url);
        taskDao = new SqliteTaskDao(url, userDao);
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        userDao.emptyTables();
        taskDao.emptyTables();
        userDao.addUser(new User("Pekka", "Pekka1"));
        userDao.addUser(new User("Matias", "MN"));
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void addingTaskToDatabaseWorks() throws Exception {
        User user = userDao.findByUsername("Pekka1");
        Task task = new Task("Kurssi", user, "10.09.2020");
        taskDao.create(task, user);
        assertEquals("Kurssi", taskDao.getTask("Kurssi", user).getName());
    }
    @Test
    public void addingTimeToTaskWorks() throws Exception {
        User user = new User("M", "N");
        Task task = new Task("Kurssi", user, "10.09.2020");
        userDao.addUser(user);
        taskDao.create(task, user);
        taskDao.addTimeUsed(task, user, 4);
        assertEquals(4, taskDao.getTimeUsed(task, user));
        
    }
    @Test
    public void getAllTasksForOneUser() throws Exception {
        
    }
    @Test
    public void savingTaskToDatabaseWorks() throws Exception {
        User user = new User("M", "N");
        Task task = new Task("Kurssi", user, "10.09.2020");
        taskDao.create(task, user);
        assertEquals("Kurssi", taskDao.getTask("Kurssi", user).getName());
    }
    @Test
    public void getTaskFromDatabaseWorks() throws Exception {
        User user = new User("M", "N");
        Task task = new Task("Kurssi", user, "10.09.2020");
        taskDao.create(task, user);
        assertEquals("Kurssi", taskDao.getTask("Kurssi", user).getName());
    }
}
