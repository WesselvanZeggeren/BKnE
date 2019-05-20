package client.view;

import client.controller.Controller;
import client.controller.interfaces.ControllerInterface;
import client.controller.interfaces.SceneInterface;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class NameScene implements SceneInterface {

    // attributes
    private ControllerInterface observer;
    private BorderPane borderPane;
    private HBox nameBox;

    // constructer
    public NameScene(ControllerInterface observer) {

        this.observer = observer;

        this.borderPane = new BorderPane();
        this.nameBox = new HBox();
    }

    // methods
    @Override
    public Scene getScene() {

        Label label = new Label("Please fill in your name!");
        label.getStyleClass().add("nameScene-label");

        TextField textField = new TextField();
        textField.getStyleClass().add("nameScene-textField");
        textField.setOnMouseClicked(this::mouseClicked);

        this.nameBox.getChildren().addAll(label, textField);
        this.update();

        return new Scene(this.borderPane);
    }

    @Override
    public void update() {}

    // events
    private void mouseClicked(MouseEvent e) {

    }
}
