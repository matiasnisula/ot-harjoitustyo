
package opintopaivakirjasovellus.domain;
import java.sql.*;
import opintopaivakirjasovellus.dao.*;

public class AppService {
    private TaskDao taskDao;
    private UserDao userDao;
    private User loggedIn;   
    /**
    * Sovelluslogiikasta vastaava luokka.
    */
    /**
    * Luokan konstruktori.
    * @param taskDao suoritusten tallentamisesta vastaava luokka
    * @param userDao käyttäjien tallentamisesta vastaava luokka
    */
    public AppService(TaskDao taskDao, UserDao userDao) {
        this.taskDao = taskDao;
        this.userDao = userDao;
    }
    /**
    * Luo uuden suorituksen ja tallentaa sen.
    * @param taskName suorituksen nimi
    * @throws Exception    
    * @return true/false riippuen onnistuiko tallennus
    */
    public boolean createTask(String taskName) throws Exception {
        if (loggedIn == null) {
            System.out.println("Kirjaudu siään");
            return false;
        }
        Task task = new Task(taskName, loggedIn);
        try {
            taskDao.create(task, loggedIn);
        } catch (SQLException e) {
            System.out.println("Virhe AppService, metodi createCourse");
            return false;
        }
        return true;
    }
    /**
    * Luo uuden käyttäjän.
    * @param name käyttäjän nimi
    * @param username käyttäjänimi
    * @throws Exception    
    * @return true, jos onnistui, muuten false
    */
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
    /**
    * Kirjautuminen sisään käyttäjänimellä.
    * @param username käyttäjänimi, jolla halutaan kirjautua
    * @throws Exception    
    * @return true, kirjautuminen onnistui, muuten false
    */
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
    /**
    * Näyttää kirjautuneen käyttäjän tehtävät.
    * @throws Exception    
    * 
    */
    public void showTasks() throws Exception {
        if (loggedIn == null) {
            System.out.println("Log in first");
            return;
        }
        for (Task t: taskDao.getAll(loggedIn)) {
            System.out.println(t.toString());
        }
    }
    /**
    * Lisää käyttäjän ilmoittaman ajan tehtävän kokonaisaikaan.
    * @param taskName tehtävän nimi
    * @param time lisättävä aika
    * @throws Exception    
    */
    public void addTimeUsed(String taskName, int time) throws Exception {
        if (loggedIn == null) {
            System.out.println("Log in first");
            return;
        }
        try {
            taskDao.addTimeUsed(taskDao.getTask(taskName, loggedIn), loggedIn, time); 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        
    }
    /**
    * Palauttaa sisäänkirjautuneen käyttäjän.
    * @return User
    */
    public User getLoggedUser() {
        return this.loggedIn;
    }
    /**
    * Kirjautuu ulos nykyiseltä käyttäjätililtä.
    */
    public void logOut() {
        this.loggedIn = null;
    }
}
