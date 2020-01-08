package com.fagawee.netwindow.demo;

import android.app.Application;
import android.graphics.Point;

import com.fagawee.netwindow.FSize;
import com.fagawee.netwindow.InterfaceName;
import com.fagawee.netwindow.NetInterpoter;
import com.fagawee.netwindow.NetWindow;
import com.fagawee.netwindow.NetWindowConfig;
import com.fagawee.netwindow.NetWindowUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.model.HttpHeaders;

import org.apache.http.params.HttpParams;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Mr.Tian on 2020/1/7.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        NetWindow.init(this);
        NetWindow.instance.getConfig().setInterfaceName(new InterfaceName() {
            @Override
            public String getShowInterfaceName(String url) {
                return url;
            }
        });
        init(this);
    }



    public static void init(Application application) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(new NetInterpoter());



        //使用数据库保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(application)));

        HttpHeaders headers = new HttpHeaders();



        OkGo.getInstance().init(application)                       //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(0)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers)                      //全局公共头
                ;                       //全局公共参数


    }


}
