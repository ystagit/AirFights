package game;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class ClearGLSurfaceView extends GLSurfaceView {

    private Game game;

    public ClearGLSurfaceView(Context context) {
        super(context);

        // Request an OpenGL ES 2.0 compatible context.
        setEGLContextClientVersion(2);

        game = new Game(context);

        // Set the renderer to our demo renderer, defined below.
        setRenderer(game);
    }

}
