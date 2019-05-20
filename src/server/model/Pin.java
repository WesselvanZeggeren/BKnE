package server.model;

public class Pin {

    private int x;
    private int y;
    private Client client;

    // getters
    public Pin(int x, int y) {

        this.x = x;
        this.y = y;
    }

    // getters
    public boolean hasClient() {

        return (this.client != null);
    }

    // setters
    public void setClient(Client client) {

        this.client = client;
    }
}
