package client.controller.interfaces;

import javafx.scene.Scene;
import org.json.simple.JSONObject;

public interface SceneInterface {

    Scene getScene();
    void update(JSONObject jsonObject);
}
