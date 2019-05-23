package client.controller.interfaces;

import client.model.GameData;
import javafx.scene.Scene;

public interface SceneInterface {

    Scene getScene();
    void update(GameData gameData);
}
