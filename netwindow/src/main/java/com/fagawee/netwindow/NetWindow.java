package com.fagawee.netwindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.net.NetworkConfig;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.internal.policy.DecorView;

/**
 * Created by Mr.Tian on 2020/1/7.
 */

public class NetWindow {
    public static NetWindow instance=new NetWindow();
    public Application application;
    private NetWindowConfig config;

    public Application getApplication() {
        return application;
    }

    public NetWindow setApplication(Application application) {
        this.application = application;
        return this;
    }

    public NetWindowConfig getConfig() {
        return config;
    }

    public void setConfig(NetWindowConfig config) {
        this.config = config;
    }

    private NetWindow() {}


    public static void init(Application application)
    {
        init(application, null);
        NetWindow.instance.setConfig(NetWindowConfig.createDefault());
    }

    public static void init(Application application, NetWindowConfig config) {
        NetWindow.instance.setApplication(application);
        NetWindow.instance.setConfig(config);
        NetWindow.instance.getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {


            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @SuppressLint("ResourceType")
            @Override
            public void onActivityResumed(Activity activity) {
                View view=activity.getWindow().getDecorView();
                View lo=view.findViewById(R.string.logoid);
                if(lo==null)
                {
                    LogoView logoView=new LogoView(activity);
                    logoView.setId(R.string.logoid);
                    FrameLayout.LayoutParams parentParams=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
                    activity.addContentView(logoView,parentParams);

                }


            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }








}
