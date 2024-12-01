package com.example.androidpizzaria;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.ListView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.databinding.ObservableArrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pizzaria.PizzaFactory;
import pizzaria.Size;
import pizzaria.Topping;

public class AddPizzaActivity extends AppCompatActivity{
    //id constants
//    private static final int RBSMALL = R.id.rb_small;
//    private static final int RBMEDIUM = R.id.rb_medium;
//    private static final int RBLARGE = R.id.rb_large;

    private final List<ToggleButton> toggleButtons = new ArrayList<>();

    Singleton singleton = Singleton.getInstance();
    Button bt_addPizza;
    RecyclerView rv_toppingOptions;
    RadioButton rb_small, rb_medium, rb_large;
    RadioGroup rg_size;
//    Switch sw_byo;
    Spinner sp_pizzaOptions;
    ToggleButton tb_chicago;
    ToggleButton tb_ny;

    private Size selectedSize;
    private PizzaFactory pizzaStyle;
    private boolean isBYO;
    ObservableArrayList<Topping> toppingOptions; //might not need
    //might need another array for "selected toppings"; premade pizzas will set this automatically, byo will get to choose

    //TODO:

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpizza_view);
        findID();
        toggleButtons.add(tb_chicago);
        toggleButtons.add(tb_ny);
        initToppingOptions();   //need a listener for pizza type?
        initClickListeners();
        initToggleListener();
        initSizeListener();
        //initPizzaOptionListener(); //listener for non-byo
//        initSwitchListener();
    }

    private void initToppingOptions(){
        toppingOptions = new ObservableArrayList<>();
        Collections.addAll(toppingOptions, Topping.values());

    }

    private void findID() {
        //set all buttons n stuff here
        bt_addPizza = bt_addPizza.findViewById(R.id.bt_addPizza);
        rv_toppingOptions = rv_toppingOptions.findViewById(R.id.rv_toppingOptions); // need to fix issue here
        rg_size = rg_size.findViewById(R.id.rg_size);
        rb_small = rb_small.findViewById(R.id.rb_small);
        rb_medium = rb_medium.findViewById(R.id.rb_medium);
        rb_large = rb_large.findViewById(R.id.rb_large);
        sp_pizzaOptions = sp_pizzaOptions.findViewById(R.id.sp_pizzaOptions);
        tb_chicago = tb_chicago.findViewById(R.id.tb_chicago);
        tb_ny = tb_chicago.findViewById(R.id.tb_ny);
//        sw_byo = findViewById(R.id.sw_byo);
    }

    private void initClickListeners() {
        bt_addPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddPizzaClick();
            }
        });
    }

//    private void initSwitchListener() {
//        sw_byo.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                //change flag to check pizza type
//                isBYO = true;
//            } else {
//                isBYO = false;
//            }
//        });
//    }
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
                    //TODO: make this a popup instead (forgot the name of it)
                    System.out.println("please select a size");
                }
            }
        });
    }

    private void initToggleListener() {
        tb_chicago.setOnClickListener(v -> {
            if (tb_chicago.isChecked()) {
                tb_ny.setChecked(false);
            }
        });

        tb_ny.setOnClickListener(v -> {
            if (tb_ny.isChecked()) {
                tb_chicago.setChecked(false);
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
    private void populateRecyclerView() { // i think we will use this for toppings

    }
}
