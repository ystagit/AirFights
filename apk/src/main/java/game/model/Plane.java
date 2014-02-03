package game.model;

import android.content.Context;
import game.programs.Object3DShaderProgram;
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


    private int texture;
    private Object3DShaderProgram shaderProgram;

    public Plane(Context context, Matrix matrix, int resourceId, int textureId) {
        super(context, resourceId);
        this.matrix = matrix;

        // Position the eye in front of the origin.
        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = -0.5f;

        // We are looking toward the distance
        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = -5.0f;

        // Set our up vector. This is where our head would be pointing were we holding the camera.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        // Set the view matrix. This matrix can be said to represent the camera position.
        // NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
        // view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
        setLookAtM(matrix.getViewMatrix(), 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

        shaderProgram = new Object3DShaderProgram(context);
        texture = TextureHelper.loadTexture(context, textureId);

    }

    public void showPnale() {
        setIdentityM(mModelMatrix, 0);
        translateM(mModelMatrix, 0, 0.0f, 0.0f, -7.0f);
        rotateM(mModelMatrix, 0, 0, 0.0f, 1.0f, 0.0f);
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
