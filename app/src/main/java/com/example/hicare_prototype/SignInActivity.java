package com.example.hicare_prototype;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity {

    private EditText et_email, et_pass;
    private TextView tv_signUp;
    private ArrayList<EditText> et_collection;
    private AppCompatButton btn_signIn;
    private Context context;

    private FormHandler formHandler;
    private FirebaseHandler firebaseHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_signin);

        context = SignInActivity.this;
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_password);
        tv_signUp = findViewById(R.id.tv_signUp);
        btn_signIn = findViewById(R.id.btn_signIn);

        et_collection = new ArrayList<>();
        et_collection.add(et_email);
        et_collection.add(et_pass);

        formHandler = new FormHandler(et_collection);
        firebaseHandler = new FirebaseHandler(context);

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!formHandler.validateForm()){
                    return;
                }
                firebaseHandler.signIn(formHandler.getValue());
                Log.d(TAG, formHandler.getValue().toString());
            }
        });

        tv_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSignUp = new Intent(context, SignUpActivity.class);
                startActivity(toSignUp);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseHandler.updateUi(BerandaActivity.class);
    }
}
