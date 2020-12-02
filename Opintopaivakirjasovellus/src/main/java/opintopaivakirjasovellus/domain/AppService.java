
package opintopaivakirjasovellus.domain;
import java.sql.*;
import java.util.Calendar;
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
        boolean created = false;
        if (loggedIn == null) {
            System.out.println("Kirjaudu sisään");
            return false;
        }
        Task task = new Task(taskName, loggedIn, getTimestamp()); 
        try {
            taskDao.create(task, loggedIn);
            created = true;
        } catch (SQLException e) {
            System.out.println("Virhe AppService, metodi createCourse");
        }
        return created;
    }
    /**
    * Luo uuden käyttäjän.
    * @param name käyttäjän nimi
    * @param username käyttäjänimi
    * @throws Exception    
    * @return true, jos onnistui, muuten false
    */
    public boolean createUser(String name, String username) throws Exception {
        if (loggedIn != null) {
            return false;
        }
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
        } else if (taskDao.getTask(taskName, loggedIn) == null) {
            System.out.println("Tehtävää ei löydy");
        }
        try {
            taskDao.addTimeUsed(taskDao.getTask(taskName, loggedIn), loggedIn, time); 
            System.out.println("Tehtävään käytetty aika päivitetty");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        
    }
    /**
    * Palauttaa sisäänkirjautuneen käyttäjän.
    * @param taskName tehtävän nimi
    * @throws Exception poikkeus
    * @return timeUsed
    */
    public int getTimeUsed(String taskName) throws Exception {
        int timeUsed = -1;
        if (loggedIn == null) {
            System.out.println("Log in first");
            return 0;
        } else if (taskDao.getTask(taskName, loggedIn) == null) {
            System.out.println("Tehtävää ei löydy");
        }
        try {
            timeUsed = taskDao.getTimeUsed(taskDao.getTask(taskName, loggedIn), loggedIn); 
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return timeUsed;
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
    
    private String getTimestamp() {
        Calendar calendar = Calendar.getInstance();
        String day = String.valueOf(calendar.get(Calendar.DATE));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        
        return day + "." + month + "." + year;   
    }
}
