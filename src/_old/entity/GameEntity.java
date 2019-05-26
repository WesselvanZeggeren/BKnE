package _old.entity;

import java.util.ArrayList;

public class GameEntity {

    private int size;
    private ArrayList<PinEntity> pin;
    private ArrayList<PlayerEntity> clients;
    private PlayerEntity trigger;
    private int clientAmount;

    // getters
    public int getSize()                        { return this.size;         }
    public ArrayList<PinEntity> getPin()        { return this.pin;          }
    public ArrayList<PlayerEntity> getClients() { return this.clients;      }
    public PlayerEntity getTrigger()            { return this.trigger;      }
    public int getClientAmount()                { return this.clientAmount; }

    // setters
    public void setSize(int size)                           { this.size         = size;         }
    public void setPin(ArrayList<PinEntity> pin)            { this.pin          = pin;          }
    public void setClients(ArrayList<PlayerEntity> clients) { this.clients      = clients;      }
    public void setTrigger(PlayerEntity trigger)            { this.trigger      = trigger;      }
    public void setClientAmount(int clientAmount)           { this.clientAmount = clientAmount; }
}
