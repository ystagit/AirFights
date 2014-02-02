package game.model;

import android.content.Context;
import game.data.OBJLoader;
import game.data.VertexArray;
import game.programs.Object3DShaderProgram;

import static android.opengl.GLES20.*;

public class Object3D {

    private final int POSITION_DATA_SIZE = 3;
    private final int NORMAL_DATA_SIZE = 3;
    private final int TEXTURE_COORDINATE_DATA_SIZE = 2;

    private final VertexArray vertexArray;
    private final VertexArray normalArray;
    private final VertexArray textureCoordinateArray;
    private final OBJLoader loader;

    public Object3D(Context context, int resourceId) {

        loader = new OBJLoader(context, resourceId);

        final float[] vertexData = loader.getVertex();
        final float[] normalData = loader.getNormal();
        final float[] textureCoordinateData = loader.getTextureCoordinate();

        vertexArray = new VertexArray(vertexData);
        normalArray = new VertexArray(normalData);
        textureCoordinateArray = new VertexArray(textureCoordinateData);

    }

    public void bindData(Object3DShaderProgram shaderProgram) {
        vertexArray.setVertexAttribPointer(0, shaderProgram.getPositionAttrLoc(), POSITION_DATA_SIZE, 0);
        normalArray.setVertexAttribPointer(0, shaderProgram.getNormalAttrLoc(), NORMAL_DATA_SIZE, 0);
        textureCoordinateArray.setVertexAttribPointer(0, shaderProgram.getTextureCoordinateAttrLoc(), TEXTURE_COORDINATE_DATA_SIZE, 0);
    }

    public void draw() {
        glDrawArrays(GL_TRIANGLES, 0, loader.getFaceCount()*3);
    }

}
