package server.model;

public class Pin {

    private int x;
    private int y;
    private Client client;
    private boolean isSolid = false;

    // contstucter
    public Pin(int x, int y) {

        this.x = x;
        this.y = y;
    }

    // getters
    public boolean hasClient() {

        return (this.client != null);
    }

    public int getX() {

        return this.x;
    }

    public int getY() {

        return this.y;
    }

    public boolean isSolid() {

        return this.isSolid;
    }

    // setters
    public void setClient(Client client) {

        this.client = client;
    }

    public void isSolid(boolean isSolid) {

        this.isSolid = isSolid;
    }

    // toString
    @Override
    public String toString() {

        StringBuilder pinString = new StringBuilder();

        pinString.append("{\n\t");
        pinString.append("\"x\": ")     .append(this.x)       .append(",\n\t");
        pinString.append("\"y\": ")     .append(this.y)       .append(",\n\t");
        pinString.append("\"solid\": ") .append(this.isSolid) .append("\n");
        pinString.append("}");

        return pinString.toString();
    }
}
