package client;

import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import util.Input;

import java.io.File;
import java.io.IOException;

public class ClientViewController {

    private Client client;
    private ObservableList<String> chatLog;
    private String username;

    @FXML
    private Label bannerLabel;

    @FXML
    private MenuBar menubar;

    @FXML
    private Menu fileMenu;

    @FXML
    private MenuItem btAddFile;

    @FXML
    private MenuItem btAddImage;

    @FXML
    private MenuItem btSaveChat;

    @FXML
    private MenuItem btLogout;

    @FXML
    private Menu optionMenu;

    @FXML
    private MenuItem btProfile;

    @FXML
    private MenuItem btSettings;

    @FXML
    private Menu gameMenu;

    @FXML
    private MenuItem btBattleships;

    @FXML
    private Menu musicMenu;

    @FXML
    private MenuItem btPlayMusic;

    @FXML
    private MenuItem btMuteMusic;

    @FXML
    private MenuItem btAddMusic;

    @FXML
    private Menu toolMenu;

    @FXML
    private MenuItem btScheduler;

    @FXML
    private Menu aboutMenu;

    @FXML
    private MenuItem btAbout;

    @FXML
    private ListView<?> userView;

    @FXML
    private ListView<String> chatView;

    @FXML
    private Button addButton;

    @FXML
    private JFXTextArea messageField;

    @FXML
    private Button btSend;

    @FXML
    private ListView<?> channelView;

    @FXML
    private ImageView userIcon;

    @FXML
    private Label usernameField;

    @FXML
    private ComboBox<?> statusMenu;

    @FXML
    private Circle statusIndicator;

    @FXML
    private TextField statusField;

    @FXML
    private Circle connectionIndicator;

    @FXML
    void btSendFile(ActionEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File selectedFile = chooser.showOpenDialog(addButton.getScene().getWindow());
        if(selectedFile!=null){
        client.sendFile(selectedFile);}
    }

    @FXML
    void btSendMessage(ActionEvent event) throws IOException {
        client.sendString(messageField.getText());
        messageField.clear();
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        client.outputStream.close();
        client.inputStream.close();
        Platform.exit();
        System.exit(0);
    }

    public void initialize(){
    }
    public void setUsername(){
        usernameField.setText(username);
    }
    public void setClient(Client client){
        this.client = client;
    }
    public void setChatView(){
        chatView.setItems(client.chatLog);;
    }
    public void receivedFile(Input input) throws IOException {
        FileChooser chooser = new FileChooser();
        String fileName = input.getFilename();
        //hacky way to display filename
        chooser.setInitialFileName(fileName+" ");
        chooser.setTitle("Received file, save as...");
        File selectedFile = chooser.showSaveDialog(addButton.getScene().getWindow());
        if(selectedFile!=null){
            client.readFileFromBytes(input, selectedFile);
        }
    }

}
