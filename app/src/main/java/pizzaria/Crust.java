package pizzaria;

/**
 * Crust is an Enum class that stores information about available pizza crust types
 * and which style of pizza it is associated with.
 *
 * @author Elizabeth Tsang, Ron Amistad
 */
public enum Crust {
    DEEPDISH("Chicago"),
    PAN ("Chicago"),
    STUFFED ("Chicago"),
    BROOKLYN ("New York"),
    THIN ("New York"),
    HANDTOSSED ("New York");

    private final String crustType;

    Crust(String crustType) {
        this.crustType = crustType;
    }

    public String getCrustType(){
        return crustType;
    }
}
