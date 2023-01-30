package com.example.hicare_prototype;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class ProfilActivity extends AppCompatActivity {

    private TextView tv_nama;
    private TextView tv_noTelepon;
    private TextView tv_usia;
    private TextView tv_jenisKelamin;
    private TextView tv_btn_toEditProfil;
    private ImageView iv_profil;

    private Context context;
    private BottomNavigationView navbar;

    private FirebaseHandler firebaseHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_profil);

        context = ProfilActivity.this;
        navbar = findViewById(R.id.bottom_navigation);
        tv_nama = findViewById(R.id.tv_nama);
        tv_noTelepon = findViewById(R.id.tv_noTelepon);
        tv_usia = findViewById(R.id.tv_usia);
        tv_jenisKelamin = findViewById(R.id.tv_jenisKelamin);

        iv_profil = findViewById(R.id.iv_profil);
        tv_btn_toEditProfil = findViewById(R.id.tv_btn_toEditProfil);

        firebaseHandler = new FirebaseHandler(context);

        firebaseHandler.getProfilePicture(new FirebaseHandler.GetProfilListener() {
            @Override
            public void getProfil(Bitmap bitmap) {
                if(bitmap!=null){
                    iv_profil.setImageBitmap(bitmap);
                }
            }
        });

        firebaseHandler.getDataProfil(new FirebaseHandler.GetDataProfilListener() {
            @Override
            public void getDataProfil(ArrayList<String> dataprofil) {
                if(dataprofil.size()>2){
                    tv_nama.setText(dataprofil.get(0));
                    tv_noTelepon.setText(dataprofil.get(1));
                    tv_usia.setText(dataprofil.get(2));
                    tv_jenisKelamin.setText(dataprofil.get(3));
                }
                else{
                    tv_nama.setText(dataprofil.get(0));
                }
            }
        });

        tv_btn_toEditProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toEditProfil = new Intent(context, EditProfilActivity.class);
                startActivity(toEditProfil);
            }
        });

        navbar.setSelectedItemId(R.id.Profil);
        navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.Beranda:
                        startActivity(new Intent(getApplicationContext(),BerandaActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Riwayat:
                        startActivity(new Intent(getApplicationContext(),RiwayatActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Profil:
                        return true;
                }
                return false;
            }
        });
    }
}
