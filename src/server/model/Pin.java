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

    // toString
    @Override
    public String toString() {

        StringBuilder pinString = new StringBuilder();

        pinString.append("{\n");
        pinString.append("\n\t\"x\": ") .append(this.x) .append(",");
        pinString.append("\n\t\"y\": ") .append(this.y);
        pinString.append("\n}");

        return pinString.toString();
    }
}
