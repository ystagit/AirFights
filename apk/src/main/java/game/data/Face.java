package game.data;

public class Face {

    private int[] vertexIndexes = {-1, -1, -1};
    private int[] normalIndexes = {-1, -1, -1};
    private int[] textureCoordinateIndexes = {-1, -1, -1};

    public int[] getVertexIndexes() {
        return vertexIndexes;
    }

    public int[] getNormalIndexes() {
        return normalIndexes;
    }

    public int[] getTextureCoordinateIndexes() {
        return textureCoordinateIndexes;
    }

    public Face(int[] vertexIndexes) {
        this.vertexIndexes = vertexIndexes;
    }

    public Face(int[] vertexIndexes, int[] normalIndexes) {
        this.vertexIndexes = vertexIndexes;
        this.normalIndexes = normalIndexes;
    }

    public Face(int[] vertexIndexes, int[] textureCoordinateIndexes, int[] normalIndexes) {
        this.vertexIndexes = vertexIndexes;
        this.textureCoordinateIndexes = textureCoordinateIndexes;
        this.normalIndexes = normalIndexes;
    }

}
