package com.example.hicare_prototype;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BerandaActivity extends AppCompatActivity {
    private BottomNavigationView navbar;
    private TextView tv_namaPengguna;
    private ImageView iv_profilPengguna;
    private ImageView iv_temp_logout;
    private AppCompatButton btn_tambahData;
    private FirebaseHandler firebaseHandler;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_beranda);

        context = BerandaActivity.this;
        navbar = findViewById(R.id.bottom_navigation);
        tv_namaPengguna = findViewById(R.id.tv_namaPengguna);
        iv_profilPengguna = findViewById(R.id.iv_profilPengguna);
        iv_temp_logout = findViewById(R.id.iv_temp_logout);
        btn_tambahData = findViewById(R.id.btn_tambahData);

        firebaseHandler = new FirebaseHandler(context);

        firebaseHandler.getUsername(new FirebaseHandler.GetUsernameListener() {
            @Override
            public void getUsername(String username) {
                tv_namaPengguna.setText("Halo, "+username);
            }
        });

        firebaseHandler.getProfilePicture(new FirebaseHandler.GetProfilListener() {
            @Override
            public void getProfil(Bitmap bitmap) {
                if(bitmap!=null){
                    iv_profilPengguna.setImageBitmap(bitmap);
                }
            }
        });

        btn_tambahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toForm = new Intent(context, FormDataAnakActivity.class);
                startActivity(toForm);
            }
        });

        iv_profilPengguna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProfilActivity.class));
                overridePendingTransition(0,0);
            }
        });

        iv_temp_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseHandler.logout(context);
            }
        });

        navbar.setSelectedItemId(R.id.Beranda);
        navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.Beranda:
                        return true;
                    case R.id.Riwayat:
                        startActivity(new Intent(getApplicationContext(),RiwayatActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Profil:
                        startActivity(new Intent(getApplicationContext(),ProfilActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}
