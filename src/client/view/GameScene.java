package client.view;

import both.Config;
import client.controller.interfaces.ClientInterface;
import client.controller.interfaces.SceneInterface;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;
import server.entity.GameEntity;

import java.awt.*;

public class GameScene implements SceneInterface {

    // attributes
    private ClientInterface observer;
    private ResizableCanvas canvas;
    private TextField textField;
    private String chatLog;
    private TextArea chat;
    private VBox players;

    // startup
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

        VBox vBox = new VBox();
        vBox.getStyleClass().add("gameScene-vBox");
        vBox.getChildren().addAll(this.chat, this.textField, button);

        BorderPane mainBox = new BorderPane();
        mainBox.getStyleClass().add("gameScene-hBox");
        mainBox.setLeft(vBox);

        this.canvas = new ResizableCanvas(this::draw, mainBox);
        this.draw(new FXGraphics2D(this.canvas.getGraphicsContext2D()));

        mainBox.setRight(this.players);

        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("gameScene-borderPane");
        borderPane.setCenter(mainBox);

        return new Scene(borderPane, Config.GAME_SCREEN_WIDTH, Config.GAME_SCREEN_HEIGHT);
    }

    // methods
    @Override
    public void update(Object object) {

        if (object instanceof GameEntity) {

            this.draw(new FXGraphics2D(this.canvas.getGraphicsContext2D()));
        }
    }

    // canvas
    public void draw(FXGraphics2D graphics2D) {

        graphics2D.setColor(Color.WHITE);
        graphics2D.drawRect(0, 0, 1000, 1000);
    }

    // events
}
