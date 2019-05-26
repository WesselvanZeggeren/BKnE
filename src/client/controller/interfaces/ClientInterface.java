package client.controller.interfaces;

import org.json.simple.JSONObject;

public interface ClientInterface {

    // connection
    void receiveJSON(JSONObject json);
    void sendJSON(String data);

    // all scenes
    void setScene(SceneInterface sceneInterface);
}
