package com.example.androidpizzaria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import pizzaria.ChicagoPizza;
import pizzaria.NYPizza;
import pizzaria.PizzaFactory;
import pizzaria.Size;
import pizzaria.Topping;

public class AddPizzaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //id constants
//    private static final int RBSMALL = R.id.rb_small;
//    private static final int RBMEDIUM = R.id.rb_medium;
//    private static final int RBLARGE = R.id.rb_large;

    Singleton singleton = Singleton.getInstance();
    ToppingsAdapter toppingsAdapter;

    Button bt_addPizza, bt_addPizzaBack;
    RecyclerView rv_toppingOptions;
    RadioButton rb_small, rb_medium, rb_large;
    RadioGroup rg_size;
    //    Switch sw_byo;
    Spinner sp_pizzaOptions;
    ToggleButton tb_chicago;
    ToggleButton tb_ny;
    ImageView pizzaView;

    private Size selectedSize;
    private PizzaFactory pizzaStyle;
    private boolean isBYO; //use this to make the recyclerview selectable (isBYO = true)/unselectable (isBYO = false)
     ArrayList<Topping> toppingOptions;
    //might need another array for "selected toppings"; premade pizzas will set this automatically, byo will get to choose

    ArrayAdapter<String> pizzasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpizza_view);
        findID();
        initClickListeners();
        initToggleListener();
        initSizeListener();
        //initPizzaOptionListener(); //listener for non-byo
        initSpinner();
        selectedSize = Size.SMALL;
        sp_pizzaOptions.setOnItemSelectedListener(this);
        //set a default pizza factory for now
        singleton.setPizzaFactory(new ChicagoPizza());
        initToppingOptions();
        //initialize the toppings list, preselected is empty on start
        isBYO = false;
        toppingsAdapter = new ToppingsAdapter(this, toppingOptions, new ArrayList<>(), isBYO);
        rv_toppingOptions.setAdapter(toppingsAdapter);
        rv_toppingOptions.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initToppingOptions() {
        toppingOptions = new ArrayList<>();
        Topping[] itemList = Topping.values();
        toppingOptions.addAll(Arrays.asList(itemList));
    }

    private void findID() {
        //set all buttons n stuff here
        bt_addPizza = findViewById(R.id.bt_addPizza);
        bt_addPizzaBack = findViewById(R.id.bt_addPizzaBack);
        rv_toppingOptions = findViewById(R.id.rv_toppingOptions);
        rg_size = findViewById(R.id.rg_size);
        rb_small = findViewById(R.id.rb_small);
        rb_medium = findViewById(R.id.rb_medium);
        rb_large = findViewById(R.id.rb_large);
        sp_pizzaOptions = findViewById(R.id.sp_pizzaOptions);
        tb_chicago = findViewById(R.id.tb_chicago);
        tb_ny = findViewById(R.id.tb_ny);
        pizzaView = findViewById((R.id.pizzaView));
    }

    private void initClickListeners() {
        bt_addPizza.setOnClickListener(v -> onAddPizzaClick());

        bt_addPizzaBack.setOnClickListener(v -> returnToCreateOrder());
    }

    private void initSpinner() {
        String[] pizzaStyles = {"Deluxe", "BBQ Chicken", "Meatzza", "BYO"}; //TODO: populate with (default) pizzas using pizza factory
        pizzasAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, pizzaStyles);
        sp_pizzaOptions.setAdapter(pizzasAdapter);
    }

    private void initSizeListener() {
        rg_size.setOnCheckedChangeListener((group, checkedId) -> {
            //small note to self - ron
            //we hold the sizes for later when we click add pizza
            if (checkedId == rb_small.getId()) {
                selectedSize = Size.SMALL;
            } else if (checkedId == rb_medium.getId()) {
                selectedSize = Size.MEDIUM;
            } else if (checkedId == rb_large.getId()) {
                selectedSize = Size.LARGE;
            } else {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.select_size_notif),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initToggleListener() {
        tb_chicago.setOnClickListener(v -> {
            if (tb_chicago.isChecked()) {
                tb_ny.setChecked(false);
                singleton.setPizzaFactory(new ChicagoPizza());
            }
        });

        tb_ny.setOnClickListener(v -> {
            if (tb_ny.isChecked()) {
                tb_chicago.setChecked(false);
                singleton.setPizzaFactory(new NYPizza());
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
            Toast.makeText(getApplicationContext(),
                    getString(R.string.add_pizza_success),
                    Toast.LENGTH_SHORT).show();
        } else {
            //display error somewhere else
            Toast.makeText(getApplicationContext(),
                    getString(R.string.add_pizza_error),
                    Toast.LENGTH_SHORT).show();
            //System.out.println("unable to add pizza");
        }
    }

    private void getRVSelection() {
        //todo: get selected pizza style and type from selection in RV, also populate rv
        //todo: figure out what to do for BYO selection + toppings
        //not sure if this method is needed yet, need to look more into adapter capabilities -elz
    }

    /**
     * The event handler implemented for "this" object
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Pizza selectedPizzaType = (Pizza) sp_pizzaOptions.getSelectedItem(); //get the selected item
        String selectedItem = sp_pizzaOptions.getSelectedItem().toString(); //get the selected item

        if(singleton.getPizzaFactory() != null){
            if (selectedSize != null) {
                switch(selectedItem){
                    case("Deluxe"):
                        singleton.setPizza(singleton.getPizzaFactory().createDeluxe());
                        singleton.getPizza().setSize(selectedSize); //temp todo: delete
                        System.out.println(singleton.getPizza());
                        isBYO = false;
                        break;
                    case("BBQ Chicken"):
                        singleton.setPizza(singleton.getPizzaFactory().createBBQChicken());
                        singleton.getPizza().setSize(selectedSize); //temp todo: delete
                        System.out.println(singleton.getPizza());
                        isBYO = false;
                        break;
                    case("Meatzza"):
                        singleton.setPizza(singleton.getPizzaFactory().createMeatzza());
                        singleton.getPizza().setSize(selectedSize); //temp todo: delete
                        System.out.println(singleton.getPizza());
                        isBYO = false;
                        break;
                    case("BYO"):
                        singleton.setPizza(singleton.getPizzaFactory().createBuildYourOwn());
                        singleton.getPizza().setSize(selectedSize); //temp todo: delete
                        isBYO = true; //update toppings list here? TODO: need to figure out adapter implementation with controller
                        break;
                }
//                toppingsAdapter.clearMap();
                toppingsAdapter.setSelectedToppings(singleton.getPizza().getToppings());
                toppingsAdapter.setBYOSelected(isBYO);
                toppingsAdapter.notifyDataSetChanged();
            } else {
                //todo: create a toast or alert dialog
                Toast.makeText(getApplicationContext(),
                        getString(R.string.select_size_notif),
                        Toast.LENGTH_SHORT).show();
            }
        }
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
