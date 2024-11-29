package com.example.androidpizzaria;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import pizzaria.Order;

public class ManageOrderActivity extends AppCompatActivity{
    Singleton singleton = Singleton.getInstance();
    Button bt_removeOrder, bt_exportOrders;
    Spinner sp_selectOrder;
    ListView lv_selectedOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manageorder_view);
        findID();
        initClickListeners();
    }

    private void findID() {
        bt_removeOrder = findViewById(R.id.bt_removeOrder);
        bt_exportOrders = findViewById(R.id.bt_exportOrders);
        sp_selectOrder = findViewById(R.id.sp_selectOrder);
        lv_selectedOrder = findViewById(R.id.lv_selectedOrder);
    }

    private void initClickListeners() {
        bt_removeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeOrder((Order) sp_selectOrder.getSelectedItem());
            }
        });
        bt_exportOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportOrders();
            }
        });
    }

    //idk if this is all i need lol
    private void removeOrder(Order order) {
        singleton.getOrderList().remove(order);
    }

    //taken from p4
    private void exportOrders() {
        try {
            File output = new File("exported_orders.txt");
            if (output.exists()) {
                System.exit(1);
            }
            PrintWriter pw = new PrintWriter(output);
            for (Order order : singleton.getOrderList()) {
                pw.println(order);
            }
            pw.close();
        } catch (IOException e) {
            //todo: change the print statement to toast
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
