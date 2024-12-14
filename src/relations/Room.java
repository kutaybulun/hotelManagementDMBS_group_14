package relations;

public class Room {
    private int roomID;
    private int roomTypeID;
    private double price;
    private String status;
    private int hotelID;

    public Room(int roomID, int roomTypeID, double price, String status, int hotelID) {
        this.roomID = roomID;
        this.roomTypeID = roomTypeID;
        this.price = price;
        this.status = status;
        this.hotelID = hotelID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomID=" + roomID +
                ", roomTypeID='" + roomTypeID + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", hotelID=" + hotelID +
                '}';
    }
}

