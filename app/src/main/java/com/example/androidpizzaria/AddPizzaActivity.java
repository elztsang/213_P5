package com.example.androidpizzaria;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import pizzaria.Size;

public class AddPizzaActivity extends AppCompatActivity{
    //id constants
//    private static final int RBSMALL = R.id.rb_small;
//    private static final int RBMEDIUM = R.id.rb_medium;
//    private static final int RBLARGE = R.id.rb_large;


    Singleton singleton = Singleton.getInstance();
    Button bt_addPizza;
    RecyclerView rv_pizzaOptions;
    RadioButton rb_small, rb_medium, rb_large;
    RadioGroup rg_size;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpizza_view);
        findID();
        initClickListeners();
        initSizeListener();
        //initPizzaOptionListener();
    }

    private void findID() {
        //set all buttons n stuff here
        bt_addPizza = findViewById(R.id.bt_addPizza);
        rv_pizzaOptions = findViewById(R.id.rv_pizzaOptions);
        rg_size = findViewById(R.id.rg_size);
        rb_small = findViewById(R.id.rb_small);
        rb_medium = findViewById(R.id.rb_medium);
        rb_large = findViewById(R.id.rb_large);
    }

    private void initClickListeners() {
        bt_addPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddPizzaClick();
            }
        });
    }

    private void initSizeListener() {
        rg_size.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rb_small.getId()) {
                    singleton.getPizza().setSize(Size.SMALL);
                } else if (checkedId == rb_medium.getId()) {
                    singleton.getPizza().setSize(Size.MEDIUM);
                } else if (checkedId == rb_large.getId()) {
                    singleton.getPizza().setSize(Size.LARGE);
                } else {
                    //print some error somewhere
                    System.out.println("please select a size");
                }
            }
        });
    }

    private void onAddPizzaClick() {
        singleton.getPizzaList().add(singleton.getPizza());
    }
}
