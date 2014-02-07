package game.data;

import android.util.Log;
import game.model.info.Plane;

public class ModelData {

    private static String TAG = ModelData.class.getName();

    private static int QUANTITY_OF_PLANES = 10;

    private Plane[] planes = new Plane[QUANTITY_OF_PLANES];

    private static volatile ModelData instance;
    private int planeIndex;

    public static ModelData getInstance() {
        ModelData localInstance = instance;
        if (localInstance == null) {
            synchronized (ModelData.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ModelData();
                }
            }
        }
        return localInstance;
    }

    public void remove() {
        instance = null;
    }

    public ModelData() {
        planeIndex = 0;
    }

    public void setPlanes(Plane[] planes) {
        for (Plane plane : planes) {
            setPlane(plane);
        }
    }

    public void setPlane(Plane plane) {
        if (plane != null) {
            if (planeIndex < QUANTITY_OF_PLANES) {
                planes[planeIndex] = plane;
                planeIndex++;
            } else {
                Log.w(TAG, "Class plane is empty");
            }
        } else {
            Log.w(TAG, "Class plane is empty");
        }
    }

    public Plane[] getPlanes() {
        Plane[] newPlanes = new Plane[planeIndex];

        for (int i = 0; i < planeIndex; i++) {
            newPlanes[i] = planes[i];
        }

        return newPlanes;
    }

}
