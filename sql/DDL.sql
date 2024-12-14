CREATE TABLE Hotel (
    hotelID INT AUTO_INCREMENT PRIMARY KEY,
    hotelName VARCHAR(50) NOT NULL,
    location VARCHAR(50),
    contactNumber VARCHAR(20)
);

CREATE TABLE Users (
    userID INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) NOT NULL,
    userpassword VARCHAR(20) NOT NULL,
    userType VARCHAR(20) NOT NULL,
    contactDetails VARCHAR(100),
    CHECK (userType IN ('guest', 'administrator', 'receptionist', 'housekeeping', 'db_admin'))
);

CREATE TABLE Room (
    roomID INT AUTO_INCREMENT PRIMARY KEY,
    roomTypeID INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    roomStatus VARCHAR(20) NOT NULL,
    hotelID INT NOT NULL,
    FOREIGN KEY (roomTypeID) REFERENCES RoomType(roomTypeID)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (hotelID) REFERENCES Hotel(hotelID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CHECK (roomStatus IN ('available', 'booked', 'cleaning')),
    INDEX hotelID
);

CREATE TABLE RoomType (
    roomTypeID INT AUTO_INCREMENT PRIMARY KEY,
    roomTypeName VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Booking (
    bookingID INT AUTO_INCREMENT PRIMARY KEY,
    userID INT NOT NULL,
    checkInDate DATE NOT NULL,
    checkOutDate DATE NOT NULL,
    numberOfGuests INT NOT NULL,
    paymentStatus VARCHAR(20) NOT NULL,
    reservationStatus VARCHAR(20) NOT NULL,
    FOREIGN KEY (userID) REFERENCES Users(userID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CHECK (paymentStatus IN ('pending', 'paid')),
    CHECK (reservationStatus IN ('confirmed', 'cancelled', 'pending', 'checked-in', 'checked-out')),
    INDEX userID
);

CREATE TABLE BookedRooms (
    bookingID INT NOT NULL,
    roomID INT NOT NULL,
    PRIMARY KEY (bookingID, roomID),
    FOREIGN KEY (bookingID) REFERENCES Booking(bookingID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (roomID) REFERENCES Room(roomID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX (bookingID),
    INDEX (roomID)
);

CREATE TABLE Payment (
    paymentID INT AUTO_INCREMENT PRIMARY KEY,
    bookingID INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    paymentDate DATE NOT NULL,
    FOREIGN KEY (bookingID) REFERENCES Booking(bookingID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX (bookingID)
);

CREATE TABLE HousekeepingSchedule (
    taskID INT AUTO_INCREMENT PRIMARY KEY,
    roomID INT NOT NULL,
    assignedTo INT,
    scheduledDate DATE NOT NULL,
    taskStatus VARCHAR(20) NOT NULL,
    FOREIGN KEY (roomID) REFERENCES Room(roomID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (assignedTo) REFERENCES Users(userID)
        ON DELETE SET NULL ON UPDATE CASCADE,
    CHECK (taskStatus IN ('pending', 'completed')),
    INDEX (roomID),
    INDEX (assignedTo)
);

CREATE TABLE Employee (
    employeeID INT AUTO_INCREMENT PRIMARY KEY,
    ename VARCHAR(50) NOT NULL,
    erole VARCHAR(20) NOT NULL,
    hotelID INT NOT NULL,
    contactDetails VARCHAR(100),
    FOREIGN KEY (hotelID) REFERENCES Hotel(hotelID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CHECK (erole IN ('administrator', 'receptionist', 'housekeeping')),
    INDEX (hotelID),
    INDEX (erole)
);



