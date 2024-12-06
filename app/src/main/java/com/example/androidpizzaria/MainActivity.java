package com.example.androidpizzaria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity to handle the main menu of the app and being able to open the create order screen or the manage orders screen.
 *
 * @author Ron Chrysler Amistad, Elizabeth Tsang
 */
public class MainActivity extends AppCompatActivity {
    Singleton singleton = Singleton.getInstance(); //use this to obtain data
    private ImageButton bt_createOrder, bt_manageOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu_view);

        findID();
        initClickListeners();
    }

    /**
     * Find the references of the GUI objects.
     * prefix rb - RadioButton objects
     * prefix cx - CheckBox objects
     * prefix cp - Chip objects
     */
    private void findID() {
        bt_createOrder = findViewById(R.id.bt_createOrder);
        bt_manageOrder = findViewById(R.id.bt_manageOrder);
    }

    /**
     * Helper method to initialize the listeners for the button to move to the
     * create order screen, and the button to move to the manage orders screen.
     */
    private void initClickListeners() {
        bt_createOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateOrderClick();
            }
        });

        bt_manageOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onManageOrderClick();
            }
        });
    }

    /**
     * Moves to the Create Order screen.
     */
    public void onCreateOrderClick() {
        Intent intent = new Intent(this, CreateOrderActivity.class);
        startActivity(intent);
    }

    /**
     * Moves to the Manage Order screen.
     */
    public void onManageOrderClick() {
        Intent intent = new Intent(this, ManageOrderActivity.class);
        startActivity(intent);
    }
}
