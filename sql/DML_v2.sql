-- Insert Room Types (unchanged as requested)
INSERT INTO RoomType (roomTypeName) VALUES
    ('single'),
    ('double'),
    ('family');

-- Insert Employee Roles (unchanged as requested)
INSERT INTO EmployeeRole (roleName, dailySalary) VALUES
    ('administrator', 250.00),
    ('receptionist', 150.00),
    ('housekeeping', 100.00);

-- Insert Hotels (using a more systematic approach)
INSERT INTO Hotel (hotelName, location, contactNumber) VALUES
    ('Efe Hotel', 'Atasehir', CONCAT('216-', FLOOR(RAND() * 9000) + 1000)),
    ('Kutay Hotel', 'Adana', CONCAT('333-', FLOOR(RAND() * 9000) + 1000)),
    ('Sunrise Suites', 'Istanbul', CONCAT('212-', FLOOR(RAND() * 9000) + 1000)),
    ('Moonlight Inn', 'Izmir', CONCAT('232-', FLOOR(RAND() * 9000) + 1000));

-- Insert Users (more dynamic and varied data)
INSERT INTO Users (username, userpassword, userType, contactDetails) VALUES
    ('admin_h1', 'adminpass1', 'administrator', 'admin1@hotel1.com'),
    ('recept_h1', 'receptpass1', 'receptionist', 'recept1@hotel1.com'),
    ('house1_h1', 'housepass1', 'housekeeping', 'house1@hotel1.com'),
    ('house2_h1', 'housepass2', 'housekeeping', 'house2@hotel1.com'),
    ('house3_h2', 'housepass3', 'housekeeping', 'house3@hotel2.com'),
    ('guest1_h1', 'guestpass1', 'guest', 'guest1@hotel1.com'),
    ('guest2_h1', 'guestpass2', 'guest', 'guest2@hotel1.com'),
    ('guest1_h2', 'guestpass3', 'guest', 'guest1@hotel2.com'),
    ('guest1_h3', 'guestpass4', 'guest', 'guest1@hotel3.com');

-- Insert Employees (linking to user accounts and employee roles)
INSERT INTO Employee (ename, userID, roleID, hotelID, contactDetails)
VALUES
    ('Efe Admin', (SELECT userID FROM Users WHERE username = 'admin_h1'),
     (SELECT roleID FROM EmployeeRole WHERE roleName = 'administrator'),
     (SELECT hotelID FROM Hotel WHERE hotelName = 'Efe Hotel'),
     'admin1@hotel1.com'),

    ('Kutay Receptionist', (SELECT userID FROM Users WHERE username = 'recept_h1'),
     (SELECT roleID FROM EmployeeRole WHERE roleName = 'receptionist'),
     (SELECT hotelID FROM Hotel WHERE hotelName = 'Kutay Hotel'),
     'recept1@hotel1.com'),

    ('Housekeeper 1', (SELECT userID FROM Users WHERE username = 'house1_h1'),
     (SELECT roleID FROM EmployeeRole WHERE roleName = 'housekeeping'),
     (SELECT hotelID FROM Hotel WHERE hotelName = 'Efe Hotel'),
     'house1@hotel1.com'),

    ('Housekeeper 2', (SELECT userID FROM Users WHERE username = 'house2_h1'),
     (SELECT roleID FROM EmployeeRole WHERE roleName = 'housekeeping'),
     (SELECT hotelID FROM Hotel WHERE hotelName = 'Efe Hotel'),
     'house2@hotel1.com'),

    ('Housekeeper 3', (SELECT userID FROM Users WHERE username = 'house3_h2'),
     (SELECT roleID FROM EmployeeRole WHERE roleName = 'housekeeping'),
     (SELECT hotelID FROM Hotel WHERE hotelName = 'Kutay Hotel'),
     'house3@hotel2.com');

