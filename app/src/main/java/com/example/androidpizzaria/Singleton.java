package com.example.androidpizzaria;
import java.util.ArrayList;

import pizzaria.*;

/**
 * smth smth store all the values across controllers
 *
 */
public class Singleton {
    private static Singleton instance = null;
    private Pizza pizza;
    private ArrayList<Pizza> pizzaList;
    private PizzaFactory pizzaFactory;
    private Order order;
    private ArrayList<Order> orderList;

    private Singleton() {
        //idk if we need this?
    }

    public static Singleton getInstance() {
        if (instance == null)
            return instance = new Singleton();
        return instance;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public ArrayList<Pizza> getPizzaList() {
        return pizzaList;
    }

    public Order getOrder() {
        return order;
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public Order setOrder(Order order) {
        this.order = order;
        return this.order;
    }
}
