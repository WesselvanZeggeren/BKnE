package client.view;

import both.Config;
import client.controller.interfaces.ClientInterface;
import client.controller.interfaces.SceneInterface;
import client.model.GameData;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class LobbyScene implements SceneInterface {

    // attributes
    private ClientInterface observer;
    private TextField textField;
    private TextArea chat;

    // constructor
    public LobbyScene(ClientInterface observer) {

        this.observer = observer;
    }

    // methods
    @Override
    public Scene getScene() {

        Label label = new Label("Waiting for players!");
        label.getStyleClass().add("LobbyScene-label");

        this.textField = new TextField();
        this.textField.getStyleClass().add("LobbyScene-textField");

        Button button = new Button("Send");
        button.getStyleClass().add("LobbyScene-button");
        button.setOnMouseClicked((e) -> this.mouseClicked());

        VBox vBox = new VBox();
        vBox.getStyleClass().add("LobbyScene-vBox");
        vBox.getChildren().addAll(label, this.textField, button);

        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("LobbyScene-borderPane");
        borderPane.setCenter(vBox);

        return new Scene(borderPane, Config.GAME_SCREEN_WIDTH, Config.GAME_SCREEN_HEIGHT);
    }

    @Override
    public void update(GameData gameData) {

        // empty
    }

    // events
    private void mouseClicked() {

        if (this.textField.getText().length() > 0) {

            this.observer.sendData("{\"name\":\"" + this.textField.getText() + "\"}");
            this.observer.setScene(new LobbyScene(this.observer));
        }
    }
}
