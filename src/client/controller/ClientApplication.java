package client.controller;

import client.controller.interfaces.ClientInterface;
import client.controller.interfaces.SceneInterface;
import client.model.ServerConnection;
import client.view.NameScene;
import both.Config;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

public class ClientApplication extends Application implements ClientInterface {

    // attributes
    private ServerConnection serverConnection;
    private SceneInterface scene;
    private JSONObject json;
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

            this.setAnimationTimer();
        }
    }

    // main loop
    private void setAnimationTimer() {

        new AnimationTimer() {

            private long last  = -1;

            @Override
            public void handle(long now) {

                if (this.last == -1)
                    this.last = now;

                if ((now - this.last) / 1000000.0 > (1000.0 / Config.GAME_FPS)) {

                    this.last = now;
                    scene.update(json);
                }
            }
        }.start();
    }

    // observer
    @Override
    public void receiveData(JSONObject json) {

        this.json = json;
    }

    @Override
    public void sendData(String json) {

        System.out.println("send: " + json);
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
