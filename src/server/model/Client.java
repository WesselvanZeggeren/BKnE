package server.model;

import server.controller.interfaces.ServerInterface;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements Runnable, Serializable {

    // attributes
    private Game game;
    private Socket socket;
    private ArrayList<Pin> pins;
    private ServerInterface observer;

    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;

    private Color color;
    private String name = "";

    private boolean isPlaying = true;
    private boolean isRunning = true;

    // constructor
    public Client (Socket socket, ServerInterface observer) {

        this.socket = socket;
        this.observer = observer;
        this.color = new Color(0, 0, 0);
        this.pins = new ArrayList<>();
    }

    // connection
    @Override
    public void run() {

        try {

            this.objectIn  = new ObjectInputStream(this.socket.getInputStream());
            this.objectOut = new ObjectOutputStream(this.socket.getOutputStream());

            this.manageObjectInput();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private void manageObjectInput() {

        new Thread(() -> { while (this.isRunning) {

            try {

                Object object = this.objectIn.readObject();

                if (this.name.length() == 0 && object instanceof String) {

                    this.name = (String) object;
                    this.observer.addToGame(this);
                } else {

                    this.observer.receiveObject(this, object);
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
        }}).start();
    }

    public void writeObject(Object object) {

        try {

            this.objectOut.writeObject(object);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    // methods
    public boolean containsPin(int x, int y) {

        for (Pin pin : this.pins)
            if (pin.getX() == x && pin.getY() == y)
                return true;

        return false;
    }

    // setters
    public void setGame(Game game) {

        this.game = game;
    }

    public void setPins(ArrayList<Pin> pins) {

        this.pins = pins;
    }

    public void addPin(Pin pin) {

        this.pins.add(pin);
    }

    // getters
    public String getName() {

        return this.name;
    }

    public Color getColor() {

        return this.color;
    }

    public Game getGame() {

        return this.game;
    }

    public boolean isInGame() {

        return (this.game != null);
    }
}
