package game.model.info;

import static game.util.ObjectHelper.*;

public class Plane {

    private String name;
    private Position position;
    private Rotation rotation;
    private int resourceId;
    private int textureId;
    private boolean camera;
    private InfoCamera infoCamera;

    public Plane() {}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }

    public int getTextureId() {
        return textureId;
    }

    public void setCamera(boolean camera) {
        this.camera = camera;
    }

    public boolean isCamera() {
        return camera;
    }

    public void setInfoCamera(InfoCamera infoCamera) {
        this.infoCamera = infoCamera;
    }

    public InfoCamera getInfoCamera() {
        return infoCamera;
    }
}
