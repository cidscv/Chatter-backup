package client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.User;
import util.Input;


/*
Basic login/register page for chatter application by Owen Reid - 03/21/2020
This program will display the user interface the user will
see when they launch the application and it will allow the user
to login and register with the application.

No functionality, just the interface
 */
public class Login extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        homepage(primaryStage);

    }

    // The first page users will see when the application launches
    private void homepage(Stage primaryStage) {

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        GridPane grid = new GridPane();
        grid.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, CornerRadii.EMPTY, Insets.EMPTY)));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Image logo = new Image("client/images/Chatter.png");
        ImageView logoView = new ImageView(logo);

        logoView.setEffect(dropShadow);
        logoView.setFitHeight(200);
        logoView.setFitWidth(200);

        grid.add(logoView, 1, 0, 2, 1);

        Button loginbtn = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(loginbtn);
        grid.add(hbBtn, 1, 2);
        Button registerbtn = new Button("Register");
        HBox reghbbtn = new HBox(10);
        reghbbtn.setAlignment(Pos.BOTTOM_RIGHT);
        reghbbtn.getChildren().add(registerbtn);
        grid.add(reghbbtn, 2, 2);

        loginbtn.setOnAction(e -> loginpage(primaryStage));
        registerbtn.setOnAction(e -> registerpage(primaryStage));

        primaryStage.setTitle("Home Page");
        Scene scene = new Scene(grid, 300, 275, Color.LIGHTPINK);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // The page users will use to log in to accounts already registered with the application
    private void loginpage(Stage primaryStage) {

        primaryStage.hide();
        Stage loginstage = new Stage();
        GridPane grid = new GridPane();
        grid.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, CornerRadii.EMPTY, Insets.EMPTY)));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button logbtn = new Button("Login");

        Button gobackbtn = new Button("Go back");

        HBox logBtn = new HBox(5);
        logBtn.setAlignment(Pos.BOTTOM_LEFT);
        logBtn.getChildren().add(logbtn);
        grid.add(logBtn, 1, 3);

        HBox gobackbutton = new HBox(5);
        gobackbutton.setAlignment(Pos.BOTTOM_RIGHT);
        gobackbutton.getChildren().add(gobackbtn);
        grid.add(gobackbutton, 1, 3);

        loginstage.setTitle("Login Page");
        Scene scene2 = new Scene(grid, 300, 275, Color.LIGHTPINK);
        loginstage.setScene(scene2);
        loginstage.show();

        logBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                User establisheduser = new User(UserTextField.getText(), pwBox.getText());
                Input reginput = new Input("login");
                reginput.setUser(establisheduser);
            }
        });

        // Button allows user to return to home page if mistake has been made
        gobackbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                loginstage.hide();
                homepage(primaryStage);
            }
        });

    }

    // The page users will go to if they need a to register a new account with the application
    private void registerpage(Stage primaryStage) {
        primaryStage.hide();
        Stage registerstage = new Stage();
        GridPane grid = new GridPane();
        grid.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, CornerRadii.EMPTY, Insets.EMPTY)));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label FullName = new Label("Full Name:");
        grid.add(FullName, 0, 0);

        TextField nameTextField = new TextField();
        grid.add(nameTextField, 1, 0);

        Label DOB = new Label("Date of Birth:");
        grid.add(DOB, 0, 1);

        DatePicker birthdate = new DatePicker();
        grid.add(birthdate, 1, 1);

        Label UserName = new Label("User Name:");
        grid.add(UserName, 0, 2);

        TextField UserTextField = new TextField();
        grid.add(UserTextField, 1, 2);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 3);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 3);

        Button registerbtn = new Button("Register");

        Button gobackbtn = new Button("Go back");

        HBox regBtn = new HBox(5);
        regBtn.setAlignment(Pos.BOTTOM_LEFT);
        regBtn.getChildren().add(registerbtn);
        grid.add(regBtn, 1, 4);

        HBox gobackbutton = new HBox(5);
        gobackbutton.setAlignment(Pos.BOTTOM_RIGHT);
        gobackbutton.getChildren().add(gobackbtn);
        grid.add(gobackbutton, 1, 4);

        registerstage.setTitle("Registration Page");
        Scene scene3 = new Scene(grid, 500, 275, Color.LIGHTPINK);
        registerstage.setScene(scene3);
        registerstage.show();

        registerbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                User newuser = new User(UserTextField.getText(), pwBox.getText());
                Input reginput = new Input("register");
                reginput.setUser(newuser);


            }
        });

        gobackbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                registerstage.hide();
                homepage(primaryStage);
            }
        });


    }


    public static void main(String[] args) {
        launch(args);
    }

}
