package com.lzw.httpprocessor.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.lzw.httpprocessor.R;
import com.lzw.httpprocessor.bean.CommEntity;
import com.lzw.httpprocessor.bean.CommonEntity;
import com.lzw.httpprocessor.bean.ExpressBean;
import com.lzw.httpprocessor.bean.TestEntity;
import com.lzw.httpprocessor.http.HttpCallback;
import com.lzw.httpprocessor.http.HttpHelper;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private Button button;
    private Button button1;
    private Button button2;
    //快递接口
    private String url2 = "http://www.kuaidi100.com/query?type=quanfengkuaidi&postid=300008026630";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> head = new HashMap();
                head.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlYzQ4NWZmNWY2MzE0MDA4YjQyODI2YTg1MGUwODQ5ZSIsInN1YiI6IjEzMjI0NDQ5ODg4IiwiaWF0IjoxNTUzODIyODEzLCJyb2xlcyI6Iue6quW3peWnlOWFmumjjuenkeWupOS6uuWRmCznuqrlt6Xlp5TmuIXljZXkuIrmiqUiLCJleHAiOjE1NTM4MjI4MjB9.1QIr_IpEzadhv_LTxkFVMDdD_HC8cE2gjcoZBWQ1lnw");
                HttpHelper httpHelper = new HttpHelper.Builder()
                        .post("\"http://192.168.0.123:8080/mobile/api/jdrz/list\"")
                        .head(head)
                        .setJson("{\n" +
                                "\"ywbh\":\"1111\"\n" +
                                "}")
                        .setCallback(new HttpCallback<String>() {
                    @Override
                    public void onSuccess(String s) {

                    }

                    @Override
                    public void onFailed(String string) {

                    }
                }).build();
                httpHelper.request(httpHelper);

                if(true) return;
                HttpHelper.obtain().postJson("\"http://192.168.0.123:8080/mobile/api/jdrz/list\"",
                        "{\n" +
                                "\"ywbh\":\"1111\"\n" +
                                "}", new HttpCallback<String>() {

                            @Override
                            public void onSuccess(String s) {

                            }

                            @Override
                            public void onFailed(String string) {

                            }
                        });

                if(true)return;
                RequestParams params = new RequestParams("http://192.168.0.123:8080/mobile/api/jdrz/list");
                params.setHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlYzQ4NWZmNWY2MzE0MDA4YjQyODI2YTg1MGUwODQ5ZSIsInN1YiI6IjEzMjI0NDQ5ODg4IiwiaWF0IjoxNTUzODIyODEzLCJyb2xlcyI6Iue6quW3peWnlOWFmumjjuenkeWupOS6uuWRmCznuqrlt6Xlp5TmuIXljZXkuIrmiqUiLCJleHAiOjE1NTM4MjI4MjB9.1QIr_IpEzadhv_LTxkFVMDdD_HC8cE2gjcoZBWQ1lnw");
                params.setAsJsonContent(true);
                params.setBodyContent("{\n" +
                        "\"ywbh\":\"1111\"\n" +
                        "}");
                Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String baseBean) {
                        Toast.makeText(x.app(), baseBean, Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(Throwable ex, boolean b) {
                        Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                        //ToastUtil.showToast(CaseDetailActivityV2.this,"登录失败，请检查您的网络");
                    }
                    @Override
                    public void onCancelled(CancelledException e) {
                        //  Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFinished() {

                    }
                });

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            //访问网络
            HttpHelper.obtain().get(url2,
                null, new HttpCallback<CommEntity<TestEntity>>() {
                    @Override
                    public void onSuccess(CommEntity<TestEntity> expressBean) {
                        Log.i("onSuccess: ",expressBean.getErrorMsg());
                        StringBuffer sb = new StringBuffer();
                        if(expressBean != null){
                            List<TestEntity.OfficeListBean> datas = expressBean.getData().getOfficeList();
                            for(TestEntity.OfficeListBean data : datas){
                                sb.append("时间：")
                                    .append(data.getName()+"\r\n")
                                    .append("地点和跟踪进度：")
                                    .append(data.getSort()+"\r\n"+"\r\n");
                                textView.setText(sb.toString());
                            }
                        }
                    }

                    @Override
                    public void onFailed(String string) {
                        Toast.makeText(MainActivity.this,"请求失败了。。"+ string,Toast.LENGTH_SHORT).show();
                    }
                });
        } else if(v.getId() == R.id.button1) {
            HttpHelper.obtain().get("http://192.168.0.110:8080/jdrzcs/detail/1b605cd153ff493189bae7f59e45939b",
                    null, new HttpCallback<CommonEntity<TestEntity>>() {
                        @Override
                        public void onSuccess(CommonEntity<TestEntity> expressBean) {
                            Log.i("onSuccess: ",expressBean.getErrorMsg());
                            StringBuffer sb = new StringBuffer();
                            if(expressBean != null){
//                                List<TestEntity.OfficeListBean> datas = expressBean.getData().get(0).getOfficeList();
//                                for(TestEntity.OfficeListBean data : datas){
//                                    sb.append("时间：")
//                                            .append(data.getName()+"\r\n")
//                                            .append("地点和跟踪进度：")
//                                            .append(data.getSort()+"\r\n"+"\r\n");
//                                    textView.setText(sb.toString());
//                                }
                            }
                        }

                        @Override
                        public void onFailed(String string) {
                            Toast.makeText(MainActivity.this,"请求失败了。。"+ string,Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
