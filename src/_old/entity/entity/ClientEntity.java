package _old.entity.entity;

import server.model.Client;

public class ClientEntity {

    // attributes
    private int x;
    private int y;
    private Client trigger;
    private String message;

    // constructor
    public ClientEntity(Client trigger) {

        this.trigger = trigger;
    }

    // getters
    public int getX()          { return this.x;       }
    public int getY()          { return this.y;       }
    public String getMessage() { return this.message; }
    public Client getTrigger() { return this.trigger; }

    // setters
    public void setX(int x)                { this.x       = x;       }
    public void setY(int y)                { this.y       = y;       }
    public void setMessage(String message) { this.message = message; }
}
