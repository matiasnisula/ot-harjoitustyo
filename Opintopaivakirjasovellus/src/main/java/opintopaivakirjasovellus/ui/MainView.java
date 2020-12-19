
package opintopaivakirjasovellus.ui;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import opintopaivakirjasovellus.domain.Task;

public class MainView {
    private Button addNewTask;
    private Button getTask;
    private Button getAllUndoneTasks;
    private Button addTimeUsed;
    private Button logOut;
    private Button deleteTask;
    private Button markDone;
    private Button getAllDoneTasks;
    private TextField addNewTaskName;
    private TextField getTaskName;
    private TextField addTimeTaskName;
    private TextField addTimeUsedTime;
    private TextField deleteTaskName;
    private TextField markDoneText;
    private TableView<Task> table;
    private final double fontSize = 40.0;
    private BorderPane pane;
    private VBox vbox;
    private Label messages;
    
    public Parent getView() {
        vbox = new VBox();
        pane = new BorderPane();
        
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(30);
        //vbox.setPadding(new Insets(25, 25, 25, 25));
        vbox.autosize();
        
        addNewTask = new Button("Lisää tehtävä");
        addNewTask.setFont(new Font(fontSize));
        
        getTask = new Button("Hae tehtävä");
        getTask.setFont(new Font(fontSize));
        
        markDone = new Button("Merkkaa tehdyksi");
        markDone.setFont(new Font(fontSize));
        
        getAllUndoneTasks = new Button("Hae tekemättömät tehtävät");
        getAllUndoneTasks.setFont(new Font(fontSize));
        
        getAllDoneTasks = new Button("Hae tehdyt tehtävät");
        getAllDoneTasks.setFont(new Font(fontSize));
        
        addTimeUsed = new Button("Lisää aika");
        addTimeUsed.setFont(new Font(fontSize));
        
        logOut = new Button("Kirjaudu ulos");
        logOut.setFont(new Font(fontSize));
        
        deleteTask = new Button("Poista tehtävä");
        deleteTask.setFont(new Font(fontSize));
        
        addNewTaskName = new TextField();
        addNewTaskName.setFont(new Font(fontSize));
        
        getTaskName = new TextField();
        getTaskName.setFont(new Font(fontSize));
        
        addTimeTaskName = new TextField();
        addTimeTaskName.setFont(new Font(fontSize));
        
        addTimeUsedTime = new TextField();
        addTimeUsedTime.setFont(new Font(fontSize));
        
        deleteTaskName = new TextField();
        deleteTaskName.setFont(new Font(fontSize));
        
        markDoneText = new TextField();
        markDoneText.setFont(new Font(fontSize));
        
        messages = new Label("");
        messages.setFont(new Font(fontSize));
        messages.setTextFill(Color.RED);
        
        Label nimi = new Label("Nimi:");
        Label aika = new Label("Aika:");
        nimi.setFont(new Font(fontSize));
        aika.setFont(new Font(fontSize));
        
        
        vbox.getChildren().add(addNewTask);
        vbox.getChildren().add(addNewTaskName);
        vbox.getChildren().add(getTask);
        vbox.getChildren().add(getTaskName);      
        vbox.getChildren().add(markDone);      
        vbox.getChildren().add(markDoneText);
        vbox.getChildren().add(getAllUndoneTasks);
        vbox.getChildren().add(getAllDoneTasks);
        vbox.getChildren().add(addTimeUsed);
        vbox.getChildren().add(nimi);
        vbox.getChildren().add(addTimeTaskName);
        vbox.getChildren().add(aika);
        vbox.getChildren().add(addTimeUsedTime);
        vbox.getChildren().add(deleteTask);
        vbox.getChildren().add(deleteTaskName);
        vbox.getChildren().add(logOut);
        vbox.getChildren().add(messages);
          
        table = new TableView<Task>();
        //table.setEditable(true);
        TableColumn<Task, String> nameColumn = new TableColumn("Nimi");
        nameColumn.setMinWidth(500);
        
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
             
        TableColumn<Task, Integer> timeColumn = new TableColumn("Käytetty aika(h)");
        timeColumn.setMinWidth(350);
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeUsed"));
        
        TableColumn<Task, String> dateColumn = new TableColumn("Lisätty");
        dateColumn.setMinWidth(400);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        table.getColumns().addAll(nameColumn, timeColumn, dateColumn);
        //table.setMaxSize(1800, 1200);
        table.autosize();
        table.setStyle("-fx-font-weight:bold;\n" +
                       "-fx-font-size:35px;");
        pane.setLeft(vbox);
        pane.setCenter(table);
        
        return pane;
    }

    public Button getAddNewTask() {
        return addNewTask;
    }

    public Button getTask() {
        return getTask;
    }

    public Button getAll() {
        return getAllUndoneTasks;
    }
    public Button getAllDoneButton() {
        return this.getAllDoneTasks;
    }
    public Button getMarkDoneButton() {
        return this.markDone;
    }

    public Button getAddTimeUsed() {
        return addTimeUsed;
    }
    public Button deleteTask() {
        return deleteTask;
    }
    public TextField deleteTaskName() {
        return deleteTaskName;
    }

    public TextField getAddNewTaskName() {
        return addNewTaskName;
    }

    public TextField getTaskName() {
        return getTaskName;
    }

    public TextField getAddTimeTaskName() {
        return addTimeTaskName;
    }

    public TextField getAddTimeUsedTime() {
        return addTimeUsedTime;
    }
    public TextField getMarkDoneText() {
        return this.markDoneText;
    }

    public TableView<Task> getTable() {
        return table;
    }
    public Button getLogOut() {
        return this.logOut;
    }
    public Label getMessages() {
        return this.messages;
    }
}
