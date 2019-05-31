package client.view;

import client.controller.interfaces.ClientInterface;
import client.controller.interfaces.SceneInterface;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;

public class GameScene implements SceneInterface {

    private ClientInterface observer;

    private String chatLog;

    public GameScene(ClientInterface observer, String chatLog) {

        this.observer = observer;
        this.chatLog = chatLog;
    }

    @Override
    public Scene getScene() {

        return new Scene(new TextArea(this.chatLog));
    }

    @Override
    public void update(Object object) {

    }
}
