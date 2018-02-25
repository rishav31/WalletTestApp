package com.rst.rishav.wallettestapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by Rishav on 25-Feb-18.
 */

public class MapDialog extends Dialog {
    Context context;
    public MapDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_dialog);
    }
}
