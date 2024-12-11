INSERT INTO Hotel (hotelID, hotelName, location, contactNumber) VALUES
(1, 'Efe Hotel', 'Atasehir', '216-1001'),
(2, 'Kutay Hotel', 'Adana', '333-2121');

INSERT INTO Users (userID, userName, userpassword, userType, contactDetails) VALUES
(1, 'admin_h1', 'adminpass1', 'administrator', 'admin1@hotel1.com'),
(2, 'recept_h1', 'receptpass1', 'receptionist', 'recept1@hotel1.com'),
(3, 'house1_h1', 'housepass1', 'housekeeping', 'house1@hotel1.com'),
(4, 'house2_h1', 'housepass2', 'housekeeping', 'house2@hotel1.com'),
(5, 'house3_h1', 'housepass3', 'housekeeping', 'house3@hotel1.com'),
(6, 'house4_h1', 'housepass4', 'housekeeping', 'house4@hotel1.com'),
(7, 'house5_h1', 'housepass5', 'housekeeping', 'house5@hotel1.com'),
(8, 'house6_h1', 'housepass6', 'housekeeping', 'house6@hotel1.com'),
(9, 'house7_h1', 'housepass7', 'housekeeping', 'house7@hotel1.com'),
(10, 'house8_h1', 'housepass8', 'housekeeping', 'house8@hotel1.com');

INSERT INTO Users (userID, userName, userpassword, userType, contactDetails) VALUES
(41, 'guest1_h1', 'guestpass1', 'guest', 'guest1@hotel1.com'),
(42, 'guest2_h1', 'guestpass2', 'guest', 'guest2@hotel1.com'),
(43, 'guest3_h1', 'guestpass3', 'guest', 'guest3@hotel1.com'),
(44, 'guest4_h1', 'guestpass4', 'guest', 'guest4@hotel1.com'),
(45, 'guest5_h1', 'guestpass5', 'guest', 'guest5@hotel1.com'),
(46, 'guest6_h1', 'guestpass6', 'guest', 'guest6@hotel1.com'),
(47, 'guest7_h1', 'guestpass7', 'guest', 'guest7@hotel1.com'),
(48, 'guest8_h1', 'guestpass8', 'guest', 'guest8@hotel1.com'),
(49, 'guest9_h1', 'guestpass9', 'guest', 'guest9@hotel1.com'),
(50, 'guest10_h1', 'guestpass10', 'guest', 'guest10@hotel1.com'),
(51, 'guest11_h1', 'guestpass11', 'guest', 'guest11@hotel1.com'),
(52, 'guest12_h1', 'guestpass12', 'guest', 'guest12@hotel1.com');

INSERT INTO Employee (employeeID, ename, erole, hotelID, contactDetails) VALUES
(1, 'Admin H1', 'administrator', 1, 'admin1@hotel1.com'),
(2, 'Receptionist H1', 'receptionist', 1, 'recept1@hotel1.com'),
(3, 'Housekeeper 1 H1', 'housekeeping', 1, 'house1@hotel1.com'),
(4, 'Housekeeper 2 H1', 'housekeeping', 1, 'house2@hotel1.com'),
(5, 'Housekeeper 3 H1', 'housekeeping', 1, 'house3@hotel1.com'),
(6, 'Housekeeper 4 H1', 'housekeeping', 1, 'house4@hotel1.com'),
(7, 'Housekeeper 5 H1', 'housekeeping', 1, 'house5@hotel1.com'),
(8, 'Housekeeper 6 H1', 'housekeeping', 1, 'house6@hotel1.com'),
(9, 'Housekeeper 7 H1', 'housekeeping', 1, 'house7@hotel1.com'),
(10, 'Housekeeper 8 H1', 'housekeeping', 1, 'house8@hotel1.com');

SET @current_user = 1;
INSERT INTO Room (roomID, roomType, price, roomStatus, hotelID) VALUES
(101, 'single', 100.00, 'booked', 1),
(102, 'double', 150.00, 'booked', 1),
(103, 'family', 200.00, 'booked', 1),
(104, 'single', 110.00, 'available', 1),
(105, 'double', 160.00, 'available', 1),
(106, 'family', 210.00, 'available', 1),
(107, 'single', 120.00, 'available', 1),
(108, 'double', 170.00, 'available', 1),
(109, 'family', 220.00, 'available', 1),
(110, 'single', 130.00, 'available', 1),
(111, 'double', 180.00, 'available', 1),
(112, 'family', 230.00, 'available', 1),
(113, 'single', 140.00, 'available', 1),
(114, 'double', 190.00, 'available', 1),
(115, 'family', 240.00, 'available', 1);

INSERT INTO Booking (bookingID, userID, checkInDate, checkOutDate, numberOfGuests, paymentStatus, reservationStatus) VALUES
(4, 41, '2024-12-15', '2024-12-20', 2, 'pending', 'pending'),
(5, 42, '2024-12-22', '2024-12-25', 1, 'pending', 'pending'),
(6, 43, '2024-12-27', '2024-12-30', 2, 'paid', 'confirmed');

