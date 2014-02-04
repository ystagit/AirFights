package game.model;

import android.content.Context;
import game.camera.Camera;
import game.model.info.PlaneInfo;
import game.programs.Object3DShaderProgram;
import game.util.MoveHelper;
import game.util.ObjectHelper;
import game.util.TextureHelper;

/*
* These static imports will help with autocomplete.
* */
import static android.opengl.Matrix.*;

public class Plane extends Object3D {

    private static final String TAG = Plane.class.getName();

    private float[] mLightPosInEyeSpace = new float[4];

    /*
     * Store the model matrix. This matrix is used to move models from object space (where each model can be thought
     * of being located at the center of the universe) to world space.
     */
    private float[] mModelMatrix = new float[16];
    private Matrix matrix;

    private PlaneInfo planeInfo;
    private MoveHelper moveHelper;

    private int texture;
    private Object3DShaderProgram shaderProgram;

    public Plane(Context context, Matrix matrix, int resourceId, int textureId) {
        super(context, resourceId);
        this.matrix = matrix;

        moveHelper = new MoveHelper(mModelMatrix);
        planeInfo = new PlaneInfo();

        shaderProgram = new Object3DShaderProgram(context);
        texture = TextureHelper.loadTexture(context, textureId);

    }

    public PlaneInfo getPlaneInfo() {
        return planeInfo;
    }

    public void showPlane(PlaneInfo planeInfo) {
        moveHelper.setIdentity();
        moveHelper.position(planeInfo.getPosition());
        moveHelper.rotate(planeInfo.getRotatio());
        this.drawPlane();
    }

    private void drawPlane() {
        shaderProgram.useProgram();
        super.bindData(shaderProgram);
        multiplyMM(matrix.getMVPMatrix(), 0, matrix.getViewMatrix(), 0, mModelMatrix, 0);
        multiplyMM(matrix.getMVPMatrix(), 0, matrix.getProjectionMatrix(), 0, matrix.getMVPMatrix(), 0);
        shaderProgram.setUniforms(matrix.getMVPMatrix(), mLightPosInEyeSpace, texture);
        super.draw();
    }

}
