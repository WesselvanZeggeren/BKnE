package client.controller.interfaces;

import org.json.simple.JSONObject;

public interface ClientInterface {

    // connection
    void receiveData(JSONObject json);
    void sendData(String data);

    // all scenes
    void setScene(SceneInterface sceneInterface);
}
