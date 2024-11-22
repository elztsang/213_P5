package pizzaria;

/** NYPizza is a Pizza Factory for NY-style pizzas.
 * Creates various pizzas with NY-style crusts.
 *
 * @author Elizabeth Tsang, Ron Chrysler Amistad
 */
public class NYPizza implements PizzaFactory {
    /** Creates a Deluxe pizza, NY-style.
     *
     * @return Deluxe pizza
     */
    @Override
    public Pizza createDeluxe() {
        return new Deluxe(Crust.BROOKLYN);
    }

    /** Creates a Meatzza pizza, NY-style.
     *
     * @return Meatzza pizza
     */
    @Override
    public Pizza createMeatzza() {
        return new Meatzza(Crust.HANDTOSSED);
    }

    /** Creates a BBQChicken pizza, NY-style.
     *
     * @return BBQChicken pizza
     */
    @Override
    public Pizza createBBQChicken() {
        return new BBQChicken(Crust.THIN);
    }

    /** Creates a BYO pizza, NY-style.
     *
     * @return BYO pizza
     */
    @Override
    public Pizza createBuildYourOwn() {
        return new BuildYourOwn(Crust.HANDTOSSED);
    }
}