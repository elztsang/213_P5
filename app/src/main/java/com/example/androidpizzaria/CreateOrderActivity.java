package com.example.androidpizzaria;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

import pizzaria.Order;
import pizzaria.Pizza;

/**
 * Activity to handle displaying the list of pizzas in the order, the subtotal/sales tax/total price of the order,
 * and removing a pizza from the order, and placing the order.
 *
 * @author Ron Chrysler Amistad, Elizabeth Tsang
 */
public class CreateOrderActivity extends AppCompatActivity {
    private Button bt_addPizzas, bt_addOrder, bt_createBackButton;
    private TextView t_orderTotal, t_pizzaTotal, t_salesTax, t_orderNumber;
    private ListView lv_curOrder;
    private ArrayAdapter<Pizza> pizzaArrayAdapter;
    Singleton singleton = Singleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createorder_view);
        findID();
        initClickListeners();
        initLVClickListener();
        updateCurrentOrder();
        toggleAddOrderWhenValid(); //disable button if order is empty
        updateTotals();
        updateCurrentOrderNumber();
        if (singleton.getOrder() != null) {
            singleton.getOrder().setOrderNumber(singleton.getOrderCounter());
        }
    }

    /**
     * Helper method to initialize all the GUI elements with their respective IDs.
     */
    private void findID() {
        bt_addPizzas = findViewById(R.id.bt_addPizzas);
        bt_addOrder = findViewById(R.id.bt_addOrder);
        bt_createBackButton = findViewById(R.id.bt_createBackButton);
        t_orderTotal = findViewById(R.id.t_orderTotal);
        t_pizzaTotal = findViewById(R.id.t_pizzaTotal);
        t_salesTax = findViewById(R.id.t_salesTax);
        t_orderNumber = findViewById(R.id.t_orderNumber);
        lv_curOrder = findViewById(R.id.lv_curOrder);
    }

    /**
     * Helper method to initialize the click listeners on the add order, add pizzas, and back buttons.
     */
    private void initClickListeners() {
        bt_addPizzas.setOnClickListener(v -> onAddPizzasClick());

        bt_addOrder.setOnClickListener(v -> {
            onAddOrderClick();
            toggleAddOrderWhenValid();
        });

        bt_createBackButton.setOnClickListener(v -> returnToMainMenu());
    }

    /**
     * Helper method to initialize the click listener on the listview.
     * Clicking on an item in the listview will trigger a dialog asking the user if they want to remove a pizza.
     */
    private void initLVClickListener() {
        lv_curOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try { //remove pizza on click
                    Pizza selectedPizza = (Pizza) parent.getItemAtPosition(position);
                    createRemovePizzaAlertDialog(selectedPizza);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.remove_pizza_error),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Helper method to toggle the add order button if the list of pizzas in the order is empty.
     */
    private void toggleAddOrderWhenValid() {
        if (singleton.getOrder().getPizzas().isEmpty()) {
            bt_addOrder.setEnabled(false);
        } else {
            bt_addOrder.setEnabled(true);
        }
    }

    /**
     * Helper method that switches to the add pizza activity when called.
     */
    private void onAddPizzasClick() {
        Intent intent = new Intent(this, AddPizzaActivity.class);
        startActivity(intent);
    }

    /**
     * Helper method to add the current order to the list of orders in the singleton.
     */
    private void onAddOrderClick() {
        if (singleton.getOrder() != null) {
            singleton.setOrderCounter(singleton.getOrderCounter() + 1); //increment order counter
            singleton.getOrderList().add(singleton.getOrder());
            updateCurrentOrderNumber();
            Toast.makeText(getApplicationContext(),
                    getString(R.string.add_order_success),
                    Toast.LENGTH_SHORT).show();
            singleton.setOrder(new Order());

            updateCurrentOrder();
            updateTotals();
        } else {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.add_order_error),
                    Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Helper method to increase the current order number by 1 after the previous order was placed.
     */
    private void updateCurrentOrderNumber() {
        t_orderNumber.setText(String.format("Current Order Number: %s",
                singleton.getOrderCounter()));
    }

    /**
     * Helper method to update the current order's list of pizzas.
     */
    private void updateCurrentOrder() {
        pizzaArrayAdapter = new ArrayAdapter<Pizza>(this,
                android.R.layout.simple_selectable_list_item,
                singleton.getOrder().getPizzas());

        lv_curOrder.setAdapter(pizzaArrayAdapter);
    }

    /**
     * Helper method to handle returning to the main menu screen.
     */
    private void returnToMainMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Helper method to handle the alert displayed when a user attempts to remove the specified pizza from the current order,
     * and removes the pizza from the order if the user confirms their decision to remove the specified pizza.
     *
     * @param selectedPizza the pizza requested to remove from the current order
     */
    private void createRemovePizzaAlertDialog(Pizza selectedPizza) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateOrderActivity.this);
        builder.setMessage(getString(R.string.pizza_remove_alert));
        builder.setTitle(getString(R.string.alert_title));
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (dialog, which) -> {
            singleton.getOrder().getPizzas().remove(selectedPizza);
            pizzaArrayAdapter.notifyDataSetChanged();
            updateTotals();
        });

        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Helper method to update the  subtotal, sales tax, and total price of the current order.
     */
    private void updateTotals() {
        DecimalFormat moneyFormat = new DecimalFormat("###,##0.00");

        t_orderTotal.setText(String.format("$%s",
                moneyFormat.format(singleton.getOrder().getOrderTotal())));

        t_salesTax.setText(String.format("$%s",
                moneyFormat.format(singleton.getOrder().getSalesTax())));
        ;

        t_pizzaTotal.setText(String.format("$%s",
                moneyFormat.format(singleton.getOrder().getTotal())));
    }
}
