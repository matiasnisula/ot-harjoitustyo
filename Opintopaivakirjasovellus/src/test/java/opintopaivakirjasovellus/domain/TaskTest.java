
package opintopaivakirjasovellus.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tehtäväolion testiluokka.
 */
public class TaskTest {
    
    @Test
    public void equalsWhenSameTask() {
        User user = new User("M", "N");
        Task task1 = new Task("Ohjelmointi", user, "10.12.2004");
        Task task2 = new Task("Ohjelmointi", user, "10.12.2004");
        assertEquals(true, task1.equals(task2));
    }
    @Test
    public void doesntEqualWhenDifferentTaskName() {
        User user = new User("M", "N");
        Task task1 = new Task("Ohjelmointi2", user, "10.12.2004");
        Task task2 = new Task("Ohjelmointi", user, "10.12.2004");
        assertEquals(false, task1.equals(task2));
    }
    @Test
    public void doesntEqualWhenDifferentUser() {
        User user = new User("M", "N");
        User user1 = new User("M", "M");
        Task task1 = new Task("Ohjelmointi", user, "10.12.2004");
        Task task2 = new Task("Ohjelmointi", user1, "10.12.2004");
        assertEquals(false, task1.equals(task2));
    }
    @Test
    public void doesntEqualWhenDifferentDate() {
        User user = new User("M", "N");
        Task task1 = new Task("Ohjelmointi", user, "10.12.2004");
        Task task2 = new Task("Ohjelmointi", user, "11.12.2004");
        assertEquals(false, task1.equals(task2));
    }
}
