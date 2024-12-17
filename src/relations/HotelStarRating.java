package relations;

public class HotelStarRating {
    private int hotelID;
    private String hotelName;
    private double averageRating;

    public HotelStarRating(int hotelID, String hotelName, double averageRating) {
        this.hotelID = hotelID;
        this.hotelName = hotelName;
        this.averageRating = averageRating;
    }

    public int getHotelID() {
        return hotelID;
    }

    public String getHotelName() {
        return hotelName;
    }

    public double getAverageRating() {
        return averageRating;
    }

    @Override
    public String toString() {
        return "HotelStarRating{" +
                "hotelID=" + hotelID +
                ", hotelName='" + hotelName + '\'' +
                ", averageRating=" + averageRating +
                '}';
    }
}
