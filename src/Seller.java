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

    public void removeProperty(Property property) { propertiesOwned.remove(property); }
    public void viewMyListings() {
        if (!propertiesOwned.isEmpty())
            Output.viewTable(propertiesOwned, 0, propertiesOwned.size(), 0);
        else
            System.out.println("No properties owned.");
    }

//    public Property[] getPropertiesOwned() {
//        return propertiesOwned;
//    }
}