-- Insert Rooms (more dynamic data using subqueries)
INSERT INTO Room (roomTypeID, price, roomStatus, hotelID)
VALUES
    ( (SELECT roomTypeID FROM RoomType WHERE roomTypeName = 'single'),
      ROUND(90 + (RAND() * 30), 2),
      'available',
      (SELECT hotelID FROM Hotel WHERE hotelName = 'Efe Hotel')),

    ( (SELECT roomTypeID FROM RoomType WHERE roomTypeName = 'double'),
      ROUND(120 + (RAND() * 30), 2),
      'available',
      (SELECT hotelID FROM Hotel WHERE hotelName = 'Efe Hotel')),

    ( (SELECT roomTypeID FROM RoomType WHERE roomTypeName = 'family'),
      ROUND(180 + (RAND() * 30), 2),
      'available',
      (SELECT hotelID FROM Hotel WHERE hotelName = 'Efe Hotel')),

    ( (SELECT roomTypeID FROM RoomType WHERE roomTypeName = 'single'),
      ROUND(95 + (RAND() * 25), 2),
      'available',
      (SELECT hotelID FROM Hotel WHERE hotelName = 'Kutay Hotel')),

    ( (SELECT roomTypeID FROM RoomType WHERE roomTypeName = 'double'),
      ROUND(130 + (RAND() * 20), 2),
      'available',
      (SELECT hotelID FROM Hotel WHERE hotelName = 'Kutay Hotel')),

    ( (SELECT roomTypeID FROM RoomType WHERE roomTypeName = 'family'),
      ROUND(220 + (RAND() * 20), 2),
      'available',
      (SELECT hotelID FROM Hotel WHERE hotelName = 'Kutay Hotel')),

    ( (SELECT roomTypeID FROM RoomType WHERE roomTypeName = 'single'),
      ROUND(88 + (RAND() * 12), 2),
      'available',
      (SELECT hotelID FROM Hotel WHERE hotelName = 'Sunrise Suites')),

    ( (SELECT roomTypeID FROM RoomType WHERE roomTypeName = 'double'),
      ROUND(140 + (RAND() * 30), 2),
      'available',
      (SELECT hotelID FROM Hotel WHERE hotelName = 'Sunrise Suites')),

    ( (SELECT roomTypeID FROM RoomType WHERE roomTypeName = 'family'),
      ROUND(190 + (RAND() * 40), 2),
      'available',
      (SELECT hotelID FROM Hotel WHERE hotelName = 'Sunrise Suites')),

    ( (SELECT roomTypeID FROM RoomType WHERE roomTypeName = 'single'),
      ROUND(90 + (RAND() * 20), 2),
      'available',
      (SELECT hotelID FROM Hotel WHERE hotelName = 'Moonlight Inn')),

    ( (SELECT roomTypeID FROM RoomType WHERE roomTypeName = 'double'),
      ROUND(150 + (RAND() * 25), 2),
      'available',
      (SELECT hotelID FROM Hotel WHERE hotelName = 'Moonlight Inn')),

    ( (SELECT roomTypeID FROM RoomType WHERE roomTypeName = 'family'),
      ROUND(210 + (RAND() * 30), 2),
      'available',
      (SELECT hotelID FROM Hotel WHERE hotelName = 'Moonlight Inn'));


-- Insert Booking Records
INSERT INTO Booking (userID, checkInDate, checkOutDate, numberOfGuests, paymentStatus, reservationStatus)
VALUES
    (1, '2024-12-15', '2024-12-20', 2, 'pending', 'pending'),
    (2, '2024-12-22', '2024-12-25', 1, 'pending', 'pending');

-- Insert Booked Rooms
INSERT INTO BookedRooms (bookingID, roomID)
VALUES
    (1, 1),
    (2, 2);

-- Insert Payment Records
INSERT INTO Payment (bookingID, amount, paymentDate)
VALUES
    (1, 500.00, '2024-12-10'),
    (2, 300.00, '2024-12-15');

-- Insert Housekeeping Schedule
INSERT INTO HousekeepingSchedule (roomID, assignedTo, scheduledDate, taskStatus)
VALUES
    (1, 2, '2024-12-21', 'pending'),
    (2, 3, '2024-12-26', 'pending');

-- Insert Employee Records
INSERT INTO Employee (ename, erole, hotelID, contactDetails)
VALUES
    ('Admin H1', 'administrator', 1, 'admin1@hotel1.com'),
    ('Receptionist H1', 'receptionist', 1, 'recept1@hotel1.com');

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
SELECT RT.roomTypeName, COUNT(*) AS booking_count
FROM Room R
         JOIN BookedRooms BR ON R.roomID = BR.roomID
         JOIN RoomType RT ON R.roomTypeID = RT.roomTypeID
GROUP BY RT.roomTypeName
ORDER BY booking_count DESC;;

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