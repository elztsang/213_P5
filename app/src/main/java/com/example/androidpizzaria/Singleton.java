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
        orderList = new ArrayList<>();
        order = new Order();
        pizzaList = new ArrayList<>();
        //idk what to set for pizza/pizzafactory yet
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

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
}
