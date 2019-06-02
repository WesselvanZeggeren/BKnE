package client.view;

import both.Config;
import client.controller.interfaces.ClientInterface;
import client.controller.interfaces.SceneInterface;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import server.entity.GameEntity;
import server.entity.ClientEntity;

import java.awt.*;
import java.util.ArrayList;

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
        this.textField.setOnKeyPressed(this::keyPressed);

        this.players = new VBox();
        this.players.getStyleClass().add("lobbyScene-players");

        this.timer = new Label("60");
        this.timer.getStyleClass().add("lobbyScene-timer");

        this.chat = new TextArea();
        this.chat.getStyleClass().add("lobbyScene-textArea");
        this.chat.setEditable(false);

        Button button = new Button("Send");
        button.getStyleClass().add("lobbyScene-button");
        button.setOnMouseClicked((e) -> this.mouseClicked());

        VBox vBox = new VBox();
        vBox.getStyleClass().add("lobbyScene-vBox");
        vBox.getChildren().addAll(this.chat, this.textField, button);

        HBox hBox = new HBox();
        hBox.getStyleClass().add("lobbyScene-hBox");
        hBox.getChildren().addAll(vBox, this.players);

        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("lobbyScene-borderPane");
        borderPane.setCenter(hBox);

        return new Scene(borderPane, Config.GAME_SCREEN_WIDTH, Config.GAME_SCREEN_HEIGHT);
    }

    @Override
    public void update(Object object) {

        Platform.runLater(() -> {

            if (object instanceof String)
                this.printMessage((String) object);

            if (object instanceof GameEntity) {

                GameEntity gameEntity = (GameEntity) object;

                this.setClients(gameEntity.getClientEntities());
                this.startGame(gameEntity);
            }
        });
    }

    private void printMessage(String message) {

        this.chat.setText(this.chat.getText() + message + "\n");
        this.chat.setScrollTop(Double.MAX_VALUE);
    }

    private void startGame(GameEntity gameEntity) {

        if (gameEntity.isRunning())
            this.observer.setScene(new GameScene(this.observer, this.chat.getText(), gameEntity));
    }

    private void setClients(ArrayList<ClientEntity> clients) {

        this.players.getChildren().clear();

        for (ClientEntity clientEntity : clients) {

            Color color = clientEntity.getColor();

            Pane pane = new Pane();
            pane.getStyleClass().add("lobbyScene-pane");
            pane.setStyle("-fx-background-color: rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ");");

            Label label = new Label(clientEntity.getName());
            label.getStyleClass().add("lobbyScene-clientName");

            HBox hBox = new HBox();
            hBox.getChildren().addAll(pane, label);

            this.players.getChildren().add(hBox);
        }
    }

    // events
    private void keyPressed(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER)
            mouseClicked();
    }

    private void mouseClicked() {

        if (this.textField.getText().length() > 0) {

            this.observer.writeObject("<" + this.observer.getName() + "> " + this.textField.getText());
            this.textField.setText("");
        }
    }
}
