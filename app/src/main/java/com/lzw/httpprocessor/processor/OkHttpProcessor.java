package com.lzw.httpprocessor.processor;


import android.os.Handler;

import com.lzw.httpprocessor.interfaces.ICallBack;
import com.lzw.httpprocessor.interfaces.IhttpProcessor;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OkHttpProcessor implements IhttpProcessor {

    public static final String TAG ="OkHttpProcessor";

    private static OkHttpClient mOkHttpClient;
    private Handler mHandler;
    private ExecutorService executorService = Executors.newFixedThreadPool(3);

    public OkHttpProcessor(){
        mOkHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(new LoggerInterceptor())
                .addInterceptor(new TokenHeaderInterceptor())
                .build();
        mHandler = new Handler();
    }


    @Override
    public void get(String url, Map<String, Object> params, final ICallBack callback) {
        final Request request = new Request.Builder()
                .url(url)
                .build();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callback.onFailed(e.toString());
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        /*if(response.isSuccessful()){
                            final String result = response.body().toString();
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onSuccess(result);
                                }
                            });

                        }else{
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onFailed(response.message().toString());
                                }
                            });
                        }*/
                        boolean isSuccessful = response.isSuccessful();
                        postParams(callback,isSuccessful,response);
                    }
                });

            }
        });
    }


    @Override
    public void post(String url, Map<String, Object> params, final ICallBack callback) {

        RequestBody requestBody = appendBody(params);

        final Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callback.onFailed(e.toString());
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                       /* if(response.isSuccessful()){
                            final String result = response.body().toString();
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onSuccess(result);
                                }
                            });

                        }else{
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onFailed(response.message().toString());
                                }
                            });
                        }*/
                        boolean isSuccessful = response.isSuccessful();
                        postParams(callback,isSuccessful,response);
                    }
                });
            }
        });
    }

    @Override
    public void postJson(String url, String params, final ICallBack callback) {
        postJson(url, null, params, callback);
    }

    public void postJson(String url, Map<String, Object> head, String params, final ICallBack callback) {

        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), params);

        Request.Builder builder = new Request.Builder();
        if(null != head && head.size() > 0) {
            builder.url(url);
            Iterator<Map.Entry<String, Object>> iterator = head.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                builder.addHeader(next.getKey(), (String) next.getValue());
            }
            builder.post(requestBody);
            builder.build();
        } else {
            builder.url(url);//请求的url
            builder.post(requestBody);
            builder.build();
        }

        final Request request = builder.build();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if(e.toString().contains("closed")) {
                            //如果是主动取消的情况下
                        }else {
                            //其他情况下
                            callback.onFailed(e.toString());
                        }
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                       /* if(response.isSuccessful()){
                            final String result = response.body().toString();
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onSuccess(result);
                                }
                            });

                        }else{
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onFailed(response.message().toString());
                                }
                            });
                        }*/
                        boolean isSuccessful = response.isSuccessful();
                        postParams(callback,isSuccessful,response);
                    }
                });
            }
        });
    }

    @Override
    public void request(Map<String, Object> params) {
        if("METHOD_GET".equals(params.get("method"))) {
            get(params.get("url").toString(), (Map<String, Object>) params.get("mParams"), (ICallBack)params.get("callback"));
        } else if("METHOD_POST".equals(params.get("method"))) {
            if(null == params.get("json")) {
                post(params.get("url").toString(), (Map<String, Object>) params.get("mParams"), (ICallBack)params.get("callback"));
            } else {
                postJson(params.get("url").toString(), params.get("json").toString(), (ICallBack)params.get("callback"));
            }
        }
    }

//    @Override
//    public void request(Map<String, Object> params) {
//        if(instance.method == METHOD_GET) {
//            get(instance.url, instance.mParams, instance.callback);
//        } else if(instance.method == METHOD_POST) {
//            if(TextUtils.isEmpty(instance.json)) {
//                post(instance.url, instance.mParams, instance.callback);
//            } else {
//                postJson(instance.url, instance.json, instance.callback);
//            }
//        }
//    }

    //传入参数，返回添加头信息
    private RequestBody appendBody( Map<String, Object> params){
        FormBody.Builder body = new FormBody.Builder();
        if(params == null || params.isEmpty()){
            return body.build();
        }
        for(Map.Entry<String, Object> entry : params.entrySet()){
                body.add(entry.getKey(),entry.getValue().toString());
        }
        return body.build();
    }


    private void postParams(final ICallBack callback, final boolean isSuccess, final Response response){
        final String[] result = {""};
        try {
            if(isSuccess == true){
                result[0] = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(result[0]);

                    }
                });
            }else{
                result[0] = response.message();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailed(result[0]);

                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
