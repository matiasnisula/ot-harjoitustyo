
package opintopaivakirjasovellus.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CreateUserView {
    private final double fontSize = 40.0;
    private Button logInButton;
    private Button createNewUserButton;
    private TextField usernameTextField;
    private TextField nameTextField;
    private Label messages;
    
    public Parent getView() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Label userName = new Label("Käyttäjänimi:");
        userName.setFont(new Font(fontSize));
        grid.add(userName, 0, 1);
        
        usernameTextField = new TextField();
        usernameTextField.setFont(new Font(fontSize));
        grid.add(usernameTextField, 1, 1);
        
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
        
        messages = new Label("");
        messages.setFont(new Font(fontSize));
        messages.setTextFill(Color.RED);
        grid.add(messages, 1,7);
       
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
    public TextField getUsernameTextField() {
        return this.usernameTextField;
    }
    public TextField getNameTextField() {
        return this.nameTextField;
    }
    public Label getUserInfo() {
        return this.messages;
    }
}
