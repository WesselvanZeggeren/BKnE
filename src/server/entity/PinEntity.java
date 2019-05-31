package server.entity;

import java.io.Serializable;

public class PinEntity implements Serializable {

    // attributes
    private int x;
    private int y;

    private boolean isSolid = false;

    // constructor
    public PinEntity(int x, int y) {

        this.x = x;
        this.y = y;
    }

    // getters
    public int     getX()    { return this.x;       }
    public int     getY()    { return this.y;       }
    public boolean isSolid() { return this.isSolid; }

    // setters
    public void isSolid(boolean isSolid) {

        this.isSolid = isSolid;
    }
}
