package com.lzw.httpprocessor.processor;


import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.lzw.httpprocessor.interfaces.ICallBack;
import com.lzw.httpprocessor.interfaces.IhttpProcessor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OkHttpProcessor implements IhttpProcessor {

    public static final String TAG ="OkHttpProcessor";

    private static OkHttpClient mOkHttpClient;
    private Handler mHandler;
    private ExecutorService executorService = Executors.newFixedThreadPool(3);
    private Call call;

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
                call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
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

        Request.Builder builder = new Request.Builder();
        Iterator<Map.Entry<String, Object>> iter = params.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry<String, Object> next = iter.next();
            if(next.getKey().contains("head$")) {
                builder.addHeader(next.getKey(), (String) next.getValue());
                iter.remove();
            }
        }

        RequestBody requestBody = appendBody(params);

        final Request request = builder
                .post(requestBody)
                .url(url)
                .build();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
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

    public void postJson(String url, Map<String, Object> head, String params, final ICallBack callback) {

        //MediaType  设置Content-Type 标头中包含的媒体类型值
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), params);

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if(null != head && head.size() > 0) {
            Iterator<Map.Entry<String, Object>> iterator = head.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                if(next.getKey().contains("head$")) {
                    builder.addHeader(next.getKey(), (String) next.getValue());
                }
            }
        }
        final Request request = builder.post(requestBody).build();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
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
        }
        else if("METHOD_POST".equals(params.get("method"))) {
            if(null == params.get("json")) {
                post(params.get("url").toString(), (Map<String, Object>) params.get("mParams"), (ICallBack)params.get("callback"));
            } else {
                postJson(params.get("url").toString(), (Map<String, Object>) params.get("mParams"), params.get("json").toString(), (ICallBack)params.get("callback"));
            }
        }
    }

    @Override
    public void cancelRequest() {
        cancelAll();
    }

    //传入参数，返回添加头信息
    private RequestBody appendBody( Map<String, Object> params){

        boolean hasFile = false;
        Iterator<Map.Entry<String, Object>> iter = params.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry<String, Object> next = iter.next();
            if(next.getKey().toString().contains("file$")) {
                hasFile = true;
                break;
            }
        }

        //如果有文件则使用multipart/form-data
        if(hasFile) {
            MediaType MutilPart_Form_Data = MediaType.parse("multipart/form-data; charset=utf-8");
//            RequestBody bodyParams = RequestBody.create(MutilPart_Form_Data, JSON.toJSONString(params));
            MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            Iterator<Map.Entry<String, Object>> paramIter = params.entrySet().iterator();
            //添加所有非文件参数
            while(paramIter.hasNext()) {
                Map.Entry<String, Object> next = paramIter.next();
                if(next.getKey().toString().contains("file$")) {
                    File file = new File(next.getValue().toString());
                    if(!file.exists()) continue;
                    requestBodyBuilder.addFormDataPart(next.getKey().substring(5), file.getName(), RequestBody.create(MutilPart_Form_Data, file));
                } else {
                    requestBodyBuilder.addFormDataPart(next.getKey(), next.getValue().toString());
                }
            }
            return requestBodyBuilder.build();
        }

        //默认表单类型
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

    /** 根据Tag取消请求 */
    public void cancelTag(Object tag) {
        if (tag == null) return;
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    /** 根据Tag取消请求 */
    public static void cancelTag(OkHttpClient client, Object tag) {
        if (client == null || tag == null) return;
        for (Call call : client.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : client.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    /** 取消所有请求请求 */
    public void cancelAll() {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            call.cancel();
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            call.cancel();
        }
    }

    /** 取消所有请求请求 */
    public static void cancelAll(OkHttpClient client) {
        if (client == null) return;
        for (Call call : client.dispatcher().queuedCalls()) {
            call.cancel();
        }
        for (Call call : client.dispatcher().runningCalls()) {
            call.cancel();
        }
    }
}
