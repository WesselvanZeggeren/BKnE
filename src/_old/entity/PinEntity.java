package _old.entity;

public class PinEntity {

    private int x;
    private int y;
    private boolean isSolid;

    // getters
    public int getX()        { return this.x;       }
    public int getY()        { return this.y;       }
    public boolean isSolid() { return this.isSolid; }

    // setters
    public void setX(int x)             { this.x       = x;     }
    public void setY(int y)             { this.y       = y;     }
    public void setSolid(boolean solid) { this.isSolid = solid; }
}
