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

public class CreateOrderController extends AppCompatActivity{
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
        bt_addPizzas = findViewById(R.id.bt_addPizzas);
        bt_addOrder = findViewById(R.id.bt_addOrder);
    }

    private void initClickListeners() {
        bt_addPizzas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddPizzasClick();
            }
        });

        bt_addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddOrderClick();
            }
        });
    }

    private void onAddPizzasClick() {

    }

    private void onAddOrderClick() {

    }

}
