package com.rst.rishav.wallettestapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by Rishav on 24-Feb-18.
 */

public class ConfirmDailog extends Dialog {
    Context context;
    String s;
    public ConfirmDailog(@NonNull Context context) {
        super(context);
        this.context = context;
    }
    public ConfirmDailog(@NonNull Context context,String userid) {
        super(context);
        this.context = context;
        s = userid;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_dialog);
        ((TextView)findViewById(R.id.userid)).setText(s);
        ((TextView)findViewById(R.id.transc_id)).setText(UUID.randomUUID().toString());
        (findViewById(R.id.confirm)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    }
