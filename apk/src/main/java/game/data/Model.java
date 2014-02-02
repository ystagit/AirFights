package game.data;

import java.util.List;
import java.util.LinkedList;


public class Model {

    private List<Vector3f> vertexes = new LinkedList<Vector3f>();
    private List<Vector3f> normals = new LinkedList<Vector3f>();
    private List<Vector2f> textureCoordinate = new LinkedList<Vector2f>();
    private List<Face> faces = new LinkedList<Face>();

    public void addVertex(Vector3f vertex) {
        vertexes.add(vertex);
    }

    public void addNormal(Vector3f normal) {
        normals.add(normal);
    }

    public void addFace(Face face) {
        faces.add(face);
    }

    public List<Vector3f> getVertex() {
        return vertexes;
    }

    public List<Vector3f> getNormal() {
        return normals;
    }

    public List<Face> getFace() {
        return faces;
    }

    public List<Vector2f> getTextureCoordinate() {
        return textureCoordinate;
    }

    public void setTextureCoordinate(Vector2f textureCoordinateVertex) {
        textureCoordinate.add(textureCoordinateVertex);
    }
}
