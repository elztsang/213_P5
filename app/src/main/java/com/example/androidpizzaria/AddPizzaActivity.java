package com.example.androidpizzaria;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AddPizzaActivity extends AppCompatActivity{
    Singleton singleton = Singleton.getInstance();
    Button bt_addPizzas, bt_addOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpizza_view);
        findID();
        initClickListeners();
    }

    private void findID() {
        //set all buttons n stuff here
    }

    private void initClickListeners() {
        //set click listeners here n stuff
    }
}
