package com.example.hicare_prototype;

import android.content.Context;
import android.widget.Toast;

public class ToastGenerator {

    private Context context;

    public ToastGenerator(Context context){
        this.context = context;
    }

    public void generate(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
