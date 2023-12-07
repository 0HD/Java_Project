import java.util.ArrayList;
import java.util.Arrays;

class Buyer extends User {

    private int numberOfBuyers;
    private ArrayList<Property> propertiesOwned = new ArrayList<>();

    public Buyer(String username, String password) {
        super(username, password);
        numberOfBuyers++;
    }

    public void listAllProperties() {
        if (!Main.listedProperties.isEmpty())
            Output.viewTable(Main.listedProperties, 0, Main.listedProperties.size(), 0);
        else
            System.out.println("No properties listed.");
    }

    public void listMyProperties() {
        if (!propertiesOwned.isEmpty())
            Output.viewTable(propertiesOwned, 0, propertiesOwned.size(), 0);
        else
            System.out.println("No properties owned.");
    }

    public void purchaseProperty(int ownerId, Property property) {
        for (Property prop : Main.listedProperties) {
            if (prop.equals(property)) {
                ((Seller) Main.registeredUsers.get(ownerId)).removeProperty(property);
                propertiesOwned.add(property);
            }
        }
    }


    public Property searchProperties(double minValue, double maxValue) {

        Property property = null;
        for (Property prop : propertiesOwned) {
            if (prop.getValue() >= minValue && prop.getValue() <= maxValue) {
                property = prop;
                break;
            }
        }
        return property;
    }
}