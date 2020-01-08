package com.fagawee.netwindow;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Mr.Tian on 2020/1/7.
 */

public class LogWindowDialog extends FDialog {

    private ListView loglistview;
    private TextView clear,refresh;
    private LogWindowAdapter logWindowAdapter;
    private ArrayList<LogWindowEntity> data=new ArrayList<>();




    public LogWindowDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.net_logwindowdialog_layout);
        loglistview=findViewById(R.id.loglistview);
        clear=findViewById(R.id.clear);
        refresh=findViewById(R.id.refresh);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetMessagePoster.defaultPoster.clear();
                data.clear();
                logWindowAdapter.setDataList(data);
                logWindowAdapter.notifyDataSetChanged();

            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();

            }
        });
        logWindowAdapter=new LogWindowAdapter();
        logWindowAdapter.setDataList(data);

        loglistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogWindowEntity data = (LogWindowEntity) parent.getItemAtPosition(position);
                if (data != null) {
                    LogDialog dialog = new LogDialog(getContext());
                    dialog.setData(data);
                    dialog.show();
                } else {
                    Toast.makeText(getContext(),"暂无数据",Toast.LENGTH_SHORT).show();


                }
            }
        });
        data= NetMessagePoster.defaultPoster.getRecentMessage();
        logWindowAdapter=new LogWindowAdapter();
        logWindowAdapter.setDataList(data);
        loglistview.setAdapter(logWindowAdapter);

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                if (NetMessagePoster.defaultPoster !=null)
                {
                    NetMessagePoster.defaultPoster.destory();
                }
            }
        });
    }

    private void refreshData()
    {

        ArrayList<LogWindowEntity> datalist= NetMessagePoster.defaultPoster.getRecentMessage();
        data=datalist;
        logWindowAdapter.setDataList(data);
        logWindowAdapter.notifyDataSetChanged();
    }



}