INSERT INTO BookedRooms (bookingID, roomID) VALUES
(4, 104),
(5, 105),
(6, 106);

INSERT INTO Payment (paymentID, bookingID, amount, paymentDate) VALUES
(4, 4, 500.00, '2024-12-10'),
(5, 5, 300.00, '2024-12-15'),
(6, 6, 450.00, '2024-12-20');

INSERT INTO HousekeepingSchedule (taskID, roomID, assignedTo, scheduledDate, taskStatus) VALUES
(4, 104, 3, '2024-12-21', 'pending'),
(5, 105, 4, '2024-12-26', 'pending'),
(6, 106, 5, '2024-12-31', 'pending');

-- Following are DML triggers mentioned in the report

DELIMITER $$

CREATE TRIGGER prevent_double_booking
BEFORE INSERT ON BookedRooms
FOR EACH ROW
BEGIN
    DECLARE overlap_count INT;
    
    SELECT COUNT(*)
    INTO overlap_count
    FROM BookedRooms BR
    JOIN Booking B ON BR.bookingID = B.bookingID
    WHERE BR.roomID = NEW.roomID
      AND (
        B.checkInDate <= (SELECT checkOutDate FROM Booking WHERE bookingID = NEW.bookingID)
        AND B.checkOutDate >= (SELECT checkInDate FROM Booking WHERE bookingID = NEW.bookingID)
      );
    
    IF overlap_count > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot book for overlapping dates';
    END IF;
END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER prevent_past_booking
BEFORE INSERT ON Booking
FOR EACH ROW
BEGIN
    IF NEW.checkInDate < CURDATE() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Check-in date cannot be in the past.';
    END IF;
END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER update_room_status_on_booking
AFTER INSERT ON BookedRooms
FOR EACH ROW
BEGIN
    UPDATE Room
    SET roomStatus = 'booked'
    WHERE roomID = NEW.roomID;
END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER update_room_status_on_booking_delete
AFTER DELETE ON BookedRooms
FOR EACH ROW
BEGIN
    UPDATE Room
    SET roomStatus = 'available'
    WHERE roomID = OLD.roomID;
END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER mark_task_completed
AFTER UPDATE ON HousekeepingSchedule
FOR EACH ROW
BEGIN
    IF NEW.taskStatus = 'completed' THEN
        UPDATE Room
        SET roomStatus = 'available'
        WHERE roomID = NEW.roomID;
    END IF;
END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER restrict_room_modification
BEFORE INSERT ON Room
FOR EACH ROW
BEGIN
    DECLARE user_type VARCHAR(20);
    
    SELECT userType INTO user_type
    FROM Users
    WHERE userID = @current_user;
    
    IF user_type != 'administrator' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Access denied! Only administrators can add rooms.';
    END IF;
END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER restrict_room_deletion
BEFORE DELETE ON Room
FOR EACH ROW
BEGIN
    DECLARE user_type VARCHAR(20);

    SELECT userType INTO user_type
    FROM Users
    WHERE userID = @current_user;
    
    IF user_type != 'administrator' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Access denied! Only administrators can delete rooms.';
    END IF;
END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER restrict_booking_confirmation
BEFORE UPDATE ON Booking
FOR EACH ROW
BEGIN
    DECLARE user_type VARCHAR(20);

    SELECT userType INTO user_type
    FROM Users
    WHERE userID = @current_user;

    IF user_type != 'receptionist' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Access denied! Only receptionists can confirm bookings.';
    END IF;
END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER restrict_booking_cancellation
BEFORE DELETE ON Booking
FOR EACH ROW
BEGIN
    DECLARE user_type VARCHAR(20);

    SELECT userType INTO user_type
    FROM Users
    WHERE userID = @current_user;

    IF user_type != 'administrator' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Access denied! Only administrators can cancel bookings.';
    END IF;

    IF OLD.paymentStatus = 'paid' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot cancel a booking with completed payment.';
    END IF;
END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER restrict_task_assignment
BEFORE INSERT ON HousekeepingSchedule
FOR EACH ROW
BEGIN
    DECLARE user_type VARCHAR(20);

    SELECT userType INTO user_type
    FROM Users
    WHERE userID = @current_user;

    IF user_type != 'receptionist' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Access denied! Only receptionists can assign housekeeping tasks.';
    END IF;
END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER prevent_unauthorized_check_in
BEFORE UPDATE ON Booking
FOR EACH ROW
BEGIN
    IF NEW.reservationStatus = 'checked-in' THEN
        IF OLD.reservationStatus != 'confirmed' THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Unable to check in without confirmation.';
        END IF;
    END IF;
END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER prevent_unauthorized_check_out
BEFORE UPDATE ON Booking
FOR EACH ROW
BEGIN
    IF NEW.reservationStatus = 'checked-out' THEN
        IF OLD.paymentStatus != 'paid' THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Unable to check out without payment.';
        END IF;
    END IF;
END $$

DELIMITER ;

-- Following are DML queries mentioned in the report, they will be either used in the java side while implementing the application or for testing purposes here

