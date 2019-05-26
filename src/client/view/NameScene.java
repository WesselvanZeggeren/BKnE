package client.view;

import client.controller.interfaces.ClientInterface;
import client.controller.interfaces.SceneInterface;
import both.Config;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONObject;

public class NameScene implements SceneInterface {

    // attributes
    private ClientInterface observer;
    private TextField textField;

    // constructer
    public NameScene(ClientInterface observer) {

        this.observer = observer;
    }

    // methods
    @Override
    public Scene getScene() {

        Label label = new Label("Please fill in your name!");
        label.getStyleClass().add("nameScene-label");

        this.textField = new TextField();
        this.textField.getStyleClass().add("nameScene-textField");

        Button button = new Button("Start");
        button.getStyleClass().add("nameScene-button");
        button.setOnMouseClicked((e) -> this.mouseClicked());

        VBox vBox = new VBox();
        vBox.getStyleClass().add("nameScene-vBox");
        vBox.getChildren().addAll(label, this.textField, button);

        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("nameScene-borderPane");
        borderPane.setCenter(vBox);

        return new Scene(borderPane, Config.GAME_SCREEN_WIDTH, Config.GAME_SCREEN_HEIGHT);
    }

    @Override
    public void update(JSONObject json) {

        // empty
    }

    // events
    private void mouseClicked() {

        if (this.textField.getText().length() > 0) {

            this.observer.sendJSON("{\"name\": \"" + this.textField.getText() + "\"}");
            this.observer.setScene(new LobbyScene(this.observer));
        }
    }
}
