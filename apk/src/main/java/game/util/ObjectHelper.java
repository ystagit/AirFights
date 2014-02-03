package game.util;

public class ObjectHelper {

    public static class Position {

        public final float x, y, z;

        public Position(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

    }

    public static class Rotation {

        public final float x, y, z;
        public final float angle;

        public Rotation(float angle, float x, float y, float z) {
            this.angle = angle;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

}
