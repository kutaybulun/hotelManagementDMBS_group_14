package services;
import relations.*;
import databaseaccess.*;
import java.sql.*;
import db.DBConnection;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReceptionistService {
    //DB access objects
    private final RoomDataBaseAccess roomDataBaseAccess;
    private final BookingDataBaseAccess bookingDataBaseAccess;
    private final BookedRoomsDataBaseAccess bookedRoomsDataBaseAccess;
    private final HousekeepingTaskDataBaseAccess housekeepingTaskDataBaseAccess;
    private final PaymentDataBaseAccess paymentDataBaseAccess;
    //DB access objects initialized in the constructor
    public ReceptionistService() {
        this.roomDataBaseAccess = new RoomDataBaseAccess();
        this.bookingDataBaseAccess = new BookingDataBaseAccess();
        this.bookedRoomsDataBaseAccess = new BookedRoomsDataBaseAccess();
        this.housekeepingTaskDataBaseAccess = new HousekeepingTaskDataBaseAccess();
        this.paymentDataBaseAccess = new PaymentDataBaseAccess();
    }

    // Add a new booking for multiple rooms by a receptionist
    public boolean addNewBooking(int userID, LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuests, List<Integer> roomIDs) {
        int bookingID = bookingDataBaseAccess.getNextBookingID(); // Get next available booking ID
        String paymentStatus = "pending";
        String reservationStatus = "pending";

        // 1. Create a new booking
        boolean isBookingCreated = bookingDataBaseAccess.create(new Booking(bookingID, userID, checkInDate, checkOutDate, numberOfGuests, paymentStatus, reservationStatus));

        if (isBookingCreated) {
            boolean allRoomsBooked = true;
            // 2. Link the booking to multiple rooms via BookedRooms
            for (int roomID : roomIDs) {
                boolean isRoomBooked = bookedRoomsDataBaseAccess.create(new BookedRooms(bookingID, roomID));
                if (!isRoomBooked) {
                    allRoomsBooked = false;
                    break;
                }
            }

            if (allRoomsBooked) {
                return true; // Booking was successful
            } else {
                // Rollback the booking if at least one room could not be linked
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

    public boolean assignHouseKeepingTask(int roomID, int assignedTo, LocalDate scheduledDate, String taskStatus) {
        int housekeeperID = housekeepingTaskDataBaseAccess.getNextTaskID();
        HousekeepingTask housekeeping = new HousekeepingTask(housekeeperID, roomID, assignedTo, scheduledDate, taskStatus);
        return housekeepingTaskDataBaseAccess.create(housekeeping);
    }

    public List<HousekeeperTask> viewAllHouseKeepersRecordsAndAvailability() {
        return housekeepingTaskDataBaseAccess.getHousekeeperTaskDetails();
    }

    public BigDecimal getTotalPayment(int bookingID) {
        return bookingDataBaseAccess.getTotalPayment(bookingID);
    }

    //payment status for a booking will be paid and related payment will be added to the payment table
    public boolean processPayment(int bookingID) {
        int paymentID = paymentDataBaseAccess.getNextPaymentID();
        Connection connection = null;

        try {
            // 1. Establish a connection and start a transaction
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false); // Disable auto-commit

            // 2. Calculate the total payment for the booking
            BigDecimal totalPayment = bookingDataBaseAccess.getTotalPayment(bookingID);
            if (totalPayment.compareTo(BigDecimal.ZERO) == 0) {
                connection.rollback();
                return false; // No payment due, so rollback and return false
            }

            // 3. Insert the payment into the Payment table
            Payment payment = new Payment(paymentID, bookingID, totalPayment, LocalDate.now());
            boolean isPaymentInserted = paymentDataBaseAccess.create(payment, connection);

            if (!isPaymentInserted) {
                connection.rollback();
                return false; // Rollback if payment insertion failed
            }

            // 4. Update the booking's paymentStatus to 'paid'
            boolean isBookingUpdated = bookingDataBaseAccess.updatePaymentStatus(bookingID, "paid", connection);

            if (!isBookingUpdated) {
                connection.rollback();
                return false; // Rollback if booking update failed
            }

            // 5. Commit the transaction since all steps were successful
            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) connection.rollback(); // Rollback if any error occurs
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            return false;

        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true); // Reset auto-commit to default
                    connection.close(); // Close the connection
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



}
