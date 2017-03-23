package com.plusonesoftwares.plusonesoftwares.bignews.chromeCustomTab;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by Plus 3 on 23-03-2017.
 */

public class KeepAliveService extends Service {
    private static final Binder sBinder = new Binder();

    @Override
    public IBinder onBind(Intent intent) {
        return sBinder;
    }
}
