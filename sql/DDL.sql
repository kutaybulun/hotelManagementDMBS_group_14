-- Create Address Table
CREATE TABLE Address (
    addressID INT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL
);

-- Update Hotel Table to reference Address
CREATE TABLE Hotel (
    hotelID INT AUTO_INCREMENT PRIMARY KEY,
    hotelName VARCHAR(50) NOT NULL,
    addressID INT NOT NULL,
    contactNumber VARCHAR(20),
    FOREIGN KEY (addressID) REFERENCES Address(addressID)
       ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE Users (
    userID INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE,
    userpassword VARCHAR(20) NOT NULL,
    userType VARCHAR(20) NOT NULL,
    contactDetails VARCHAR(100),
    CHECK (userType IN ('guest', 'administrator', 'receptionist', 'housekeeping', 'db_admin'))
);

CREATE TABLE RoomType (
    roomTypeID INT AUTO_INCREMENT PRIMARY KEY,
    roomTypeName VARCHAR(50) NOT NULL UNIQUE
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
    CHECK (roomStatus IN ('available', 'booked', 'cleaning'))
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
    paymentID INT AUTO_INCREMENT PRIMARY KEY,
    bookingID INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    paymentDate DATE NOT NULL,
    FOREIGN KEY (bookingID) REFERENCES Booking(bookingID)
        ON DELETE CASCADE ON UPDATE CASCADE
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
    CHECK (taskStatus IN ('pending', 'completed'))
);


CREATE TABLE EmployeeRole (
    roleID INT AUTO_INCREMENT PRIMARY KEY,
    roleName VARCHAR(50) NOT NULL UNIQUE,
    dailySalary DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Employee (
    employeeID INT AUTO_INCREMENT PRIMARY KEY,
    ename VARCHAR(50) NOT NULL,
    userID INT NOT NULL UNIQUE, -- Ensuring an employee is linked to one user account
    roleID INT NOT NULL,
    hotelID INT NOT NULL,
    contactDetails VARCHAR(100),
    FOREIGN KEY (roleID) REFERENCES EmployeeRole(roleID)
      ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (hotelID) REFERENCES Hotel(hotelID)
      ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (userID) REFERENCES Users(userID)
      ON DELETE CASCADE ON UPDATE CASCADE
    );


