package com.lzw.httpprocessor.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lzw.httpprocessor.R;
import com.lzw.httpprocessor.bean.CommEntity;
import com.lzw.httpprocessor.bean.CommonEntity;
import com.lzw.httpprocessor.bean.TestEntity;
import com.lzw.httpprocessor.http.HttpCallback;
import com.lzw.httpprocessor.http.ZkjcHttp;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private EditText editUrl;
    private EditText editParam;
    private Button button;
    private Button button1;
    private Button button2;
    //快递接口
    private String url2 = "http://www.kuaidi100.com/query?type=quanfengkuaidi&postid=300008026630";

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        editUrl = (EditText) findViewById(R.id.et_url);
        editParam = (EditText) findViewById(R.id.et_param);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editUrl.getText().toString()))  {
                    editUrl.setText("http://192.168.0.152:8080/mobile/api/jdrz/list");
                    return;
                }
                if(TextUtils.isEmpty(editParam.getText().toString()))  {
                    editParam.setText("{\"ywbh\":\"1111\"}");
                    return;
                }

                for(int i = 0; i < 2; i++) {
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            ZkjcHttp httpHelper = new ZkjcHttp.Builder()
//                                    .post(editUrl.getText().toString())
                                    .post("http://www.kuaidi100.com/query")
                                    .addParam("type", "quanfengkuaidi")
                                    .addParam("postid", "300008026630")
//                                    .setJsonContent(editParam.getText().toString())
                                    .setCallback(new HttpCallback<String>() {
                                        @Override
                                        public void onSuccess(final String result) {
                                            Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                                        }

                                        @Override
                                        public void onFailed(final String string) {
                                            Toast.makeText(x.app(), string, Toast.LENGTH_LONG).show();
                                        }
                                    }).request();


                        }
                    });
                }

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
            ZkjcHttp.obtain().get(url2,
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
            ZkjcHttp.obtain().get("http://192.168.0.110:8080/jdrzcs/detail/1b605cd153ff493189bae7f59e45939b",
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
