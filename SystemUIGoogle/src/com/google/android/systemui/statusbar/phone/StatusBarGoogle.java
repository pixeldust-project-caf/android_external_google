package com.google.android.systemui.statusbar.phone;

import android.widget.ImageView;
import com.android.internal.util.pixeldust.PixeldustUtils;
import com.android.systemui.R;
import com.android.systemui.Dependency;
import com.android.systemui.dock.DockManager;
import com.android.systemui.statusbar.phone.StatusBar;
import com.google.android.systemui.dreamliner.DockIndicationController;
import com.google.android.systemui.dreamliner.DockObserver;

public class StatusBarGoogle extends StatusBar {
    @Override
    public void start() {
        super.start();
        DockObserver dockObserver = (DockObserver) Dependency.get(DockManager.class);
        dockObserver.setDreamlinerGear((ImageView) mStatusBarWindow.findViewById(R.id.dreamliner_gear));
        dockObserver.setIndicationController(new DockIndicationController(mContext));

        // Enable MotionSense plugin
        enableOsloPlugin();
    }

    private void enableOsloPlugin() {
        if (PixeldustUtils.isPackageInstalled(mContext, "com.google.oslo")) {
            PixeldustUtils.setComponentState(mContext, "com.google.oslo", "com.google.oslo.OsloOverlay", true);
            PixeldustUtils.setComponentState(mContext, "com.google.oslo", "com.google.oslo.OsloSensorManager", true);
            PixeldustUtils.setComponentState(mContext, "com.google.oslo", "com.google.oslo.service.OsloService", true);
        }
    }
}
