package client.controller;

import both.JSONModel;
import client.controller.interfaces.ClientInterface;
import client.controller.interfaces.SceneInterface;
import client.model.GameData;
import client.model.ServerConnection;
import client.view.NameScene;
import both.Config;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApplication extends Application implements ClientInterface {

    // attributes
    private ServerConnection serverConnection;
    private SceneInterface scene;
    private GameData gameData;
    private Stage stage;

    // start
    public void startup() {

        launch(ClientApplication.class);
    }

    @Override
    public void start(Stage stage) {

        this.serverConnection = new ServerConnection(this);
        this.gameData = new GameData();

        if (this.serverConnection.connect()) {

            this.stage = stage;
            this.stage.setTitle("Boter Kaas & Eieren - Battle Royale");
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
                    scene.update();
                }
            }
        }.start();
    }

    // observer
    @Override
    public void receiveData(String json) {

        this.gameData = JSONModel.convertClientJSON(json);
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
