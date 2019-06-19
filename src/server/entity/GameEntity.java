package server.entity;

import both.Config;
import server.model.ClientModel;
import server.model.PinModel;

import java.io.Serializable;
import java.util.ArrayList;

public class GameEntity implements Serializable {

    // attributes
    private ArrayList<ClientEntity> clientEntitiesOrder;
    private ArrayList<ClientEntity> clientEntities;
    private ArrayList<PinEntity> pinEntities;

    private int size;
    private int key;

    private boolean isRunning = false;

    // getters
    public ArrayList<ClientEntity> getClientEntitiesOrder() { return this.clientEntitiesOrder; }
    public ArrayList<ClientEntity> getClientEntities()      { return this.clientEntities;      }
    public ArrayList<PinEntity>    getPinEntities()         { return this.pinEntities;         }
    public int                     getSize()                { return this.size;                }
    public int                     getKey()                 { return this.key;                 }
    public boolean                 isRunning()              { return this.isRunning;           }

    public ArrayList<ClientEntity> getOtherClientEntities() {

        ArrayList<ClientEntity> clientEntities = new ArrayList<>();

        for (ClientEntity clientEntity : this.clientEntities)
            if (!clientEntity.isFinished())
                clientEntities.add(clientEntity);

        return clientEntities;
    }

    // setters
    public void isRunning(boolean isRunning) { this.isRunning = isRunning; }
    public void setKey(int key)              { this.key       = key;       }

    public void setClientEntitiesOrder(ArrayList<ClientModel> clientModels) {

        ArrayList<ClientEntity> clientEntities = new ArrayList<>();

        for (ClientModel clientModel : clientModels)
            clientEntities.add(clientModel.getClientEntity());

        this.clientEntitiesOrder = clientEntities;
    }

    public void setClientEntities(ArrayList<ClientModel> clientModels) {

        ArrayList<ClientEntity> clientEntities = new ArrayList<>();

        for (ClientModel clientModel : clientModels)
            clientEntities.add(clientModel.getClientEntity());

        this.clientEntities = clientEntities;
    }

    public void setPinEntities(ArrayList<PinModel> pinModels) {

        ArrayList<PinEntity> pinEntities = new ArrayList<>();

        for (PinModel pinModel : pinModels)
            pinEntities.add(pinModel.getPinEntity());

        this.pinEntities = pinEntities;
    }

    public void setSize(int size, int playerAmount) {

        if ((size * size) > (playerAmount * Config.GAME_PIN_PER_CLIENT))
            this.size = size;
        else
            this.setSize(size + 1, playerAmount);
    }

    // toString
    @Override
    public String toString() {

        return
            "GameEntity {" +
                "clientEntities = " + this.clientEntities +
                ", pinEntities = "  + this.pinEntities +
                ", size = "         + this.size +
                ", isRunning = "    + this.isRunning +
            '}'
        ;
    }
}
