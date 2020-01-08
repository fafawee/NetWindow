package com.fagawee.netwindow;

import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * Created by Mr.Tian on 2020/1/7.
 */

public class NetInterpoter implements Interceptor{


    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

            /*
            String Authorization= ShareManager.instance.getString(ShareManager.DeviceId_Type);
            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .addHeader("Authorization", Authorization);
            Request request = requestBuilder.build();
            */


        int logId = (int) (Math.random() * Integer.MAX_VALUE);


        long t1 = System.currentTimeMillis();
        String url = request.url().toString();
        RequestBody requestBodybody = request.body();
        String requestBody = "";
        if (requestBodybody != null) {
            if (requestBodybody instanceof FormBody) {
                FormBody formBody = (FormBody) requestBodybody;
                for (int i = 0; i < formBody.size(); i++) {
                    requestBody = requestBody + "name:" + formBody.name(i) + "---value:" + formBody.value(i) + "\n";
                }
            }
            else
            {

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


                Sink sink = null;
                BufferedSink bufferedSink = null;
                sink = Okio.sink(byteArrayOutputStream);
                bufferedSink = Okio.buffer(sink);
                requestBodybody.writeTo(bufferedSink);

                sink.flush();
                bufferedSink.flush();

                byte[] buff = byteArrayOutputStream.toByteArray();

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buff);
                Source source = Okio.source(byteArrayInputStream);
                Buffer buffer = bufferedSink.buffer();
                source.read(buffer, buff.length);

                String s = buffer.clone().readUtf8();
                requestBody = s + "\n";

                byteArrayInputStream.close();
                source.close();
                buffer.clone();
                byteArrayOutputStream.close();
                sink.close();
                bufferedSink.close();
                byteArrayOutputStream.close();
            }





        }
        StringBuffer stringBuffer_request = new StringBuffer();
        stringBuffer_request.append("请求信息:\n");
        stringBuffer_request.append("url:" + url + "\n");
        stringBuffer_request.append("header:\n" + request.headers().toString());
        stringBuffer_request.append("method:" + request.method() + "\n");
        if (!TextUtils.isEmpty(requestBody)) {
            stringBuffer_request.append("params:\n" + url + "?" + requestBody + "");
        }

        sendStartLogMessage(url.toString(), stringBuffer_request.toString());


        Response response = chain.proceed(request);

        int code = response.code();
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();

        if (!bodyEncoded(response.headers())) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    return response;
                }
            }

            if (!isPlaintext(buffer)) {
                return response;
            }


            if (contentLength != 0) {
                String result = buffer.clone().readString(charset);
                long t2 = System.currentTimeMillis();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("返回信息:\n");
                stringBuffer.append("url:" + response.request().url() + "\n");
                stringBuffer.append("耗时:" + (t2 - t1) + "ms\n");
                stringBuffer.append("code:" + code + "\n");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String text = NetWindowUtils.formatJson(result);


                        sendEndLogMessage(response.request().url().toString(), text);
                    }
                })
                        .start();
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                }
            }).start();
        }
        return response;
    }


    private  boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }

    private  boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }






    private static int id = -1;
    private static long duration = -1;

    private static void sendStartLogMessage(String interfaceName, String request) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                duration = System.currentTimeMillis();

                id = (int) (Math.random() * 100000000);
                String name="";
                name=NetWindow.instance.getConfig().getInterfaceName().getShowInterfaceName(interfaceName);

                RequestMessage requestMessage=new RequestMessage();
                requestMessage.setId(id);
                requestMessage.setRequest(request);
                requestMessage.setInterfacename(name);
                NetMessagePoster.defaultPoster.postRequest(requestMessage);

            }
        })
                .start();
    }

    private static void sendEndLogMessage(String interfaceName, String result) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                if (id > 0) {
                    long du = System.currentTimeMillis() - duration;
                    String name="";
                    name=NetWindow.instance.getConfig().getInterfaceName().getShowInterfaceName(interfaceName);

                    ResponseMessage responseMessage=new ResponseMessage();
                    responseMessage.setId(id);
                    responseMessage.setResult(result);
                    responseMessage.setTime(du+"");
                    responseMessage.setInterfacename(name);
                    NetMessagePoster.defaultPoster.postResponse(responseMessage);

                    id = -1;
                }
            }
        })
                .start();
    }



}
