package com.qmobileme.noqoodypay.util;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmobileme.noqoodypay.R;


/**
 * Created by saneebsalam
 * on 8/24/20.
 */
public class Showdialog_Success {

    ClickListener clickListener;

    public Showdialog_Success(Context context, String Message) {
        Dialog Customdialog;
        Customdialog = new Dialog(context, R.style.full_screen_dialog);
        Customdialog.setContentView(R.layout.noqoodypay_dialoge_success);
        Customdialog.show();

        TextView ok = Customdialog.findViewById(R.id.ok);
        TextView message = Customdialog.findViewById(R.id.message);
        ImageView alert_image = Customdialog.findViewById(R.id.alert_image);
//        alert_image.startAnimation(anim_shake);

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
