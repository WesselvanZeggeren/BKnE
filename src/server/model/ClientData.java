package server.model;

public class ClientData {

    // attributes
    private int x;
    private int y;
    private Client client;
    private String message;

    // constructor
    public ClientData(Client client) {

        this.client = client;
    }

    // getters
    public int getX() {

        return this.x;
    }

    public int getY() {

        return this.y;
    }

    public String getMessage() {

        return this.message;
    }

    public Client getClient() {

        return this.client;
    }

    // setters
    public void setX(int x) {

        this.x = x;
    }

    public void setY(int y) {

        this.y = y;
    }

    public void setMessage(String message) {

        this.message = message;
    }
}
