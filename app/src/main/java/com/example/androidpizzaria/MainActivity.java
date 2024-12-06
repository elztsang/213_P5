package com.example.androidpizzaria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Singleton singleton = Singleton.getInstance(); //use this to obtain data
    private ImageButton bt_createOrder, bt_manageOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu_view);

        findID();
        initClickListeners();
        //initSingleton();
    }

    /**
     * Find the references of the GUI objects.
     * prefix rb - RadioButton objects
     * prefix cx - CheckBox objects
     * prefix cp - Chip objects
     */
    private void findID() {
        bt_createOrder = findViewById(R.id.bt_createOrder);
        bt_manageOrder = findViewById(R.id.bt_manageOrder);
    }

    private void initClickListeners() {
        bt_createOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateOrderClick();
            }
        });

        bt_manageOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onManageOrderClick();
            }
        });
    }

    public void onCreateOrderClick() {
        Intent intent = new Intent(this, CreateOrderActivity.class);
        startActivity(intent);
    }

    public void onManageOrderClick() {
        Intent intent = new Intent(this, ManageOrderActivity.class);
        startActivity(intent);
    }

}
