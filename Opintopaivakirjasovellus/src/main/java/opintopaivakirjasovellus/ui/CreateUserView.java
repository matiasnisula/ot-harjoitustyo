
package opintopaivakirjasovellus.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class CreateUserView {
    private final double fontSize = 40.0;
    private Button logInButton;
    private Button createNewUserButton;
    private TextField userTextField;
    private TextField nameTextField;
    
    public Parent getView() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Label userName = new Label("Käyttäjänimi:");
        userName.setFont(new Font(fontSize));
        grid.add(userName, 0, 1);
        
        userTextField = new TextField();
        userTextField.setFont(new Font(fontSize));
        grid.add(userTextField, 1, 1);
        
        Label name = new Label("Nimi: ");
        name.setFont(new Font(fontSize));
        grid.add(name, 0, 2);

        nameTextField = new TextField();
        nameTextField.setFont(new Font(fontSize));
        grid.add(nameTextField, 1, 2);
        
        logInButton = new Button("Kirjaudu sisään");
        logInButton.setFont(new Font(fontSize));
        logInButton.setStyle("-fx-background-color: #00ff00");
        
        createNewUserButton = new Button("Luo käyttäjä");
        createNewUserButton.setFont(new Font(fontSize));
        createNewUserButton.setStyle("-fx-background-color: #00ff00");
       
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(logInButton);
        hbBtn.getChildren().add(createNewUserButton);
        grid.add(hbBtn, 1, 4);
        
        return grid;
        
    }
    public Button getLogInButton() {
        return this.logInButton;
    }
    public Button getCreateNewuserButton() {
        return this.createNewUserButton;
    }
    public TextField getUserTextField() {
        return this.userTextField;
    }
    public TextField getNameTextField() {
        return this.nameTextField;
    }
}