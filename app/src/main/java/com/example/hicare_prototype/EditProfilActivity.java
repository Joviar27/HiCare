package com.example.hicare_prototype;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Objects;

public class EditProfilActivity extends AppCompatActivity {

    private EditText et_nama;
    private EditText et_nomorTelepon;
    private EditText et_email;
    private ArrayList<EditText> et_collection;
    private TextView tv_tanggalLahir;
    private TextView tv_uploadProfil;
    private ArrayList<TextView> tv_collection;
    private Spinner spinner_jenisKelamin;
    private ArrayList<Spinner> spinner_colletion;

    private RelativeLayout rl_profil;
    private ImageView iv_btn_back;
    private ImageView iv_previewProfil;
    private ImageView iv_uploadProfil;
    private AppCompatButton btn_simpanProfil;

    private Context context;
    private Uri profilUri;
    private Window window;

    private FirebaseHandler firebaseHandler;
    private FormHandler formHandler;
    private ToastGenerator toastGenerator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_profil_edit);

        context = EditProfilActivity.this;
        et_nama = findViewById(R.id.et_nama);
        et_nomorTelepon = findViewById(R.id.et_nomorTelepon);
        et_email = findViewById(R.id.et_email);
        et_collection = new ArrayList<>();
        et_collection.add(et_nama);
        et_collection.add(et_nomorTelepon);
        et_collection.add(et_email);

        tv_tanggalLahir = findViewById(R.id.tv_tanggalLahir);
        tv_collection = new ArrayList<>();
        tv_collection.add(tv_tanggalLahir);

        spinner_jenisKelamin = findViewById(R.id.spinner_jenisKelamin);
        spinner_colletion = new ArrayList<>();
        spinner_colletion.add(spinner_jenisKelamin);

        rl_profil = findViewById(R.id.rl_profil);
        iv_previewProfil = findViewById(R.id.iv_previewProfil);
        tv_uploadProfil = findViewById(R.id.tv_uploadProfil);
        iv_uploadProfil = findViewById(R.id.iv_uploadProfil);

        iv_btn_back = findViewById(R.id.iv_btn_back);
        btn_simpanProfil = findViewById(R.id.btn_simpanProfil);

        firebaseHandler = new FirebaseHandler(context);
        formHandler = new FormHandler(et_collection,tv_collection,spinner_colletion);
        toastGenerator = new ToastGenerator(context);

        window = getWindow();

        ArrayAdapter adapterJenisKelamin = ArrayAdapter.createFromResource(this,R.array.jenis_kelamin,R.layout.spinner_selected);
        spinner_jenisKelamin.setAdapter(adapterJenisKelamin);

        tv_tanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = DatePickerSpinner.newInstance("Tanggal Lahir", new DatePickerSpinner.OnPositiveClickListener() {
                    @Override
                    public void onClick(String date) {
                        tv_tanggalLahir.setText(date);
                    }
                });
                newFragment.show(getSupportFragmentManager(), "dialog");
            }
        });

        firebaseHandler.getDataProfil(new FirebaseHandler.GetDataProfilListener() {
            @Override
            public void getDataProfil(ArrayList<String> dataprofil) {
                et_nama.setText(dataprofil.get(0));
                et_nomorTelepon.setText(dataprofil.get(1));
                String[] arJenisKelamin = getResources().getStringArray(R.array.jenis_kelamin);
                for (int i = 0; i < arJenisKelamin.length; i++) {
                    if (Objects.equals(arJenisKelamin[i], dataprofil.get(3))) {
                        spinner_jenisKelamin.setSelection(i);
                        break;
                    }
                }
                et_email.setText(dataprofil.get(4));
                tv_tanggalLahir.setText(dataprofil.get(5));
            }
        });

        iv_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, null);
                finish();
            }
        });

        rl_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                ActivityResultLauncher.launch(intent);
            }
        });

        btn_simpanProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!formHandler.validateForm()){
                    return;
                }

                ArrayList<String> input_user = formHandler.getValue();

                if(iv_previewProfil.getVisibility()== View.VISIBLE) {
                    firebaseHandler.insertFotoProfil(context, window, profilUri, new FirebaseHandler.OnUploadSuccessListener() {
                        @Override
                        public void success(boolean success, String fileName) {
                            if (!success) {
                                toastGenerator.generate("Gagal Mengupload Gambar");
                            } else {
                                input_user.add(fileName);
                                firebaseHandler.insertUser(input_user);
                                Intent toProfil = new Intent(context, ProfilActivity.class);
                                startActivity(toProfil);
                            }
                        }
                    });
                }
                else{
                    firebaseHandler.insertUser(input_user);
                    Intent toProfil = new Intent(context, ProfilActivity.class);
                    startActivity(toProfil);
                }
            }
        });
    }

    androidx.activity.result.ActivityResultLauncher<Intent> ActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        tv_uploadProfil.setVisibility(View.INVISIBLE);
                        iv_uploadProfil.setVisibility(View.INVISIBLE);
                        iv_previewProfil.setVisibility(View.VISIBLE);
                        profilUri = data.getData();
                        iv_previewProfil.setImageURI(profilUri);
                    }
                }
            });
}
