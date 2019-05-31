package client.view;

import client.controller.interfaces.ClientInterface;
import client.controller.interfaces.SceneInterface;
import javafx.scene.Scene;

public class GameScene implements SceneInterface {

    private ClientInterface observer;

    public GameScene(ClientInterface observer) {

        this.observer = observer;
    }

    @Override
    public Scene getScene() {

        return null;
    }

    @Override
    public void update(Object object) {

    }
}
