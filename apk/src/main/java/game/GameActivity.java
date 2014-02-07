package game;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.*;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import game.data.ModelData;
import game.model.info.Plane;
import game.util.ObjectHelper;
import main.java.game.R;

public class GameActivity extends Activity {

    private GLViewGroup glViewGroup;
    private boolean rendererSet;
    private PlanesListFragment planesListFragment;
    private ModelData modelData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Plane[] planes = getPlanes(2);
        modelData = ModelData.getInstance();
        modelData.setPlanes(planes);

        // Check if the system supports OpenGL ES 2.0.
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")));

        if (supportsEs2) {
//            glViewGroup = new GLViewGroup(this);
            getIntent().putExtra("planes", planes);
            planesListFragment = new PlanesListFragment();
            setContentView(R.layout.game_main);
            rendererSet = true;
        } else {
            // This is where you could create an OpenGL ES 1.x compatible
            // renderer if you wanted to support both ES 1 and ES 2.
            Toast.makeText(this, "This device does not support OpenGL ES 2.0.", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void onClick(View v){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);

        if(planesListFragment.isVisible()){
            transaction.remove(planesListFragment);
            fm.popBackStack();
        }else{
            transaction.replace(R.id.fragment, planesListFragment);
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    private Plane[] getPlanes(int amount) {
        Plane[] planes = new Plane[amount];

        for (int i = 0; i < amount; i++) {
            ObjectHelper.Position position = new ObjectHelper.Position(6.0f*i, 0.0f, -10.0f);
            ObjectHelper.Rotation rotation = new ObjectHelper.Rotation(30.0f, 0.0f, 1.0f, 0.0f);

            Plane plane = new Plane();
            plane.setName("Plane "+i);
            plane.setResourceId(R.raw.sphere);
            plane.setTextureId(R.drawable.texture1);
            plane.setPosition(position);
            plane.setRotation(rotation);
            if (i == 0) {
                plane.setCamera(true);
            }
            planes[i] = plane;
        }

        return planes;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (rendererSet) {
//            // The activity must call the GL surface view's onResume() on activity onResume().
//            glViewGroup.onResume();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (rendererSet) {
//            // The activity must call the GL surface view's onPause() on activity onPause().
//            glViewGroup.onPause();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        modelData.remove();
    }
}
