package com.example.androidpizzaria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import pizzaria.ChicagoPizza;
import pizzaria.Order;
import pizzaria.Pizza;
import pizzaria.PizzaFactory;
import pizzaria.Size;

public class CreateOrderActivity extends AppCompatActivity{
    Singleton singleton = Singleton.getInstance();
    private Button bt_addPizzas, bt_addOrder, bt_createBackButton;
    private ListView lv_curOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createorder_view);
        findID();
        initClickListeners();
        //initTempOrder(); // for testing - todo: delete later
        updateCurrentOrder();

        //disable button if order is empty
        if (singleton.getOrder().getPizzas().isEmpty()) {
            bt_addOrder.setEnabled(false);
        } else {
            bt_addOrder.setEnabled(true);
        }
    }

    private void findID() {
        bt_addPizzas = findViewById(R.id.bt_addPizzas);
        bt_addOrder = findViewById(R.id.bt_addOrder);
        bt_createBackButton = findViewById(R.id.bt_createBackButton);
        lv_curOrder = findViewById(R.id.lv_curOrder);
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
                updateListView();
            }
        });

        bt_createBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMainMenu();
            }
        });

        lv_curOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pizza selectedPizza = (Pizza) parent.getItemAtPosition(position);
                singleton.getOrder().getPizzas().remove(selectedPizza);

                updateListView();
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
        if (singleton.getOrder() != null) {
            //todo: set order pizzalist(?)
            singleton.getOrderList().add(singleton.getOrder());
            //todo: temp debugging print - replace with a toast or smth
            System.out.println("added order: " + singleton.getOrder());
            singleton.setOrder(new Order());
        } else {
            //display error somewhere else
            System.out.println("unable to add order");
        }

    }

    private void updateCurrentOrder(){
        ArrayAdapter<Pizza> dataAdapter = new ArrayAdapter<Pizza>(this,
                android.R.layout.simple_selectable_list_item,
                singleton.getOrder().getPizzas());

        lv_curOrder.setAdapter(dataAdapter);
    }

    private void updateListView() {
        ArrayAdapter<Pizza> dataAdapter = new ArrayAdapter<Pizza>(this,
                android.R.layout.simple_selectable_list_item,
                singleton.getOrder().getPizzas());

        dataAdapter.notifyDataSetChanged();
        lv_curOrder.setAdapter(dataAdapter);
    }

    private void returnToMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //todo: delete later
    private void initTempOrder() {
        PizzaFactory pf = new ChicagoPizza();
        Pizza tp = pf.createMeatzza();
        tp.setSize(Size.SMALL);
        Pizza tp2 = pf.createDeluxe();
        tp2.setSize(Size.MEDIUM);
        Pizza tp3 = pf.createBBQChicken();
        tp3.setSize(Size.LARGE);

        Order order = new Order();
        order.addPizza(tp);
        order.addPizza(tp2);
        order.addPizza(tp3);
        order.addPizza(tp2);
        order.addPizza(tp3);
        order.setOrderNumber(21);
        singleton.setOrder(order);
    }
}
