package pizzaria;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * BBQChicken is a subclass of the Pizza class.
 * Defines a pizza type with BBQChicken, Green Pepper, Provolone, and Cheddar as toppings.
 *
 * @author Elizabeth Tsang, Ron Chrysler Amistad
 */
public class BBQChicken extends Pizza {
    private final static double SMALL = 14.99;
    private final static double MEDIUM = 16.99;
    private final static double LARGE = 19.99;

    /**
     * Default constructor for BBQChicken.
     */
    public BBQChicken() {

    }

    /**
     * Parametrized constructor for BBQChicken.
     * Makes a BBQChicken Pizza with the specified crust.
     *
     * @param crust crust type
     */
    public BBQChicken(Crust crust) {
        super.setCrust(crust);
        super.setToppings(new ArrayList<>(Arrays.asList(Topping.BBQCHICKEN,
                Topping.GREENPEPPER,
                Topping.PROVOLONE,
                Topping.CHEDDAR)));
    }

    /**
     * Return the price of the pizza.
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
