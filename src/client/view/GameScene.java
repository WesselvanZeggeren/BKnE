package client.view;

import both.Config;
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
import org.jfree.fx.ResizableCanvas;

public class GameScene implements SceneInterface {

    private ClientInterface observer;
    private ResizableCanvas canvas;
    private TextField textField;
    private TextArea chat;
    private VBox players;

    private String chatLog;

    public GameScene(ClientInterface observer, String chatLog) {

        this.observer = observer;
        this.chatLog = chatLog;
    }

    @Override
    public Scene getScene() {

        this.textField = new TextField();
        this.textField.getStyleClass().add("gameScene-textField");
//        this.textField.setOnKeyPressed(this::keyPressed);

        this.players = new VBox();
        this.players.getStyleClass().add("gameScene-players");

        this.chat = new TextArea();
        this.chat.getStyleClass().add("gameScene-textArea");
        this.chat.setEditable(false);

        Button button = new Button("Send");
        button.getStyleClass().add("gameScene-button");
//        button.setOnMouseClicked((e) -> this.mouseClicked());

        VBox vBox1 = new VBox();
        vBox1.getStyleClass().add("gameScene-vBox");
        vBox1.getChildren().addAll(this.chat, this.textField, button);

        HBox hBox = new HBox();
        hBox.getStyleClass().add("gameScene-hBox");
        hBox.getChildren().addAll(vBox1, this.players);

        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("gameScene-borderPane");
        borderPane.setCenter(hBox);

        return new Scene(borderPane, Config.GAME_SCREEN_WIDTH, Config.GAME_SCREEN_HEIGHT);
    }

    @Override
    public void update(Object object) {


    }
}
