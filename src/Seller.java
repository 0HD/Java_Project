import java.util.ArrayList;

class Seller extends User {
    private int numberOfSeller;
    private ArrayList<Property> propertiesOwned = new ArrayList<>();

    public Seller(String username, String password) {
        super(username, password);
//        propertiesOwned = new Property[0];
        numberOfSeller++;
    }

    public void addProperty(Property property) {
        propertiesOwned.add(property);
    }
//
//    public void sellProperty(int id, int buyerId) {
//        for (Property property : propertiesOwned) {
//            if (property.getPropertyId() == id) {
//                property.setOwner(buyerId);
//                break;
//            }
//        }
//    }

    public void viewMyListings() {
        if (!propertiesOwned.isEmpty())
            Output.viewTable(propertiesOwned, 0, propertiesOwned.size(), 0);
    }

//    public Property[] getPropertiesOwned() {
//        return propertiesOwned;
//    }
}