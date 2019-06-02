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
    public boolean hasClient() {

        return (this.clientModel != null);
    }

    public int getX() {

        return this.pinEntity.getX();
    }

    public int getY() {

        return this.pinEntity.getY();
    }

    public boolean isSolid() {

        return this.pinEntity.isSolid();
    }

    public PinEntity getPinEntity() {

        return this.pinEntity;
    }
    // setters
    public void setClientModel(ClientModel clientModel) {

        this.clientModel = clientModel;
    }

    public void isSolid(boolean isSolid) {

        this.pinEntity.isSolid(isSolid);
    }
}
