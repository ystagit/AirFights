package game.util;

import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;
import static game.util.Geometry.Point;

public class MoveHelper {

    private float[] matrix;

    public MoveHelper(float[] matrix) {
        this.matrix = matrix;
    }

    public void setIdentity() {
        setIdentityM(matrix, 0);
    }

    public Point setPosition(Point point) {
        translateM(matrix, 0, point.x, point.y, point.z);
        return point;
    }

    public void rotate(float angleInDegrees, float x, float y, float z) {
        rotateM(matrix, 0, angleInDegrees, x, y, z);
    }

}
