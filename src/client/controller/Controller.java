package client.controller;

import client.controller.interfaces.ControllerInterface;
import client.controller.interfaces.SceneInterface;
import client.model.Connection;
import client.view.NameScene;
import config.Config;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public class Controller extends Application implements ControllerInterface {

    // attributes
    private Connection connection;
    private SceneInterface scene;
    private Stage stage;

    // start
    public void startup() {

        launch(Controller.class);
    }

    @Override
    public void start(Stage stage) {

        this.connection = new Connection(this);

        if (this.connection.connect()) {

            this.scene = new NameScene();

            this.stage = stage;
            this.stage.setScene(this.scene.getScene());
            this.stage.setTitle("Boter Kaas & Eieren - Battle Royale");
            this.stage.show();

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

    // connection
    @Override
    public void receiveData(String message) {

    }

    // setters
    public void setScene(SceneInterface scene) {

        this.scene = scene;
        this.stage.setScene(this.scene.getScene());
    }
}
