package pizzaria;

import java.util.ArrayList;

/** BuildYourOwn is a subclass of the Pizza class.
 * Defines a pizza type where the user is able to choose what toppings they want.
 *
 * @author Elizabeth Tsang, Ron Chrysler Amistad
 */
public class BuildYourOwn extends Pizza{
    private final static double SMALL = 8.99;
    private final static double MEDIUM = 10.99;
    private final static double LARGE = 12.99;
    private final static double TOPPINGPRICE = 1.69;

    private ArrayList<Topping> toppings;

    public BuildYourOwn() {

    }


    /**
     * Parametrized constructor for BYO.
     * Makes a BYO Pizza with the specified crust.
     *
     * @param crust crust type
     */
    public BuildYourOwn(Crust crust) {
        super.setCrust(crust);
        toppings = new ArrayList<>();
    }

    /**
     * Return the price of the pizza.
     * BYO also increases the price of the pizza based on amount of toppings.
     *
     * @return price
     */
    @Override
    public double price() {
        double toppingPrice = 0.0;

        for (int i = 0; i < toppings.size(); i++) {
            toppingPrice += TOPPINGPRICE;
        }

        if (this.getSize() == null) {
            return -1;
        }

        if (this.getSize().equals(Size.SMALL))  {
            return SMALL + toppingPrice;
        } else if (this.getSize().equals(Size.MEDIUM)) {
            return MEDIUM + toppingPrice;
        } else if (this.getSize().equals(Size.LARGE)) {
            return LARGE + toppingPrice;
        } else {
            return -1; // no price
        }
    }

    /**
     * Sets the list of toppings for the BYO pizza.
     *
     * @param toppingsList list of toppings
     */
    public void setToppings(ArrayList<Topping> toppingsList) {
        if (toppings == null) {
            toppings = new ArrayList<>();
        }
        toppings.clear();
        toppings.addAll(toppingsList);
        super.setToppings(toppings);
    }
}
