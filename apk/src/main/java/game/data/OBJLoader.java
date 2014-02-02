package game.data;

import android.content.Context;
import android.util.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader {

    private static final String TAG = OBJLoader.class.getName();

    private float x, y, z;
    private Model model = new Model();

    public OBJLoader(Context context, int resourceId) {
        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("v ")) {
                    parseVertex(line);
                } else if (line.startsWith("vt ")) {
                    parseTextureCoordinate(line);
                } else if (line.startsWith("vn ")) {
                    parseNormal(line);
                } else if (line.startsWith("f ")) {
                    int[] vertexIndexes = parseFace(line, 0);
                    int[] textureCoordinateIndexes = parseFace(line, 1);
                    int[] normalIndexes = parseFace(line, 2);
                    model.addFace(new Face(vertexIndexes, textureCoordinateIndexes, normalIndexes));
                }
            }

            bufferedReader.close();

        } catch(IOException e) {
            Log.wtf(TAG, e);
        }
    }

    public float[] getVertex() {
        List<Float> list = new ArrayList<Float>();

        for (Face face : model.getFace()) {

            Vector3f v1 = model.getVertex().get(face.getVertexIndexes()[0] - 1);
            Vector3f v2 = model.getVertex().get(face.getVertexIndexes()[1] - 1);
            Vector3f v3 = model.getVertex().get(face.getVertexIndexes()[2] - 1);
            list.addAll(v1.getVectorList());
            list.addAll(v2.getVectorList());
            list.addAll(v3.getVectorList());
        }

        return convertToArray(list);
    }

    public float[] getTextureCoordinate() {
        List<Float> list = new ArrayList<Float>();

        for (Face face : model.getFace()) {
            Vector2f vt1 = model.getTextureCoordinate().get(face.getTextureCoordinateIndexes()[0] - 1);
            Vector2f vt2 = model.getTextureCoordinate().get(face.getTextureCoordinateIndexes()[1] - 1);
            Vector2f vt3 = model.getTextureCoordinate().get(face.getTextureCoordinateIndexes()[2] - 1);
            list.addAll(vt1.getVectorList());
            list.addAll(vt2.getVectorList());
            list.addAll(vt3.getVectorList());
        }

        return convertToArray(list);
    }

    public float[] getNormal() {
        List<Float> list = new ArrayList<Float>();

        for (Face face : model.getFace()) {
            Vector3f vn1 = model.getNormal().get(face.getNormalIndexes()[0] - 1);
            Vector3f vn2 = model.getNormal().get(face.getNormalIndexes()[1] - 1);
            Vector3f vn3 = model.getNormal().get(face.getNormalIndexes()[2] - 1);
            list.addAll(vn1.getVectorList());
            list.addAll(vn2.getVectorList());
            list.addAll(vn3.getVectorList());
        }

        return convertToArray(list);
    }

    private float[] convertToArray(List<Float> list) {
        float[] array = new float[list.size()];

        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    private void parseVertex(String line) {
        x = Float.parseFloat(line.split(" ")[1]);
        y = Float.parseFloat(line.split(" ")[2]);
        z = Float.parseFloat(line.split(" ")[3]);
        model.addVertex(new Vector3f(x, y, z));
    }

    private void parseNormal(String line) {
        x = Float.parseFloat(line.split(" ")[1]);
        y = Float.parseFloat(line.split(" ")[2]);
        z = Float.parseFloat(line.split(" ")[3]);
        model.addNormal(new Vector3f(x, y, z));
    }

    private void parseTextureCoordinate(String line) {
        x = Float.parseFloat(line.split(" ")[1]);
        y = Float.parseFloat(line.split(" ")[2]);
        model.setTextureCoordinate(new Vector2f(x, y));
    }

    private int[] parseFace(String line, int i) {
        int[] index = new int[3];
        index[0] = Integer.parseInt(line.split(" ")[1].split("/")[i]);
        index[1] = Integer.parseInt(line.split(" ")[2].split("/")[i]);
        index[2] = Integer.parseInt(line.split(" ")[3].split("/")[i]);

        return index;
    }

    public int getFaceCount() {
        return model.getFace().size();
    }

}
