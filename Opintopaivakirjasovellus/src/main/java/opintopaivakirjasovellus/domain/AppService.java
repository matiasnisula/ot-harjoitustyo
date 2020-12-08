
package opintopaivakirjasovellus.domain;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
            return;
        } else if (time < 0) {
            return;
        }
        try {
            
            taskDao.addTimeUsed(taskDao.getTask(taskName, loggedIn), loggedIn, time, getTimestamp()); 
            System.out.println("Tehtävään käytetty aika päivitetty");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        
    }
    /**
    * Palauttaa sisäänkirjautuneen käyttäjän tehtävät listana.
    * @throws Exception poikkeus
    * @return tasks listana
    */
    public List<Task> getAll() throws Exception {
        List<Task> tasks = new ArrayList<>();
        if (loggedIn == null) {
            return tasks;
        }
        try {
            tasks = taskDao.getAll(loggedIn);
        } catch (Exception e) {
            
        }
        return tasks;
    }
    /**
    * Palauttaa tehtäväolion.
    * @param taskName tehtävän nimi
    * @throws Exception poikkeus
    * @return tehtäväolio
    */
    public Task getTask(String taskName) throws Exception {
        Task task = null;
        try {
            task = taskDao.getTask(taskName, loggedIn);
        } catch (Exception e) {
            
        }
        return task;
    }
    

    /**
    * Palauttaa käytetytyn kokonaisajan tiettyyn tehtävään.
    * @param taskName tehtävän nimi
    * @throws Exception poikkeus
    * @return timeUsed
    */
    public int getTimeUsedOneTask(String taskName) throws Exception {
        int timeUsed = 0;
        if (loggedIn == null) {
            System.out.println("Log in first");
            return 0;
        } else if (taskDao.getTask(taskName, loggedIn) == null) {
            System.out.println("Tehtävää ei löydy");
        }
        try {
            timeUsed = taskDao.getTimeUsedOneTask(taskDao.getTask(taskName, loggedIn), loggedIn); 
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return timeUsed;
    }
    /**
    *Palauttaa sisäänkirjautuneen käyttäjän kaikkiin tehtäviin käyttämän ajan.
    * @throws Exception poikkeus
    * @return aika yhteensä
    */
    public int getTimeUsedAllTasks() throws Exception {
        int result = 0;
        if (loggedIn == null) {
            return -1;
        }
        try {
            result = taskDao.getTimeUsedAllTasks(loggedIn);
        } catch  (Exception e) {
            
        }
        return result;
        
    }
    /**
    *Poistaa tehtävän.
    * @param taskName tehtävän nimi
    * @throws Exception poikkeus
    */
    public void deleteTask(String taskName) throws Exception {
        if (loggedIn == null) {
            return;
        }
        try {
            taskDao.deleteTask(taskName, loggedIn);
        } catch (Exception e) {
            
        }
    }
    /**
    *Palauttaa yhden tehtävän suoritukset listana.
    * @param taskName tehtävän nimi
    * @throws Exception poikkeus
    * @return lista tehtäväolioita
    */
    public List<Task> getHistoryOneTask(String taskName) throws Exception {
        List<Task> tasks = new ArrayList<>();
        if (loggedIn == null) {
            return tasks;
        }
        try {
            tasks = taskDao.getHistoryOneTask(taskName, loggedIn);
        } catch (Exception e) {
            
        }
        return tasks;
    }
    
    /**
    * Palauttaa sisäänkirjautuneen käyttäjän.
    * @return sisäänkirjautunut käyttäjä
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
    /**
    *Palauttaa tämänhetkisen päivämäärän merkkijonona, joka liitetään jokaiseen tehtäväolioon.
    * @return päivämäärä merkkijonona
    */
    private String getTimestamp() {
        Calendar calendar = Calendar.getInstance();
        String day = String.valueOf(calendar.get(Calendar.DATE));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        
        return day + "." + month + "." + year;   
    }
}
