
package opintopaivakirjasovellus.ui;
import java.util.Scanner;
import opintopaivakirjasovellus.domain.*;
import opintopaivakirjasovellus.dao.*;


public class Main {
    public static void main(String[] args) throws Exception {
        Scanner reader = new Scanner(System.in);
        String url = "jdbc:sqlite:tietokanta.db";
        UserDao userDao = new SqliteUserDao(url);
        TaskDao taskDao = new SqliteTaskDao(url, userDao);
        AppService service = new AppService(taskDao, userDao);
        TUI textUserInterface = new TUI(reader, service);
        textUserInterface.start();
        
    }
}
