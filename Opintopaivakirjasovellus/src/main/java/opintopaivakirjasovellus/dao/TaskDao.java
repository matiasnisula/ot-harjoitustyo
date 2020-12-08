
package opintopaivakirjasovellus.dao;

import java.util.ArrayList;
import java.util.List;
import opintopaivakirjasovellus.domain.Task;
import opintopaivakirjasovellus.domain.User;
/**
    * Rajapinta suorituksiin liittyvän tiedon tallentamiseen.
    */
public interface TaskDao {
    /**
     * Lisää tehtävän tietokantaan.
    * @param task tehtävä
    * @param user käyttäjä
    * @throws Exception   
    */
    void create(Task task, User user) throws Exception;
    /**
    * Asettaa tehtävän tehdyksi.
    * @param task tehtävä
    * @param user käyttäjä
    * @throws Exception   
    */
    void setDone(Task task, User user) throws Exception;
    /**
    * Paluttaa listan kirjautuneen käyttäjän kaikista tehtävistä.
    * @param user käyttäjä
    * @throws Exception   
    * @return lista tehtävistä
    */
    List<Task> getAll(User user) throws Exception;
    /**
    * Päivittää tiettyyn tehtävään käytetyn kokonaisajan.
    * @param task tehtävä jonka kokonaisaika päivitetään
    * @param user kirjautunut käyttäjä
    * @param time käytetty aika, joka lisätään kokonaisaikaan
    * @param date päivämäärä
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
    * @param task tehtävä
    * @param user käyttäjä
    * @throws Exception   
    * @return käytetty aika
    */
    int getTimeUsedOneTask(Task task, User user) throws Exception;
    /**
    * Paluttaa käyttäjän kaikkiin tehtäviin käyttämän ajan.
    * @param user käyttäjä
    * @throws Exception poikkeus
    * @return aika yhteensä
    */
    int getTimeUsedAllTasks(User user) throws Exception;
    /**
    * Palauttaa listan yhden tehtävän suorituksista.
    * @param taskName tehtävän nimi
    * @param user käyttäjä
    * @throws Exception poikkeus
    * @return lista tehtäväolioita
    */
    List<Task> getHistoryOneTask(String taskName, User user) throws Exception;
    /**
    * Poistaa tehtävän tietokannasta.
    * @param taskName tehtävän nimi
    * @param user käyttäjä
    * @throws Exception poikkeus
    */
    void deleteTask(String taskName, User user) throws Exception;
    
    
}
