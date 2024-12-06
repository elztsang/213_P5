package com.example.androidpizzaria;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

import pizzaria.ChicagoPizza;
import pizzaria.Order;
import pizzaria.Pizza;
import pizzaria.PizzaFactory;
import pizzaria.Size;

/**
 * Activity class that handles the ability to view and cancel orders that have been placed.
 * @author Ron Chrysler Amistad, Elizabeth Tsang
 */
public class ManageOrderActivity extends AppCompatActivity{
    Singleton singleton = Singleton.getInstance();
    Button bt_removeOrder, bt_manageBackButton;
    TextView t_curOrderTotal;
    Spinner sp_selectOrder;
    ListView lv_selectedOrder;
    ArrayAdapter<Pizza> currentOrderAdapter;
    ArrayAdapter<Order> orderListAdapter;
    Order currentSelectedOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manageorder_view);
        findID();
        initClickListeners();
        //createTestOrderList(); //for testing todo: delete later
        initSpinnerAdapter();
        updateCurrentOrderAdapter();

        populateListView();
        toggleRemoveOrderIfEmpty();

    }

    /**
     * Helper method to initialize all the GUI elements to their respective IDs.
     */
    private void findID() {
        bt_removeOrder = findViewById(R.id.bt_removeOrder);
        bt_manageBackButton = findViewById(R.id.bt_manageBackButton);
        sp_selectOrder = findViewById(R.id.sp_selectOrder);
        t_curOrderTotal = findViewById(R.id.t_curOrderTotal);
        lv_selectedOrder = findViewById(R.id.lv_selectedOrder);
    }

    /**
     * Helper method to initialize the listeners for the button to return to the main menu screen, and button to remove the current selected order.
     */
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
//        ArrayAdapter<Pizza> dataAdapter = new ArrayAdapter<Pizza>(this,
//                android.R.layout.simple_list_item_1,
//                singleton.getOrder().getPizzas());

        if (singleton.getOrderList().isEmpty()) {
            currentOrderAdapter.clear();
            currentOrderAdapter.notifyDataSetChanged();
            //lv_selectedOrder.setAdapter(dataAdapter);
        }
    }

    private void initSpinnerAdapter() {
        orderListAdapter = new ArrayAdapter<Order>(this,
                android.R.layout.simple_spinner_item,
                singleton.getOrderList());

        sp_selectOrder.setAdapter(orderListAdapter);
    }

    private void updateCurrentOrderAdapter() {
        if (sp_selectOrder.getSelectedItem() != null) {
            Order selectedOrder = (Order) sp_selectOrder.getSelectedItem();
            currentOrderAdapter = new ArrayAdapter<Pizza>(this,
                    android.R.layout.simple_list_item_1,
                    selectedOrder.getPizzas());
        } else {
            Order empty = new Order();
            currentOrderAdapter = new ArrayAdapter<Pizza>(this,
                    android.R.layout.simple_list_item_1,
                    empty.getPizzas());
        }

        lv_selectedOrder.setAdapter(currentOrderAdapter);
    }
    private void populateListView() {
        currentOrderAdapter.notifyDataSetChanged();

        //update listview on order selection
        sp_selectOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Order selectedOrder = (Order) parentView.getItemAtPosition(position);
                updateCurTotal(selectedOrder);
                updateCurrentOrderAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                currentOrderAdapter.clear();
                currentOrderAdapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(),
                        R.string.select_order_notif,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Helper method to handle returning to the main menu screen.
     */
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
            orderListAdapter.notifyDataSetChanged(); //update spinner
            toggleRemoveOrderIfEmpty();
            clearLVIfEmpty();
            if (singleton.getOrderList().isEmpty()) {
                updateCurTotal(new Order());
            } else {
                sp_selectOrder.setSelection(0);
                updateCurrentOrderAdapter();
            }
        });

        alert.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

         //update listview to new order
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
