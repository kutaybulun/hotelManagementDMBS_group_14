package relations;

public class Hotel {
    private int hotelID;
    private String hotelName;
    private int addressID;
    private String contactNumber;

    public Hotel(int hotelID, String hotelName, int addressID, String contactNumber) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.addressID = addressID;
        this.contactNumber = contactNumber;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotelID=" + hotelID +
                ", hotelName='" + hotelName + '\'' +
                ", addressID=" + addressID +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }
}
