package game;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

class GLViewGroup extends ViewGroup {

    private ControllerGLSurfaceView mGLView;

    public GLViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GLViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mGLView = new ControllerGLSurfaceView(context);
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
