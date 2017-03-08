package com.plusonesoftwares.plusonesoftwares.bignews;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewDebug;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Toast;

import com.plusonesoftwares.plusonesoftwares.bignews.flip.FlipCards;
import com.plusonesoftwares.plusonesoftwares.bignews.flip.FlipRenderer;
import com.plusonesoftwares.plusonesoftwares.bignews.unit.AphidLog;

import junit.framework.Assert;

import java.util.LinkedList;

/**
 * Created by Plus 3 on 07-03-2017.
 */

public class FlipViewController extends AdapterView<Adapter> {
    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;
    private GLSurfaceView surfaceView;


    public static interface ViewFlipListener {

        void onViewFlipped(View view, int position);
    }
    private static final int MAX_RELEASED_VIEW_SIZE = 1;


    public static final int MSG_SURFACE_CREATED = 1;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == MSG_SURFACE_CREATED) {
                contentWidth = 0;
                contentHeight = 0;
                requestLayout();
                return true;
            } else {
                System.out.println("Unknown msg.what: " + msg.what);
            }
            return false;
        }
    });

    private FlipRenderer renderer;
    private FlipCards cards;

    private int contentWidth;
    private int contentHeight;

    @ViewDebug.ExportedProperty
    private int flipOrientation;

    private volatile boolean inFlipAnimation = false;

    //AdapterView Related
    private Adapter adapter;

    private int adapterDataCount = 0;

    private DataSetObserver adapterDataObserver;

    private final LinkedList<View> bufferedViews = new LinkedList<View>();
    private final LinkedList<View> releasedViews = new LinkedList<View>(); //XXX: use a SparseArray to keep the related view indices?
    private int bufferIndex = -1;
    private int adapterIndex = -1;
    private final int sideBufferSize = 1;

    private float touchSlop;

    private ViewFlipListener onViewFlipListener;

    private boolean overFlipEnabled = true;

    private boolean flipByTouchEnabled = true;
    @ViewDebug.ExportedProperty
    private Bitmap.Config animationBitmapFormat = Bitmap.Config.ARGB_8888;

    public FlipViewController(Context context) {

        this(context, VERTICAL);
    }

    public FlipViewController(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        int orientation = VERTICAL;

        TypedArray
                a =
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.FlipViewController, 0, 0);

        try {
            int value = a.getInteger(R.styleable.FlipViewController_orientation, VERTICAL);
            if (value == HORIZONTAL) {
                orientation = HORIZONTAL;
            }

            value = a.getInteger(R.styleable.FlipViewController_animationBitmapFormat, 0);
            if (value == 1) {
                setAnimationBitmapFormat(Bitmap.Config.ARGB_4444);
            } else if (value == 2) {
                setAnimationBitmapFormat(Bitmap.Config.RGB_565);
            } else {
                setAnimationBitmapFormat(Bitmap.Config.ARGB_8888);
            }
        } finally {
            a.recycle();
        }

        init(context, orientation);
    }

    public FlipViewController(Context context, int flipOrientation) {
        super(context);
        init(context, flipOrientation);
    }
    public FlipViewController(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void init(Context context, int orientation) {
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        touchSlop = configuration.getScaledTouchSlop();
        this.flipOrientation = orientation;
        setupSurfaceView(context);
    }

    public void setAnimationBitmapFormat(Bitmap.Config animationBitmapFormat) {
        this.animationBitmapFormat = animationBitmapFormat;
    }

    @Override
    public int getSelectedItemPosition() {
        return adapterIndex;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (AphidLog.ENABLE_DEBUG) {
            AphidLog.d("onLayout: %d, %d, %d, %d; child %d", l, t, r, b, bufferedViews.size());
        }

        for (View child : bufferedViews) {
            child.layout(0, 0, r - l, b - t);
        }

        if (changed || contentWidth == 0) {
            int w = r - l;
            int h = b - t;
            surfaceView.layout(0, 0, w, h);

            if (contentWidth != w || contentHeight != h) {
                contentWidth = w;
                contentHeight = h;
            }
        }

        if (bufferedViews.size() >= 1) {
            View frontView = bufferedViews.get(bufferIndex);
            View backView = null;
            if (bufferIndex < bufferedViews.size() - 1) {
                backView = bufferedViews.get(bufferIndex + 1);
            }
            renderer.updateTexture(adapterIndex, frontView, backView == null ? -1 : adapterIndex + 1,
                    backView);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        for (View child : bufferedViews) {
            child.measure(widthMeasureSpec, heightMeasureSpec);
        }

        surfaceView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    public GLSurfaceView getSurfaceView() {
        return surfaceView;
    }
    public float getTouchSlop() {
        return touchSlop;
    }
    public int getContentHeight() {
        return contentHeight;
    }
    FlipRenderer getRenderer() {
        return renderer;
    }
    public int getContentWidth() {
        return contentWidth;
    }
    public boolean isOverFlipEnabled() {
        return overFlipEnabled;
    }

    public void sendMessage(int message) {
        handler.sendMessage(Message.obtain(handler, message));
    }

    public void setOverFlipEnabled(boolean overFlipEnabled) {
        this.overFlipEnabled = overFlipEnabled;
    }

    public boolean isFlipByTouchEnabled() {
        return flipByTouchEnabled;
    }

    public void setFlipByTouchEnabled(boolean flipByTouchEnabled) {
        this.flipByTouchEnabled = flipByTouchEnabled;
    }

    private void setupSurfaceView(Context context) {
        surfaceView = new GLSurfaceView(getContext());

        cards = new FlipCards(this, flipOrientation == VERTICAL);
        renderer = new FlipRenderer(this, cards);

        surfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        surfaceView.setZOrderOnTop(true);
        surfaceView.setRenderer(renderer);
        surfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        surfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        addViewInLayout(surfaceView, -1, new AbsListView.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT), false);
    }

    public Bitmap.Config getAnimationBitmapFormat() {
        return animationBitmapFormat;
    }

    private void updateVisibleView(int index) {
        for (int i = 0; i < bufferedViews.size(); i++) {
            bufferedViews.get(i).setVisibility(index == i ? VISIBLE : GONE);
        }
    }

    public void postHideFlipAnimation() {
        if (inFlipAnimation) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    hideFlipAnimation();

                }
            });
        }
    }

    public void setOnViewFlipListener(ViewFlipListener onViewFlipListener) {
        this.onViewFlipListener = onViewFlipListener;
    }

    public ViewFlipListener getOnViewFlipListener() {
        return onViewFlipListener;
    }

    private void hideFlipAnimation() {
        if (inFlipAnimation) {
            inFlipAnimation = false;

            updateVisibleView(bufferIndex);

            if (onViewFlipListener != null) {
                onViewFlipListener.onViewFlipped(bufferedViews.get(bufferIndex), adapterIndex);
            }

            handler.post(new Runnable() {
                public void run() {
                    if (!inFlipAnimation) {
                        cards.setVisible(false);
                        surfaceView.requestRender(); //ask OpenGL to clear its display
                    }
                }
            });
        }
    }
    public void showFlipAnimation() {
        if (!inFlipAnimation) {
            inFlipAnimation = true;

            cards.setVisible(true);
            cards.setFirstDrawFinished(false);
            surfaceView.requestRender();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (flipByTouchEnabled) {
            return cards.handleTouchEvent(event, false);
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (flipByTouchEnabled) {
            return cards.handleTouchEvent(event, true);
        } else {
            return false;
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //XXX: adds a global layout listener?
    }

    public void refreshPage(View pageView) {
        if (cards.refreshPageView(pageView)) {
            requestLayout();
        }
    }

    public void refreshPage(int pageIndex) {
        if (cards.refreshPage(pageIndex)) {
            requestLayout();
        }
    }
    public void refreshAllPages() {
        cards.refreshAllPages();
        requestLayout();
    }



    public void postFlippedToView(final int indexInAdapter) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                flippedToView(indexInAdapter, true);
            }
        });
    }

    private void debugBufferedViews() {
        if (AphidLog.ENABLE_DEBUG) {
            AphidLog.d("bufferedViews: %s; buffer index %d, adapter index %d", bufferedViews, bufferIndex,
                    adapterIndex);
        }
    }

    private void setupAdapterView(View view, boolean addToTop, boolean isReusedView) {
        LayoutParams params = view.getLayoutParams();
        if (params == null) {
            params =
                    new AbsListView.LayoutParams(LayoutParams.FILL_PARENT,
                            LayoutParams.WRAP_CONTENT, 0);
        }

        if (isReusedView) {
            attachViewToParent(view, addToTop ? 0 : 1, params);
        } else {
            addViewInLayout(view, addToTop ? 0 : 1, params, true);
        }
    }

    private View viewFromAdapter(int position, boolean addToTop) {
        Assert.assertNotNull(adapter);

        View releasedView = releasedViews.isEmpty() ? null : releasedViews.removeFirst();

        View view = adapter.getView(position, releasedView, this);
        if (releasedView != null && view != releasedView) {
            addReleasedView(releasedView);
        }

        setupAdapterView(view, addToTop, view == releasedView);
        return view;
    }

    public void flippedToView(final int indexInAdapter, boolean isPost) {
          if (AphidLog.ENABLE_DEBUG) {
            AphidLog.d("flippedToView: %d, isPost %s", indexInAdapter, isPost);
        }
        debugBufferedViews();

        if (indexInAdapter >= 0 && indexInAdapter < adapterDataCount) {

            if (indexInAdapter == adapterIndex + 1) { //forward one page
                if (adapterIndex < adapterDataCount - 1) {
                    adapterIndex++;
                    View old = bufferedViews.get(bufferIndex);
                    if (bufferIndex + 1 > sideBufferSize) {
                        releaseView(bufferedViews.removeFirst());
                    }
                    if (adapterIndex + sideBufferSize < adapterDataCount) {
                        bufferedViews.addLast(viewFromAdapter(adapterIndex + sideBufferSize, true));
                    }
                    bufferIndex = bufferedViews.indexOf(old) + 1;
                    requestLayout();
                    updateVisibleView(bufferIndex);
                }
            } else if (indexInAdapter == adapterIndex - 1) {
                if (adapterIndex > 0) {
                    adapterIndex--;
                    View old = bufferedViews.get(bufferIndex);
                    if (bufferIndex - 1 + sideBufferSize < bufferedViews.size() - 1) {
                        releaseView(bufferedViews.removeLast());
                    }
                    if (adapterIndex - sideBufferSize >= 0) {
                        bufferedViews.addFirst(viewFromAdapter(adapterIndex - sideBufferSize, false));
                    }
                    bufferIndex = bufferedViews.indexOf(old) - 1;
                    requestLayout();
                    updateVisibleView(bufferIndex);
                }
            } else {
                AphidLog.e("Should not happen: indexInAdapter %d, adapterIndex %d", indexInAdapter,
                        adapterIndex);
            }
        } else {
            Assert.fail("Invalid indexInAdapter: " + indexInAdapter);
        }
        //debugBufferedViews();
    }

    public void onResume() {
        surfaceView.onResume();
    }

    public void onPause() {
        surfaceView.onPause();
    }

    @Override
    public Adapter getAdapter() {
        return adapter;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        setAdapter(adapter, 0);
    }

    public void setAdapter(Adapter adapter, int initialPosition) {
        if (this.adapter != null) {
            this.adapter.unregisterDataSetObserver(adapterDataObserver);
        }

        Assert.assertNotNull("adapter should not be null", adapter);

        this.adapter = adapter;
        adapterDataCount = adapter.getCount();

        adapterDataObserver = new MyDataSetObserver();
        this.adapter.registerDataSetObserver(adapterDataObserver);
        if (adapterDataCount > 0) {
            setSelection(initialPosition);
        }
    }

    @Override
    public View getSelectedView() {
        return (bufferIndex < bufferedViews.size() && bufferIndex >= 0) ? bufferedViews.get(bufferIndex)
                : null;
    }

    @Override
    public void setSelection(int position) {

        if (adapter == null) {
            return;
        }

        Assert.assertTrue("Invalid selection position", position >= 0 && position < adapterDataCount);

        releaseViews();

        View selectedView = viewFromAdapter(position, true);
        bufferedViews.add(selectedView);

        for (int i = 1; i <= sideBufferSize; i++) {
            int previous = position - i;
            int next = position + i;

            if (previous >= 0) {
                bufferedViews.addFirst(viewFromAdapter(previous, false));
            }
            if (next < adapterDataCount) {
                bufferedViews.addLast(viewFromAdapter(next, true));
            }
        }

        bufferIndex = bufferedViews.indexOf(selectedView);
        adapterIndex = position;

        requestLayout();
        updateVisibleView(inFlipAnimation ? -1 : bufferIndex);

        cards.resetSelection(position, adapterDataCount);

    }

    private void releaseViews() {
        for (View view : bufferedViews) {
            releaseView(view);
        }
        bufferedViews.clear();
        bufferIndex = -1;
        adapterIndex = -1;
    }

    private void releaseView(View view) {
        Assert.assertNotNull(view);
        detachViewFromParent(view);
        addReleasedView(view);
    }
    private void addReleasedView(View view) {
        Assert.assertNotNull(view);
        if (releasedViews.size() < MAX_RELEASED_VIEW_SIZE) {
            releasedViews.add(view);
        }
    }


    private void onDataChanged() {
        adapterDataCount = adapter.getCount();
        int activeIndex;
        if (adapterIndex < 0) {
            activeIndex = 0;
        } else {
            activeIndex = Math.min(adapterIndex, adapterDataCount - 1);
        }

        releaseViews();
        setSelection(activeIndex);
    }

    private class MyDataSetObserver extends DataSetObserver {

        @Override
        public void onChanged() {
            onDataChanged();
        }

        @Override
        public void onInvalidated() {
            onDataChanged();
        }
    }
}
