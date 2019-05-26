package _old.entity;

import java.awt.*;
import java.util.ArrayList;

public class PlayerEntity {

    private String name;
    private Color color;
    private ArrayList<PinEntity> pins;

    // getters
    public String getName()               { return this.name;  }
    public Color getColor()               { return this.color; }
    public ArrayList<PinEntity> getPins() { return this.pins;  }

    // setters
    public void setName(String name)               { this.name  = name;  }
    public void setColor(Color color)              { this.color = color; }
    public void setPins(ArrayList<PinEntity> pins) { this.pins  = pins;  }
}
