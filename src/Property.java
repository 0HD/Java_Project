enum PropertyType {
    PROPERTY,
    RESIDENTIAL,
    COMMERCIAL,
    INDUSTRIAL
}

class Property {

    private int propertyId;
    private int ownerId;
    private String name;
    private final PropertyType type;
    private String address;
    private String features;
    private double value;
    private double discount;
    private double interest;
    private static double tax;
    private static int numberOfProperties;

    public Property(int ownerId, String name, double value) {
        this.ownerId = ownerId;
        this.name = name;
        this.value = value;
        this.propertyId = numberOfProperties;
        numberOfProperties++;
        if (this.getClass() == Residential.class)
            this.type = PropertyType.RESIDENTIAL;
        else if (this.getClass() == Commercial.class)
            this.type = PropertyType.COMMERCIAL;
        else if (this.getClass() == Industrial.class)
            this.type = PropertyType.INDUSTRIAL;
        else
            this.type = PropertyType.PROPERTY;
    }

    public void setOwner(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public void setTax(double tax) {
        Property.tax = tax;
    }

    public int getOwner() {
        return this.ownerId;
    }

    public String getName() {
        return this.name;
    }

    public PropertyType getType() {
        return this.type;
    }

    public String getAddress() {
        return this.address;
    }

    public String getFeatures() {
        return this.features;
    }

    public double getValue() {
        return this.value;
    }

    public double getDiscount() {
        return this.discount;
    }

    public double getInterest() {
        return this.interest;
    }

    public double getTax() {
        return tax;
    }

    public double calculateTotal() {
        return getValue() - (getValue() * getDiscount())
                + (getValue() * getInterest()) + (getValue() * getTax());
    }

    int getPropertyId() {
        return this.propertyId;
    }



}