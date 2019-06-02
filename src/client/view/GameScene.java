package client.view;

import both.Config;
import client.controller.interfaces.ClientInterface;
import client.controller.interfaces.SceneInterface;
import both.Texture;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;
import server.entity.ClientEntity;
import server.entity.GameEntity;
import server.entity.PinEntity;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class GameScene implements SceneInterface {

    // attributes
    private ClientInterface observer;
    private ResizableCanvas canvas;
    private TextField textField;
    private TextArea chat;
    private VBox players;

    private HashMap<Rectangle2D, PinEntity> squares;
    private HashMap<Rectangle2D, Color> texture;

    private boolean isBuild = false;

    private int boardOffset;
    private int size;

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
//        this.textField.setOnKeyPressed(this::keyPressed);

        this.players = new VBox();
        this.players.getStyleClass().add("gameScene-players");

        this.chat = new TextArea();
        this.chat.getStyleClass().add("gameScene-textArea");
        this.chat.setEditable(false);

        Button button = new Button("Send");
        button.getStyleClass().add("gameScene-button");
//        button.setOnMouseClicked((e) -> this.mouseClicked());

        VBox vBox = new VBox();
        vBox.getStyleClass().add("gameScene-vBox");
        vBox.getChildren().addAll(this.chat, this.textField, button);

        BorderPane canvasPane = new BorderPane();
        this.canvas = new ResizableCanvas(this::draw, canvasPane);
        this.canvas.setWidth(350);
        this.canvas.setHeight(100);
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

        if (object instanceof GameEntity) {

            this.gameEntity = (GameEntity) object;

            if (!this.isBuild)
                this.buildBoard();

            this.draw(new FXGraphics2D(this.canvas.getGraphicsContext2D()));
        }
    }

    public void buildBoard() {

        this.size = this.gameEntity.getSize();

        this.setBoard();
        this.setSquares();

        this.isBuild = true;
    }

    // canvas
    public void draw(FXGraphics2D graphics2D) {

        graphics2D.setColor(Color.getHSBColor(.5f, .64f, .73f));
        graphics2D.fill(new Rectangle2D.Double(0.0, 0.0, this.canvas.getWidth(), this.canvas.getHeight()));

        if (this.isBuild) {

            this.drawBoard(graphics2D);
            this.drawPins(graphics2D);
        }
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

    // events
    private void mouseClickedCanvas(MouseEvent mouseEvent) {

        for (Rectangle2D rectangle2D : this.squares.keySet())
            if (rectangle2D.contains(mouseEvent.getX(), mouseEvent.getY()))
                this.observer.writeObject(this.squares.get(rectangle2D));
    }

    // setters
    private void setBoard() {

        this.texture = Texture.getTexture(
            this.getBoardSize(),
            this.getBoardSize(),
            Color.getHSBColor(.03f, .65f, .2f)
        );
    }

    private void setSquares() {

        this.squares = new HashMap<>();

        for (PinEntity pinEntity : this.gameEntity.getPinEntities()) {

            pinEntity.setTexture(Texture.getTexture(
                this.getSquareSize(),
                this.getSquareSize(),
                Color.getHSBColor(.11f, .4f, .9f),
                Color.getHSBColor(.09f, .2f, .7f)
            ));

            this.squares.put(new Rectangle2D.Double(
                this.getSquareOffsetX(pinEntity.getX()) - 1,
                this.getSquareOffsetY(pinEntity.getY()) + 145,
                this.getSquareSize(),
                this.getSquareSize()
            ), pinEntity);
        }
    }

    // getters
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
}
