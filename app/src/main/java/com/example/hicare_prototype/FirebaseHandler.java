package com.example.hicare_prototype;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class FirebaseHandler extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private FirebaseStorage mStorage;
    private Context context;

    private ToastGenerator toastGenerator;

    public FirebaseHandler(Context context){
        this.context=context;
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance();
        toastGenerator = new ToastGenerator(context);
    }

    public void signIn(ArrayList<String> value){
        Log.d(TAG, "Melakukan Sign In");
        String email = value.get(0);
        String password = value.get(1);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "Berhasil Sign In? : " + task.isSuccessful());
                if (!task.isSuccessful()) {
                    String exception = task.getException().toString();
                    if(exception.contains("badly formatted")){
                        toastGenerator.generate("Email yang dimasukkan tidak tepat");
                    }
                    else if(exception.contains("no user record corresponding")){
                        toastGenerator.generate("Email Belum Terdaftar");
                    }
                    else if(exception.contains("password is invalid")){
                        toastGenerator.generate("Password Salah");
                    }
                }
                updateUi(BerandaActivity.class);
            }
        });
    }

    public void signUp(ArrayList<String> value) {
        Log.d(TAG, "Melakukan SignUp");
        String username = value.get(0);
        String email = value.get(1);
        String password = value.get(2);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "Berhasil SignUp? : " + task.isSuccessful());
                if (!task.isSuccessful()) {
                    String exception = task.getException().toString();
                    if (exception.contains("already in use")) {
                        toastGenerator.generate("Email sudah terdaftar");
                    } else if (exception.contains("password is invalid")) {
                        toastGenerator.generate("Password Minimal Terdiri dari 6 Karakter");
                    }
                    else if(exception.contains("badly formatted")){
                        toastGenerator.generate("Email yang dimasukkan tidak tepat");
                    }
                }
                else{
                    insertUser(username, email);
                    updateUi(BerandaActivity.class);
                }
            }
        });
    }

    public void logout(Context context){
        mAuth.signOut();
        Intent intent = new Intent(context, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void insertUser (String username, String email){
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference dReference = mDatabase.getReference();
//        Log.d(TAG, "User baru model : " + new UserModel(username, email));
        dReference.child("user").child(uid).setValue(new UserModel(username,email));
    }

    public void insertUser (ArrayList<String> input_user){
//        Log.d(TAG, "Coba masukin data profil baru Input: "+ input_user.toString());
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference dReference = mDatabase.getReference();
        String nama = input_user.get(0);
        String nomorTelepon = input_user.get(1);
        String email = input_user.get(2);
        String tanggalLahir = input_user.get(3);
        String jenisKelamin = input_user.get(4);
        String profil;
        if (input_user.size()>5){
            profil = input_user.get(5);
        }
        else {
            profil = "";
        }

        dReference.child("user").child(uid).setValue(new UserModel(nama, tanggalLahir, jenisKelamin, nomorTelepon, email, profil));
    }

    public void insertAntrian (ArrayList<String> input_user){
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference dReference = mDatabase.getReference();
        String namaAnak = input_user.get(0);
        String beratBadan = input_user.get(1);
        String tinggiBadan= input_user.get(2);
        String lingkarKepala = input_user.get(3);
        String tanggalLahir = input_user.get(4);
        String tanggalLayanan = input_user.get(5);
        String rumahSakit = input_user.get(6);
        String jenisKelamin = input_user.get(7);
        String goldar = input_user.get(8);
        String alergi = input_user.get(9);
        String pelayanan = input_user.get(10);
        String noAntrian = input_user.get(11);
        String kartuBPJS;
        if (input_user.size()>12){
            kartuBPJS = input_user.get(12);
        }
        else {
            kartuBPJS = "";
        }

        dReference.child("antrian").child(uid).child(noAntrian).setValue(new AntrianModel(rumahSakit, namaAnak, tanggalLahir, jenisKelamin, beratBadan, tinggiBadan, lingkarKepala, goldar, alergi, tanggalLayanan, pelayanan, kartuBPJS, noAntrian));
    }

    public void insertKartuBPJS(Context context, Window window, Uri bpjsUri, OnUploadSuccessListener listener){
//        Log.d(TAG, "Mencoba masukin foto profil Uri:" +bpjsUri);
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss", Locale.UK);
        Date tanggal = new Date();
        String fileName = formatter.format(tanggal);

        String uid = mAuth.getCurrentUser().getUid();
        StorageReference sReference = mStorage.getReference(uid+"/kartuBPJS/"+fileName);

        setDialog(context,true);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        sReference.putFile(bpjsUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                setDialog(context, false);
                Log.d(TAG, "Upload Berhasil Uri:" +bpjsUri);
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                listener.success(true, fileName);
                Log.d(TAG, "Berhasil Upload Gambar");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                setDialog(context, false);
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                listener.success(false, fileName);
                Log.d(TAG, "Gagal Upload Gambar");
            }
        });
    }

    public void insertFotoProfil(Context context, Window window, Uri profilUri, OnUploadSuccessListener listener){
//        Log.d(TAG, "Mencoba masukin foto profil Uri:" +profilUri);
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss", Locale.UK);
        Date tanggal = new Date();
        String fileName = formatter.format(tanggal);

        String uid = mAuth.getCurrentUser().getUid();
        StorageReference sReference = mStorage.getReference(uid+"/profil/"+fileName);

        setDialog(context,true);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        sReference.putFile(profilUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Log.d(TAG, "Upload Berhasil Uri:" +profilUri);
                setDialog(context, false);
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                listener.success(true, fileName);
                Log.d(TAG, "Berhasil Upload Gambar");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                setDialog(context, false);
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                listener.success(false, fileName);
                Log.d(TAG, "Gagal Upload Gambar");
            }
        });
    }

    private void setDialog(Context context, boolean show) {
        Dialog dialogUpload;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.dialog_progress_bar);
        dialogUpload = builder.create();

        if (show) dialogUpload.show();
        else dialogUpload.dismiss();
    }

    public ArrayList<AntrianModel> getAllAntrian(){
        ArrayList<AntrianModel> daftar_riwayat = new ArrayList<>();
        DatabaseReference dReference = mDatabase.getReference();
        String uid = mAuth.getCurrentUser().getUid();

        dReference.child("antrian").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tanggalLayanan, rumahSakit, beratBadan, tinggiBadan, lingkarKepala;

                for(DataSnapshot ds : snapshot.getChildren()){
                    tanggalLayanan = ds.child("tanggalLayanan").getValue(String.class);
                    rumahSakit = ds.child("rumahSakit").getValue(String.class);
                    beratBadan = ds.child("beratBadan").getValue(String.class);
                    tinggiBadan = ds.child("tinggiBadan").getValue(String.class);
                    lingkarKepala = ds.child("lingkarKepala").getValue(String.class);

                    daftar_riwayat.add(new AntrianModel(tanggalLayanan, rumahSakit, beratBadan, tinggiBadan, lingkarKepala));
                }

                Log.d(TAG, daftar_riwayat.toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.toString());
            }
        });
        return daftar_riwayat;
    }

    public void getUsername(GetUsernameListener listener){
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference dReference = mDatabase.getReference();
        dReference.child("user").child(uid).child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = snapshot.getValue(String.class);
                listener.getUsername(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG,error.toString());
            }
        });
    }

    public void getDataProfil(GetDataProfilListener listener){
//        Log.d(TAG,"Mencari Profil Di database");
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference dReference = mDatabase.getReference();
        ArrayList<String> dataProfil = new ArrayList<>();

        dReference.child("user").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds : snapshot.getChildren()){
//                    dataProfil.add(ds.getValue(String.class));
//                }
                Log.d(TAG,snapshot.toString());
                String username;
                String nomorTelepon;
                String usia;
                String jenisKelamin;
                String email;
                String tanggalLahir;

                username = snapshot.child("username").getValue(String.class);
                nomorTelepon = snapshot.child("nomorTelepon").getValue(String.class);
                usia = snapshot.child("usia").getValue(String.class);
                jenisKelamin = snapshot.child("jenisKelamin").getValue(String.class);
                email = snapshot.child("email").getValue(String.class);
                tanggalLahir = snapshot.child("tanggalLahir").getValue(String.class);

                dataProfil.add(username);
                dataProfil.add(nomorTelepon);
                dataProfil.add(usia);
                dataProfil.add(jenisKelamin);
                dataProfil.add(email);
                dataProfil.add(tanggalLahir);

                listener.getDataProfil(dataProfil);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.toString());
            }
        });
    }

    public void getProfilFileName(GetProfilFileNameListener listener){
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference dReference = mDatabase.getReference();

        dReference.child("user").child(uid).child("profil").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fileName = snapshot.getValue(String.class);
                try {
                    listener.getFileName(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG,error.toString());
            }
        });
    }

    public void getProfilePicture(GetProfilListener listener){
        String uid = mAuth.getCurrentUser().getUid();

        getProfilFileName(new GetProfilFileNameListener() {
            @Override
            public void getFileName(String fileName) throws IOException {
//                Log.d(TAG, "nama file di database "+fileName);
                if (!Objects.equals(fileName, "")){
                    StorageReference sReference = mStorage.getReference(uid+"/profil/"+fileName);

                    File localFile = File.createTempFile("profil",".jpg");
                    sReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            listener.getProfil(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG,e.toString());
                        }
                    });
                }
                else{
                    listener.getProfil(null);
                }
            }
        });
    }

    interface GetUsernameListener{
        void getUsername(String username);
    }

    interface GetDataProfilListener{
        void getDataProfil(ArrayList<String> dataprofil);
    }

    interface GetProfilFileNameListener{
        void getFileName(String fileName) throws IOException;
    }

    interface GetProfilListener{
        void getProfil(Bitmap bitmap);
    }

    interface OnUploadSuccessListener{
        void success (boolean success, String fileName);
    }

    public void updateUi(Class destination){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null) {
            Intent update = new Intent(context, destination);
            context.startActivity(update);
            Log.d(TAG, "user aktif : " + user.getEmail());
        }
    }

}
