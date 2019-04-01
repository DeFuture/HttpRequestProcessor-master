package com.lzw.httpprocessor.app;

import android.app.Application;

import com.lzw.httpprocessor.BuildConfig;
import com.lzw.httpprocessor.http.ZkjcHttp;
import com.lzw.httpprocessor.processor.OkHttpProcessor;

import org.xutils.x;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //这里只需要一行代码切换网络框架，6不6！！！

        //初始化Volley方式网络请求代理
//        ZkjcHttp.init(new VolleyProcessor(this));

        //初始化Okhttp方式网络请求代理
        ZkjcHttp.init(new OkHttpProcessor());

        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能

        // 全局默认信任所有https域名 或 仅添加信任的https域名
        // 使用RequestParams#setHostnameVerifier(...)方法可设置单次请求的域名校验
        x.Ext.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }


}
