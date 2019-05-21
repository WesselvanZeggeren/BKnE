package client.view;

import client.controller.interfaces.ControllerInterface;
import client.controller.interfaces.SceneInterface;
import javafx.scene.Scene;

public class LobbyScene implements SceneInterface {

    private ControllerInterface observer;

    public LobbyScene(ControllerInterface observer) {

        this.observer = observer;
    }

    @Override
    public Scene getScene() {
        return null;
    }

    @Override
    public void update() {

    }
}
