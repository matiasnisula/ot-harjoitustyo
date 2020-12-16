
package opintopaivakirjasovellus.ui;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
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
    private String url;
    private AppService service;
    private LogInView logInView;
    private CreateUserView createUserView;
    private MainView mainView;
    private ObservableList<Task> tasks;
    private SqliteTaskDao taskDao;
    private SqliteUserDao userDao;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Opintopäiväkirjasovellus");
        Scene logInScene = new Scene(logInView.getView(), 1200, 700);
        Scene createUserScene = new Scene(createUserView.getView(), 1200, 700);
        Scene mainScene = new Scene(mainView.getView(), 1800, 1400);

        //Sisäänkirjautumisnäkymän toiminnallisuus
        logInView.getLogInButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    logInView.getMessages().setText("");
                    primaryStage.setScene(logInScene);
                    String username = logInView.getTextField().getText();
                    boolean found = service.login(username);
                    if (found) {
                        primaryStage.setScene(mainScene);
                        mainView.getTable().setItems(getTasks());     
                    } else {
                        logInView.getMessages().setText("Kirjautuminen epäonnistui");
                        logInView.getTextField().setText("");
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
        
        
        //Käyttäjänluomisnäkymän toiminnalisuus
        createUserView.getCreateNewuserButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    createUserView.getMessages().setText("");
                    String name = createUserView.getNameTextField().getText();
                    String username = createUserView.getUserTextField().getText();
                    name = name.trim();
                    username = username.trim();
                    boolean created = service.createUser(name, username);
                    if (created) {
                        createUserView.getMessages().setText("Käyttäjän luonti onnistui!");
                    } else {
                        createUserView.getMessages().setText("Käyttäjätunnus on jo olemassa!");
                    }
                } catch (Exception ex) {
                    System.out.println("Virhe GUI: " + ex.getMessage());
                }
            }
        });
        
        createUserView.getLogInButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(logInScene);
                createUserView.getNameTextField().setText("");
                createUserView.getUserTextField().setText("");
                createUserView.getMessages().setText("");
            }
        });
        
        
        //Päänäkymän toiminnallisuus
        mainView.getAll().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    mainView.getMessages().setText("");
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
                    mainView.getMessages().setText("");
                    String taskName = mainView.getAddNewTaskName().getText();
                    if (taskName.equals("")) {
                        return;
                    }
                    taskName = taskName.trim();
                    boolean created = service.createTask(taskName);
                    if (!created) {
                        mainView.getMessages().setText("Tehtävän luonti epäonnistui");
                        mainView.getAddNewTaskName().setText("");
                        return;
                    }
                    mainView.getMessages().setText("Tehtävän luonti onnistui");
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
                    mainView.getMessages().setText("");
                    String taskName = mainView.getTaskName().getText();
                    taskName = taskName.trim();
                    tasks = FXCollections.observableArrayList();
                    List<Task> list = service.getHistoryOneTask(taskName);
                    if (list.isEmpty()) {
                        mainView.getMessages().setText("Tehtävää ei löydy");
                        return;
                    }
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
                logInView.getMessages().setText("");
            }
        });
        
        mainView.getAddTimeUsed().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    mainView.getMessages().setText("");
                    String taskName = mainView.getAddTimeTaskName().getText();
                    String timeStr = mainView.getAddTimeUsedTime().getText();
                    taskName = taskName.trim();
                    timeStr = timeStr.trim();
                    int time = 0;
                    if (timeStr.matches("[0-9]+")) {
                        time = Integer.parseInt(timeStr);
                        if (time == 0) {
                            return;
                        }
                    } else {
                        mainView.getMessages().setText("Syötä aika oikeassa muodossa");
                        return;
                    }
                    if (service.getTask(taskName) == null) {
                        mainView.getMessages().setText("Tehtävää ei löydy");
                        return;
                    }
                    service.addTimeUsed(taskName, time);
                    mainView.getAddTimeTaskName().setText("");
                    mainView.getAddTimeUsedTime().setText("");
                    mainView.getMessages().setText("Tehtävälistaa päivitetty");
                } catch (Exception e) {
                    
                }
                
            }
        });
        mainView.deleteTask().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String taskName = mainView.deleteTaskName().getText();
                    service.deleteTask(taskName);
                    mainView.deleteTaskName().setText("");
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
        try (InputStream input = new FileInputStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            url = properties.getProperty("db.url");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
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
