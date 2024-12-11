package relations;

public class Room {
    private int roomID;
    private String roomType;
    private double price;
    private String status;
    private int hotelID;

    public Room(int roomID, String roomType, double price, String status, int hotelID) {
        this.roomID = roomID;
        this.roomType = roomType;
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

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
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
                ", roomType='" + roomType + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", hotelID=" + hotelID +
                '}';
    }
}

