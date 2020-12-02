
package opintopaivakirjasovellus.ui;

import opintopaivakirjasovellus.domain.*;
import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;

//Text User Interface

public class TUI {
    private Scanner reader;
    private AppService service;
    private Map<String, String> commands;
    
    public TUI(Scanner reader, AppService service) {
        this.reader = reader;
        this.service = service;
        commands = new TreeMap<>();
        commands.put("x", "x Lopeta");
        commands.put("1", "1 Luo käyttäjä");
        commands.put("2", "2 Kirjaudu sisään");
        commands.put("3", "3 Luo tehtävä");
        commands.put("4", "4 Näytä tehtävät");
        commands.put("5", "5 Kirjaudu ulos");
        commands.put("6", "6 Päivitä tehtävään käytetty aika");
        
    }
    public void start() throws Exception {
        System.out.println("Opintopäiväkirjasovellus");
        printCommands();
        while (true) {
            System.out.println();
            System.out.print("Komento: ");
            String command = reader.nextLine();
            if (!commands.keySet().contains(command)) {
                System.out.println("Virheellinen komento.");
                printCommands();
            }

            if (command.equals("x")) {
                break;
            } else if (command.equals("1")) {
                createUser();
            } else if (command.equals("2")) {
                logIn();
            } else if (command.equals("3")) {
                createTask();
            } else if (command.equals("4")) {
                showTasks();
            } else if (command.equals("5")) {
                logOut();
            } else if (command.equals("6")) {
                addTimeUsed();
            }
       }
    }
    private void createUser() throws Exception {
        System.out.print("Nimi: ");
        String name = reader.nextLine();
        System.out.println("");
        System.out.print("Käyttäjätunnus: ");
        String username = reader.nextLine();
        service.createUser(name, username);
        System.out.println("Käyttäjätunnus luotu");
        
    }
    private void logIn() throws Exception {
        System.out.println("Kirjaudu sisään");
        System.out.print("Käyttäjätunnus: ");
        String username = reader.nextLine();
        service.login(username);
        
    }
    private void createTask() throws Exception {
        System.out.println("Luo tehtävä");
        System.out.println("Nimi: ");
        String name = reader.nextLine();
        service.createTask(name);
    }
    private void showTasks() throws Exception{
        service.showTasks();
    }
    private void addTimeUsed() throws Exception {
        System.out.print("Kirjoita tehtävän nimi: ");
        String taskName = reader.nextLine();
        System.out.println("");
        System.out.print("Käytetty aika: ");
        String s = reader.nextLine();
        int time = 0;
        if (s.matches("[0-9]+")) {
            time = Integer.parseInt(s);
        } else {
            System.out.println("Syötä aika oikeassa muodossa");
            return;
        }
        service.addTimeUsed(taskName, time);
    }
    private void logOut() {
        service.logOut();
        System.out.println("Uloskirjautuminen onnistui");
    }
    private void printCommands() {
        for (String s: commands.values()) {
            System.out.println(s);
        }
    }
}
