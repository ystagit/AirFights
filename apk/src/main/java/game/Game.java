package game;

import android.content.Context;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import game.model.Object3D;
import game.programs.Object3DShaderProgram;
import game.util.TextureHelper;
import main.java.game.R;

/*
* These static imports will help with autocomplete.
* */
import static android.opengl.GLES20.*;
import static android.opengl.GLUtils.*;
import static android.opengl.Matrix.*;

public class Game implements Renderer {

    private static final String TAG = Game.class.getName();

    private final Context context;

    private float[] mLightPosInEyeSpace = new float[4];

    /*
     * Store the model matrix. This matrix is used to move models from object space (where each model can be thought
     * of being located at the center of the universe) to world space.
     */
    private float[] mModelMatrix = new float[16];

    /*
     * Store the view matrix. This can be thought of as our camera. This matrix transforms world space to eye space;
     * it positions things relative to our eye.
     */
    private float[] mViewMatrix = new float[16];

    /* Store the projection matrix. This is used to project the scene onto a 2D viewport. */
    private float[] mProjectionMatrix = new float[16];

    /* Allocate storage for the final combined matrix. This will be passed into the shader program. */
    private float[] mMVPMatrix = new float[16];

    private int texture;
    private Object3DShaderProgram shaderProgram;
    private Object3D object3D;

    public Game(Context context) {
        this.context = context;
        object3D = new Object3D(context, R.raw.sphere);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        /*
         * glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
         * The first three components corresponds to red, green, blue, and the last correspond to a special component called alpha,
         * witch is often used for translucency and transparency. By setting the first component to 1 and the rest to 0,
         * we are setting red to full strength and the screen will become red when cleared.
         */
        glClearColor(0.5f, 0.5f, 0.5f, 0.0f);

        // Use culling to remove back faces.
        glEnable(GL_CULL_FACE);

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
        setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

        shaderProgram = new Object3DShaderProgram(context);
        texture = TextureHelper.loadTexture(context, R.drawable.texture1);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Set the OpenGL viewport to the same size as the surface.
        glViewport(0, 0, width, height);

        // Create a new perspective projection matrix. The height will stay the same
        // while the width will vary as per aspect ratio.
        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;

        frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        /*
        * This will wipe out all colors on the screen and fill the screen with the color previously defined by our call to glClearColor.
        * Clear the rendering surface
        *
        * NOTE:
        * If you are using emulator and it's not working for you, and you have checked the Use Host GPU is checked in the emulator
        * settings, then try adding a call to glSurfaceView.setEGConfigChooser(8, 8, 8, 16, 0); before the call to glSurfaceView.setRender().
        * */
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        setIdentityM(mModelMatrix, 0);
        translateM(mModelMatrix, 0, 0.0f, 0.0f, -7.0f);
        rotateM(mModelMatrix, 0, 0, 0.0f, 1.0f, 0.0f);
        draw();
    }

    private void draw() {

        shaderProgram.useProgram();
        object3D.bindData(shaderProgram);
        multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
        shaderProgram.setUniforms(mMVPMatrix, mLightPosInEyeSpace, texture);
        object3D.draw();
    }

}