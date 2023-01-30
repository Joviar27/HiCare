package com.example.hicare_prototype;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class FormHandler {

    ArrayList<EditText> et_collection;
    ArrayList<TextView> tv_collection;
    ArrayList<Spinner> spinner_collection;

    public FormHandler(ArrayList<EditText> et_collection){
        this.et_collection = et_collection;
        tv_collection = new ArrayList<>();
        spinner_collection = new ArrayList<>();
    }

    public FormHandler(ArrayList<EditText> et_collection, ArrayList<TextView> tv_colletion, ArrayList<Spinner> spinner_collection){
        this.et_collection = et_collection;
        this.tv_collection = tv_colletion;
        this.spinner_collection = spinner_collection;
    }

    public boolean validateForm() {
        ArrayList<Boolean> result = new ArrayList<>();
        et_collection.forEach((input) -> {
            if (TextUtils.isEmpty(input.getText().toString())) {
                input.setError("Harus diisi");
                result.add(false);
            }
        });
        if(result.size()>0){
            return false;
        }
        else return true;
    }

//    interface ValidateFormListener{
//        void validity(boolean valid);
//    }

    public ArrayList<String> getValue(){
        ArrayList<String> value_collection = new ArrayList<>();
        if (et_collection.size()!=0) {
            et_collection.forEach((input) -> {
                String value = input.getText().toString();
                value_collection.add(value);
            });
        }
        if(tv_collection.size()!=0){
            tv_collection.forEach((input) -> {
                String value = input.getText().toString();
                value_collection.add(value);
            });
        }
        if(spinner_collection.size()!=0){
            spinner_collection.forEach((input) -> {
                String value = input.getSelectedItem().toString();
                value_collection.add(value);
            });
        }
        return value_collection;
    }
}
