package game.model;

import android.content.Context;
import game.model.info.Plane;
import game.programs.Object3DShaderProgram;
import game.util.MoveHelper;
import game.util.TextureHelper;

/*
* These static imports will help with autocomplete.
* */
import static android.opengl.Matrix.*;

public class PlaneModel extends Object3D {

    private static final String TAG = PlaneModel.class.getName();

    private float[] mLightPosInEyeSpace = new float[4];

    /*
     * Store the model matrix. This matrix is used to move models from object space (where each model can be thought
     * of being located at the center of the universe) to world space.
     */
    private float[] mModelMatrix = new float[16];
    private Matrix matrix;

    private MoveHelper moveHelper;

    private int texture;
    private Object3DShaderProgram shaderProgram;
    private Plane plane;

    public PlaneModel(Context context, Matrix matrix, Plane plane) {
        super(context, plane.getResourceId());
        this.matrix = matrix;
        this.plane = plane;

        moveHelper = new MoveHelper(mModelMatrix);

        shaderProgram = new Object3DShaderProgram(context);
        texture = TextureHelper.loadTexture(context, plane.getTextureId());
    }

    public void showPlaneModel() {
        moveHelper.setIdentity();
        moveHelper.position(plane.getPosition());
        moveHelper.rotate(plane.getRotatio());
        this.drawPlaneModel();
    }

    private void drawPlaneModel() {
        shaderProgram.useProgram();
        super.bindData(shaderProgram);
        multiplyMM(matrix.getMVPMatrix(), 0, matrix.getViewMatrix(), 0, mModelMatrix, 0);
        multiplyMM(matrix.getMVPMatrix(), 0, matrix.getProjectionMatrix(), 0, matrix.getMVPMatrix(), 0);
        shaderProgram.setUniforms(matrix.getMVPMatrix(), mLightPosInEyeSpace, texture);
        super.draw();
    }

}
