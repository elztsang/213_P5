package pizzaria;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Deluxe is a subclass of the Pizza class.
 * Defines a pizza type with Sausage, Pepperoni, Green Pepper, Onion, and Mushroom as toppings.
 *
 * @author Elizabeth Tsang, Ron Chrysler Amistad
 */
public class Deluxe extends Pizza {
    private final static double SMALL = 16.99;
    private final static double MEDIUM = 18.99;
    private final static double LARGE = 20.99;

    /**
     * Default constructor for Deluxe.
     */
    public Deluxe() {

    }

    /**
     * Parametrized constructor for Deluxe.
     * Makes a Deluxe Pizza with the specified crust.
     *
     * @param crust crust type
     */
    public Deluxe(Crust crust) {
        super.setCrust(crust);
        super.setToppings(new ArrayList<>(Arrays.asList(Topping.SAUSAGE,
                Topping.PEPPERONI,
                Topping.GREENPEPPER,
                Topping.ONION,
                Topping.MUSHROOM)));
    }

    /**
     * Return the price of the pizza.
     * BYO also increases the price of the pizza based on amount of toppings.
     *
     * @return price
     */
    @Override
    public double price() {
        if (this.getSize().equals(Size.SMALL)) {
            return SMALL;
        } else if (this.getSize().equals(Size.MEDIUM)) {
            return MEDIUM;
        } else if (this.getSize().equals(Size.LARGE)) {
            return LARGE;
        } else {
            return -1; // no price
        }
    }
}
