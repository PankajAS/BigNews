package com.plusonesoftwares.plusonesoftwares.bignews;

import android.content.Context;
import android.widget.Scroller;

/**
 * Created by Plus 3 on 03-03-2017.
 */

public class ScrollerCustomDuration extends Scroller {
    private double mScrollFactor = 6;

    public ScrollerCustomDuration(Context context) {
        super(context);
    }

    public void setScrollDurationFactor(double scrollFactor) {
        mScrollFactor = scrollFactor;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, (int) (duration * mScrollFactor));
    }
}
