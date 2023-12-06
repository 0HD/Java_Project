class Residential extends Property {

    private int ownerId;
    private String districtName;
    private String nearbyCommercial;
    private String nearbyParks;
    private int numberOfResidential;

    public Residential(int ownerId, String name, double value) {
        super(ownerId, name, value);
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getNearbyCommercial() {
        return nearbyCommercial;
    }

    public void setNearbyCommercial(String nearbyCommercial) {
        this.nearbyCommercial = nearbyCommercial;
    }

    public String getNearbyParks() {
        return nearbyParks;
    }

    public void setNearbyParks(String nearbyParks) {
        this.nearbyParks = nearbyParks;
    }

    public int getNumberOfResidential() {
        return numberOfResidential;
    }

    public void setNumberOfResidential(int numberOfResidential) {
        this.numberOfResidential = numberOfResidential;
    }

    @Override
    public String toString() {
        return "Residential{" +
                "ownerId=" + ownerId +
                ", districtName='" + districtName + '\'' +
                ", nearbyCommercial='" + nearbyCommercial + '\'' +
                ", nearbyParks='" + nearbyParks + '\'' +
                ", numberOfResidential=" + numberOfResidential +
                '}';
    }
}