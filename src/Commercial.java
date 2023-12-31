class Commercial extends Property {

    private String ownerId;
    private String name;
    private double value;
    private String nearbyIndustrial;
    private String nearbyWarehouses;
    private int numberOfCommercial;

    public Commercial(int ownerId, String name, double value) {
        super(ownerId, name, value);
    }

    public String getNearbyIndustrial() {
        return nearbyIndustrial;
    }

    public void setNearbyIndustrial(String nearbyIndustrial) {
        this.nearbyIndustrial = nearbyIndustrial;
    }

    public String getNearbyWarehouses() {
        return nearbyWarehouses;
    }

    public void setNearbyWarehouses(String nearbyWarehouses) {
        this.nearbyWarehouses = nearbyWarehouses;
    }

    public int getNumberOfCommercial() {
        return numberOfCommercial;
    }

    public void setNumberOfCommercial(int numberOfCommercial) {
        this.numberOfCommercial = numberOfCommercial;
    }

    @Override
    public String toString() {
        return "Commercial{" +
                "ownerId='" + ownerId + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", nearbyIndustrial='" + nearbyIndustrial + '\'' +
                ", nearbyWarehouses='" + nearbyWarehouses + '\'' +
                ", numberOfCommercial=" + numberOfCommercial +
                '}';
    }
}