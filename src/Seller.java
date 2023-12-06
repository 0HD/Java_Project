class Seller extends User {
    private int numberOfSeller;
    private Property[] propertiesOwned;

    public Seller(String username, String password) {
        super(username, password);
        propertiesOwned = new Property[0];
        numberOfSeller++;
    }

    public void addProperty(Property property) {
        Property[] newPropertiesOwned = new Property[propertiesOwned.length + 1];
        System.arraycopy(propertiesOwned, 0, newPropertiesOwned, 0, propertiesOwned.length);
        newPropertiesOwned[propertiesOwned.length] = property;
        propertiesOwned = newPropertiesOwned;
    }

    public void sellProperty(int id, int buyerId) {
        for (Property property : propertiesOwned) {
            if (property.getPropertyId() == id) {
                property.setOwner(buyerId);
                break;
            }
        }
    }

    public Property[] getPropertiesOwned() {
        return propertiesOwned;
    }
}