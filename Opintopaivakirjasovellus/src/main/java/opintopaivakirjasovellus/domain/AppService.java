
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
            return false;
        }
        if (taskDao.getTask(taskName, loggedIn) != null) {
            return false;
        }
        taskName = taskName.trim();
        if (taskName.equals("")) {
            return false;
        }
        Task task = new Task(taskName, loggedIn, getTimestamp()); 
        try {
            taskDao.saveTask(task, loggedIn);
            created = true;
        } catch (SQLException e) {
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
        name = name.trim();
        username = username.trim();
        if (name.equals("") || username.equals("")) {
            return false;
        }
        boolean created = false;
        if (userDao.usernameExists(username)) {
            return false;
        } else {
            userDao.saveUser(new User(name, username));
            
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
            return false;
        }
        try {
            if (userDao.usernameExists(username)) {
                loggedIn = userDao.findByUsername(username);
            } else {
                return false;
            }
        } catch (Exception e) {
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
            return;
        } else if (taskDao.getTask(taskName, loggedIn) == null) {
            return;
        } else if (time <= 0) {
            return;
        }
        try {  
            taskDao.addTimeUsed(taskDao.getTask(taskName, loggedIn), loggedIn, time, getTimestamp()); 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        
    }
    /**
    * Merkkaa tehtävän tehdyksi.
    * @param taskName tehtävän nimi
    * @throws Exception poikkeus  
    */
    public void markDoneTask(String taskName) throws Exception {
        if (loggedIn == null) {
            return;
        }
        taskName = taskName.trim();
        try {
            Task task = taskDao.getTask(taskName, loggedIn);
            taskDao.setDone(task, loggedIn);
        } catch (Exception e) {
            
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
            return 0;
        } else if (taskDao.getTask(taskName, loggedIn) == null) {
            return 0;
        }
        try {
            timeUsed = taskDao.getTimeUsedOneTask(taskDao.getTask(taskName, loggedIn), loggedIn); 
            
        } catch (Exception e) {
            
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
            return 0;
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
        taskName = taskName.trim();
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
