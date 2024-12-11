CREATE TABLE Hotel (
    hotelID INT PRIMARY KEY,
    hotelName VARCHAR(50) NOT NULL,
    location VARCHAR(50),
    contactNumber VARCHAR(20)
);

CREATE TABLE Users (
    userID INT PRIMARY KEY,
    username VARCHAR(20) NOT NULL,
    userpassword VARCHAR(20) NOT NULL,
    userType VARCHAR(20) NOT NULL,
    contactDetails VARCHAR(100),
    CHECK (userType IN ('guest', 'administrator', 'receptionist', 'housekeeping', 'db_admin'))
);

CREATE TABLE Room (
    roomID INT PRIMARY KEY,
    roomType VARCHAR(20) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    roomStatus VARCHAR(20) NOT NULL,
    hotelID INT NOT NULL,
    FOREIGN KEY (hotelID) REFERENCES Hotel(hotelID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CHECK (roomType IN ('single', 'double', 'family')), 
    CHECK (roomStatus IN ('available', 'booked', 'cleaning'))
);

CREATE TABLE Booking (
    bookingID INT PRIMARY KEY,
    userID INT NOT NULL,
    checkInDate DATE NOT NULL,
    checkOutDate DATE NOT NULL,
    numberOfGuests INT NOT NULL,
    paymentStatus VARCHAR(20) NOT NULL,
    reservationStatus VARCHAR(20) NOT NULL,
    FOREIGN KEY (userID) REFERENCES Users(userID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CHECK (paymentStatus IN ('pending', 'paid')),
    CHECK (reservationStatus IN ('confirmed', 'cancelled', 'pending', 'checked-in', 'checked-out'))
);

CREATE TABLE BookedRooms (
    bookingID INT NOT NULL,
    roomID INT NOT NULL,
    PRIMARY KEY (bookingID, roomID),
    FOREIGN KEY (bookingID) REFERENCES Booking(bookingID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (roomID) REFERENCES Room(roomID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Payment (
    paymentID INT PRIMARY KEY,
    bookingID INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    paymentDate DATE NOT NULL,
    FOREIGN KEY (bookingID) REFERENCES Booking(bookingID)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE HousekeepingSchedule (
    taskID INT PRIMARY KEY,
    roomID INT NOT NULL,
    assignedTo INT,
    scheduledDate DATE NOT NULL,
    taskStatus VARCHAR(20) NOT NULL,
    FOREIGN KEY (roomID) REFERENCES Room(roomID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (assignedTo) REFERENCES Users(userID)
        ON DELETE SET NULL ON UPDATE CASCADE,
    CHECK (taskStatus IN ('pending', 'completed'))
);

CREATE TABLE Employee (
    employeeID INT PRIMARY KEY,
    ename VARCHAR(50) NOT NULL,
    erole VARCHAR(20) NOT NULL,
    hotelID INT NOT NULL,
    contactDetails VARCHAR(100),
    FOREIGN KEY (hotelID) REFERENCES Hotel(hotelID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CHECK (erole IN ('administrator', 'receptionist', 'housekeeping'))
);



