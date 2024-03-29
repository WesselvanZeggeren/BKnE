package server.entity;

import server.model.PinModel;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class ClientEntity implements Serializable {

    // attributes
    private ArrayList<PinEntity> pinEntities;

    private Color color;
    private String name = "";

    private boolean isPlaying = true;
    private boolean isFinished = false;

    // constructor
    public ClientEntity() {

        this.generateColor();
        this.pinEntities = new ArrayList<>();
    }

    // methods
    public void generateColor() {

        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);

        this.setColor(new Color(red, green, blue));
    }

    // getters
    public ArrayList<PinEntity> getPinEntities() { return this.pinEntities; }
    public Color                getColor()       { return this.color;       }
    public String               getName()        { return this.name;        }
    public boolean              isPlaying()      { return this.isPlaying;   }
    public boolean              isFinished()     { return this.isFinished;  }

    // setters
    public void setColor(Color color)          { this.color      = color;      }
    public void setName(String name)           { this.name       = name;       }
    public void isPlaying(boolean isPlaying)   { this.isPlaying  = isPlaying;  }
    public void isFinished(boolean isFinished) { this.isFinished = isFinished; }

    public void setPinEntities(ArrayList<PinModel> pinModels) {

        ArrayList<PinEntity> pinEntities = new ArrayList<>();

        for (PinModel pinModel : pinModels)
            pinEntities.add(pinModel.getPinEntity());

        this.pinEntities = pinEntities;
    }

    // toString
    @Override
    public String toString() {

        return
            "ClientEntity {" +
                "pinEntities = "  + this.pinEntities +
                ", color = "      + this.color +
                ", name = '"      + this.name + '\'' +
                ", isPlaying = "  + this.isPlaying +
                ", isFinished = " + this.isFinished +
            '}'
        ;
    }
}
