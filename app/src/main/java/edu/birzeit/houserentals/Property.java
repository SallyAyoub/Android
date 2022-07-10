package edu.birzeit.houserentals;



public class Property {

    private String city;
    private String postalAddress;
    private long SurfaceArea;
    private int numberOfBedrooms;
    private long rentalPrice;
    private String status;
    private String balcony;
    private String garden;



    public Property(String city, String postalAddress , long surfaceArea, int numberOfBedrooms, long rentalPrice, String status, String balcony, String garden) {
        this.city = city;
        this.postalAddress= postalAddress;
        SurfaceArea = surfaceArea;
        this.numberOfBedrooms = numberOfBedrooms;
        this.rentalPrice = rentalPrice;
        this.status = status;
        this.balcony = balcony;
        this.garden = garden;
    }

    public String getCity() {
        return city;
    }



    public void setCity(String city) {
        this.city = city;
    }
    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public long getSurfaceArea() {
        return SurfaceArea;
    }

    public int getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    public long getRentalPrice() {
        return rentalPrice;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBalcony() {
        return balcony;
    }



    public void setBalcony(String balcony) {
        this.balcony = balcony;
    }

    public String getGarden() {
        return garden;
    }

    public void setGarden(String garden) {
        this.garden = garden;
    }

    public void setRentalPrice(long rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public void setNumberOfBedrooms(int numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    public void setSurfaceArea(long surfaceArea) {
        SurfaceArea = surfaceArea;

    }

}
