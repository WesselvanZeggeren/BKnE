package client.controller.interfaces;

import javafx.scene.Scene;
import org.json.simple.JSONObject;
import server.model.Game;

public interface SceneInterface {

    Scene getScene();

    void update(Object object);
}
