
package opintopaivakirjasovellus.dao;

import java.util.ArrayList;
import java.util.List;
import opintopaivakirjasovellus.domain.Task;
import opintopaivakirjasovellus.domain.User;

public interface TaskDao {
    void create(Task task, String username) throws Exception;
    void setDone(String name) throws Exception;
    List<Task> getAll(User user) throws Exception;
    
}
