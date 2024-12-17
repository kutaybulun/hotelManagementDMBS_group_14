package relations;

public class HotelWithAddress {
    private int hotelID;
    private String hotelName;
    private String street;
    private String city;
    private String state;
    private String contactNumber;

    public HotelWithAddress(int hotelID, String hotelName, String street, String city, String state, String contactNumber) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.street = street;
        this.city = city;
        this.state = state;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "HotelWithAddress{" +
                "hotelID=" + hotelID +
                ", hotelName='" + hotelName + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }
}
