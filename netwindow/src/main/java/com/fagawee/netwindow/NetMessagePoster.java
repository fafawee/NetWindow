package com.fagawee.netwindow;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mr.Tian on 2019/4/9.
 */

public class NetMessagePoster {


    public static final String LogKey = "LogKey";
    public static final String Sharename = "hykd";
    public static final String Receiver_Action = "Receiver_Action";
    public static final String Receiver_Data = "Receiver_Data";

    SharedPreferences pre;

    private NetMessagePosterReceiver register;
    private boolean isRegister = false;
    public static NetMessagePoster defaultPoster=new NetMessagePoster();




    private NetMessagePoster() {
        /*
        register=new NetMessagePosterReceiver();
        IntentFilter intentFilter=new IntentFilter(Receiver_Action);

        if (!isRegister) {
            NetWindow.instance.getApplication().registerReceiver(register,intentFilter);
        }
        */

    }

    public void destory() {
        if (register != null && isRegister) {
            NetWindow.instance.getApplication().unregisterReceiver(register);

        }
    }



    private void buildData(ArrayList<LogWindowEntity> data, LogWindowEntity message) {
        if (!TextUtils.isEmpty(message.interfacename)) {
            if (data != null) {
                boolean isHave = false;
                for (int i = 0; i < data.size(); i++) {
                    LogWindowEntity entity = data.get(i);
                    if (entity.id == message.id) {

                        entity.time = message.time;
                        entity.result = message.result;
                        entity.id = message.id;
                        isHave = true;
                        break;
                    }
                }
                if (!isHave) {
                    data.add(0, message);

                }

            }
        }
    }


    public void postRequest(RequestMessage requestMessage)
    {
        if(requestMessage==null)
        {
            throw new IllegalArgumentException("requestMessage 不能为null");

        }
        if(requestMessage.getId()==0)
        {
            throw new IllegalArgumentException("requestMessage id 需要设置值，并且和ResponseMessage的id一致");
        }
        LogWindowEntity logWindowEntity=new LogWindowEntity();
        logWindowEntity.setId(requestMessage.getId());
        logWindowEntity.setInterfacename(requestMessage.getInterfacename());
        logWindowEntity.setRequest(requestMessage.getRequest());
        addNetMessage(logWindowEntity);

    }
    public void postResponse(ResponseMessage responseMessage)
    {
        if(responseMessage==null)
        {
            throw new IllegalArgumentException("responseMessage 不能为null");

        }
        if(responseMessage.getId()==0)
        {
            throw new IllegalArgumentException("responseMessage id 需要设置值，并且和requestMessage的id一致");
        }
        LogWindowEntity logWindowEntity=new LogWindowEntity();
        logWindowEntity.setId(responseMessage.getId());
        logWindowEntity.setInterfacename(responseMessage.getInterfacename());
        logWindowEntity.setResult(responseMessage.getResult());
        logWindowEntity.setTime(responseMessage.getTime());
        addNetMessage(logWindowEntity);
    }


    /**
     * 添加一条日志信息，存到本地
     *
     * @param message
     */
    private void addNetMessage(LogWindowEntity message) {

        new Thread(new Runnable() {
            @Override
            public void run() {


                ArrayList<LogWindowEntity> list = getRecentMessage();
                if (list == null || list.size() == 0) {
                    ArrayList<LogWindowEntity> listsave = new ArrayList<>();
                    listsave.add(message);
                    saveMessageToPrefre(listsave);
                } else {
                    ArrayList<LogWindowEntity> datalist = new ArrayList<>();

                    if (list.size() > 30) {
                        for (int i = 0; i < 30; i++) {
                            datalist.add(list.get(i));
                        }

                    } else {
                        datalist = list;
                    }
                    buildData(datalist, message);
                    saveMessageToPrefre(datalist);
                }
            }
        })
                .start();


    }

    public ArrayList<LogWindowEntity> getRecentMessage() {
        pre = NetWindow.instance.getApplication().getSharedPreferences(Sharename, Context.MODE_PRIVATE);
        String json = pre.getString(LogKey, "");

        try {
            if (!TextUtils.isEmpty(json)) {
                ArrayList<LogWindowEntity> list = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    long id = jsonObject.getLong("id");
                    String interfacename = jsonObject.getString("interfacename");
                    String request = "";
                    if (jsonObject.has("request")) {
                        request = jsonObject.getString("request");
                    }

                    String result = "";
                    if (jsonObject.has("result")) {
                        result = jsonObject.getString("result");
                    }

                    String time = jsonObject.getString("time");
                    LogWindowEntity logWindowEntity = new LogWindowEntity();
                    logWindowEntity.setId(id);
                    logWindowEntity.setTime(time);
                    logWindowEntity.setRequest(request);
                    logWindowEntity.setResult(result);
                    logWindowEntity.setInterfacename(interfacename);
                    list.add(logWindowEntity);
                }
                return list;
            } else {
                return new ArrayList<LogWindowEntity>();
            }
        } catch (Exception e) {
            return new ArrayList<LogWindowEntity>();
        }


    }

    public void clear() {
        saveMessageToPrefre(null);
    }


    private void saveMessageToPrefre(ArrayList<LogWindowEntity> messagelist) {
        if (messagelist != null) {
            try {
                if (messagelist.size() < 3000) {
                    String json = toJson(messagelist);
                    SharedPreferences.Editor editor = getEditor();
                    editor.putString(LogKey, json);
                    editor.commit();
                }

            } catch (Exception e) {

            }

        } else {
            SharedPreferences.Editor editor = getEditor();
            editor.putString(LogKey, "");
            editor.commit();
        }

    }



    private String toJson(ArrayList<LogWindowEntity> messagelist)
    {
        String json="";
        JSONArray jsonArray=new JSONArray();
        if(messagelist!=null)
        {
            for (int i = 0; i < messagelist.size(); i++) {
                LogWindowEntity logWindowEntity=messagelist.get(i);
                JSONObject jsonObject=new JSONObject();

                try {
                    jsonObject.put("id",logWindowEntity.getId());
                    jsonObject.put("interfacename",logWindowEntity.getInterfacename());
                    jsonObject.put("request",logWindowEntity.getRequest());
                    jsonObject.put("result",logWindowEntity.getResult());
                    jsonObject.put("time",logWindowEntity.getTime());
                    jsonArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            json=jsonArray.toString();
        }


        return json;

    }





    private SharedPreferences.Editor getEditor() {
        SharedPreferences.Editor editor;
        pre = NetWindow.instance.getApplication().getSharedPreferences(Sharename, Context.MODE_PRIVATE);
        editor = pre.edit();
        return editor;

    }



    public  class NetMessagePosterReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();

            if(!TextUtils.isEmpty(action)&&action.equals(Receiver_Action))
            {
                LogWindowEntity logWindowMessage = (LogWindowEntity)intent.getSerializableExtra(Receiver_Data);
                addNetMessage(logWindowMessage);
            }



        }
    }


}
