package relations;
import java.math.BigDecimal;

public class Room {
    private int roomID;
    private int roomTypeID;
    private BigDecimal price;
    private String status;
    private int hotelID;

    public Room(int roomID, int roomTypeID, BigDecimal price, String status, int hotelID) {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRoomStatus() {
        return status;
    }

    public void setRoomStatus(String status) {
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

