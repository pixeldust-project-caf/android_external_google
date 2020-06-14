package com.google.android.systemui.assist.uihints.edgelights.mode;

import android.metrics.LogMaker;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.assist.p003ui.EdgeLight;
import com.android.systemui.assist.p003ui.PerimeterPathGuide;
import com.google.android.systemui.assist.uihints.edgelights.EdgeLightsView;

public final class Gone implements EdgeLightsView.Mode {
    public int getSubType() {
        return 0;
    }

    public void start(EdgeLightsView edgeLightsView, PerimeterPathGuide perimeterPathGuide, EdgeLightsView.Mode mode) {
        edgeLightsView.setAssistLights(new EdgeLight[0]);
    }

    public void onNewModeRequest(EdgeLightsView edgeLightsView, EdgeLightsView.Mode mode) {
        edgeLightsView.setVisibility(0);
        edgeLightsView.commitModeTransition(mode);
    }

    public void logState() {
        MetricsLogger.action(new LogMaker(1716).setType(2));
    }
}
