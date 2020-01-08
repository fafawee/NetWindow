package com.fagawee.netwindow;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Tian on 2020/1/7.
 */

public class LogWindowAdapter extends BaseAdapter {


    private List<LogWindowEntity>  dataList=new ArrayList<>();

    public LogWindowAdapter setDataList(List<LogWindowEntity> dataList) {
        this.dataList = dataList;
        return this;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogWindowEntity item=dataList.get(position);
        LogWindowAdapterHolder logWindowAdapterHolder=null;
        if(convertView==null)
        {
            convertView=View.inflate(parent.getContext(),R.layout.net_logwindowdialog_item_layout,null);
            logWindowAdapterHolder=new LogWindowAdapterHolder();
            logWindowAdapterHolder.name=convertView.findViewById(R.id.name);
            logWindowAdapterHolder.timecontent=convertView.findViewById(R.id.time);
            convertView.setTag(logWindowAdapterHolder);
        }
        else
        {
            logWindowAdapterHolder=(LogWindowAdapterHolder)convertView.getTag();
        }
        logWindowAdapterHolder.name.setText("接口名："+item.interfacename);
        if (!TextUtils.isEmpty(item.time))
        {
            float time= Float.parseFloat(item.time);
            if (time>1000)
            {
                logWindowAdapterHolder.timecontent.setTextColor(Color.RED);
            }
            else
            {
                logWindowAdapterHolder.timecontent.setTextColor(0xff000000);
            }
            logWindowAdapterHolder.timecontent.setText("耗时："+item.time+"ms");
        }
        return convertView;
    }

    public static class LogWindowAdapterHolder
    {
        public TextView name;
        public TextView timecontent;
    }




}
