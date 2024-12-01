package com.example.androidpizzaria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.databinding.ObservableArrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pizzaria.Pizza;
import pizzaria.PizzaFactory;
import pizzaria.Size;
import pizzaria.Topping;

public class AddPizzaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    //id constants
//    private static final int RBSMALL = R.id.rb_small;
//    private static final int RBMEDIUM = R.id.rb_medium;
//    private static final int RBLARGE = R.id.rb_large;

    Singleton singleton = Singleton.getInstance();
    Button bt_addPizza, bt_addPizzaBack;
    RecyclerView rv_toppingOptions;
    RadioButton rb_small, rb_medium, rb_large;
    RadioGroup rg_size;
    //    Switch sw_byo;
    Spinner sp_pizzaOptions;
    ToggleButton tb_chicago;
    ToggleButton tb_ny;

    private Size selectedSize;
    private PizzaFactory pizzaStyle;
    private boolean isBYO; //use this to make the recyclerview selectable (isBYO = true)/unselectable (isBYO = false)
    ObservableArrayList<Topping> toppingOptions; //might not need
    //might need another array for "selected toppings"; premade pizzas will set this automatically, byo will get to choose

    //TODO: set up lists of "premade" toppings to assign to recycler view
    //also need to make sure recycler view is not selectable when byo is selected
    ArrayAdapter<Pizza> chicagoPizzasAdapter;
    ArrayAdapter<Pizza> nyPizzasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpizza_view);
        findID();
        initToppingOptions();
        initClickListeners();
        initToggleListener();
        initSizeListener();
        //initPizzaOptionListener(); //listener for non-byo
        initSpinner();
        // TODO: add listener for pizza style (spinner dropdown menu)
    }

    private void initToppingOptions() {
        toppingOptions = new ObservableArrayList<>();
        Collections.addAll(toppingOptions, Topping.values());
    }

    private void findID() {
        //set all buttons n stuff here
        bt_addPizza = findViewById(R.id.bt_addPizza);
        bt_addPizzaBack = findViewById(R.id.bt_addPizzaBack);
        rv_toppingOptions = rv_toppingOptions.findViewById(R.id.rv_toppingOptions);
        rg_size = rg_size.findViewById(R.id.rg_size);
        rb_small = rb_small.findViewById(R.id.rb_small);
        rb_medium = rb_medium.findViewById(R.id.rb_medium);
        rb_large = rb_large.findViewById(R.id.rb_large);
        sp_pizzaOptions = sp_pizzaOptions.findViewById(R.id.sp_pizzaOptions);
        tb_chicago = tb_chicago.findViewById(R.id.tb_chicago);
        tb_ny = tb_chicago.findViewById(R.id.tb_ny);
    }

    private void initClickListeners() {
        bt_addPizza.setOnClickListener(v -> onAddPizzaClick());

        bt_addPizzaBack.setOnClickListener(v -> returnToCreateOrder());
    }

    private void initSpinner(){
        Pizza[] chicagoPizzas = {}; //TODO: populate with (default) pizzas using pizza factory
        Pizza[] nyPizzas = {};

        chicagoPizzasAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, chicagoPizzas);
        nyPizzasAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, nyPizzas);

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
                    //TODO: make this a popup instead (forgot the name of it)
                    Toast.makeText(getApplicationContext(), "Please select a size", Toast.LENGTH_SHORT).show();
                    //System.out.println("please select a size");
                }
            }
        });
    }

    private void initToggleListener() {
        tb_chicago.setOnClickListener(v -> {
            if (tb_chicago.isChecked()) {
                tb_ny.setChecked(false);
                //TODO: populate spinner here
                /*
                Deluxe Deep Dish, BBQ Pan, Meatzza Stuffed, BYO Pan
                 */
                sp_pizzaOptions.setAdapter(chicagoPizzasAdapter); //not sure if this will work atm
            }
        });

        tb_ny.setOnClickListener(v -> {
            if (tb_ny.isChecked()) {
                tb_chicago.setChecked(false);
                //TODO: populate spinner here
                /*
                Deluxe Brooklyn, BBQ Thin, Meatzza Hand-tossed, BYO Hand-tossed
                 */
                sp_pizzaOptions.setAdapter(nyPizzasAdapter);
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
            Toast.makeText(getApplicationContext(), "Unable to add pizza!", Toast.LENGTH_SHORT).show();
            //System.out.println("unable to add pizza");
        }
    }

    private void getRVSelection() {
        //todo: get selected pizza style and type from selection in RV, also populate rv
        //todo: figure out what to do for BYO selection + toppings
        //not sure if this method is needed yet, need to look more into adapter capabilities -elz
    }

    @Override
    /**
     * The event handler implemented for "this" object
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Pizza selectedPizzaType = (Pizza) sp_pizzaOptions.getSelectedItem(); //get the selected item
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //it's fine to leave it blank
    }

    private void returnToCreateOrder() {
        Intent intent = new Intent(this, CreateOrderActivity.class);
        startActivity(intent);
    }
}
