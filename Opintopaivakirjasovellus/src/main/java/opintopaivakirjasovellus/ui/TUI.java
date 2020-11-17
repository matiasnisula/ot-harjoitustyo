
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
        commands.put("x", "x stop");
        commands.put("1", "1 create user");
        commands.put("2", "2 log in");
        commands.put("3", "3 create task");
        commands.put("4", "4 show your tasks");
        commands.put("5", "5 log out");
        
    }
    public void start() throws Exception {
        System.out.println("Opintopäiväkirjasovellus");
        printCommands();
        while (true) {
            System.out.println();
            System.out.print("Command: ");
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
            }
       }
    }
    private void createUser() throws Exception {
        System.out.print("Name: ");
        String name = reader.nextLine();
        System.out.println("");
        System.out.print("Username: ");
        String username = reader.nextLine();
        service.createUser(name, username);
        
    }
    private void logIn() throws Exception {
        System.out.println("Log in");
        System.out.print("Username: ");
        String username = reader.nextLine();
        service.login(username);
        
    }
    private void createTask() throws Exception {
        System.out.println("Create task");
        System.out.println("Name: ");
        String name = reader.nextLine();
        service.createTask(name);
    }
    private void showTasks() throws Exception{
        service.showTasks();
    }
    private void logOut() {
        service.logOut();
        System.out.println("Logged out successfully");
    }
    private void printCommands() {
        for (String s: commands.values()) {
            System.out.println(s);
        }
    }
}
