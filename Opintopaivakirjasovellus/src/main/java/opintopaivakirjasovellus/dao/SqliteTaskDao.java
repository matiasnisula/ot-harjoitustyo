
package opintopaivakirjasovellus.dao;
import opintopaivakirjasovellus.domain.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteTaskDao implements TaskDao {
    String url;
    UserDao userDao;
    /**
    * Suoritusten tietokantatoiminnoista vastaava luokka.
    */
    
    /**
    * Luokan konstruktori.
    *
    * @param   url tietokannan "osoite"
    * @param   userDao käyttäjään liittyvistä tietokantatoiminnoista vastaava luokka
    * @throws SQLException   
    */
    public SqliteTaskDao(String url, UserDao userDao) throws SQLException {
        this.url = url;
        this.userDao = userDao;
        createTableIfDoesntExist();
    }
    private boolean createTableIfDoesntExist() throws SQLException {
        Connection conn = connect();
        boolean created = false;
        try {
            Statement s = conn.createStatement();
            s.execute("CREATE TABLE IF NOT EXISTS Tasks (user_id INTEGER, name TEXT NOT NULL, time INTEGER);");
            System.out.println("Table Tasks created");
            s.close();
            created = true;
        } catch (SQLException e) {
            System.out.println("Creating table failed: " + e.getMessage());
        } finally {
            conn.close();         
        }
        return created;
    }
       
    @Override
    public void create(Task task, String username) throws Exception {
        Connection conn = connect();
        try {
            PreparedStatement p = conn.prepareStatement("INSERT INTO Tasks(user_id,name,time) VALUES (?,?,?);");
            p.setInt(1, userDao.getUserId(username));
            p.setString(2, task.getName());
            p.setInt(3, task.getTimeUsed());
            p.execute();
            System.out.println("Task added to database!");
            p.close();
        } catch (Exception e) {
            System.out.println("Adding task failed: " + e.getMessage());
        } finally {
            conn.close();
        }
    }
    
    @Override
    public List<Task> getAll(User user) throws SQLException {
        Connection conn = connect();
        List<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement p = conn.prepareStatement("SELECT name, time FROM Tasks WHERE user_id = "
                    + "(SELECT id FROM Users WHERE username = ?)");
            p.setString(1, user.getUsername());
            ResultSet r = p.executeQuery();
            while (r.next()) {
                Task task = new Task(r.getString("name"));
                task.addTime(r.getInt("time"));
                tasks.add(task);
            }
            p.close();
            r.close();
        } catch (SQLException e) {
            System.out.println("Method showTasks failed: " + e.getMessage());
        } finally {
            conn.close();
        }
        return tasks;
    }

    @Override
    public void setDone(String name) {
        
    }
 
    private Connection connect() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return conn;
    }
    /**
     * Palauttaa tiettyyn tehtävään käytetyn ajan.
    *@param task tehtävä
    * @param user kirjautunut käyttäjä
    * @throws Exception   
    * @return Paluttaa tiettyyn tehtävään käyteteyn ajan
    */
    public int getTimeUsed(Task task, User user) throws Exception {
        Connection conn = null;
        int result = 0;
        try {
            conn = connect();
            PreparedStatement p = conn.prepareStatement("SELECT time FROM Tasks WHERE user_id = ? and name = ?");
            p.setInt(1, userDao.getUserId(user.getUsername()));
            p.setString(2, task.getName());
            ResultSet r = p.executeQuery();
            if (r.next()) {
                result = r.getInt("time");
            } 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void addTimeUsed(Task task, User user, int time) throws Exception {
        Connection conn = null;
        try {
            conn = connect();
            PreparedStatement p = conn.prepareStatement("UPDATE Tasks SET time=time+? WHERE user_id=? AND name=?");
            p.setInt(1, time);
            p.setInt(2, userDao.getUserId(user.getUsername()));
            p.setString(3, task.getName());
            p.execute();
            p.close();
        } catch (Exception e) {
            System.out.println("Virhe addTimeUsed: " + e.getMessage());
        } finally {
            conn.close();
        }
    }
    @Override
    public Task getTask(String name, User user) throws Exception {
        Connection conn = null;
        Task task = null;
        try {
            conn = connect();
            PreparedStatement p = conn.prepareStatement("SELECT name FROM Tasks WHERE user_id=? and name=?");
            p.setInt(1, userDao.getUserId(user.getUsername()));
            p.setString(2, name);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                task = new Task(r.getString("name"));
            }
            p.close();
        } catch (Exception e) {
            System.out.println("Virhe getTask: " + e.getMessage());
        } finally {
            conn.close();
        }
        return task;
    }

    
    
}
