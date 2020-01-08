package com.fagawee.netwindow;

import android.annotation.NonNull;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

/**
 * Created by Mr.Tian on 2020/1/7.
 */

public class FDialog extends Dialog {

    public FDialog(@NonNull Context context) {
        super(context);
        setDialogTheme();
    }
    /** set dialog theme(设置对话框主题) */
    private void setDialogTheme() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// android:windowNoTitle
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// android:windowBackground
    }
}
