package com.fagawee.netwindow.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fagawee.netwindow.LogWindowEntity;
import com.fagawee.netwindow.NetMessagePoster;
import com.fagawee.netwindow.RequestMessage;
import com.fagawee.netwindow.ResponseMessage;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.ProgressRequestBody;

import org.w3c.dom.Text;

public class MainActivity extends Activity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.activiy1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String json="{\n" +
                        "\t\"ls\": \"d\"\n" +
                        "}";
                OkGo.<String>post("http://api.medrd.com/api/user/getWeather?did=7484E1047010")
                        .upJson(json)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {

                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                            }
                        });


                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });



        //发送请求消息
        long start=System.currentTimeMillis();
        RequestMessage requestMessage=new RequestMessage();
        //接口名称，展示作用，自定义
        requestMessage.setInterfacename("api/weixin/deletecar/");
        requestMessage.setRequest("请求信息，用户自己定义,例如包含了url,参数、header");
        //这个是请求id，到时候需要和发送返回消息的id要一致，因为要配对，用户自己逻辑定义
        requestMessage.setId(222111);
        NetMessagePoster.defaultPoster.postRequest(requestMessage);
        /*
        *
        * *
        * *
        * 经过了一段时间的访问，马上有结果了...........loading
        * *
        * *
        * *
        * *
        *
         */
        ResponseMessage responseMessage=new ResponseMessage();
        responseMessage.setInterfacename("api/weixin/deletecar/");
        responseMessage.setResult("返回信息，用户自己定义,例如包含了body");
        responseMessage.setId(222111);
        long duration=System.currentTimeMillis()-start;
        responseMessage.setTime(duration+"");
        NetMessagePoster.defaultPoster.postResponse(responseMessage);







    }
}
