package com.plusonesoftwares.plusonesoftwares.bignews.chromeCustomTab;

import android.support.customtabs.CustomTabsClient;

/**
 * Created by Plus 3 on 23-03-2017.
 */

public interface ServiceConnectionCallback {
    void onServiceConnected(CustomTabsClient client);

    /**
     * Called when the service is disconnected.
     */
    void onServiceDisconnected();
}
