package com.example.hicare_prototype;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    private EditText et_username, et_email, et_pass;
    private TextView tv_signIn;
    private ArrayList<EditText> et_collection;
    private AppCompatButton btn_signUp;
    private Context context;

    private FormHandler formHandler;
    private FirebaseHandler firebaseHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_signup);

        context = SignUpActivity.this;
        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_password);
        tv_signIn = findViewById(R.id.tv_signIn);
        btn_signUp = findViewById(R.id.btn_signUp);

        et_collection = new ArrayList<>();
        et_collection.add(et_username);
        et_collection.add(et_email);
        et_collection.add(et_pass);

        formHandler = new FormHandler(et_collection);
        firebaseHandler = new FirebaseHandler(context);

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!formHandler.validateForm()){
                    return;
                }
                firebaseHandler.signUp(formHandler.getValue());
                Log.d(TAG, formHandler.getValue().toString());
            }
        });

        tv_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSignIn= new Intent(context, SignInActivity.class);
                startActivity(toSignIn);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseHandler.updateUi(BerandaActivity.class);
    }
}
