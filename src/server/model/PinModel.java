package server.model;

import server.entity.PinEntity;

public class PinModel {

    private ClientModel clientModel;

    private PinEntity pinEntity;

    // constructor
    public PinModel(int x, int y) {

        this.pinEntity = new PinEntity(x, y);
    }
    public PinModel(PinEntity pinEntity) {

        this.pinEntity = pinEntity;
    }

    // getters
    public int       getX()         { return this.pinEntity.getX();    }
    public int       getY()         { return this.pinEntity.getY();    }
    public PinEntity getPinEntity() { return this.pinEntity;           }
    public boolean   hasClient()    { return this.clientModel != null; }

    // setters
    public void setClientModel(ClientModel clientModel) { this.clientModel = clientModel; }
}
