package game;

import android.content.Context;
import android.view.ViewGroup;

class GLViewGroup extends ViewGroup {

    private ClearGLSurfaceView mGLView;

    public GLViewGroup(Context context) {
        super(context);

        mGLView = new ClearGLSurfaceView(context);
        addView(mGLView);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mGLView.layout(l, t, r, b);
    }

    protected void onResume() {
        mGLView.onResume();
    }

    protected void onPause() {
        mGLView.onPause();
    }
}
