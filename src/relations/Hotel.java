package relations;

public class Hotel {
    private int hotelID;
    private String hotelName;
    private String location;
    private String contactNumber;

    public Hotel(int hotelID, String hotelName, String location, String contactNumber) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
                ", location='" + location + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }
}

