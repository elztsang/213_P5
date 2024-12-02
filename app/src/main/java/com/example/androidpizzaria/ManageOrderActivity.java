package com.example.androidpizzaria;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import pizzaria.ChicagoPizza;
import pizzaria.Order;
import pizzaria.Pizza;
import pizzaria.PizzaFactory;
import pizzaria.Size;

public class ManageOrderActivity extends AppCompatActivity{
    Singleton singleton = Singleton.getInstance();
    Button bt_removeOrder, bt_exportOrders, bt_manageBackButton;
    Spinner sp_selectOrder;
    ListView lv_selectedOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manageorder_view);
        findID();
        initClickListeners();
        setTestOrderList(); //for testing todo: delete later
        updateSpinner();
        populateListView();
        toggleRemoveOrderIfEmpty();
    }

    private void findID() {
        bt_removeOrder = findViewById(R.id.bt_removeOrder);
        bt_exportOrders = findViewById(R.id.bt_exportOrders);
        bt_manageBackButton = findViewById(R.id.bt_manageBackButton);
        sp_selectOrder = findViewById(R.id.sp_selectOrder);
        lv_selectedOrder = findViewById(R.id.lv_selectedOrder);
    }

    private void initClickListeners() {
        bt_removeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onRemoveOrder();
                } catch (Exception e) {
                    displayRemoveErrorToast();
                    e.printStackTrace();
                }
            }
        });

        bt_exportOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addOrderTEMP(); //for testing - todo: delete later
                exportOrders();
            }
        });

        bt_manageBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMainMenu();
            }
        });
    }

    private void toggleRemoveOrderIfEmpty() {
        if (singleton.getOrderList().isEmpty()) {
            bt_removeOrder.setEnabled(false);
        } else {
            bt_removeOrder.setEnabled(true);
        }
    }

    private void onRemoveOrder() {
        Order selectedOrder = (Order) sp_selectOrder.getSelectedItem();
        singleton.getOrderList().remove(selectedOrder);
        updateSpinner();
        toggleRemoveOrderIfEmpty();
        clearLVIfEmpty();
    }

    private void displayRemoveErrorToast() {
        Toast.makeText(getApplicationContext(),
                getString(R.string.remove_order_error),
                Toast.LENGTH_SHORT).show();
    }

    private void clearLVIfEmpty() {
        ArrayAdapter<Pizza> dataAdapter = new ArrayAdapter<Pizza>(this,
                android.R.layout.simple_list_item_1,
                singleton.getOrder().getPizzas());

        if (singleton.getOrderList().isEmpty()) {
            dataAdapter.clear();
            dataAdapter.notifyDataSetChanged();
            lv_selectedOrder.setAdapter(dataAdapter);

        }
    }

    private void exportOrders() {
        try {
            File output = new File(getFilesDir() + "/exported_orders.txt");
            if (output.exists()) {
                //System.exit(1);
            }
            PrintWriter pw = new PrintWriter(output);
            for (Order order : singleton.getOrderList()) {
                pw.println(order);
            }
            pw.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.export_order_error),
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void updateSpinner() {
        ArrayAdapter<Order> dataAdapter = new ArrayAdapter<Order>(this,
                android.R.layout.simple_spinner_item,
                singleton.getOrderList());

        dataAdapter.notifyDataSetChanged();
        sp_selectOrder.setAdapter(dataAdapter);
    }

    private void populateListView() {
        ArrayAdapter<Pizza> dataAdapter = new ArrayAdapter<Pizza>(this,
                android.R.layout.simple_list_item_1,
                singleton.getOrder().getPizzas()); // may need to change

        lv_selectedOrder.setAdapter(dataAdapter);

        //update listview on order selection
        sp_selectOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Order selectedOrder = (Order) parentView.getItemAtPosition(position);

                dataAdapter.clear();
                dataAdapter.addAll(selectedOrder.getPizzas());
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                dataAdapter.clear();
                dataAdapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(), "No order selected!", Toast.LENGTH_SHORT).show();
                //System.out.println("some error");
            }
        });
    }

    private void returnToMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //todo: delete later
    private void setTestOrderList() {
        PizzaFactory pf = new ChicagoPizza();
        Pizza tp = pf.createMeatzza();
        tp.setSize(Size.SMALL);

        Order order1 = new Order();
        Order order2 = new Order();
        Order order3 = new Order();
        order1.addPizza(tp);
        order3.addPizza(tp);
        order3.addPizza(tp);
        order3.addPizza(tp);
        order3.addPizza(tp);

        singleton.getOrderList().add(order1);
        singleton.getOrderList().add(order2);
        singleton.getOrderList().add(order3);
    }

    //todo: delete later
    private void addOrderTEMP() {
        System.out.println(singleton.getOrderList());
        PizzaFactory pf = new ChicagoPizza();
        Pizza tp = pf.createMeatzza();
        tp.setSize(Size.SMALL);

        Order order = new Order();
        order.addPizza(tp);
        order.setOrderNumber(21);
        singleton.getOrderList().add(order);
    }
}
