package Client;

import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import util.Message;

import java.io.*;

public class ClientViewController {

    private Client client;
    private ObservableList<String> chatLog;
    private String username;
    private String userImage;

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
    private TextArea chat;

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
    void btSendMessage(ActionEvent event) throws IOException {
        String msg = messageField.getText();
        System.out.println(msg);
        Message m = new Message(msg);
        client.sendMessage(m);
        messageField.clear();
        chat.appendText(client.loadFile() + ": " + client.getMessage() + "\n");

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

    public void setClient(Client client){
        this.client = client;
    }
}
