package game;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import game.data.ModelData;
import game.model.info.InfoCamera;
import game.model.info.Plane;
import main.java.game.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PlanesListFragment extends ListFragment {

    private ArrayList<HashMap<String, Object>> list;
    private static final String TITLE = "planeName";
    private Plane[] planes;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list = new ArrayList<HashMap<String, Object>>();

        ModelData modelData = ModelData.getInstance();
        planes = modelData.getPlanes();

        for (Plane plane : planes) {
            addToList(plane);
        }

        SimpleAdapter adapter = new SimpleAdapter(
                getActivity().getApplicationContext(), list,
                R.layout.planes_list_fragment, new String[] { TITLE },
                new int[] { R.id.planeName});

        setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getListView().setItemChecked(getIndex(), true);

    }

    private int getIndex() {
        int index = 0;

        for (Plane plane : planes) {
            if (plane.isCamera()) {
                break;
            }
            index++;
        }

        return index;
    }

    private void addToList(Plane plane) {
        HashMap hashMap = new HashMap<String, Object>();
        hashMap.put(TITLE, plane.getName());
        list.add(hashMap);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);

        for (Plane plane : planes) {
            plane.setCamera(false);
            plane.setInfoCamera(null);
        }

        planes[position].setCamera(true);

        InfoCamera infoCamera = new InfoCamera();
        infoCamera.setPosition(planes[position].getPosition());
        infoCamera.setRadius(10);
        infoCamera.setYRotation(45);
        infoCamera.setXRotation(0);

        planes[position].setInfoCamera(infoCamera);

        transaction.remove(this);
        fragmentManager.popBackStack();
    }

}
