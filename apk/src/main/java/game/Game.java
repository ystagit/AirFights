package game;

import android.content.Context;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import game.camera.Camera;
import game.data.ModelData;
import game.model.Matrix;
import game.model.PlaneModel;
import game.model.info.Plane;

/*
* These static imports will help with autocomplete.
* */
import static android.opengl.GLES20.*;
import static android.opengl.Matrix.*;

public class Game implements Renderer {

    private static final String TAG = Game.class.getName();
    private Matrix matrix = new Matrix();
    private Plane[] planes;

    private final Context context;

    private PlaneModel[] planeModels;
    private Camera camera;

    public Game(Context context) {
        this.context = context;
        ModelData modelData = ModelData.getInstance();
        planes = modelData.getPlanes();
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

        camera = new Camera(matrix.getViewMatrix());
        createPlanes();
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
        final float far = 40.0f;

        frustumM(matrix.getProjectionMatrix(), 0, left, right, bottom, top, near, far);
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

        if (setCameraPosition()) {
            drawPlanes();
        }

    }

    // Camera position is set to a plane.
    private boolean setCameraPosition() {

        for (Plane plane : planes) {
            if (plane.isCamera()) {
                camera.update(plane.getPosition(), 90.0f, 60.0f, 10.0f);
                return true;
            }
        }

        Log.wtf(TAG, "Camera was not set for a plane");
        return false;
    }

    private void drawPlanes() {
        for (PlaneModel planeModel : planeModels) {
            planeModel.showPlaneModel();
        }
    }

    private void createPlanes() {
        planeModels = new PlaneModel[planes.length];

        for (int i = 0; i < planes.length; i++) {
            planeModels[i] = new PlaneModel(context, matrix, planes[i]);
        }
    }

}