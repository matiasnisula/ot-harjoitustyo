
package opintopaivakirjasovellus.dao;

import opintopaivakirjasovellus.domain.User;

public interface UserDao {
    /**
    * Rajapinta käyttäjiin liittyvän tiedon tallentamiseen.
    */
    
    /**
     * Lisää käyttäjän tietokantaan.
    * @param user käyttäjäolio, joka lisätään tietokantaan
    * @throws Exception   
    */
    void saveUser(User user) throws Exception;
    /**
    * Etsii käyttäjän käyttäjänimen peruteella.
    * @param username tämän avulla etsitään käyttäjä
    * @throws Exception   
    * @return Käyttäjäolio, jos löytyi, muuten null
    */
    User findByUsername(String username) throws Exception;
    /**
     * Kertoo löytyykö käyttäjänimi tietokannasta.
    * @param username käyttäjänimi
    * @throws Exception   
    * @return true, jos käyttäjänimi löytyy tietokannasta, muuten false
    */
    boolean usernameExists(String username) throws Exception;
    /**
     * Palauttaa Users tietokantataulussa olevan id:n.
    * @param username käyttäjänimi
    * @throws Exception   
    * @return tietokantataulussa Users oleva id
    */
    int getUserId(String username) throws Exception;
      
}
