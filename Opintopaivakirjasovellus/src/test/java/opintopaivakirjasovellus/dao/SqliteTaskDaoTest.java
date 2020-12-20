
package opintopaivakirjasovellus.dao;

import java.io.FileInputStream;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Properties;
import opintopaivakirjasovellus.domain.Task;
import opintopaivakirjasovellus.domain.User;

/**
 * Tehtäviin liittyvistä tietokantatoiminnoista vastaavan luokan testiluokka.
 *
 */

public class SqliteTaskDaoTest {
    String url;
    SqliteUserDao userDao;
    SqliteTaskDao taskDao;
    Task task;
    Task task1;
    Task task2;
    Task task3;
    User user;
    User user1;
    
    public SqliteTaskDaoTest() throws Exception {
        try (InputStream input = new FileInputStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            url = properties.getProperty("db.testiUrl");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        userDao = new SqliteUserDao(url);
        taskDao = new SqliteTaskDao(url, userDao);
    }
    
    
    
    @Before
    public void setUp() throws Exception {
        userDao.emptyTables();
        taskDao.emptyTables();
        user = new User("Pekka", "Pekka1");
        user1 = new User("Matias", "MN");
        userDao.saveUser(user);
        userDao.saveUser(user1);
        task = new Task("Kurssi1", user, "7.12.2020");
        task1 = new Task("Kurssi2", user, "8.12.2020");
        task2 = new Task("Tira", user1, "10.10.1990");
        task3 = new Task("Ohjelmointi", user1, "11.10.1990");
        
    }

    @Test
    public void getTimeUsedAllTasksWorks() throws Exception {
        task.addTime(4);
        task1.addTime(3);
        taskDao.saveTask(task, user);
        taskDao.saveTask(task1, user);
        assertEquals(7, taskDao.getTimeUsedAllTasks(user));
    }
    @Test
    public void getTimeUsedAllTasksReturn0IfNoTasksAdded() throws Exception {
        assertEquals(0, taskDao.getTimeUsedAllTasks(user));
    }
    @Test
    public void getTimeUsedOneTaskReturn0IfNoTasksAdded() throws Exception {
        assertEquals(0, taskDao.getTimeUsedOneTask(task, user));
    }
    @Test
    public void getTimeUsedOneTaskWorks() throws Exception {
        taskDao.saveTask(task, user);
        taskDao.saveTask(task1, user);
        taskDao.saveTask(task2, user);
        taskDao.saveTask(task3, user1);
        taskDao.saveTask(task1, user1);
        taskDao.addTimeUsed(task, user, 2, "10.10.2000");
        taskDao.addTimeUsed(task1, user, 4, "10.10.2000");
        taskDao.addTimeUsed(task, user, 3, "10.10.2000");
        taskDao.addTimeUsed(task3, user1, 3, "11.11.2001");
        assertEquals(5, taskDao.getTimeUsedOneTask(task, user));
        
    }
    @Test
    public void addingTimeThrowsExceptionIfTaskDoesntExists() {
        try {
            taskDao.addTimeUsed(task, user, 2, "10.12.2002");
        } catch (Exception e) {
            assertEquals(true, e.getMessage().equals("Ajan lisäys epäonnistui"));
        }
    }
    @Test
    public void addingTimeThrowsExceptionIfUserDoesntExists() {
        try {
            taskDao.saveTask(task, user);
            User testUser = new User("M", "N");
            taskDao.addTimeUsed(task, testUser, 2, "10.12.2002");
        } catch (Exception e) {
            assertEquals(true, e.getMessage().equals("Ajan lisäys epäonnistui"));
        }
    }
    
    @Test
    public void addingTimeToTaskWorks() throws Exception {
        taskDao.saveTask(task, user);
        taskDao.addTimeUsed(task, user, 4, "10.09.2020");
        assertEquals(4, taskDao.getTimeUsedOneTask(task, user));
        
    }
    @Test
    public void addingTimeToTaskDoesntAddNegativeInteger() throws Exception {
        taskDao.saveTask(task, user);
        taskDao.addTimeUsed(task, user, 4, "10.09.2020");
        taskDao.addTimeUsed(task, user, -2, "10.09.2020");
        assertEquals(4, taskDao.getTimeUsedOneTask(task, user));
        
    }
    @Test
    public void getAllTasksForOneUser() throws Exception {
        taskDao.saveTask(task, user);
        taskDao.saveTask(task1, user);
        List<Task> list = taskDao.getAll(user);
        assertEquals(true, list.contains(task) && list.contains(task1));
    }
    @Test
    public void addingTaskToDatabaseWorks() throws Exception {
        taskDao.saveTask(task, user);
        assertEquals("Kurssi1", taskDao.getTask("Kurssi1", user).getName());
    }

    @Test
    public void noTwoSameTasksForOneUser() throws Exception {
        taskDao.saveTask(task, user);
        taskDao.saveTask(task, user);
        List<Task> tasks = taskDao.getAll(user);
        assertEquals(1, tasks.size());
    }
    @Test
    public void getTaskFromDatabaseWorks() throws Exception {
        taskDao.saveTask(task, user);
        assertEquals("Kurssi1", taskDao.getTask("Kurssi1", user).getName());
    }
    @Test
    public void getTaskReturnsNullIfTaskDoesntExists() throws Exception {
        assertEquals(null, taskDao.getTask("Kurssi1", user));
    }
    @Test
    public void setTaskDoneWorks() throws Exception {
        taskDao.saveTask(task, user);
        taskDao.setDone(task, user);
        assertEquals(true, taskDao.getTask(task.getName(), user).getDone());
    }
    @Test
    public void setTaskThrowsExceptionIfTaskDoesntExists() {
        try {
            taskDao.setDone(task, user);
        } catch (Exception e) {
            assertEquals(true, e.getMessage().equals("Tehtävää ei löydy"));
        }
            
    }
    @Test
    public void getHistoryOneTaskWorks() throws Exception {
        taskDao.saveTask(task, user);
        taskDao.addTimeUsed(task, user, 2, "10.10.1990");
        taskDao.addTimeUsed(task, user, 4, "11.10.1990");
        List<Task> history = taskDao.getHistoryOneTask(task.getName(), user);
        assertEquals(2, history.size());
    }
    
    @Test
    public void deleteTaskWorks() throws Exception {
        taskDao.saveTask(task, user);
        taskDao.saveTask(task1, user);
        taskDao.deleteTask(task.getName(), user);
        List<Task> tasks = taskDao.getAll(user);
        assertEquals(1, tasks.size());
    }
    @Test
    public void deleteTaskThrowsExceptionIfTaskDoesntExists()  {
        try {
            taskDao.saveTask(task, user);
            taskDao.saveTask(task1, user);
            taskDao.deleteTask("Tehtävä", user);
        } catch (Exception e) {
            assertEquals(true, e.getMessage().equals("Tehtävän poistaminen epäonnistui"));
        }
    }
    
    
}
