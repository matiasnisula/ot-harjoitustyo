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
    UserDao userDao;
    TaskDao taskDao;
    
    public SqliteTaskDaoTest() throws SQLException {
        
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
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void addingTaskToDatabaseWorks() throws Exception {
        User user = new User("Pekka", "Pekka2");
        userDao.addUser(user);
        Task task = new Task("Kurssi", user, "10.09.2020");
        taskDao.create(task, user);
        assertEquals("Kurssi", taskDao.getTask("Kurssi", user).getName());
    }
}
