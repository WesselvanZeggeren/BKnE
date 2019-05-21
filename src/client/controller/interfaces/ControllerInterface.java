package client.controller.interfaces;

public interface ControllerInterface {

    // connection
    void receiveData(String data);
    void sendData(String data);

    // all scenes
    void setScene(SceneInterface sceneInterface);
}
