package com.fagawee.netwindow;

import android.content.Context;
import android.os.Bundle;
import android.annotation.NonNull;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Tian on 2020/1/7.
 */

public class LogDialog extends FDialog {

    private EditText request;
    private TextView request_title;
    private ListView result;
    private View headerView;
    private LogWindowEntity data;
    private LogResultContentAdapter adapter;
    private List<String> dataList = new ArrayList<>();

    private View initHeaderView() {
        View v = View.inflate(getContext(), R.layout.log_dialog_listview_headerview, null);
        request = (EditText) v.findViewById(R.id.request);
        request_title = (TextView) v.findViewById(R.id.request_title);
        request_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request.getVisibility() == View.VISIBLE) {
                    request.setVisibility(View.GONE);
                } else {
                    request.setVisibility(View.VISIBLE);
                }
            }
        });
        return v;
    }
    public void setData(LogWindowEntity data) {
        this.data = data;
    }
    public LogDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_dialog_layout);
        headerView=initHeaderView();
        result = (ListView) findViewById(R.id.result);
        result.addHeaderView(initHeaderView());


        request.setMovementMethod(LinkMovementMethod.getInstance());
        request.setAutoLinkMask(Linkify.WEB_URLS);
        if (data != null) {
            if (!TextUtils.isEmpty(data.request)) {
                dataList.clear();
                if (!TextUtils.isEmpty(data.result))
                {
                    dataList = buildStringList(data.result);
                    adapter = new LogResultContentAdapter();
                    adapter.setData(dataList);
                    result.setAdapter(adapter);
                }

            }
            if (!TextUtils.isEmpty(data.request)) {
                request.setText(data.request);
            }
        }


    }



    public List<String> buildStringList(String src) {
        List<String> result = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new StringReader(src));
        try {
            int lineNum = 0;
            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while (line != null) {
                buffer.append(line);
                line = reader.readLine();
                lineNum++;
                if (lineNum == 40) {
                    result.add(buffer.toString());
                    buffer = new StringBuffer();
                    lineNum = 0;

                } else {
                    buffer.append("\n");
                }
            }
            if (!TextUtils.isEmpty(buffer.toString())) {
                result.add(buffer.toString());
            }
        } catch (Exception e) {

        }
        return result;
    }
}
