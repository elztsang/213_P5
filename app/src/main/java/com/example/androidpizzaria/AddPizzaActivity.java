package com.example.androidpizzaria;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import pizzaria.PizzaFactory;
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
    Switch sw_byo;

    private Size selectedSize;
    private PizzaFactory pizzaStyle;
    private boolean isBYO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpizza_view);
        findID();
        initClickListeners();
        initSizeListener();
        //initPizzaOptionListener(); //listener for non-byo
        initSwitchListener();
    }

    private void findID() {
        //set all buttons n stuff here
        bt_addPizza = findViewById(R.id.bt_addPizza);
        rv_pizzaOptions = findViewById(R.id.rv_pizzaOptions);
        rg_size = findViewById(R.id.rg_size);
        rb_small = findViewById(R.id.rb_small);
        rb_medium = findViewById(R.id.rb_medium);
        rb_large = findViewById(R.id.rb_large);
        sw_byo = findViewById(R.id.sw_byo);
    }

    private void initClickListeners() {
        bt_addPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddPizzaClick();
            }
        });
    }

    private void initSwitchListener() {
        sw_byo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //change flag to check pizza type
                isBYO = true;
            } else {
                isBYO = false;
            }
        });
    }
    private void initSizeListener() {
        rg_size.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //small note to self - ron
                //we hold the sizes for later when we click add pizza
                if (checkedId == rb_small.getId()) {
                    selectedSize = Size.SMALL;
                } else if (checkedId == rb_medium.getId()) {
                    selectedSize = Size.MEDIUM;
                } else if (checkedId == rb_large.getId()) {
                    selectedSize = Size.LARGE;
                } else {
                    //print some error somewhere
                    System.out.println("please select a size");
                }
            }
        });
    }

    private void onAddPizzaClick() {
        //todo: check P4 for all the validity checks
        if (singleton.getPizza() != null) {
            //set size, pizzastyle, type
            //getRVSelection();
            singleton.getPizza().setSize(selectedSize);
            singleton.getPizzaList().add(singleton.getPizza());
        } else {
            //display error somewhere else
            System.out.println("unable to add pizza");
        }
    }

    private void getRVSelection() {
        //todo: get selected pizza style and type from selection in RV, also populate rv
        //todo: figure out what to do for BYO selection + toppings
    }

    //todo: method to populate recycler view
    private void populateRecyclerView() {

    }
}
