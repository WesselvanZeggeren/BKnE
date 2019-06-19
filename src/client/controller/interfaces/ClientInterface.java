package client.controller.interfaces;

public interface ClientInterface {

    // connection
    void receiveObject(Object object);
    void writeObject(Object object);

    // all scenes
    void setScene(SceneInterface sceneInterface);
    void setName(String name);

    String getName();
}
