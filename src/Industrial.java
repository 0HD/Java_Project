class Industrial extends Property {

    private int ownerId;
    private String name;
    private double value;
    private String nearbyCommercial;
    private String nearbyWarehouses;

    public Industrial(int ownerId, String name, double value) {
        super(ownerId, name, value);
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getNearbyCommercial() {
        return nearbyCommercial;
    }

    public void setNearbyCommercial(String nearbyCommercial) {
        this.nearbyCommercial = nearbyCommercial;
    }

    public String getNearbyWarehouses() {
        return nearbyWarehouses;
    }

    public void setNearbyWarehouses(String nearbyWarehouses) {
        this.nearbyWarehouses = nearbyWarehouses;
    }

    @Override
    public String toString() {
        return "Industrial{" +
                "ownerId=" + ownerId +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", nearbyCommercial='" + nearbyCommercial + '\'' +
                ", nearbyWarehouses='" + nearbyWarehouses + '\'' +
                '}';
    }
}