package client.view;

import both.Config;
import client.controller.interfaces.ClientInterface;
import client.controller.interfaces.SceneInterface;
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
import org.json.simple.JSONObject;
import server.model.Client;
import server.model.Game;

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
        this.chat.setDisable(true);

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
    public void update(Object object) {

        if (object instanceof String)
            this.printMessage((String) object);

        if (object instanceof Game) {

            Game game = (Game) object;

            this.startGame(game);
            this.setClients(game.getClients());
        }
    }

    private void printMessage(String message) {

        this.chat.setText(this.chat.getText() + message + "\n");
    }

    private void startGame(Game game) {

        if (game.isRunning())
            this.observer.setScene(new GameScene(this.observer));
    }

    private void setClients(ArrayList<Client> clients) {

        for (Client client : clients) {

            Color color = client.getColor();

            Pane pane = new Pane();
            pane.getStylesheets().add("lobbyScene-pane");
            pane.setStyle("-fx-background-color: rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ");");

            Label label = new Label(client.getName());
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
