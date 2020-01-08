package com.fagawee.netwindow;

import android.content.Context;
import android.graphics.Color;
import android.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Mr.Tian on 2020/1/7.
 */

public class LogoView extends LinearLayout{


    private TextView textView;
    private NetWindowConfig config;


    public LogoView(Context context) {
        super(context);
        init();
    }

    public LogoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LogoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        config=NetWindow.instance.getConfig();
        buildView();

    }
    private void buildView()
    {

        setOrientation(VERTICAL);

        textView=new TextView(getContext());
        if(config!=null)
        {
            if(config.getNetLogoText()!=null)
            {
                textView.setText(config.getNetLogoText());
                textView.setTextColor(Color.RED);
                textView.setBackgroundColor(Color.GRAY);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                textView.setGravity(Gravity.CENTER);
            }
            else if(config.getNetLogoImage()!=-1)
            {
                textView.setBackgroundResource(config.getNetLogoImage());
            }

        }
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(config.getSize().getWidth(),config.getSize().getHeight());

        params.leftMargin=config.getPositionOnScreen().x;
        params.topMargin=config.getPositionOnScreen().y;
        textView.setLayoutParams(params);
        removeAllViews();
        addView(textView);


        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LogWindowDialog logWindowDialog=new LogWindowDialog(getContext());
                logWindowDialog.show();

            }
        });


    }







}
