package client.controller;

import client.controller.interfaces.ClientInterface;
import client.controller.interfaces.SceneInterface;
import client.model.ServerConnection;
import client.view.NameScene;
import both.Config;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import server.model.Game;
import server.model.Pin;

public class ClientApplication extends Application implements ClientInterface {

    // attributes
    private ServerConnection serverConnection;
    private SceneInterface scene;
    private Stage stage;

    private String name;

    // start
    public void startup() {

        launch(ClientApplication.class);
    }

    @Override
    public void start(Stage stage) {

        this.serverConnection = new ServerConnection(this);

        System.out.println("build");

        if (this.serverConnection.connect()) {

            this.stage = stage;
            this.stage.setTitle(Config.GAME_TITLE);
            this.stage.show();

            System.out.println("scene0");

            this.setScene(new NameScene(this));
        }
    }

    // connection observer
    @Override
    public void receiveObject(Object object) {

        this.scene.update(object);
    }

    @Override
    public void writeObject(Object object) {

        this.serverConnection.writeObject(object);
    }

    // scene observer
    @Override
    public void setScene(SceneInterface sceneInterface) {

        this.scene = sceneInterface;

        Scene scene = this.scene.getScene();
        scene.getStylesheets().add(Config.CSS_PATH);

        this.stage.setScene(scene);
    }

    @Override
    public void setName(String name) {

        this.name = name;
    }

    @Override
    public String getName() {

        return this.name;
    }
}
