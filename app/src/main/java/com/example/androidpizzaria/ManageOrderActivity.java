package com.example.androidpizzaria;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

import pizzaria.ChicagoPizza;
import pizzaria.Order;
import pizzaria.Pizza;
import pizzaria.PizzaFactory;
import pizzaria.Size;

public class ManageOrderActivity extends AppCompatActivity{
    Singleton singleton = Singleton.getInstance();
    Button bt_removeOrder, bt_manageBackButton;
    TextView t_curOrderTotal;
    Spinner sp_selectOrder;
    ListView lv_selectedOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manageorder_view);
        findID();
        initClickListeners();
        //createTestOrderList(); //for testing todo: delete later
        updateSpinner();
        populateListView();
        toggleRemoveOrderIfEmpty();

        if (sp_selectOrder.getSelectedItem() != null) {
            Order selectedOrder = (Order) sp_selectOrder.getSelectedItem();
            updateCurTotal(selectedOrder);
        } else {
            updateCurTotal(new Order());
        }
    }

    private void findID() {
        bt_removeOrder = findViewById(R.id.bt_removeOrder);
        bt_manageBackButton = findViewById(R.id.bt_manageBackButton);
        sp_selectOrder = findViewById(R.id.sp_selectOrder);
        t_curOrderTotal = findViewById(R.id.t_curOrderTotal);
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
        createRemoveOrderAlertDialog(selectedOrder);
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
                updateCurTotal(selectedOrder);
                dataAdapter.clear();
                dataAdapter.addAll(selectedOrder.getPizzas());
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                dataAdapter.clear();
                dataAdapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(),
                        R.string.select_order_notif,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void returnToMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void createRemoveOrderAlertDialog(Order selectedOrder) {
        AlertDialog.Builder alert = new AlertDialog.Builder(ManageOrderActivity.this);
        alert.setMessage(getString(R.string.order_remove_alert));
        alert.setTitle(getString(R.string.alert_title));
        alert.setCancelable(false);

        alert.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            singleton.getOrderList().remove(selectedOrder);
            updateSpinner();
            toggleRemoveOrderIfEmpty();
            clearLVIfEmpty();
            if (singleton.getOrderList().isEmpty()) {
                updateCurTotal(new Order());
            }
        });

        alert.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    private void updateCurTotal(Order selectedOrder) {
        DecimalFormat moneyFormat = new DecimalFormat("###,##0.00");
        t_curOrderTotal.setText(String.format("$%s", moneyFormat.format(selectedOrder.getOrderTotal())));
    }

//    DecimalFormat moneyFormat = new DecimalFormat("###,##0.00");
//        tf_total.setText(String.format("$%s", moneyFormat.format(pizzaOrder.getTotal())))

    //todo: delete later
    private void createTestOrderList() {
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
