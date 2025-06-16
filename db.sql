CREATE DATABASE IF NOT EXISTS GOATBOOKING CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE GOATBOOKING;

-- Bảng admin
CREATE TABLE Admins (
	admin_id BIGINT AUTO_INCREMENT PRIMARY KEY,
	user_name VARCHAR(255) NOT NULL,
    pass_word VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

-- Bảng Users
CREATE TABLE Users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(255) NOT NULL UNIQUE,
    gender INT,
    full_name VARCHAR(255) NOT NULL,
    avatar VARCHAR(255),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX (user_id)
) ENGINE=InnoDB;

-- Bảng Bookings
CREATE TABLE Bookings (
    booking_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    check_in_date DATETIME NOT NULL,
    check_out_date DATETIME NOT NULL,
    deposit_price DECIMAL(10,2),
    total_price DECIMAL(10,2),
    status INT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    INDEX (user_id)
) ENGINE=InnoDB;

-- Bảng Homestays
CREATE TABLE Homestays (
    homestay_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    ward VARCHAR(255),
    district VARCHAR(255),
    province VARCHAR(255),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    INDEX (user_id)
) ENGINE=InnoDB;

-- Bảng Rooms
CREATE TABLE Rooms (
    room_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_name VARCHAR(255) NOT NULL,
    room_type INT NOT NULL,
    price DECIMAL(10,2),
    status INT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    homestay_id BIGINT NOT NULL,
    booking_id BIGINT,
    FOREIGN KEY (homestay_id) REFERENCES Homestays(homestay_id),
    FOREIGN KEY (booking_id) REFERENCES Bookings(booking_id),
    INDEX (homestay_id),
    INDEX (booking_id)
) ENGINE=InnoDB;

-- Bảng Reviews
CREATE TABLE Reviews (
    review_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rate INT,
    comment TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    homestay_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (homestay_id) REFERENCES Homestays(homestay_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    INDEX (homestay_id),
    INDEX (user_id)
) ENGINE=InnoDB;

-- Bảng Photos
CREATE TABLE Photos (
    photo_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name_photo VARCHAR(255),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    homestay_id BIGINT NOT NULL,
    FOREIGN KEY (homestay_id) REFERENCES Homestays(homestay_id),
    INDEX (homestay_id)
) ENGINE=InnoDB;

-- Bảng Services
CREATE TABLE Services (
    service_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_name VARCHAR(255),
    description TEXT,
    price DECIMAL(10,2),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Bảng Services_advantage
CREATE TABLE Services_advantage (
    homestay_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY (homestay_id, service_id),
    FOREIGN KEY (homestay_id) REFERENCES Homestays(homestay_id),
    FOREIGN KEY (service_id) REFERENCES Services(service_id),
    INDEX (homestay_id),
    INDEX (service_id)
) ENGINE=InnoDB;

CREATE TABLE booking_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    room_name VARCHAR(255),
    address VARCHAR(500),
    check_in_date DATETIME,
    total_price DECIMAL(38, 2),
    deposit_price DECIMAL(38, 2),
    created_at DATETIME
);

    
SELECT * FROM Users;
SELECT * FROM Bookings;
SELECT * FROM Homestays;
SELECT * FROM Rooms;
SELECT * FROM Reviews;
SELECT * FROM Photos;
SELECT * FROM Services;
SELECT * FROM Services_advantage; 
SELECT * FROM Admins;
