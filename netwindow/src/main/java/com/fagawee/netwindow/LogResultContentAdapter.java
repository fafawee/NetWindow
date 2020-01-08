package com.fagawee.netwindow;

import android.graphics.Color;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Tian on 2020/1/7.
 */

public class LogResultContentAdapter  extends BaseAdapter{

    private List<String> data=new ArrayList<>();

    public LogResultContentAdapter setData(List<String> data) {
        this.data = data;
        return this;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item=data.get(position);
        LogAdapterHolder holder=null;
        if(convertView==null)
        {
            convertView=View.inflate(parent.getContext(),R.layout.log_dialog_result_listitem,null);
            holder=new LogAdapterHolder();
            holder.log_edit=convertView.findViewById(R.id.log_edit);

            convertView.setTag(holder);
        }
        else
        {
            holder=(LogAdapterHolder)convertView.getTag();
        }

        holder.log_edit.setMovementMethod(LinkMovementMethod.getInstance());
        holder.log_edit.setAutoLinkMask(Linkify.WEB_URLS);
        if (!TextUtils.isEmpty(item)) {
            holder.log_edit.setHint("");
            holder.log_edit.setText(item);
        } else {
            holder.log_edit.setHint("暂无数据");
            holder.log_edit.setText("");
        }
        return convertView;
    }


    public static class LogAdapterHolder
    {
        public EditText log_edit;

    }


}
