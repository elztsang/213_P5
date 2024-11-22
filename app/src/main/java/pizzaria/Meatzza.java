package pizzaria;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Meatzza is a subclass of the Pizza class.
 * Defines a pizza type with Sausage, Pepperoni, Beef, and Ham as toppings.
 *
 * @author Elizabeth Tsang, Ron Chrysler Amistad
 */
public class Meatzza extends Pizza {
    private final static double SMALL = 17.99;
    private final static double MEDIUM = 19.99;
    private final static double LARGE = 21.99;

    /**
     * Default constructor for Meatzza.
     */
    public Meatzza() {

    }

    /**
     * Parametrized constructor for Meatzza.
     * Makes a Meatzza Pizza with the specified crust.
     *
     * @param crust crust type
     */
    public Meatzza(Crust crust) {
        super.setCrust(crust);
        super.setToppings(new ArrayList<>(Arrays.asList(Topping.SAUSAGE,
                Topping.PEPPERONI,
                Topping.BEEF,
                Topping.HAM)));
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
