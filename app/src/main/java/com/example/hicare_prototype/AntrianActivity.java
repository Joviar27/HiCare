package com.example.hicare_prototype;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class AntrianActivity extends AppCompatActivity {

    private TextView tv_noAntrian;
    private AppCompatButton btn_toBeranda;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_antrian);

        tv_noAntrian = findViewById(R.id.tv_noAntrian);
        btn_toBeranda = findViewById(R.id.btn_toBeranda);
        context = AntrianActivity.this;

        Bundle extras = getIntent().getExtras();
        String nomorAntrian = extras.getString("no_antrian");

        tv_noAntrian.setText(nomorAntrian);

        btn_toBeranda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBeranda = new Intent(context, BerandaActivity.class);
                startActivity(toBeranda);
            }
        });
    }
}
