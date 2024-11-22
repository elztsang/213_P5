package pizzaria;

/** Interface for creating Pizza Factories.
 * Pizza Factories are responsible for handling the creation of pizza objects.
 *
 * @author Elizabeth Tsang, Ron Chrysler Amistad
 */
public interface PizzaFactory {
    /** Creates a Deluxe pizza.
     *
     * @return Deluxe
     */
    Pizza createDeluxe();

    /** Creates a Meatzza pizza.
     *
     * @return Meatzza
     */
    Pizza createMeatzza();

    /** Creates a BBQChicken pizza.
     *
     * @return BBQChicken
     */
    Pizza createBBQChicken();

    /** Creates a BYO pizza.
     *
     * @return BYO
     */
    Pizza createBuildYourOwn();
}