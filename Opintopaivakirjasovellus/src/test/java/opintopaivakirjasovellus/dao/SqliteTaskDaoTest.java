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
import java.util.ArrayList;
import java.util.List;
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
    public void getTimeUsedAllTasksWorks() throws Exception {
        User user = userDao.findByUsername("MN");
        Task task = new Task("Kurssi1", user, "7.12.2020");
        Task task1 = new Task("Kurssi2", user, "8.12.2020");
        task.addTime(4);
        task1.addTime(3);
        taskDao.create(task, user);
        taskDao.create(task1, user);
        assertEquals(7, taskDao.getTimeUsedAllTasks(user));
    }
    @Test
    public void addingTimeToTaskWorks() throws Exception {
        User user = new User("M", "N");
        Task task = new Task("Kurssi", user, "10.09.2020");
        userDao.addUser(user);
        taskDao.create(task, user);
        taskDao.addTimeUsed(task, user, 4, "10.09.2020");
        assertEquals(4, taskDao.getTimeUsedOneTask(task, user));
        
    }
    @Test
    public void getAllTasksForOneUser() throws Exception {
        User user = userDao.findByUsername("MN");
        Task task = new Task("Kurssi1", user, "7.12.2020");
        Task task1 = new Task("Kurssi2", user, "8.12.2020");
        taskDao.create(task, user);
        taskDao.create(task1, user);
        List<Task> list = taskDao.getAll(user);
        assertEquals(true, list.contains(task) && list.contains(task1));
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
