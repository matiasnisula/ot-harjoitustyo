
package opintopaivakirjasovellus.ui;

import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import opintopaivakirjasovellus.dao.*;
import opintopaivakirjasovellus.domain.AppService;
import opintopaivakirjasovellus.domain.Task;

public class OpintopaivakirjasovellusGUI extends Application {
    private final String url = "jdbc:sqlite:tietokanta.db";
    private AppService service;
    private LogInView logInView;
    private CreateUserView createUserView;
    private MainView mainView;
    private ObservableList<Task> tasks;
    private SqliteTaskDao taskDao;
    private SqliteUserDao userDao;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Opintopaivakirjasovellus");
        Scene logInScene = new Scene(logInView.getView(), 1200, 700);
        Scene createUserScene = new Scene(createUserView.getView(), 1200, 700);
        Scene mainScene = new Scene(mainView.getView(), 1800, 1200);

        //Sisäänkirjautumisnäkymän toiminnallisuus
        logInView.getLogInButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    primaryStage.setScene(logInScene);
                    String username = logInView.getTextField().getText();
                    boolean found = service.login(username);
                    if (found) {
                        primaryStage.setScene(mainScene);
                        mainView.getTable().setItems(getTasks());     
                    } 
                    
                } catch (Exception ex) {
                    System.out.println("Virhe GUI: " + ex.getMessage());
                }
            }
        });
        
        logInView.getCreateNewuserButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(createUserScene);
            }
        });
        
        logInView.getTextField().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
            }
        });
        
        //Käyttäjänluomisnäkymän toiminnalisuus
        createUserView.getCreateNewuserButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String name = createUserView.getNameTextField().getText();
                    String username = createUserView.getUserTextField().getText();
                    service.createUser(name, username);
                } catch (Exception ex) {
                    System.out.println("Virhe GUI: " + ex.getMessage());
                }
            }
        });
        //12.0.2
        createUserView.getLogInButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(logInScene);
                createUserView.getNameTextField().setText("");
                createUserView.getUserTextField().setText("");
            }
        });
        
        createUserView.getUserTextField().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
            }
        });
        
        createUserView.getNameTextField().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
            }
        });
        
        //Päänäkymän toiminnallisuus
        mainView.getAll().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    mainView.getTable().setItems(getTasks());
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        mainView.getAddNewTask().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String taskName = mainView.getAddNewTaskName().getText();
                    if (taskName.equals("")) {
                        return;
                    }
                    service.createTask(taskName);
                    System.out.println("Lisääminen onnistui");
                    mainView.getAddNewTaskName().setText("");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        mainView.getTask().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String taskName = mainView.getTaskName().getText();
                    tasks = FXCollections.observableArrayList();
                    List<Task> list = service.getHistoryOneTask(taskName);
                    for (Task t : list) {
                        tasks.add(t);
                    }
                    mainView.getTable().setItems(tasks);
                    mainView.getTaskName().setText("");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        mainView.getLogOut().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                service.logOut();
                primaryStage.setScene(logInScene);
            }
        });
        
        mainView.getAddTimeUsed().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String taskName = mainView.getAddTimeTaskName().getText();
                    String timeStr = mainView.getAddTimeUsedTime().getText();
                    int time = 0;
                    if (timeStr.matches("[0-9]+")) {
                        time = Integer.parseInt(timeStr);
                    }
                    service.addTimeUsed(taskName, time);
                    mainView.getAddTimeTaskName().setText("");
                    mainView.getAddTimeUsedTime().setText("");
                } catch (Exception e) {
                    
                }
                
            }
        });
        
        primaryStage.setScene(logInScene);
        primaryStage.show();
    }
    public ObservableList<Task> getTasks() throws Exception {
        tasks = FXCollections.observableArrayList();
        try {           
            List<Task> list = service.getAll();
            for (Task t : list) {
                tasks.add(t);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return tasks;
    }
    
    @Override
    public void init() throws Exception {
        logInView = new LogInView();
        createUserView = new CreateUserView();
        mainView = new MainView();
        userDao = new SqliteUserDao(url);
        taskDao = new SqliteTaskDao(url, userDao);
        service = new AppService(taskDao, userDao);
        tasks = FXCollections.observableArrayList();
    }
    public static void main(String[] args) {
        launch(args);
    }

    
    
}
