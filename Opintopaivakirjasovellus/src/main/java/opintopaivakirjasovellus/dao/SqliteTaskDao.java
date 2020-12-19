
package opintopaivakirjasovellus.dao;
import opintopaivakirjasovellus.domain.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteTaskDao implements TaskDao {
    private String url;
    private UserDao userDao;
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
        createTableHistoryIfDoesntExist();
    }
    /**
    * Luo tietokantaan taulun Tasks, johon tallennetaan käyttäjän tehtävät.
    * @throws SQLException
    */
    private boolean createTableIfDoesntExist() throws SQLException {
        Connection conn = connect();
        boolean created = false;
        try {
            Statement s = conn.createStatement();
            s.execute("CREATE TABLE IF NOT EXISTS Tasks (user_id INTEGER, name TEXT NOT NULL, time INTEGER, done INTEGER, date TEXT);");
            //System.out.println("Table Tasks created");
            s.close();
            created = true;
        } catch (SQLException e) {
            System.out.println("Creating table failed: " + e.getMessage());
        } finally {
            conn.close();         
        }
        return created;
    }
    /**
    * Luo tietokantaan taulun History, johon tallettuu tehtävien suorituksia.
    * @throws SQLException
    */
    private boolean createTableHistoryIfDoesntExist() throws SQLException {
        Connection conn = connect();
        boolean created = false;
        try {
            Statement s = conn.createStatement();
            s.execute("CREATE TABLE IF NOT EXISTS History (user_id INTEGER, name TEXT NOT NULL, time INTEGER, date TEXT);");
            s.close();
            created = true;
        } catch (SQLException e) {
            System.out.println("Creating table failed: " + e.getMessage());
        } finally {
            conn.close();         
        }
        return created;
    }
    
    private void addTaskToTableHistory(Task task, User user, int time, String date) throws Exception {
        Connection conn = connect();
        try {
            PreparedStatement p = conn.prepareStatement("INSERT INTO History(user_id,name,time,date) VALUES (?,?,?,?);");
            p.setInt(1, userDao.getUserId(user.getUsername()));
            p.setString(2, task.getName());
            p.setInt(3, time);
            p.setString(4, date);
            p.execute();
            p.close();
        } catch (Exception e) {
            System.out.println("Adding task failed: " + e.getMessage());
        } finally {
            conn.close();
        }
    }
    
    /**
    * Tyhjentää tietokannan taulut testausta varten.
    * @throws SQLException poikkeus
    */
    public void emptyTables() throws SQLException {
        Connection conn = connect();
        try {
            Statement s = conn.createStatement();
            s.execute("DELETE FROM Tasks;");
            s.execute("DELETE FROM History;");
            s.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            conn.close();         
        }
    }
    /**
    * Lisää tehtävän tietokantaan.
    * 
    * @param task
    * @param user
    * @throws Exception poikkeus
    */
    @Override
    public void create(Task task, User user) throws Exception {
        Connection conn = connect();
        try {
            if (getTask(task.getName(),user) != null) {
                return;
            }
            PreparedStatement p = conn.prepareStatement("INSERT INTO Tasks(user_id,name,time,done,date) VALUES (?,?,?,?,?);");
            p.setInt(1, userDao.getUserId(user.getUsername()));
            p.setString(2, task.getName());
            p.setInt(3, task.getTimeUsed());
            p.setInt(4, task.getDoneInt());
            p.setString(5, task.getDate());
            p.execute();
            //System.out.println("Task added to database!");
            p.close();
        } catch (Exception e) {
            System.out.println("Adding task failed: " + e.getMessage());
        } finally {
            conn.close();
        }
    }
    /**
    * Palauttaa tietyn käyttäjän kaikki tehtävät listana.
    * @param user
    * throws SQLException poikkeus
    */
    @Override
    public List<Task> getAll(User user) throws SQLException {
        Connection conn = connect();
        List<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement p = conn.prepareStatement("SELECT name, time, done, date FROM Tasks WHERE user_id = "
                    + "(SELECT id FROM Users WHERE username = ?);");
            p.setString(1, user.getUsername());
            ResultSet r = p.executeQuery();
            while (r.next()) {
                Task task = new Task(r.getString("name"), user, r.getString("date"));
                task.addTime(r.getInt("time"));
                task.setDoneInt(r.getInt("done"));
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
    /**
    * Asettaa tehtään tehdyksi.
    * @param task
    * @param user
    * @throws SQLException poikkeus
    */
    @Override
    public void setDone(Task task, User user) throws Exception {
        Connection conn = null;
        try {
            conn = connect();
            PreparedStatement p = conn.prepareStatement("UPDATE Tasks SET done=1 WHERE user_id=? AND name=?");
            p.setInt(1, userDao.getUserId(user.getUsername()));
            p.setString(2, task.getName());
            p.execute();
            p.close();
        } catch (Exception e) {
            System.out.println("Tehtävää ei löydy");
        } finally {
            conn.close();
        }
    }
    /**
    * Muodostaa yhteyden tietokantaan.
    * @throws SQLException
    */
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
    @Override
    public int getTimeUsedOneTask(Task task, User user) throws Exception {
        Connection conn = null;
        int result = 0;
        try {
            conn = connect();
            PreparedStatement p = conn.prepareStatement("SELECT time FROM Tasks WHERE user_id = ? and name = ?");
            p.setInt(1, userDao.getUserId(user.getUsername()));
            p.setString(2, task.getName());
            ResultSet r = p.executeQuery();
            while (r.next()) {
                result += r.getInt("time");
            } 
            r.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conn.close();
        }
        return result;
    }
    /**
    * Yhden käyttäjän kaikkiin tehtäviin käytetty aika yhteensä.
    * @param user
    * @throws Exception
    * @return käytetty aika yhteensä
    */
    
    public int getTimeUsedAllTasks(User user) throws Exception {
        Connection conn = null;
        int result = 0;
        try {
            conn = connect();
            PreparedStatement p = conn.prepareStatement("SELECT time FROM Tasks WHERE user_id = ?");
            p.setInt(1, userDao.getUserId(user.getUsername()));
            ResultSet r = p.executeQuery();
            while (r.next()) {
                result += r.getInt("time");
            } 
            r.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            conn.close();
        }
        return result;
    }
    /**
    * Lisää käytettyä aikaa tiettyyn tehtävään. Lisää samalla yhden suorituksen tietokannan tauluun History.
    * @param task
    * @param user
    * @param time
    * @param date
    * @throws Exception
    */
    @Override
    public void addTimeUsed(Task task, User user, int time, String date) throws Exception {
        if (time <= 0) {
            return;
        }
        Connection conn = null;
        try {
            conn = connect();
            addTaskToTableHistory(task, user, time, date);
            PreparedStatement p = conn.prepareStatement("UPDATE Tasks SET time=time+? WHERE user_id=? AND name=? AND done=0");
            p.setInt(1, time);
            p.setInt(2, userDao.getUserId(user.getUsername()));
            p.setString(3, task.getName());
            p.execute();
            p.close();
        } catch (Exception e) {
            System.out.println("Ajan lisäys epäonnistui");
        } finally {
            conn.close();
        }
    }
    /**
    * Palauttaa tehtäväolion.
    * @param name
    * @param user
    * @throws Exception
    * @return Tehtäväolio
    */
    @Override
    public Task getTask(String name, User user) throws Exception {
        Connection conn = null;
        Task task = null;
        try {
            conn = connect();
            PreparedStatement p = conn.prepareStatement("SELECT name, time, done, date FROM Tasks WHERE user_id=? and name=?");
            p.setInt(1, userDao.getUserId(user.getUsername()));
            p.setString(2, name);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                task = new Task(r.getString("name"), user, r.getString("date"));
                task.addTime(r.getInt("time"));
                task.setDoneInt(r.getInt("done"));
            }
            p.close();
            r.close();
        } catch (Exception e) {
            System.out.println("Virhe getTask: " + e.getMessage());
        } finally {
            conn.close();
        }
        return task;
    }
    /**
    * Palauttaa listana tiettyyn tehtävään liittyvät suoritukset.
    * @param taskName
    * @param user
    * @throws Exception
    * @return lista tehtäväolioista
    */
    @Override
    public List<Task> getHistoryOneTask(String taskName, User user) throws Exception {
        Connection conn = null;
        List<Task> tasks = new ArrayList<>();
        try {
            conn = connect();
            PreparedStatement p = conn.prepareStatement("SELECT name, time, date FROM History WHERE user_id=? and name=?;");
            p.setInt(1, userDao.getUserId(user.getUsername()));
            p.setString(2, taskName);
            ResultSet r = p.executeQuery();
            while (r.next()) {
                Task task = new Task(r.getString("name"), user, r.getString("date"));
                task.addTime(r.getInt("time"));
                tasks.add(task);
            }
            p.close();
            r.close();
        } catch (SQLException e) {
            System.out.println("Method getHistory failed: " + e.getMessage());
        } finally {
            conn.close();
        }
        return tasks;
    }
    
    /**
    * Poistaa tehtävän tietokannasta.
    * @param taskName
    * @param user
    * @throws Exception
    */
    @Override
    public void deleteTask(String taskName, User user) throws Exception {
        Connection conn = null;
        try {
            conn = connect();
            PreparedStatement p = conn.prepareStatement("DELETE FROM Tasks WHERE name=? and user_id=?");
            p.setString(1, taskName);
            p.setInt(2, userDao.getUserId(user.getUsername()));
            p.execute();
            p.close();
        } catch (Exception e) {
            System.out.println("Tehtävän poistaminen epäonnistui");
        } finally {
            conn.close();
        }
    }

      
}
