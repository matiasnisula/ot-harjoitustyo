
package opintopaivakirjasovellus.dao;

import java.util.ArrayList;
import java.util.List;
import opintopaivakirjasovellus.domain.Task;
import opintopaivakirjasovellus.domain.User;

public interface TaskDao {
    /**
    * Rajapinta suorituksiin liittyvän tiedon tallentamiseen.
    */
    
    /**
     * Lisää tehtävän tietokantaan.
    * @param task tallennettava tehtäväolio
    * @param user kenen tehtäväolio
    * @throws Exception   
    */
    void create(Task task, User user) throws Exception;
    /**
    * Asettaa tehtävän tehdyksi.
    * @param task tehtävä, joka asetetaan tehdyksi
    * @param user kenen tehtävä
    * @throws Exception   
    */
    void setDone(Task task, User user) throws Exception;
    /**
    * Paluttaa listan kirjautuneen käyttäjän kaikista tehtävistä.
    * @param user kirjautunut käyttäjä
    * @throws Exception   
    * @return lista tehtävistä
    */
    List<Task> getAll(User user) throws Exception;
    /**
    * Päivittää tiettyyn tehtävään käytetyn kokonaisajan.
    * @param task tehtävä jonka kokonaisaika päivitetään
    * @param user kirjautunut käyttäjä
    * @param time käytetty aika, joka lisätään kokonaisaikaan
    * @throws Exception   
    */
    void addTimeUsed(Task task, User user, int time, String date) throws Exception;
    /**
    * Palauttaa tiettyyn käyttäjään liittyvän tehtäväolion.
    * @param taskName tehtävän nimi
    * @param user käyttäjä
    * @throws Exception   
    * @return Task-olio
    */
    Task getTask(String taskName, User user) throws Exception;
    
    /**
    * Palauttaa tiettyyn käyttäjään liittyvään tehtävään käytetyn ajan.
    * @param task tehtävän
    * @param user käyttäjä
    * @throws Exception   
    * @return käytetty aika
    */
    int getTimeUsedOneTask(Task task, User user) throws Exception;
    
    int getTimeUsedAllTasks(User user) throws Exception;
    
    List<Task> getHistoryOneTask(String taskName, User user) throws Exception;
    
    void deleteTask(String taskName, User user) throws Exception;
    
    
}
