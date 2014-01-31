package game;

import android.content.Context;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class Game implements Renderer {

    private static final String TAG = Game.class.getName();

    private final Context context;

    public Game(Context context) {
        this.context = context;
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }

}
