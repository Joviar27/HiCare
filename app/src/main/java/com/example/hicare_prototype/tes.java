//package com.example.hicare_prototype;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.AppCompatButton;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.fragment.app.DialogFragment;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//public class tes extends AppCompatActivity {
//
//    TextView tanggalLayanan, tvUpload;
//    RelativeLayout uploadBPJS;
//    ImageView previewKartu, iconUpload;
//    StorageReference storageReference;
//    DatabaseReference databaseReference;
//    AppCompatButton btnSubmit;
//    ConstraintLayout form;
//    Uri imageUri;
//    Dialog dialog;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.form_data_anak);
//
//        form = findViewById(R.id.layoutForm);
//
//        tanggalLayanan = findViewById(R.id.inputTanggalLayanan);
//        final Spinner spinnerRS = findViewById(R.id.inputRumahSakit);
//        ArrayAdapter adapterRS = ArrayAdapter.createFromResource(this,R.array.daftar_rumah_sakit,R.layout.spinner_selected);
//        spinnerRS.setAdapter(adapterRS);
//
//        tanggalLayanan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogFragment newFragment = DatePickerSpinner.newInstance("Tanggal Lahir", new DatePickerSpinner.OnPositiveClickListener() {
//                    @Override
//                    public void onClick(String date) {
//                        tanggalLayanan.setText(date);
//                    }
//                });
//                newFragment.show(getSupportFragmentManager(), "dialog");
//            }
//        });
//
//        uploadBPJS = findViewById(R.id.inputKartuBPJS);
//        previewKartu = findViewById(R.id.previewGambar);
//        tvUpload = findViewById(R.id.tv_uploadBPJS);
//        iconUpload = findViewById(R.id.icon_uploadBPJS);
//        btnSubmit = findViewById(R.id.btnInputForm);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setView(R.layout.dialog_progress_bar);
//        dialog = builder.create();
//
//        uploadBPJS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                ActivityResultLauncher.launch(intent);
//            }
//        });
//
//
////        btnSubmit.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////            }
////        });
//
//    }
//
//    ActivityResultLauncher<Intent> ActivityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        tvUpload.setVisibility(View.INVISIBLE);
//                        iconUpload.setVisibility(View.INVISIBLE);
//                        previewKartu.setVisibility(View.VISIBLE);
//                        imageUri = data.getData();
//
//                        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss", Locale.UK);
//                        Date tanggal = new Date();
//                        String filename = formatter.format(tanggal);
//                        storageReference = FirebaseStorage.getInstance().getReference("image/"+filename);
//                        databaseReference = FirebaseDatabase.getInstance().getReference();
//                        setDialog(true);
//                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//                        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                setDialog(false);
//                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//                                databaseReference.child("gambar").setValue("image/"+filename);
//                                previewKartu.setImageURI(imageUri);
//                                Toast.makeText(tes.this, "Berhasil Upload", Toast.LENGTH_SHORT).show();
//
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                setDialog(false);
//                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//                                Toast.makeText(tes.this, "Gagal Upload", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                    }
//                }
//            });
//
//    private void setDialog(boolean show) {
//        if (show) dialog.show();
//        else dialog.dismiss();
//    }
//}
//
