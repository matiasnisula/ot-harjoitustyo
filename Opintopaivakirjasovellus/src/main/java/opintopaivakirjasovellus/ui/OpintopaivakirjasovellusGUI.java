
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
        Scene mainScene = new Scene(mainView.getView(), 1800, 1500);

        //Sisäänkirjautumisnäkymän toiminnallisuus
        logInView.getLogInButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    logInView.getUserInfo().setText("");
                    primaryStage.setScene(logInScene);
                    String username = logInView.getLoginTextField().getText();
                    boolean found = service.login(username);
                    if (found) {
                        primaryStage.setScene(mainScene);
                        mainView.getTable().setItems(getTasks());     
                    } else {
                        logInView.getUserInfo().setText("Kirjautuminen epäonnistui");
                        logInView.getLoginTextField().setText("");
                    }
                    
                } catch (Exception ex) {
                    
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
                    createUserView.getUserInfo().setText("");
                    String name = createUserView.getNameTextField().getText();
                    String username = createUserView.getUsernameTextField().getText();
                    boolean created = service.createUser(name, username);
                    if (created) {
                        createUserView.getUserInfo().setText("Käyttäjän luonti onnistui!");
                    } else {
                        createUserView.getUserInfo().setText("Käyttäjätunnus on jo olemassa!");
                    }
                } catch (Exception ex) {
                    
                }
            }
        });
        
        createUserView.getLogInButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(logInScene);
                createUserView.getNameTextField().setText("");
                createUserView.getUsernameTextField().setText("");
                createUserView.getUserInfo().setText("");
                logInView.getLoginTextField().setText("");
            }
        });
        
        
        //Päänäkymän toiminnallisuus
        mainView.getAllButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    mainView.getUserInfo().setText("");
                    tasks = FXCollections.observableArrayList();
                    List<Task> allTasks = service.getAll();
                    if (allTasks.isEmpty()) {
                        mainView.getUserInfo().setText("Sinulla ei ole tehtäviä");
                        return;
                    }
                    for (Task t: allTasks) {
                        if (!t.getDone()) {
                            tasks.add(t);
                        }
                    }
                    if (tasks.isEmpty()) {
                        mainView.getUserInfo().setText("Sinulla ei ole tekemättömiä tehtäviä");
                    }
                    mainView.getTable().setItems(tasks);
                } catch (Exception e) {
                    
                }
            }
        });
        mainView.getAddNewTaskButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    mainView.getUserInfo().setText("");
                    String taskName = mainView.getAddNewTaskNameText().getText();
                    boolean created = service.createTask(taskName);
                    if (!created) {
                        mainView.getUserInfo().setText("Tehtävän luonti epäonnistui");
                        mainView.getAddNewTaskNameText().setText("");
                        return;
                    }
                    mainView.getUserInfo().setText("Tehtävän luonti onnistui");
                    System.out.println("Lisääminen onnistui");
                    mainView.getAddNewTaskNameText().setText("");
                } catch (Exception e) {
                    
                }
            }
        });
        mainView.getTaskButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    mainView.getUserInfo().setText("");
                    String taskName = mainView.getTaskNameText().getText();
                    tasks = FXCollections.observableArrayList();
                    List<Task> list = service.getHistoryOneTask(taskName);
                    if (list.isEmpty()) {
                        mainView.getUserInfo().setText("Tehtävää ei löydy");
                        return;
                    }
                    for (Task t : list) {
                        tasks.add(t);
                    }
                    mainView.getTable().setItems(tasks);
                    mainView.getTaskNameText().setText("");
                } catch (Exception e) {
                    
                }
            }
        });
        mainView.getLogOutButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                service.logOut();
                primaryStage.setScene(logInScene);
                logInView.getUserInfo().setText("");
            }
        });
        
        mainView.getMarkDoneButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String taskName = mainView.getMarkDoneText().getText();
                taskName = taskName.trim();
                if (taskName.isEmpty()) {
                    return;
                }
                try {
                    if (service.getTask(taskName) == null) {
                        mainView.getUserInfo().setText("Tehtävää ei löydy");
                        mainView.getMarkDoneText().setText("");
                        return;
                    }
                    mainView.getUserInfo().setText("");
                    service.markDoneTask(taskName);
                    mainView.getMarkDoneText().setText("");
                    mainView.getUserInfo().setText("Tehtävä merkattiin tehdyksi");
                } catch (Exception e) {
                    mainView.getUserInfo().setText("Tehtävää ei löydy");
                }
            }
        });
        mainView.getAllDoneButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    mainView.getUserInfo().setText("");
                    tasks = FXCollections.observableArrayList();
                    List<Task> allTasks = service.getAll();
                    if (allTasks.isEmpty()) {
                        mainView.getUserInfo().setText("Sinulla ei ole tehtäviä");
                        return;
                    }
                    for (Task t: allTasks) {
                        if (t.getDone()) {
                            tasks.add(t);
                        }
                    }
                    if (tasks.isEmpty()) {
                        mainView.getUserInfo().setText("Sinulla ei ole tehtyjä tehtäviä");
                    }
                    mainView.getTable().setItems(tasks);
                    
                } catch (Exception e) {
          
                }
            }
        });
        
        mainView.getAddTimeUsedButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    mainView.getUserInfo().setText("");
                    String taskName = mainView.getAddTimeTaskNameText().getText();
                    String timeStr = mainView.getAddTimeUsedTimeText().getText();
                    taskName = taskName.trim();
                    timeStr = timeStr.trim();
                    int time = 0;
                    if (timeStr.matches("[0-9]+")) {
                        time = Integer.parseInt(timeStr);
                        if (time == 0) {
                            return;
                        }
                    } else {
                        mainView.getUserInfo().setText("Syötä aika oikeassa muodossa");
                        return;
                    }
                    Task task = service.getTask(taskName);
                    if (task == null) {
                        mainView.getUserInfo().setText("Tehtävää ei löydy");
                        return;
                    }
                    if (task.getDone()) {
                        mainView.getUserInfo().setText("Tehtävä on merkattu jo tehdyksi");
                        return;
                    }
                    service.addTimeUsed(taskName, time);
                    mainView.getAddTimeTaskNameText().setText("");
                    mainView.getAddTimeUsedTimeText().setText("");
                    mainView.getUserInfo().setText("Tehtävälistaa päivitetty");
                } catch (Exception e) {
                    
                }
                
            }
        });
        mainView.deleteTaskButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String taskName = mainView.deleteTaskNameText().getText();
                    if (service.getTask(taskName) == null) {
                        mainView.getUserInfo().setText("Tehtävää ei löydy");
                        mainView.deleteTaskNameText().setText("");
                        return;
                    }
                    service.deleteTask(taskName);
                    mainView.deleteTaskNameText().setText("");
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
        //Lukee tietokantojen nimet tiedostosta
        try (InputStream input = new FileInputStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            url = properties.getProperty("db.url");
        } catch (Exception e) {
            System.out.println("Tarkasta, että tiedosto config.properties sijaitsee samassa kansiossa");
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
