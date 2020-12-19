
package opintopaivakirjasovellus.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import opintopaivakirjasovellus.domain.User;

/**
    * Käyttäjään liittyvistä tietokantatoiminnoista vastaava luokka.
    */
public class SqliteUserDao implements UserDao {
    private String url;
    
    /**
    *Luokan konstruktori.
    * @param url tietokannan "osoite"
    * @throws SQLException  
    */
    public SqliteUserDao(String url) throws SQLException {
        this.url = url;
        createTableIfDoesntExist();
    }
    /**
    *Paluttaa yhteyden tietokantaan.
    * @throws SQLException
    * @return yhteys
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
    * Luo tietokantaan taulun Users, johon tallennetaan käyttäjät.
    * @throws Exception
    * @return true tai false riippuen onnistuiko luonti vai ei.
    */
    private boolean createTableIfDoesntExist() throws SQLException {
        Connection conn = connect();
        boolean created = false;
        try {
            Statement s = conn.createStatement();
            s.execute("CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY, name TEXT, username TEXT UNIQUE);");
            //System.out.println("Table Users created");
            s.close();
            created = true;
        } catch (SQLException e) {
            //System.out.println("Creating table failed: " + e.getMessage());
        } finally {
            conn.close();         
        }     
        return created;
    }
    /**
    * Tyhjentää taulut Users tietokannan testausta varten.
    * @throws SQLException poikkeus
    */
    public void emptyTables() throws SQLException {
        Connection conn = connect();
        try {
            Statement s = conn.createStatement();
            s.execute("DELETE FROM Users;");
            s.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            conn.close();         
        }
    }
    /**
    * Lisää käyttäjän tietokantaan.
    * @param user
    * @throws SQLException
    */
    @Override
    public void addUser(User user) throws SQLException {
        Connection conn = connect();
        try {
            PreparedStatement p = conn.prepareStatement("INSERT INTO Users (name, username) VALUES(?,?)");
            p.setString(1, user.getName());
            p.setString(2, user.getUsername());
            p.execute();
            p.close();
        } catch (SQLException e) {
            System.out.println("Käyttäjän lisäys epäonnistui");
        } finally {
            conn.close();
        }
    }
    /**
    * Palauttaa tietokannan taulussa Users pääavaimen id käyttäjänimen perusteella.
    * @param username
    * @throws SQLexception
    * @return käyttäjän id
    */
    @Override
    public int getUserId(String username) throws SQLException {
        Connection conn = connect();
        int id = 0;
        try {
            PreparedStatement p = conn.prepareStatement("SELECT id FROM Users WHERE username=?");
            p.setString(1, username);
            ResultSet r = p.executeQuery();
            id = r.getInt("id");
            p.close();
            r.close();
        } catch (SQLException e) {
            //System.out.println("Getting userId failed " + e.getMessage());
        } finally {
            conn.close();
        }
        return id;
    }
    /**
    *Palauttaa listan kaikista käyttäjistä, tätä tarvitaan jos sovellukseen halutaan lisätä ylläpitäjä.
    * @return palauttaa listan kaikista käyttäjistä
    * @throws SQLException   
    */
    public List<User> getAll() throws SQLException {
        Connection conn = null;
        List<User> users = new ArrayList<>();
        try {
            conn = connect();
            PreparedStatement p = conn.prepareStatement("Select * FROM Users");
            ResultSet r = p.executeQuery();
            while (r.next()) {
                User user = new User(r.getString("name"), r.getString("username"));
                users.add(user);
            }
            p.close();
            r.close();
        } catch (SQLException e) {
            System.out.println("Virhe metodissa getAll(), SqliteUserDao" + e.getMessage());
        } finally {
            conn.close();
        }
        return users;
    }
    /**
    * Etsii tietokannasta käyttäjän, ja palauttaa sen käyttäjäoliona.
    * @param username
    * @throws SQLException
    * @return käyttäjäolio
    */
    @Override
    public User findByUsername(String username) throws SQLException {
        Connection conn = null;
        User user = null;
        try {
            conn = connect();
            PreparedStatement p = conn.prepareStatement("Select * FROM Users WHERE username=?");
            p.setString(1, username);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                user = new User(r.getString("name"), r.getString("username"));
            } else {
                System.out.println("Ei löytynyt");
            }
            p.close();
            r.close();
        } catch (SQLException e) {
            System.out.println("Username: " + username + " doesnt exists: " + e.getMessage());
        } finally {
            conn.close();
        }
        return user;
    }
    /**
    * Kertoo löytyykö käyttäjänimi tietokannasta.
    * @param username löytyykö tämä käyttäjänimi tietokannasta
    * @throws SQLException  
    * @return true/false
    */
    public boolean usernameExists(String username) throws SQLException {
        Connection conn = connect();
        boolean found = false;
        try {
            PreparedStatement p = conn.prepareStatement("Select username FROM Users WHERE username=?");
            p.setString(1, username);           
            ResultSet r = p.executeQuery();
            if (r.next()) {
                found = true;
            }
            p.close();
            r.close();
        } catch (SQLException e) {
            System.out.println("Username: " + username + " doesnt exists: " + e.getMessage());
        } finally {
            conn.close();
        }
        return found;
    }
    
}
