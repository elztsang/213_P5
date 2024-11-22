package pizzaria;

import java.text.DecimalFormat;
import java.util.ArrayList;

/** Pizza is an abstract class that stores information about toppings, crust, and size.
 * Also defines a price method, which determines the price of the pizza based on size and type.
 *
 * @author Elizabeth Tsang, Ron Chrysler Amistad
 */
public abstract class Pizza {
    private ArrayList<Topping> toppings; //pizzaria.Topping is a Enum class
    private Crust crust; //pizzaria.Crust is a Enum class
    private Size size; //pizzaria.Size is a Enum class

    /** An abstract method that returns the price of the pizza.
     *
     * @return price
     */
    public abstract double price();

    /** Sets the size of the pizza.
     *
     * @param size size
     */
    public void setSize(Size size){
        this.size = size;
    }

    /** Returns the size of the pizza.
     *
     * @return size
     */
    public Size getSize() {
        return this.size;
    }

    /** Sets the toppings of the pizza.
     *
     * @param toppingsList - list of toppings.
     */
    public void setToppings(ArrayList<Topping> toppingsList) {
        if (toppings == null) {
            toppings = new ArrayList<>();
        }
        toppings.addAll(toppingsList);
    }

    /** Sets the crust of the pizza.
     *
     * @param crust -  crust
     */
    public void setCrust(Crust crust){
        this.crust = crust;
    }

    /** Returns the list of toppings on the pizza.
     *
     * @return toppings list
     */
    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    /** Returns the crust type of the pizza.
     *
     * @return crust
     */
    public Crust getCrust() {
        return crust;
    }

    /** Returns a string representation of the pizza.
     *
     * @return pizza in the form (PizzaType, Size, Crust, PizzaStyle, Toppings, Subtotal)
     */
    @Override
    public String toString(){
        DecimalFormat moneyFormat = new DecimalFormat("###,###.00");
        return String.format("%s, %s, (%s - %s) %s $%s",
                this.getClass().getSimpleName(),
                size,
                crust.name(),
                crust.getCrustType(),
                toppings,
                moneyFormat.format(price()));
    }
}