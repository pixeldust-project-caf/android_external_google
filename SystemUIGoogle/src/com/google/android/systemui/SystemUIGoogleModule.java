/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.systemui;

import android.content.Context;

import com.android.internal.app.AssistUtils;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.SystemUIRootComponent;
import com.android.systemui.assist.AssistHandleBehaviorController;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.dock.DockManager;
import com.android.systemui.power.EnhancedEstimates;
import com.android.systemui.power.EnhancedEstimatesImpl;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.NotificationInterruptionStateProvider;
import com.android.systemui.statusbar.notification.collection.NotificationData;
import com.android.systemui.statusbar.phone.KeyguardEnvironmentImpl;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.google.android.systemui.assist.AssistManagerGoogle;
import com.google.android.systemui.dreamliner.DockObserver;
import com.google.android.systemui.dreamliner.DreamlinerUtils;
import com.google.android.systemui.statusbar.NotificationEntryManagerGoogle;
import com.google.android.systemui.statusbar.NotificationInterruptionStateProviderGoogle;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import static com.android.systemui.Dependency.ALLOW_NOTIFICATION_LONG_PRESS_NAME;
import static com.android.systemui.Dependency.LEAK_REPORT_EMAIL_NAME;

/**
 * A dagger module for injecting default implementations of components of System UI that may be
 * overridden by the System UI implementation.
 */
@Module
abstract class SystemUIGoogleModule {

    @Singleton
    @Provides
    @Named(LEAK_REPORT_EMAIL_NAME)
    static String provideLeakReportEmail() {
        return "";
    }

    @Binds
    abstract EnhancedEstimates bindEnhancedEstimates(EnhancedEstimatesImpl enhancedEstimates);

    // TODO: This is WIP.
    @Provides
    static AssistManager provideAssistManager(
            DeviceProvisionedController deviceProvisionedController, Context context, AssistUtils assistUtils,
            AssistHandleBehaviorController handleController, ConfigurationController configurationController,
            OverviewProxyService overviewProxyService) {
        return new AssistManagerGoogle(deviceProvisionedController, context, assistUtils, handleController,
                configurationController, overviewProxyService);
    }

    @Binds
    abstract NotificationEntryManager bindNotificationEntryManager(
            NotificationEntryManagerGoogle notificationEntryManagerManager);

    @Binds
    abstract NotificationLockscreenUserManager bindNotificationLockscreenUserManager(
            NotificationLockscreenUserManagerGoogle notificationLockscreenUserManager);

    @Provides
    static DockManager provideDockManager(Context context) {
        return new DockObserver(context, DreamlinerUtils.getInstance(context));
    }

    @Binds
    abstract NotificationData.KeyguardEnvironment bindKeyguardEnvironment(
            KeyguardEnvironmentImpl keyguardEnvironment);

    @Binds
    abstract NotificationInterruptionStateProvider bindNotificationInterruptionStateProvider(
            NotificationInterruptionStateProviderGoogle notificationInterruptionStateProvider);

    @Singleton
    @Provides
    static ShadeController provideShadeController(Context context) {
        return ((SystemUIApplication) context.getApplicationContext()).getComponent(StatusBar.class);
    }

    @Singleton
    @Provides
    @Named(ALLOW_NOTIFICATION_LONG_PRESS_NAME)
    static boolean provideAllowNotificationLongPress() {
        return true;
    }

    @Binds
    abstract SystemUIRootComponent bindSystemUIRootComponent(
            SystemUIGoogleRootComponent systemUIRootComponent);
}
