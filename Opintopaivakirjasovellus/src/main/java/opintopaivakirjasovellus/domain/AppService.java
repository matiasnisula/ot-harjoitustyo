
package opintopaivakirjasovellus.domain;
import java.sql.*;
import opintopaivakirjasovellus.dao.*;

public class AppService {
    private TaskDao taskDao;
    private UserDao userDao;
    private User loggedIn;
    
    public AppService(TaskDao taskDao, UserDao userDao) {
        this.taskDao = taskDao;
        this.userDao = userDao;
    }
    public boolean createTask(String taskName) throws Exception {
        if (loggedIn == null) {
            System.out.println("Kirjaudu siään");
            return false;
        }
        Task task = new Task(taskName);
        try {
            taskDao.create(task, loggedIn.getUsername());
        } catch (SQLException e) {
            System.out.println("Virhe AppService, metodi createCourse");
            return false;
        }
        return true;
    }
    public boolean createUser(String name, String username) throws Exception {
        boolean created = false;
        if (userDao.usernameExists(username)) {
            System.out.println("Username already exists");
        } else {
            userDao.addUser(new User(name, username));
            created = true;
        }
        return created;
    }
    public boolean login(String username) throws Exception {
        if (loggedIn != null) {
            System.out.println("You are already logged in");
            return false;
        }
        try {
            if (userDao.usernameExists(username)) {
                loggedIn = userDao.findByUsername(username);
                System.out.println("You are now logged in");
            } else {
                System.out.println("No match for username: " + username);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Virhe kirjautuessa sisään");
            return false;
        }
        return true;
    }
    public void showTasks() throws Exception {
        if (loggedIn == null) {
            System.out.println("Log in first");
            return;
        }
        for (Task t: taskDao.getAll(loggedIn)) {
            System.out.println(t.toString());
        }
    }
    public void addTimeUsed(String taskName, int time) throws Exception {
        if (loggedIn == null) {
            System.out.println("Log in first");
            return;
        }
        try {
           taskDao.addTimeUsed(taskDao.getTask(taskName, loggedIn), loggedIn, time); 
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        
    }
    
    public User getLoggedUser() {
        return this.loggedIn;
    }
    public void logOut() {
        this.loggedIn = null;
        System.out.println("Logged out succesfully!");
    }
}
