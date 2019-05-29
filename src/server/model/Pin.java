package server.model;

import java.io.Serializable;

public class Pin implements Serializable {

    private int x;
    private int y;
    private Client client;
    private boolean isSolid = false;

    // constructor
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
}
