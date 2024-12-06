package com.example.androidpizzaria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import pizzaria.ChicagoPizza;
import pizzaria.NYPizza;
import pizzaria.Size;
import pizzaria.Topping;

/**
 *  Activity class that handles creating a single pizza to add to the current order being placed.
 * @author Ron Chrysler Amistad, Elizabeth Tsang
 */
public class AddPizzaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ToppingsAdapter.Listener {
    private Size selectedSize;
    private boolean isBYO; //use this to make the recyclerview selectable (isBYO = true)/unselectable (isBYO = false)
    Singleton singleton = Singleton.getInstance();
    ToppingsAdapter toppingsAdapter;
    Button bt_addPizza, bt_addPizzaBack;
    RecyclerView rv_toppingOptions;
    RadioButton rb_small, rb_medium, rb_large;
    RadioGroup rg_size;
    TextView t_dynamicSubtotal;
    Spinner sp_pizzaOptions;
    ToggleButton tb_chicago;
    ToggleButton tb_ny;
    ImageView pizzaView;
    ArrayList<Topping> toppingOptions;
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
        toppingsAdapter = new ToppingsAdapter(this, toppingOptions, new ArrayList<>(), isBYO, this);
        rv_toppingOptions.setAdapter(toppingsAdapter);
        rv_toppingOptions.setLayoutManager(new LinearLayoutManager(this));
        updatePizzaImage();
    }

    /**
     * Helper method to set default values the pizza on start.
     */
    private void setDefaults() {
        selectedSize = Size.SMALL;
        singleton.setPizzaFactory(new ChicagoPizza());
        tb_chicago.setChecked(true);
        isBYO = false;
    }

    /**
     * Helper method to initialize the list of toppings.
     */
    private void initToppingOptions() {
        toppingOptions = new ArrayList<>();
        Topping[] itemList = Topping.values();
        toppingOptions.addAll(Arrays.asList(itemList));
    }

    /**
     * Helper method to initialize all the GUI elements with their respective IDs.
     */
    private void findID() {
        bt_addPizza = findViewById(R.id.bt_addPizza);
        bt_addPizzaBack = findViewById(R.id.bt_addPizzaBack);
        rv_toppingOptions = findViewById(R.id.rv_toppingOptions);
        rg_size = findViewById(R.id.rg_size);
        rb_small = findViewById(R.id.rb_small);
        rb_medium = findViewById(R.id.rb_medium);
        rb_large = findViewById(R.id.rb_large);
        sp_pizzaOptions = findViewById(R.id.sp_pizzaOptions);
        t_dynamicSubtotal = findViewById(R.id.t_dynamicSubtotal);
        tb_chicago = findViewById(R.id.tb_chicago);
        tb_ny = findViewById(R.id.tb_ny);
        pizzaView = findViewById((R.id.pizzaView));
    }

    /**
     * Helper method to initialize the listeners for the button to return to the order screen and button to add the pizza to the order.
     */
    private void initClickListeners() {
        bt_addPizza.setOnClickListener(v -> onAddPizzaClick());
        bt_addPizzaBack.setOnClickListener(v -> returnToCreateOrder());
    }

    /**
     * Helper method to initialize the spinner object for displaying the selection of pizza types.
     */
    private void initSpinner() {
        String[] pizzaStyles = {"Deluxe", "BBQ Chicken", "Meatzza", "BYO"}; //TODO: populate with (default) pizzas using pizza factory
        pizzasAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, pizzaStyles);
        sp_pizzaOptions.setAdapter(pizzasAdapter);
    }

    /**
     * Helper method to initialize the listener for the RadioGroup of pizza sizes.
     */
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
            buildPizza();
            updatePizzaImage();
        });
    }

    /**
     * Helper method to initialize the listeners for the toggle buttons Chicago Style and Ny Style.
     * When one toggle button is checked, the other will be automatically unchecked.
     */
    private void initToggleListener() {
        tb_chicago.setOnClickListener(v -> {
            if (tb_chicago.isChecked()) {
                tb_ny.setChecked(false);
                singleton.setPizzaFactory(new ChicagoPizza());
                buildPizza();
                updatePizzaImage();
            }
        });

        tb_ny.setOnClickListener(v -> {
            if (tb_ny.isChecked()) {
                tb_chicago.setChecked(false);
                singleton.setPizzaFactory(new NYPizza());
                buildPizza();
                updatePizzaImage();
            }
        });
    }

    /**
     *  Helper method to add the current pizza to the list of pizzas in the singleton class.
     */
    private void onAddPizzaClick() {
        if (singleton.getPizza() != null) {
            singleton.getOrder().getPizzas().add(singleton.getPizza());
            //selectedToppings = new ArrayList<>();
            Toast.makeText(getApplicationContext(),
                    getString(R.string.add_pizza_success),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.add_pizza_error),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Helper method to set the pizza in the Singleton class to a pizza according to the options selected by the user.
     */
    private void buildPizza() {
        String selectedItem = sp_pizzaOptions.getSelectedItem().toString();

        switch (selectedItem) {
            case ("Deluxe"):
                singleton.setPizza(singleton.getPizzaFactory().createDeluxe());
                isBYO = false;
                break;
            case ("BBQ Chicken"):
                singleton.setPizza(singleton.getPizzaFactory().createBBQChicken());
                isBYO = false;
                break;
            case ("Meatzza"):
                singleton.setPizza(singleton.getPizzaFactory().createMeatzza());
                isBYO = false;
                break;
            case ("BYO"):
                singleton.setPizza(singleton.getPizzaFactory().createBuildYourOwn());
                isBYO = true;
                break;
        }
        singleton.getPizza().setSize(selectedSize);
        toppingsAdapter.setSelectedToppings(singleton.getPizza().getToppings());
        toppingsAdapter.setBYOSelected(isBYO);
        toppingsAdapter.notifyDataSetChanged();

        updateSubtotal();
    }

    /**
     * Helper method to update the image of the pizza according to the options selected by the user.
     */
    private void updatePizzaImage() {
        String selectedItem = sp_pizzaOptions.getSelectedItem().toString();
        switch (selectedItem) {
            case ("Deluxe"):
                if (tb_chicago.isChecked()) {
                    pizzaView.setImageResource(R.drawable.chicagodeluxe);
                } else {
                    pizzaView.setImageResource(R.drawable.nydeluxe);
                }
                break;
            case ("BBQ Chicken"):
                if (tb_chicago.isChecked()) {
                    pizzaView.setImageResource(R.drawable.chicagobbqchicken);
                } else {
                    pizzaView.setImageResource(R.drawable.nybbqchicken);
                }
                break;
            case ("Meatzza"):
                if (tb_chicago.isChecked()) {
                    pizzaView.setImageResource(R.drawable.chicagomeatzza);
                } else {
                    pizzaView.setImageResource(R.drawable.nymeatzza);
                }
                break;
            case ("BYO"):
                if (tb_chicago.isChecked()) {
                    pizzaView.setImageResource(R.drawable.chicagobyo);
                } else {
                    pizzaView.setImageResource(R.drawable.nybyo);
                }
                break;
        }
    }

    /**
     * Helper method to call the create order screen.
     */
    private void returnToCreateOrder() {
        Intent intent = new Intent(this, CreateOrderActivity.class);
        startActivity(intent);
    }

    /**
     * Helper method to update the subtotal price of the current pizza.
     */
    private void updateSubtotal() {
        //todo: ensure that it's formatted correctly
        if (singleton.getPizza() != null) {
            DecimalFormat moneyFormat = new DecimalFormat("###,##0.00");
            String dynamicSubtotal = "$" + singleton.getPizza().price();
            t_dynamicSubtotal.setText(String.format("$%s",
                    moneyFormat.format(singleton.getPizza().price())));
            ;
        }
    }

    /**
     * The event handler implemented for "this" object
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (singleton.getPizzaFactory() != null) {
            buildPizza();
            updatePizzaImage();
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

    @Override
    public void onRVClick(String vas) {
        updateSubtotal();
    }
}
