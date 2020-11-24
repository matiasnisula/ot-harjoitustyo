
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
    * @param username kenen tehtäväolio
    * @throws Exception   
    */
    void create(Task task, User user) throws Exception;
    /**
    * Asettaa tehtävän tehdyksi.
    * @param name Tehtävän nimi
    * @throws Exception   
    */
    void setDone(String Taskname) throws Exception;
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
    void addTimeUsed(Task task, User user, int time) throws Exception;
    /**
    * Palauttaa tiettyyn käyttäjään liittyvän tehtäväolion.
    * @param taskName tehtävän nimi
    * @param user käyttäjä
    * @throws Exception   
    * @return Task-olio
    */
    Task getTask(String taskName, User user) throws Exception;
    
}
