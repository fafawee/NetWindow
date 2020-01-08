package com.fagawee.netwindow;

import android.graphics.Point;
import android.util.Size;

/**
 * Created by Mr.Tian on 2020/1/7.
 */

public class NetWindowConfig {
    //在屏幕上的位置
    private Point positionOnScreen;
    //logo的文本内容
    private String netLogoText;
    //logo本地资源图片
    private int netLogoImage;
    //logo的尺寸大小
    private FSize size;
    //这个要用户设置 从url获取接口名称的规则
    private InterfaceName interfaceName;

    public InterfaceName getInterfaceName() {
        return interfaceName;
    }

    public NetWindowConfig setInterfaceName(InterfaceName interfaceName) {
        this.interfaceName = interfaceName;
        return this;
    }

    public Point getPositionOnScreen() {
        return positionOnScreen;
    }

    public NetWindowConfig setPositionOnScreen(Point positionOnScreen) {
        this.positionOnScreen = positionOnScreen;
        return this;
    }

    public String getNetLogoText() {
        return netLogoText;
    }

    public NetWindowConfig setNetLogoText(String netLogoText) {
        this.netLogoText = netLogoText;
        return this;
    }

    public int getNetLogoImage() {
        return netLogoImage;
    }

    public NetWindowConfig setNetLogoImage(int netLogoImage) {
        this.netLogoImage = netLogoImage;
        return this;
    }

    public FSize getSize() {
        return size;
    }

    public NetWindowConfig setSize(FSize size) {
        this.size = size;
        return this;
    }

    public static NetWindowConfig createDefault()
    {
        NetWindowConfig netWindowConfig=new NetWindowConfig();
        netWindowConfig.setNetLogoImage(-1)
                .setNetLogoText("Net")
                .setPositionOnScreen(new Point((int)(NetWindowUtils.getScreenWidth()*0.05),(int)(NetWindowUtils.getScreenHeight()*0.8)))
                .setSize(new FSize(NetWindowUtils.dp2px(40),NetWindowUtils.dp2px(40)))
        .setInterfaceName(defaultInterface);
        return netWindowConfig;
    }
    public static InterfaceName defaultInterface=new InterfaceName() {
        @Override
        public String getShowInterfaceName(String url) {
            return url;

        }
    };


}
