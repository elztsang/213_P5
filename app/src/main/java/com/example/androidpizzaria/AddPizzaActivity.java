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
    private ArrayList<Topping> selectedToppings;

    ArrayAdapter<String> pizzasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpizza_view);
        findID();
        initClickListeners();
        initToggleListener();
        initSizeListener();
        initSpinner();
        initToppingOptions();
        setDefaults();

        sp_pizzaOptions.setOnItemSelectedListener(this);
        toppingsAdapter = new ToppingsAdapter(this, toppingOptions, new ArrayList<>(), isBYO);
        rv_toppingOptions.setAdapter(toppingsAdapter);
        rv_toppingOptions.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Helper method to set default values for adding pizza
     */
    private void setDefaults() {
        selectedSize = Size.SMALL;
        singleton.setPizzaFactory(new ChicagoPizza());
        tb_chicago.setChecked(true);
        isBYO = false;
    }
    private void initToppingOptions() {
        toppingOptions = new ArrayList<>();
        Topping[] itemList = Topping.values();
        toppingOptions.addAll(Arrays.asList(itemList));
    }

    private void findID() {
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
            //scuffed solution
            String selectedItem = sp_pizzaOptions.getSelectedItem().toString();
            buildPizza(selectedItem);
            singleton.getPizza().setToppings(selectedToppings);
            singleton.getOrder().getPizzas().add(singleton.getPizza());
            //clear selected toppings
            selectedToppings = new ArrayList<>();
            Toast.makeText(getApplicationContext(),
                    getString(R.string.add_pizza_success),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.add_pizza_error),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void buildPizza(String pizzaType) {
        switch(pizzaType){
            case("Deluxe"):
                singleton.setPizza(singleton.getPizzaFactory().createDeluxe());
                isBYO = false;
                break;
            case("BBQ Chicken"):
                singleton.setPizza(singleton.getPizzaFactory().createBBQChicken());
                isBYO = false;
                break;
            case("Meatzza"):
                singleton.setPizza(singleton.getPizzaFactory().createMeatzza());
                isBYO = false;
                break;
            case("BYO"):
                singleton.setPizza(singleton.getPizzaFactory().createBuildYourOwn());
                isBYO = true;
                break;
        }
        singleton.getPizza().setSize(selectedSize);
        toppingsAdapter.setSelectedToppings(singleton.getPizza().getToppings());
        toppingsAdapter.setBYOSelected(isBYO);
        toppingsAdapter.notifyDataSetChanged();
    }

    private void returnToCreateOrder() {
        Intent intent = new Intent(this, CreateOrderActivity.class);
        startActivity(intent);
    }

    /**
     * The event handler implemented for "this" object
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //get the selected item
        String selectedItem = sp_pizzaOptions.getSelectedItem().toString();

        if(singleton.getPizzaFactory() != null){
            buildPizza(selectedItem);
            selectedToppings = singleton.getPizza().getToppings();
        } else {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.select_size_notif),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //it's fine to leave it blank
    }
}
