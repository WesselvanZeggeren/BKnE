package client.view;

import both.Config;
import both.JSONModel;
import client.controller.interfaces.ClientInterface;
import client.controller.interfaces.SceneInterface;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.simple.JSONObject;

public class LobbyScene implements SceneInterface {

    // attributes
    private ClientInterface observer;
    private TextField textField;
    private TextArea chat;
    private Label timer;
    private VBox players;

    // constructor
    public LobbyScene(ClientInterface observer) {

        this.observer = observer;
    }

    // methods
    @Override
    public Scene getScene() {

        this.textField = new TextField();
        this.textField.getStyleClass().add("lobbyScene-textField");

        this.players = new VBox();
        this.players.getStyleClass().add("lobbyScene-players");

        this.timer = new Label();
        this.timer.getStyleClass().add("lobbyScene-timer");

        this.chat = new TextArea();
        this.chat.getStyleClass().add("lobbyScene-textArea");

        Button button = new Button("Send");
        button.getStyleClass().add("lobbyScene-button");
        button.setOnMouseClicked((e) -> this.mouseClicked());

        VBox vBox1 = new VBox();
        vBox1.getStyleClass().add("lobbyScene-vBox");
        vBox1.getChildren().addAll(this.chat, this.textField, button);

        HBox hBox = new HBox();
        hBox.getStyleClass().add("lobbyScene-hBox");
        hBox.getChildren().addAll(vBox1, this.timer, this.players);

        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("lobbyScene-borderPane");
        borderPane.setCenter(hBox);

        return new Scene(borderPane, Config.GAME_SCREEN_WIDTH, Config.GAME_SCREEN_HEIGHT);
    }

    @Override
    public void update(JSONObject json) {

        JSONObject trigger = (JSONObject) json.get("trigger");

        if (!json.get("message").equals(""))
            this.chat.setText(this.chat.getText() + "\n<" + trigger.get("name") + "> " + json.get("message"));
    }

    // events
    private void mouseClicked() {

        if (this.textField.getText().length() > 0) {

            this.observer.sendJSON("{\"message\": \"" + this.textField.getText() + "\"}");
        }
    }
}
