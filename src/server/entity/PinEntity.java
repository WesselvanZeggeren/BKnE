package server.entity;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.HashMap;

public class PinEntity implements Serializable {

    // attributes
    private int x;
    private int y;

    private boolean isSolid = false;
    private HashMap<Rectangle2D, Color> texture;

    // constructor
    public PinEntity(int x, int y) {

        this.x = x;
        this.y = y;
    }

    // getters
    public int     getX()                           { return this.x;       }
    public int     getY()                           { return this.y;       }
    public boolean isSolid()                        { return this.isSolid; }
    public HashMap<Rectangle2D, Color> getTexture() { return this.texture; }

    // setters
    public void isSolid(boolean isSolid) {

        this.isSolid = isSolid;
    }

    public void setTexture(HashMap<Rectangle2D, Color> texture) {

        this.texture = texture;
    }

    // toString
    @Override
    public String toString() {

        return
            "PinEntity {" +
                "x = "         + this.x +
                ", y = "       + this.y +
                ", isSolid = " + this.isSolid +
            '}'
        ;
    }
}
