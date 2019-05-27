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

public class ClientApplication extends Application implements ClientInterface {

    // attributes
    private ServerConnection serverConnection;
    private SceneInterface scene;
    private Stage stage;

    // start
    public void startup() {

        launch(ClientApplication.class);
    }

    @Override
    public void start(Stage stage) {

        this.serverConnection = new ServerConnection(this);

        if (this.serverConnection.connect()) {

            this.stage = stage;
            this.stage.setTitle(Config.GAME_TITLE);
            this.stage.show();

            this.setScene(new NameScene(this));
        }
    }

    // observer
    @Override
    public void receiveJSON(JSONObject json) {

        this.scene.update(json);
    }

    @Override
    public void sendJSON(String json) {

        this.serverConnection.writeUTF(json);
    }

    @Override
    public void setScene(SceneInterface sceneInterface) {

        this.scene = sceneInterface;

        Scene scene = this.scene.getScene();
        scene.getStylesheets().add(Config.CSS_PATH);

        this.stage.setScene(scene);
    }
}