-- Query to check room availability for specific range for guests:
SELECT roomID, roomType, price
FROM Room
WHERE roomStatus = 'available'
  AND roomID NOT IN (
    SELECT roomID
    FROM BookedRooms BR
    JOIN Booking B ON BR.bookingID = B.bookingID
    WHERE B.checkInDate < @desiredCheckOutDate
      AND B.checkOutDate > @desiredCheckInDate
  );
SET @current_user = 41;
-- Query for guests to view their bookings:
SELECT B.bookingID, B.checkInDate, B.checkOutDate, B.numberOfGuests, BR.roomID, B.paymentStatus, B.reservationStatus
FROM Booking B
LEFT JOIN BookedRooms BR ON B.bookingID = BR.bookingID
WHERE B.userID = @current_user;

-- Query for guests to cancel their reservation:

UPDATE Booking
SET reservationStatus = 'cancelled'
WHERE bookingID = @bookingID
  AND userID = @current_user
  AND reservationStatus = 'pending'
  AND paymentStatus = 'pending';

-- Query for administrator to generate revenue report:
SELECT SUM(amount) AS total_revenue
FROM Payment;

-- Query for administrator to see most booked room type:
SELECT roomType, COUNT(*) AS booking_count
FROM Room R
JOIN BookedRooms BR ON R.roomID = BR.roomID
GROUP BY roomType
ORDER BY booking_count DESC;

-- Query for administrator to see all booking records:
SELECT 
    B.bookingID,
    U.userName AS guestName,
    B.checkInDate,
    B.checkOutDate,
    B.numberOfGuests,
    B.paymentStatus,
    B.reservationStatus,
    GROUP_CONCAT(R.roomID SEPARATOR ', ') AS bookedRooms
FROM 
    Booking B
LEFT JOIN 
    Users U ON B.userID = U.userID
LEFT JOIN 
    BookedRooms BR ON B.bookingID = BR.bookingID
LEFT JOIN 
    Room R ON BR.roomID = R.roomID
GROUP BY 
    B.bookingID;

-- Query for administrator to see all housekeeping records:
SELECT 
    HS.taskID,
    R.roomID,
    R.roomType,
    HS.scheduledDate,
    U.userName AS assignedTo,
    HS.taskStatus
FROM 
    HousekeepingSchedule HS
LEFT JOIN 
    Room R ON HS.roomID = R.roomID
LEFT JOIN 
    Users U ON HS.assignedTo = U.userID;

-- Query for administrator to see all employees with their roles:
SELECT 
    E.employeeID,
    E.ename,
    E.erole,
    H.hotelName AS associatedHotel,
    E.contactDetails
FROM 
    Employee E
LEFT JOIN 
    Hotel H ON E.hotelID = H.hotelID;

-- Query for receptionists to list room availability:
SELECT roomID, roomType, price
FROM Room
WHERE roomStatus = 'available';

-- Query for receptionist to see booking requested by the Guests:
SELECT B.bookingID, B.userID, B.checkInDate, B.checkOutDate, B.numberOfGuests, BR.roomID
FROM Booking B
JOIN BookedRooms BR ON B.bookingID = BR.bookingID
WHERE B.reservationStatus = 'pending';

-- Query for receptionists to see all housekeeper records and their availability:
SELECT 
    U.userID AS housekeeperID,
    U.userName AS housekeeperName,
    COUNT(HS.taskID) AS pendingTasks,
    CASE 
        WHEN COUNT(HS.taskID) = 0 THEN 'Available'
        ELSE 'Busy'
    END AS availabilityStatus
FROM 
    Users U
LEFT JOIN 
    HousekeepingSchedule HS ON U.userID = HS.assignedTo AND HS.taskStatus = 'pending'
WHERE 
    U.userType = 'housekeeping'
GROUP BY 
    U.userID, U.userName;

-- Query such that housekeepers can only view room availability but nothing about Guest information:
SELECT roomID, roomType, roomStatus
FROM Room
WHERE roomStatus = 'available';
SET @current_user = 3;
-- Query for housekeepers to view their cleaning schedule:
SELECT taskID, roomID, scheduledDate, taskStatus
FROM HousekeepingSchedule
WHERE assignedTo = @current_user;

-- Query for housekeepers to view their pending housekeeping tasks:
SELECT 
    HS.taskID,
    R.roomID,
    R.roomType,
    HS.scheduledDate,
    HS.taskStatus
FROM 
    HousekeepingSchedule HS
LEFT JOIN 
    Room R ON HS.roomID = R.roomID
WHERE 
    HS.assignedTo = @current_user
    AND HS.taskStatus = 'pending';

-- Query for housekeepers to view their completed tasks:
SELECT 
    HS.taskID,
    R.roomID,
    R.roomType,
    HS.scheduledDate,
    HS.taskStatus
FROM 
    HousekeepingSchedule HS
LEFT JOIN 
    Room R ON HS.roomID = R.roomID
WHERE 
    HS.assignedTo = @current_user
    AND HS.taskStatus = 'completed';
