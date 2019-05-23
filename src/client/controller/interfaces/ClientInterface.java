package client.controller.interfaces;

import client.model.GameData;

public interface ClientInterface {

    // connection
    void receiveData(GameData gamedata);
    void sendData(String data);

    // all scenes
    void setScene(SceneInterface sceneInterface);
}
