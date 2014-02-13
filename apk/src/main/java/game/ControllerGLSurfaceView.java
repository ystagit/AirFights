package game;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import game.data.ModelData;
import game.model.info.InfoCamera;
import game.model.info.Plane;

public class ControllerGLSurfaceView extends GLSurfaceView {

    private static String TAG = ControllerGLSurfaceView.class.getName();

    private Game game;

    private float previousX;
    private float previousY;
    private float cameraRotationX;
    private float cameraRotationY;

    private ModelData modelData;
    private Plane selectedPlane;

    public ControllerGLSurfaceView(Context context) {
        super(context);

        // Request an OpenGL ES 2.0 compatible context.
        setEGLContextClientVersion(2);

        game = new Game(context);
        modelData = ModelData.getInstance();

        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, final MotionEvent event) {
                rotateCamera(event);
                return true;
            }
        });

        // Set the renderer to our demo renderer, defined below.
        setRenderer(game);
    }

    public void rotateCamera(final MotionEvent event) {

        if (event != null) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                previousX = event.getX();
                previousY = event.getY();
            } else {
                final float deltaX = event.getX() - previousX;
                final float deltaY = event.getY() - previousY;
                previousX = event.getX();
                previousY = event.getY();

                queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        Plane[] planes = modelData.getPlanes();

                        if (selectedPlane == null) {
                            selectedPlane = findIdPlane(planes);

                            if (selectedPlane == null) return;
                        } else {

                            if (selectedPlane.isCamera()) {
                                setCameraBorder(deltaX, deltaY);
                                updateInfoCamera();
                            } else {
                                selectedPlane = null;
                                cameraRotationX = 0f;
                                cameraRotationY = 0f;
                                previousX = 0f;
                                previousY = 0f;
                            }
                        }
                    }
                });
            }
        }
    }

    private void updateInfoCamera() {
        InfoCamera infoCamera = selectedPlane.getInfoCamera();
        infoCamera.setXRotation(cameraRotationX);
        infoCamera.setYRotation(cameraRotationY);
        selectedPlane.setInfoCamera(infoCamera);
    }

    private void setCameraBorder(float deltaX, float deltaY) {
        cameraRotationY += deltaY / 8f;
        cameraRotationX += deltaX / 6f;

        if (cameraRotationX > 360 || cameraRotationX < -360) {
            cameraRotationX = 0;
        }

        if (cameraRotationY > 90) {
            cameraRotationY = 90;
        } else if (cameraRotationY < 45) {
            cameraRotationY = 45;
        }
    }

    private Plane findIdPlane(Plane[] planes) {
        for (Plane plane : planes) {
            if (plane.isCamera()) {
                return plane;
            }
        }

        Log.wtf(TAG, "Not selected plane");
        return null;
    }

}
