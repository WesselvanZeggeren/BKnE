package client.controller.interfaces;

public interface ClientInterface {

    // connection
    void receiveData(String data);
    void sendData(String data);

    // all scenes
    void setScene(SceneInterface sceneInterface);
}
