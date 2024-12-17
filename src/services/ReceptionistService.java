package services;
import relations.*;
import databaseaccess.*;

import java.time.LocalDate;
import java.util.List;

public class ReceptionistService {
    //DB access objects
    private final RoomDataBaseAccess roomDataBaseAccess;
    private final BookingDataBaseAccess bookingDataBaseAccess;
    private final BookedRoomsDataBaseAccess bookedRoomsDataBaseAccess;
    private final HousekeepingTaskDataBaseAccess housekeepingTaskDataBaseAccess;
    //DB access objects initialized in the constructor
    public ReceptionistService() {
        this.roomDataBaseAccess = new RoomDataBaseAccess();
        this.bookingDataBaseAccess = new BookingDataBaseAccess();
        this.bookedRoomsDataBaseAccess = new BookedRoomsDataBaseAccess();
        this.housekeepingTaskDataBaseAccess = new HousekeepingTaskDataBaseAccess();
    }

    public boolean addNewBooking(int userID, LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuests, int roomID) {
        int bookingID = bookingDataBaseAccess.getNextBookingID(); // Get next available booking ID
        String paymentStatus = "pending";
        String reservationStatus = "pending";

        // 1. Create a new booking
        boolean isBookingCreated = bookingDataBaseAccess.create(new Booking(bookingID, userID, checkInDate, checkOutDate, numberOfGuests, paymentStatus, reservationStatus));

        if (isBookingCreated) {
            // 2. Link the booking to the room via BookedRooms
            boolean isRoomBooked = bookedRoomsDataBaseAccess.create(new BookedRooms(bookingID, roomID));

            if (isRoomBooked) {
                return true; // Booking was successful
            } else {
                // Rollback the booking if room could not be linked
                bookingDataBaseAccess.delete(bookingID);
            }
        }
        return false; // Booking failed
    }

    public boolean deleteBooking(int bookingID) {
        Booking booking = bookingDataBaseAccess.getBookingByID(bookingID);
        if (booking != null && "cancelled".equals(booking.getReservationStatus())) {
            return bookingDataBaseAccess.delete(bookingID);
        }
        return false; // Deletion is allowed only if cancelled
    }
    public boolean modifyBooking(int bookingID, String action) {
        if ("check-in".equalsIgnoreCase(action)) {
            return checkInBooking(bookingID);
        } else if ("check-out".equalsIgnoreCase(action)) {
            return checkOutBooking(bookingID);
        }
        return false;
    }

    private boolean checkInBooking(int bookingID) {
        Booking booking = bookingDataBaseAccess.getBookingByID(bookingID);
        if (booking != null && "pending".equals(booking.getReservationStatus())) {
            booking.setReservationStatus("checked-in");
            return bookingDataBaseAccess.update(booking);
        }
        return false;
    }

    private boolean checkOutBooking(int bookingID) {
        Booking booking = bookingDataBaseAccess.getBookingByID(bookingID);
        if (booking != null && "paid".equals(booking.getPaymentStatus())) {
            booking.setReservationStatus("checked-out");
            if (bookingDataBaseAccess.update(booking)) {
                bookingDataBaseAccess.getRoomIDsByBookingID(bookingID)
                        .forEach(roomID -> roomDataBaseAccess.updateRoomStatus(roomID, "cleaning"));
                return true;
            }
        }
        return false;
    }

    public List<BookingRecord> viewAllBookings(){
        return bookingDataBaseAccess.viewAllBookingRecords();
    }

    public List<RequestedBooking> viewRequestedBookings() {
        return bookingDataBaseAccess.getRequestedBookings();
    }

    // View rooms that need to be cleaned for a specific date to assign the room status to cleaning
    public List<CheckedOutBooking> viewRoomsToBeCleaned(LocalDate checkOutDate) {
        return bookingDataBaseAccess.getCheckedOutBookingsByDate(checkOutDate);
    }

    public void processPayment() {}

    public boolean assignHouseKeepingTask(int roomID, int assignedTo, LocalDate scheduledDate, String taskStatus) {
        int housekeeperID = housekeepingTaskDataBaseAccess.getNextTaskID();
        HousekeepingTask housekeeping = new HousekeepingTask(housekeeperID, roomID, assignedTo, scheduledDate, taskStatus);
        return housekeepingTaskDataBaseAccess.create(housekeeping);
    }

    public List<HousekeeperTask> viewAllHouseKeepersRecordsAndAvailability() {
        return housekeepingTaskDataBaseAccess.getHousekeeperTaskDetails();
    }


}
