package com.example.androidpizzaria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddPizzaController extends AppCompatActivity{
    Singleton singleton = Singleton.getInstance();
    Button bt_addPizzas, bt_addOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu_view);
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
