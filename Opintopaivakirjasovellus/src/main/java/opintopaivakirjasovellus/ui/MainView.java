
package opintopaivakirjasovellus.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;
import opintopaivakirjasovellus.domain.Task;

public class MainView {
    private Button addNewTask;
    private Button getTask;
    private Button getAll;
    private Button addTimeUsed;
    private Button logOut;
    private Button deleteTask;
    private TextField addNewTaskName;
    private TextField getTaskName;
    private TextField addTimeTaskName;
    private TextField addTimeUsedTime;
    private TableView<Task> table;
    private final double fontSize = 40.0;
    
    public Parent getView() {
        GridPane grid = new GridPane();
        BorderPane pane = new BorderPane();
        
        grid.setHgap(10);
        grid.setVgap(40);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        addNewTask = new Button("Lisää tehtävä");
        addNewTask.setFont(new Font(fontSize));
        
        getTask = new Button("Hae tehtävä");
        getTask.setFont(new Font(fontSize));
        
        getAll = new Button("Hae kaikki tehtävät");
        getAll.setFont(new Font(fontSize));
        
        addTimeUsed = new Button("Lisää aika");
        addTimeUsed.setFont(new Font(fontSize));
        
        logOut = new Button("Kirjaudu ulos");
        logOut.setFont(new Font(fontSize));
        
        addNewTaskName = new TextField();
        addNewTaskName.setFont(new Font(fontSize));
        getTaskName = new TextField();
        getTaskName.setFont(new Font(fontSize));
        addTimeTaskName = new TextField("nimi");
        addTimeTaskName.setFont(new Font(fontSize));
        addTimeUsedTime = new TextField("aika");
        addTimeUsedTime.setFont(new Font(fontSize));
        
        grid.add(addNewTask, 0, 0);
        grid.add(addNewTaskName, 0, 1);
        grid.add(getTask, 0, 2);
        grid.add(getTaskName, 0, 3);
        grid.add(getAll, 0, 4);
        grid.add(addTimeTaskName, 0, 6);
        grid.add(addTimeUsed, 0, 5);
        grid.add(addTimeUsedTime, 0, 7);
        grid.add(logOut, 0, 9);
          
        table = new TableView<Task>();
        //table.setEditable(true);
        TableColumn<Task, String> nameColumn = new TableColumn("Nimi");
        nameColumn.setMinWidth(500);
        
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
             
        TableColumn<Task, Integer> timeColumn = new TableColumn("Käytetty aika(h)");
        timeColumn.setMinWidth(250);
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeUsed"));
        
        TableColumn<Task, String> dateColumn = new TableColumn("Lisätty");
        dateColumn.setMinWidth(400);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        table.getColumns().addAll(nameColumn, timeColumn, dateColumn);
        table.setMaxSize(1800, 1200);
        //table.autosize();
        table.setStyle("-fx-font-weight:bold;\n" +
                       "-fx-font-size:35px;");
        pane.setLeft(grid);
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
        return getAll;
    }

    public Button getAddTimeUsed() {
        return addTimeUsed;
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

    public TableView<Task> getTable() {
        return table;
    }
    public Button getLogOut() {
        return this.logOut;
    }
}
