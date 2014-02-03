package game.util;

import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;
import static game.util.ObjectHelper.*;

public class MoveHelper {

    private float[] matrix;

    public MoveHelper(float[] matrix) {
        this.matrix = matrix;
    }

    public void setIdentity() {
        setIdentityM(matrix, 0);
    }

    public Position position(Position point) {
        translateM(matrix, 0, point.x, point.y, point.z);
        return point;
    }

    public void rotate(Rotation rotation) {
        rotateM(matrix, 0, rotation.angle, rotation.x, rotation.y, rotation.z);
    }

}
