package client.view;

import client.controller.interfaces.ClientInterface;
import client.controller.interfaces.SceneInterface;
import both.Config;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class NameScene implements SceneInterface {

    // attributes
    private ClientInterface observer;
    private TextField textField;

    // constructor
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
        this.textField.setOnKeyPressed(this::keyPressed);

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
    public void update(Object object) {

        // empty
    }

    // events
    private void keyPressed(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER)
            mouseClicked();
    }
    private void mouseClicked() {

        String name = this.textField.getText();

        if (name.length() > 0) {

            this.observer.setScene(new LobbyScene(this.observer));

            this.observer.setName(name);
            this.observer.writeObject(name);
        }
    }
}
