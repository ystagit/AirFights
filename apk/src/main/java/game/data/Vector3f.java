package game.data;

import java.util.LinkedList;
import java.util.List;

public class Vector3f {

    private List<Float> list;

    public Vector3f(float x, float y, float z) {
        list = new LinkedList<Float>();
        list.add(x);
        list.add(y);
        list.add(z);
    }

    public List<Float> getVectorList() {
        return list;
    }

}
