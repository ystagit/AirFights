package game.model.info;

import static game.util.ObjectHelper.*;

public class PlaneInfo {

    private Position position;
    private Rotation rotation;

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public Rotation getRotatio() {
        return rotation;
    }

}
