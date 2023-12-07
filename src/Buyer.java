import java.util.ArrayList;
import java.util.Arrays;

class Buyer extends User {

    private int numberOfBuyers;
    private ArrayList<Property> propertiesOwned = new ArrayList<>();

    public Buyer(String username, String password) {
        super(username,password);
        numberOfBuyers++;
    }

    public void listAllProperties() {
        Output.viewTable(Main.listedProperties, 0, Main.listedProperties.size(), 0);
    }

    public Property[] searchProperties(String name) {

        Property[] filteredProperties = new Property[0];
        for (Property property : propertiesOwned) {
            if (property.getName().contains(name)) {
                filteredProperties = Arrays.copyOf(filteredProperties, filteredProperties.length + 1);
                filteredProperties[filteredProperties.length - 1] = property;
            }
        }
        return filteredProperties;
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

//    public void buyProperty(int id, double price) {
//
//        Property property = new Property(id, "", price);
//        propertiesOwned = Arrays.copyOf(propertiesOwned, propertiesOwned.length + 1);
//        propertiesOwned[propertiesOwned.length - 1] = property;
//    }
}