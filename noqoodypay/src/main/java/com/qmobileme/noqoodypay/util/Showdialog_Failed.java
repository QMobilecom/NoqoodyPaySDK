package com.qmobileme.noqoodypay.util;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.qmobileme.noqoodypay.R;


/**
 * Created by saneebsalam
 * on 8/24/20.
 */
public class Showdialog_Failed {
    Dialog Customdialog;

    ClickListener clickListener;

    public Showdialog_Failed(Context context, String Message) {

        Customdialog = new Dialog(context, R.style.full_screen_dialog);
        Customdialog.setContentView(R.layout.noqoodypay_dialoge_failed);
        Customdialog.show();

        TextView ok = Customdialog.findViewById(R.id.ok);
        TextView message = Customdialog.findViewById(R.id.message);

        message.setText(Message);

        ok.setOnClickListener(v -> {
            Customdialog.dismiss();
            if (clickListener != null)
                clickListener.onClick();
        });

    }

    public void setOnClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;

    }

    public interface ClickListener {
        void onClick();
    }
}
