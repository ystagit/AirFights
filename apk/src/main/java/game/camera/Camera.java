package game.camera;


import game.util.ObjectHelper.Position;

import static android.opengl.Matrix.*;

public class Camera {

    // Position the eye in front of the origin.
    private float eyeX;
    private float eyeY;
    private float eyeZ;

    // We are looking toward the distance
    private float lookX;
    private float lookY;
    private float lookZ;

    // Set our up vector. This is where our head would be pointing were we holding the camera.
    private float upX;
    private float upY;
    private float upZ;

    private float[] viewMatrix;

    public Camera(float[] viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    public void update(Position position, float xRotation, float yRotation, float radius) {
        eyeX = (float) (position.x + radius * Math.cos(Math.toRadians(xRotation)));
        eyeY = (float) (position.y + radius * Math.cos(Math.toRadians(yRotation)));
        eyeZ = (float) (position.z + radius * Math.sin(Math.toRadians(xRotation)));

        lookX = position.x;
        lookY = position.y;
        lookZ = position.z;

//        eyeX = 0.0f;
//        eyeY = 0.0f;
//        eyeZ = -0.5f;

        upX = 0.0f;
        upY = 1.0f;
        upZ = 0.0f;

        look();
    }

    private void look() {
        /*
        * Set the view matrix. This matrix can be said to represent the camera position.
        * NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
        * view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
        * */
        setLookAtM(viewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
    }

}
