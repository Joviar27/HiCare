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
public class FormDataAnakActivity extends AppCompatActivity {

    private EditText et_namaAnak;
    private EditText et_beratBadan;
    private EditText et_tinggiBadan;
    private EditText et_lingkarKepala;
    private ArrayList<EditText> et_collection;
    private TextView tv_tanggalLahir;
    private TextView tv_tanggalLayanan;
    private TextView tv_uploadBPJS;
    private ArrayList<TextView> tv_collection;
    private Spinner spinner_rumahSakit;
    private Spinner spinner_jenisKelamin;
    private Spinner spinner_goldar;
    private Spinner spinner_alergi;
    private Spinner spinner_pelayanan;
    private ArrayList<Spinner> spinner_colletion;
    private RelativeLayout rl_kartuBPJS;
    private ImageView iv_btn_back;
    private ImageView iv_previewBPJS;
    private ImageView iv_uploadBPJS;
    private AppCompatButton btn_inputForm;

    private Context context;
    private Uri bpjsUri;
    private Window window;

    private FirebaseHandler firebaseHandler;
    private FormHandler formHandler;
    private ToastGenerator toastGenerator;
    private static NomorAntrianGenerator nomorAntrianGenerator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_data_anak);

        context = FormDataAnakActivity.this;
        et_namaAnak = findViewById(R.id.et_namaAnak);
        et_beratBadan = findViewById(R.id.et_beratBadan);
        et_tinggiBadan = findViewById(R.id.et_tinggiBadan);
        et_lingkarKepala = findViewById(R.id.et_lingkarKepala);
        et_collection = new ArrayList<>();
        et_collection.add(et_namaAnak);
        et_collection.add(et_beratBadan);
        et_collection.add(et_tinggiBadan);
        et_collection.add(et_lingkarKepala);

        tv_tanggalLahir = findViewById(R.id.tv_tanggalLahirAnak);
        tv_tanggalLayanan = findViewById(R.id.tv_tanggalLayanan);
        tv_collection = new ArrayList<>();
        tv_collection.add(tv_tanggalLahir);
        tv_collection.add(tv_tanggalLayanan);

        spinner_rumahSakit = findViewById(R.id.spinner_rumahSakit);
        spinner_jenisKelamin = findViewById(R.id.spinner_jenisKelamin);
        spinner_goldar = findViewById(R.id.spinner_goldar);
        spinner_alergi = findViewById(R.id.spinner_alergi);
        spinner_pelayanan = findViewById(R.id.spinner_pelayanan);
        spinner_colletion = new ArrayList<>();
        spinner_colletion.add(spinner_rumahSakit);
        spinner_colletion.add(spinner_jenisKelamin);
        spinner_colletion.add(spinner_goldar);
        spinner_colletion.add(spinner_alergi);
        spinner_colletion.add(spinner_pelayanan);

        rl_kartuBPJS = findViewById(R.id.rl_kartuBPJS);
        iv_previewBPJS = findViewById(R.id.iv_previewKartuBPJS);
        tv_uploadBPJS = findViewById(R.id.tv_uploadBPJS);
        iv_uploadBPJS = findViewById(R.id.iv_uploadBPJS);

        iv_btn_back = findViewById(R.id.iv_btn_back);
        btn_inputForm = findViewById(R.id.btn_inputForm);

        firebaseHandler = new FirebaseHandler(context);
        formHandler = new FormHandler(et_collection,tv_collection,spinner_colletion);
        toastGenerator = new ToastGenerator(context);
        nomorAntrianGenerator = new NomorAntrianGenerator();

        window = getWindow();

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

        tv_tanggalLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = DatePickerSpinner.newInstance("Tanggal Lahir", new DatePickerSpinner.OnPositiveClickListener() {
                    @Override
                    public void onClick(String date) {
                        tv_tanggalLayanan.setText(date);
                    }
                });
                newFragment.show(getSupportFragmentManager(), "dialog");
            }
        });

        ArrayAdapter adapterRumahSakit = ArrayAdapter.createFromResource(this,R.array.daftar_rumah_sakit,R.layout.spinner_selected);
        spinner_rumahSakit.setAdapter(adapterRumahSakit);

        ArrayAdapter adapterJenisKelamin = ArrayAdapter.createFromResource(this,R.array.jenis_kelamin,R.layout.spinner_selected);
        spinner_jenisKelamin.setAdapter(adapterJenisKelamin);

        ArrayAdapter adapterGoldar = ArrayAdapter.createFromResource(this,R.array.golongan_darah,R.layout.spinner_selected);
        spinner_goldar.setAdapter(adapterGoldar);

        ArrayAdapter adapterAlergi = ArrayAdapter.createFromResource(this,R.array.alergi,R.layout.spinner_selected);
        spinner_alergi.setAdapter(adapterAlergi);

        ArrayAdapter adapterPelayanan = ArrayAdapter.createFromResource(this,R.array.daftar_pelayanan,R.layout.spinner_selected);
        spinner_pelayanan.setAdapter(adapterPelayanan);

        rl_kartuBPJS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                ActivityResultLauncher.launch(intent);
            }
        });

        iv_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, null);
                finish();
            }
        });

        btn_inputForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!formHandler.validateForm()){
                    return;
                }

                ArrayList<String> input_user = formHandler.getValue();

                if(iv_previewBPJS.getVisibility()== View.VISIBLE){
                    firebaseHandler.insertKartuBPJS(context, window, bpjsUri, new FirebaseHandler.OnUploadSuccessListener() {
                        @Override
                        public void success(boolean success, String fileName) {
                            if(!success){
                                toastGenerator.generate("Gagal Mengupload Gambar");
                            }
                            else{
                                String noAntrian = nomorAntrianGenerator.generate();
                                input_user.add(noAntrian);
                                input_user.add(fileName);
                                firebaseHandler.insertAntrian(input_user);

                                Intent toAntrian = new Intent(context, AntrianActivity.class);
                                toAntrian.putExtra("no_antrian", noAntrian);
                                startActivity(toAntrian);
                            }
                        }
                    });
                }
                else{
                    String noAntrian = nomorAntrianGenerator.generate();
                    input_user.add(noAntrian);
                    firebaseHandler.insertAntrian(input_user);

                    Intent toAntrian = new Intent(context, AntrianActivity.class);
                    toAntrian.putExtra("no_antrian", noAntrian);
                    startActivity(toAntrian);
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
                        tv_uploadBPJS.setVisibility(View.INVISIBLE);
                        iv_uploadBPJS.setVisibility(View.INVISIBLE);
                        iv_previewBPJS.setVisibility(View.VISIBLE);
                        bpjsUri = data.getData();
                        iv_previewBPJS.setImageURI(bpjsUri);
                    }
                }
            });
}
