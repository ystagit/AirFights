package game.model.info;

import game.util.ObjectHelper.Position;

public class InfoCamera {

    private Position position;
    private float xRotation;
    private float yRotation;
    private float radius;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public float getXRotation() {
        return xRotation;
    }

    public void setXRotation(float xRotation) {
        this.xRotation = xRotation;
    }

    public float getYRotation() {
        return yRotation;
    }

    public void setYRotation(float yRotation) {
        this.yRotation = yRotation;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

}
