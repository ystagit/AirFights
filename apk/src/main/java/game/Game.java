package game;

import android.content.Context;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

/*
* These static imports will help with autocomplete.
* */
import static android.opengl.GLES20.*;
import static android.opengl.GLUtils.*;
import static android.opengl.Matrix.*;

public class Game implements Renderer {

    private static final String TAG = Game.class.getName();

    private final Context context;

    public Game(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        /*
         * glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
         * The first three components corresponds to red, green, blue, and the last correspond to a special component called alpha,
         * witch is often used for translucency and transparency. By setting the first component to 1 and the rest to 0,
         * we are setting red to full strength and the screen will become red when cleared.
         */
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        //set the OpenGL viewpoint to fill the entire surface
        glViewport(0, 0, width, height);
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
        glClear(GL_COLOR_BUFFER_BIT);
    }

}