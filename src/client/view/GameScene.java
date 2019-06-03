package client.view;

import both.Config;
import client.controller.interfaces.ClientInterface;
import client.controller.interfaces.SceneInterface;
import both.Texture;
import client.model.CodesModel;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;
import server.entity.ClientEntity;
import server.entity.GameEntity;
import server.entity.PinEntity;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameScene implements SceneInterface {

    // attributes
    private ClientInterface observer;
    private ResizableCanvas canvas;
    private TextField textField;
    private TextArea chat;
    private VBox players;

    private HashMap<Rectangle2D, PinEntity> squares;
    private HashMap<Rectangle2D, Color> texture;

    private int size;
    private boolean rebuild = false;

    private GameEntity gameEntity;
    private String chatLog;

    // startup
    public GameScene(ClientInterface observer, String chatLog, GameEntity gameEntity) {

        this.observer   = observer;
        this.chatLog    = chatLog;
        this.gameEntity = gameEntity;
    }

    @Override
    public Scene getScene() {

        this.textField = new TextField();
        this.textField.getStyleClass().add("gameScene-textField");
        this.textField.setOnKeyPressed(this::keyPressed);

        this.players = new VBox();
        this.players.getStyleClass().add("gameScene-players");

        this.chat = new TextArea();
        this.chat.getStyleClass().add("gameScene-textArea");
        this.chat.setText(this.chatLog);
        this.chat.setEditable(false);

        Button button = new Button("Send");
        button.getStyleClass().add("gameScene-button");
        button.setOnMouseClicked((e) -> this.mouseClicked());

        VBox vBox = new VBox();
        vBox.getStyleClass().add("gameScene-vBox");
        vBox.getChildren().addAll(this.chat, this.textField, button);

        BorderPane canvasPane = new BorderPane();
        this.canvas = new ResizableCanvas(this::draw, canvasPane);
        this.canvas.setWidth(350);
        this.canvas.setHeight(350);
        this.canvas.setOnMouseClicked(this::mouseClickedCanvas);
        canvasPane.getStyleClass().add("gameLobby-canvasPane");
        canvasPane.setCenter(this.canvas);

        HBox hBox = new HBox();
        hBox.getStyleClass().add("gameScene-hBox");
        hBox.getChildren().addAll(vBox, canvasPane, this.players);

        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("gameScene-borderPane");
        borderPane.setCenter(hBox);

        this.update(this.gameEntity);

        return new Scene(borderPane, Config.GAME_SCREEN_WIDTH, Config.GAME_SCREEN_HEIGHT);
    }

    // methods
    @Override
    public void update(Object object) {

        if (object instanceof String)
            this.printMessage((String) object);

        if (object instanceof GameEntity) {

            this.gameEntity = (GameEntity) object;

            this.draw(new FXGraphics2D(this.canvas.getGraphicsContext2D()));

            Platform.runLater(this::setAllClients);
        }
    }

    private void setAllClients() {

        this.players.getChildren().clear();

        this.setClients(this.gameEntity.getClientEntitiesOrder(), true);
        this.setClients(this.gameEntity.getOtherClientEntities(), false);
    }

    private void setClients(ArrayList<ClientEntity> clients, boolean placeIterator) {

        for (int i = 0; i < clients.size(); i++) {

            Color color = clients.get(i).getColor();

            Label preset = new Label();
            preset.getStyleClass().add("gameScene-preset");

            if (placeIterator)
                preset.setText(String.valueOf(i + 1));
            else if (i + (this.gameEntity.getClientEntitiesOrder().size()) == this.gameEntity.getKey())
                preset.setText(">");

            Pane pane = new Pane();
            pane.getStyleClass().add("gameScene-pane");
            pane.setStyle("-fx-background-color: rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ");");

            javafx.scene.control.Label label = new Label(clients.get(i).getName());
            label.getStyleClass().add("gameScene-clientName" + ((!clients.get(i).isPlaying()) ? "-n" : ""));

            HBox hBox = new HBox();
            hBox.getStyleClass().add("gameScene-playerBox");
            hBox.getChildren().addAll(preset, pane, label);

            this.players.getChildren().add(hBox);
        }
    }

    // canvas
    public void draw(FXGraphics2D graphics2D) {

        if (this.size != this.gameEntity.getSize() || this.rebuild) {

            this.size = this.gameEntity.getSize();

            this.setTexture();
            this.setSquares();
        }

        graphics2D.setColor(Color.getHSBColor(.5f, .64f, .73f));
        graphics2D.fill(new Rectangle2D.Double(0.0, 0.0, this.canvas.getWidth(), this.canvas.getHeight()));

        this.drawBoard(graphics2D);
        this.drawPins(graphics2D);

        this.updateSquares();
    }

    public void drawBoard(FXGraphics2D graphics2D) {

        Texture.setTexture(graphics2D, this.texture, this.getBoardOffsetX(), this.getBoardOffsetY());

        for (PinEntity pinEntity : this.squares.values()) {

            Texture.setTexture(
                graphics2D,
                pinEntity.getTexture(),
                this.getSquareOffsetX(pinEntity.getX()),
                this.getSquareOffsetY(pinEntity.getY())
            );
        }

        graphics2D.setColor(Config.BOARD_SQUARE_COLOR_1);
        for (Rectangle2D rectangle2D : this.squares.keySet())
            graphics2D.draw(rectangle2D);
    }

    public void drawPins(FXGraphics2D graphics2D) {

        for (ClientEntity clientEntity : this.gameEntity.getClientEntities()) {

            for (PinEntity pinEntity : clientEntity.getPinEntities()) {

                Texture.setTexture(
                    graphics2D,
                    pinEntity.getTexture(),
                    this.getSquareOffsetX(pinEntity.getX()),
                    this.getSquareOffsetY(pinEntity.getY())
                );
            }
        }
    }

    public void updateSquares() {

        for (Map.Entry<Rectangle2D, PinEntity> entry : this.squares.entrySet())
            entry.getKey().setRect(this.getRectangle2D(entry.getValue()));
    }

    // events
    private void mouseClickedCanvas(MouseEvent mouseEvent) {

        if (mouseEvent.isAltDown()) {

            this.rebuild = true;

            this.update(this.gameEntity);

            this.rebuild = false;

        } else  {

            for (Map.Entry<Rectangle2D, PinEntity> entry : this.squares.entrySet())
                if (entry.getKey().contains(mouseEvent.getX(), mouseEvent.getY()))
                    this.observer.writeObject(entry.getValue());
        }
    }

    // setters
    private void setTexture() {

        this.texture = Texture.getTexture(
            this.getBoardSize(),
            this.getBoardSize(),
            Config.BOARD_COLOR
        );

        for (PinEntity pinEntity : this.gameEntity.getPinEntities()){

            pinEntity.setTexture(Texture.getTexture(
                this.getSquareSize(),
                this.getSquareSize(),
                Config.BOARD_SQUARE_COLOR_1,
                Config.BOARD_SQUARE_COLOR_2
            ));
        }
    }

    private void setSquares() {

        this.squares = new HashMap<>();

        for (PinEntity pinEntity : this.gameEntity.getPinEntities())
            this.squares.put(this.getRectangle2D(pinEntity), pinEntity);
    }

    // getters
    private Rectangle2D getRectangle2D(PinEntity pinEntity) {

        return new Rectangle2D.Double(
            this.getSquareOffsetX(pinEntity.getX()),
            this.getSquareOffsetY(pinEntity.getY()),
            this.getSquareSize(),
            this.getSquareSize()
        );
    }

    private void printMessage(String message) {

        this.chat.setText(this.chat.getText() + message + "\n");

        if (message.contains("/"))
            CodesModel.cheatCode(message.substring(message.indexOf("/")));

        this.chat.setScrollTop(Double.MAX_VALUE);
    }

    private double getBoardSize() {

        return (Config.BOARD_BORDER_SIZE + ((Config.BOARD_SQUARE_SIZE + Config.BOARD_BORDER_SIZE) * this.size));
    }

    private double getBoardOffsetX() {

        return (this.canvas.getWidth() - this.getBoardSize()) / 2;
    }

    private double getBoardOffsetY() {

        return (this.canvas.getHeight() - this.getBoardSize()) / 2;
    }

    private double getSquareSize() {

        return Config.BOARD_SQUARE_SIZE;
    }

    private double getSquareOffsetX(int x) {

        return ((getSquareSize() + Config.BOARD_BORDER_SIZE) * x) + (this.getBoardOffsetX() + Config.BOARD_BORDER_SIZE);
    }

    private double getSquareOffsetY(int y) {

        return ((getSquareSize() + Config.BOARD_BORDER_SIZE) * y) + (this.getBoardOffsetY() + Config.BOARD_BORDER_SIZE);
    }

    private void keyPressed(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER)
            mouseClicked();
    }

    private void mouseClicked() {

        if (this.textField.getText().length() > 0) {

            this.observer.writeObject("<" + this.observer.getName() + "> " + this.textField.getText());
            this.textField.setText("");
        }
    }
}
