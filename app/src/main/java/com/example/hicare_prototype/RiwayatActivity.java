package com.example.hicare_prototype;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class RiwayatActivity extends AppCompatActivity {

    private RecyclerView rv_riwayatAntrian;
    private RiwayatAdapter riwayatAdapter;
    private ArrayList<AntrianModel> daftar_riwayat;
    private Context context;
    private BottomNavigationView navbar;

    private FirebaseHandler firebaseHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_riwayat);

        context = RiwayatActivity.this;
        rv_riwayatAntrian = findViewById(R.id.rv_riwayatAntrian);
        navbar = findViewById(R.id.bottom_navigation);

        firebaseHandler = new FirebaseHandler(context);

        daftar_riwayat = new ArrayList<>();
        daftar_riwayat = firebaseHandler.getAllAntrian();

        riwayatAdapter = new RiwayatAdapter(context, daftar_riwayat);
        rv_riwayatAntrian.setAdapter(riwayatAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rv_riwayatAntrian.setLayoutManager(layoutManager);

        navbar.setSelectedItemId(R.id.Riwayat);
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
