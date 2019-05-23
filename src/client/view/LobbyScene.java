package client.view;

import client.controller.interfaces.ClientInterface;
import client.controller.interfaces.SceneInterface;
import client.model.GameData;
import javafx.scene.Scene;

public class LobbyScene implements SceneInterface {

    private ClientInterface observer;

    public LobbyScene(ClientInterface observer) {

        this.observer = observer;
    }

    @Override
    public Scene getScene() {

        return null;
    }

    @Override
    public void update(GameData gameData) {

    }
}
