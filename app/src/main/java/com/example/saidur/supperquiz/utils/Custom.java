package com.example.saidur.supperquiz.utils;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.saidur.supperquiz.R;
import java.util.Objects;

public class Custom {

    public static void gotopage(Activity con, Class<?> ac) {
        Intent iss = new Intent(con, ac);
        con.startActivity(iss);
        con.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public static void disableClick(TextView tv1, TextView tv2, TextView tv3, TextView tv4) {
        tv1.setClickable(false);
        tv2.setClickable(false);
        tv3.setClickable(false);
        tv4.setClickable(false);
    }

    public static void enaableClick(TextView tv1, TextView tv2, TextView tv3, TextView tv4) {
        tv1.setClickable(true);
        tv2.setClickable(true);
        tv3.setClickable(true);
        tv4.setClickable(true);

        tv1.setTextColor(Color.parseColor("#FF000000"));
        tv2.setTextColor(Color.parseColor("#FF000000"));
        tv3.setTextColor(Color.parseColor("#FF000000"));
        tv4.setTextColor(Color.parseColor("#FF000000"));
    }

    public static Dialog dlgStart(Dialog dialog) {
        dialog.setContentView(R.layout.pop_start);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return dialog;
    }

    public static Dialog dlgExit(Dialog dialog) {
        dialog.setContentView(R.layout.pop_exit);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return dialog;
    }

    public static Dialog dlgCongress(Dialog dialog) {
        dialog.setContentView(R.layout.pop_congrates);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        return dialog;
    }




}
