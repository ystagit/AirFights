package game.data;

import java.util.LinkedList;
import java.util.List;

public class Vector2f {

    private List<Float> list;

    public Vector2f(float x, float y) {
        list = new LinkedList<Float>();
        list.add(x);
        list.add(y);
    }

    public List<Float> getVectorList() {
        return list;
    }

}
