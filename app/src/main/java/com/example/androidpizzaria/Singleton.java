package com.example.androidpizzaria;

import java.util.ArrayList;

import pizzaria.*;

/**
 * A singleton class implementing the singleton design pattern.
 * This class keeps track of order number, pizza being added, list of pizzas in an order, and a list of orders across all other objects/threads.
 *
 * @author Ron Chrysler Amistad, Elizabeth Tsang
 */
public class Singleton {
    private static Singleton instance = null;
    private Pizza pizza;
    private PizzaFactory pizzaFactory;
    private Order order;
    private ArrayList<Order> orderList;
    private int orderCounter;

    /**
     * Empty private constructor that prevents JVM from automatically creating a public default constructor.
     */
    private Singleton() {
        orderList = new ArrayList<>();
        order = new Order();
        //idk what to set for pizza/pizzafactory yet
    }

    /**
     * Creates an instance of the singleton class if it is not yet created. Otherwise, it will return the instance.
     *
     * @return the reference of the only instance of this singleton class
     */
    public static synchronized Singleton getInstance() {
        if (instance == null)
            return instance = new Singleton();
        return instance;
    }

    /**
     * Returns the Pizza object held in the singleton class.
     *
     * @return Pizza
     */
    public Pizza getPizza() {
        return pizza;
    }

    /**
     * Returns the Order object held in the singleton class.
     *
     * @return Order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Returns an ArrayList of Orders that have been placed.
     *
     * @return an ArrayList of Orders that have been placed.
     */
    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    /**
     * Sets the Order object in the singleton class to the specified Order object.
     *
     * @param order the Order object to set in the singleton class
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Sets the Pizza object in the singleton class to the specified Pizza object.
     *
     * @param pizza the Pizza object to set in the singleton class
     */
    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    /**
     * Adds the specified topping to the Pizza object held in the singleton class if the pizza is of type BuildYourOwn.
     * Otherwise, does nothing.
     *
     * @param topping The specified topping to add to the Pizza object
     */
    public void addTopping(Topping topping) {
        if (pizza != null && pizza.getClass().equals(BuildYourOwn.class)) {
            ((BuildYourOwn) pizza).addTopping(topping);
        }
    }

    /**
     * Removes the specified topping from the Pizza object held in the singleton class if the pizza is of type BuildYourOwn.
     * Otherwise, does nothing.
     *
     * @param topping The specified topping to remove from the Pizza object
     */
    public void removeTopping(Topping topping) {
        if (pizza != null && pizza.getClass().equals(BuildYourOwn.class)) {
            ((BuildYourOwn) pizza).removeTopping(topping);
        }
    }

    /**
     * Returns the current orderCounter held in the singleton class.
     *
     * @return the orderCounter in the singleton class as an int.
     */
    public int getOrderCounter() {
        return this.orderCounter;
    }

    /**
     * Sets the orderCounter in the singleton class to the specified int.
     *
     * @param i the int to set orderCounter to.
     */
    public void setOrderCounter(int i) {
        this.orderCounter = i;
    }

    /**
     * Returns the PizzaFactory object held in the singleton class.
     *
     * @return the PizzaFactory object held in the singleton class
     */
    public PizzaFactory getPizzaFactory() {
        return this.pizzaFactory;
    }

    /**
     * Sets the PizzaFactory object in the singleton class to the specified PizzaFactory object.
     *
     * @param pizzaFactory the PizzaFactory object to set in the singleton class
     */
    public void setPizzaFactory(PizzaFactory pizzaFactory) {
        this.pizzaFactory = pizzaFactory;
    }
}
