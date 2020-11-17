
package opintopaivakirjasovellus.dao;

import opintopaivakirjasovellus.domain.User;

public interface UserDao {
    void addUser(User user) throws Exception;
    User findByUsername(String username) throws Exception;
    boolean usernameExists(String username) throws Exception;
    int getUserId(String username) throws Exception;
      
}
