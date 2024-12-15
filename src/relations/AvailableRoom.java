package relations;

import java.math.BigDecimal;

public class AvailableRoom {
    private int roomID;
    private String roomTypeName;
    private BigDecimal price;

    public AvailableRoom(int roomID, String roomTypeName, BigDecimal price) {
        this.roomID = roomID;
        this.roomTypeName = roomTypeName;
        this.price = price;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "AvailableRoom{" +
                "roomID=" + roomID +
                ", roomTypeName='" + roomTypeName + '\'' +
                ", price=" + price +
                '}';
    }
}
