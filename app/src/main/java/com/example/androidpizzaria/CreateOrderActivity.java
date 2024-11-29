package com.example.androidpizzaria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CreateOrderActivity extends AppCompatActivity{
    Singleton singleton = Singleton.getInstance();
    private Button bt_addPizzas, bt_addOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createorder_view);
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

    //maybe rename this button and method to be a bit less confusing
    //this one is to navigate to the adding pizza menu
    private void onAddPizzasClick() {
        Intent intent = new Intent(this, AddPizzaActivity.class);
        startActivity(intent);
    }

    private void onAddOrderClick() {
        //add order
        if (singleton.getPizza() != null) {
            //todo: set order pizzalist(?)
            singleton.getOrderList().add(singleton.getOrder());
            //todo: temp debugging print - replace with a toast or smth
            System.out.println("added order: singleton.getOrder()");
        } else {
            //display error somewhere else
            System.out.println("unable to add order");
        }

    }

}
