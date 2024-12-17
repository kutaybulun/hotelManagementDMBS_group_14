INSERT INTO Address (street, city, state) VALUES
('123 Main St', 'Istanbul', 'Istanbul'),
('45 Elm St', 'Adana', 'Adana'),
('789 Oak St', 'Ankara', 'Ankara'),
('101 Pine St', 'Izmir', 'Izmir'),
('55 Maple Ave', 'Antalya', 'Antalya');

INSERT INTO Hotel (hotelName, addressID, contactNumber) VALUES
('Efe Hotel', 1, '555-1234'),
('Kutay Hotel', 2, '555-5678'),
('Sunrise Hotel', 3, '555-9101'),
('Blue Lagoon Hotel', 4, '555-1112'),
('Golden Sand Hotel', 5, '555-2223');

INSERT INTO EmployeeRole (roleName, dailySalary) VALUES
('administrator', 250.00),
('receptionist', 150.00),
('housekeeping', 100.00),
('db_admin', 300.00);

INSERT INTO Users (username, userpassword, userType, contactDetails) VALUES
('admin_h1', 'adminpass1', 'administrator', 'admin1@hotel1.com'),
('recept_h1', 'receptpass1', 'receptionist', 'recept1@hotel1.com'),
('house1_h1', 'housepass1', 'housekeeping', 'house1@hotel1.com'),
('house1_h2', 'housepass2', 'housekeeping', 'house1@hotel2.com'),
('house1_h3', 'housepass3', 'housekeeping', 'house1@hotel3.com'),
('guest1_h1', 'guestpass1', 'guest', 'guest1@hotel1.com'),
('guest2_h1', 'guestpass2', 'guest', 'guest2@hotel2.com'),
('db_admin1', 'dbadminpass1', 'db_admin', 'dbadmin1@hotel1.com');

INSERT INTO Employee (ename, userID, roleID, hotelID, contactDetails) VALUES
('Admin 1', 1, (SELECT roleID FROM EmployeeRole WHERE roleName = 'administrator'), 1, 'admin1@hotel1.com'),
('Receptionist 1', 2, (SELECT roleID FROM EmployeeRole WHERE roleName = 'receptionist'), 1, 'recept1@hotel1.com'),
('Housekeeper 1', 3, (SELECT roleID FROM EmployeeRole WHERE roleName = 'housekeeping'), 1, 'house1@hotel1.com'),
('Housekeeper 2', 4, (SELECT roleID FROM EmployeeRole WHERE roleName = 'housekeeping'), 2, 'house1@hotel2.com'),
('Housekeeper 3', 5, (SELECT roleID FROM EmployeeRole WHERE roleName = 'housekeeping'), 3, 'house1@hotel3.com'),
('DB Admin', 8, (SELECT roleID FROM EmployeeRole WHERE roleName = 'db_admin'), 1, 'dbadmin1@hotel1.com');

INSERT INTO RoomType (roomTypeName) VALUES
('single'),
('double'),
('family'),
('suite');


INSERT INTO Room (roomTypeID, price, roomStatus, hotelID) VALUES
( (SELECT roomTypeID FROM RoomType WHERE roomTypeName = 'single'), 100.00, 'available', 1),
( (SELECT roomTypeID FROM RoomType WHERE roomTypeName = 'double'), 150.00, 'available', 1),
( (SELECT roomTypeID FROM RoomType WHERE roomTypeName = 'family'), 200.00, 'available', 1),
( (SELECT roomTypeID FROM RoomType WHERE roomTypeName = 'suite'), 500.00, 'available', 2),
( (SELECT roomTypeID FROM RoomType WHERE roomTypeName = 'double'), 175.00, 'booked', 2),
( (SELECT roomTypeID FROM RoomType WHERE roomTypeName = 'single'), 120.00, 'available', 3);

INSERT INTO Booking (userID, checkInDate, checkOutDate, numberOfGuests, paymentStatus, reservationStatus) VALUES
( 6, '2024-12-20', '2024-12-25', 2, 'pending', 'confirmed'),
( 7, '2024-12-18', '2024-12-22', 1, 'pending', 'pending'),
( 6, '2024-12-26', '2024-12-30', 1, 'paid', 'checked-out');

INSERT INTO BookedRooms (bookingID, roomID) VALUES
(1, 1),
(1, 2),
(2, 4),
(3, 3);

INSERT INTO Payment (bookingID, amount, paymentDate) VALUES
(3, 500.00, '2024-12-30');

INSERT INTO HousekeepingSchedule (roomID, assignedTo, scheduledDate, taskStatus) VALUES
(1, 3, '2024-12-26', 'pending'),
(2, 3, '2024-12-27', 'completed'),
(4, 4, '2024-12-28', 'pending'),
(5, 5, '2024-12-29', 'pending');


--------------------- TRIGGER------------------------------------
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
--------------------- TRIGGER------------------------------------
DELIMITER //
-- insert automatically to employee if user is an employee
CREATE TRIGGER after_user_insert
    AFTER INSERT ON Users
    FOR EACH ROW
BEGIN
    -- Declare the role_id variable
    DECLARE role_id INT;

    -- Check if the new user's type is 'administrator', 'receptionist', or 'housekeeping'
    IF NEW.userType IN ('administrator', 'receptionist', 'housekeeping') THEN

        -- Get the roleID from EmployeeRole table for the inserted userType
    SELECT roleID INTO role_id
    FROM EmployeeRole
    WHERE roleName = NEW.userType
        LIMIT 1;

    -- If roleID is found, insert into Employee table
    IF role_id IS NOT NULL THEN
            INSERT INTO Employee (ename, userID, roleID, hotelID, contactDetails)
            VALUES (NEW.username, NEW.userID, role_id, 1, NEW.contactDetails);
END IF;

END IF;

END;
//
DELIMITER ;
--------------------- TRIGGER------------------------------------
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

--------------------- TRIGGER------------------------------------
DELIMITER $$

CREATE TRIGGER update_room_status_on_booking_cancel
    AFTER UPDATE ON Booking
    FOR EACH ROW
BEGIN
    -- Check if the reservation status has been changed to 'cancelled'
    IF NEW.reservationStatus = 'cancelled' THEN
        -- Update the status of all the rooms associated with this booking to 'available'
    UPDATE Room
    SET roomStatus = 'available'
    WHERE roomID IN (
        SELECT roomID
        FROM BookedRooms
        WHERE bookingID = NEW.bookingID
    );
END IF;
END $$
--------------------- TRIGGER------------------------------------
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

--------------------- TRIGGER------------------------------------
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

--------------------- TRIGGER------------------------------------
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

--------------------- TRIGGER------------------------------------
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