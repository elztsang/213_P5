package pizzaria;

/** ChicagoPizza is a Pizza Factory for Chicago-style pizzas.
 * Creates various pizzas with Chicago-style crusts.
 *
 * @author Elizabeth Tsang, Ron Chrysler Amistad
 */
public class ChicagoPizza implements PizzaFactory{
    /** Creates a Deluxe pizza, Chicago-style.
     *
     * @return Deluxe pizza
     */
    @Override
    public Pizza createDeluxe() {
        return new Deluxe(Crust.DEEPDISH);
    }

    /** Creates a Meatzza pizza, Chicago-style.
     *
     * @return Meatzza pizza
     */
    @Override
    public Pizza createMeatzza() {
        return new Meatzza(Crust.STUFFED);
    }

    /** Creates a BBQChicken pizza, Chicago-style.
     *
     * @return BBQChicken pizza
     */
    @Override
    public Pizza createBBQChicken() {
        return new BBQChicken(Crust.PAN);
    }

    /** Creates a BYO pizza, Chicago-style.
     *
     * @return BYO pizza
     */
    @Override
    public Pizza createBuildYourOwn() {
        return new BuildYourOwn(Crust.PAN);
    }
}