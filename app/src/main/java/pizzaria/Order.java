package pizzaria;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Order is a class that stores information about the pizzas in an order and order number.
 * It also has methods to calculate the total, sales tax, and order total.
 *
 * @author Elizabeth Tsang, Ron Chrysler Amistad
 */
public class Order {
    private final double NJSALESTAX = 0.06625;
    private int number; //order number
    private ArrayList<Pizza> pizzas;

    /**
     * pizzaria.Order default constructor.
     * Sets order number as -1 to indicate that it is a new order.
     */
    public Order() {
        pizzas = new ArrayList<>();
        number = -1;
    }

    /**
     * Returns the order number.
     *
     * @return order number
     */
    public int getOrderNumber() {
        return number;
    }

    /**
     * Sets the order number to the passed in number.
     *
     * @param number - number
     * @return order number
     */
    public int setOrderNumber(int number) {
        return this.number = number;
    }

    /**
     * Adds a pizza to the list of pizzas in the order.
     *
     * @param pizza - pizza to be added
     */
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    /**
     * Removes a pizza from the list of pizzas in the order.
     *
     * @param pizza - pizza to be removed
     */
    public void removePizza(Pizza pizza) {
        pizzas.remove(pizza);
    }

    /**
     * Removes all pizzas in the list of pizzas.
     */
    public void removeAllPizzas() {
        pizzas.clear();
    }

    /**
     * Calculate the total of the order.
     * This total is calculated by adding the subtotals of all pizzas in the list.
     *
     * @return total
     */
    public double getTotal() {
        double total = 0;

        for (Pizza pizza : pizzas) {
            total += pizza.price();
        }

        return total;
    }

    /**
     * Calculate the amount paid due to sales tax.
     * This sales tax is set to 6.625% to reflect NJ sales tax.
     *
     * @return sales tax
     */
    public double getSalesTax() {
        return getTotal() * NJSALESTAX;
    }

    /**
     * Calculate the total cost of the order including sales tax.
     *
     * @return order total
     */
    public double getOrderTotal() {
        return getTotal() + getSalesTax();
    }

    /**
     * Return the list of pizzas in the order.
     *
     * @return pizzas
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Returns a string representation of the order.
     *
     * @return the order in the format of [order number] [order total] [list of pizzas]
     */
    @Override
    public String toString() {
        DecimalFormat moneyFormat = new DecimalFormat("###,##0.00");
        return String.format("[#%s] [%s] [%s]",
                number,
                String.format("$%s", moneyFormat.format(getOrderTotal())),
                pizzas);
    }
}