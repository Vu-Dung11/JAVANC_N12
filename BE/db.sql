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

-- bảng History
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


INSERT INTO Users (user_id, user_name, password, email, phone_number, gender, full_name, avatar, created_at, updated_at)
VALUES 
    (1, 'john_doe', 'password123', 'john.doe@example.com', '1234567890', 1, 'John Doe', 'avatar1.png', 20241001, 20241001),
    (2, 'jane_smith', 'mypassword', 'jane.smith@example.com', '0987654321', 0, 'Jane Smith', 'avatar2.png', 20241002, 20241002),
    (3, 'alice_nguyen', 'alicepass', 'alice.nguyen@example.com', '1122334455', 1, 'Alice Nguyen', 'avatar3.png', 20241003, 20241003),
    (4, 'bob_lee', 'bobsecure', 'bob.lee@example.com', '4433221100', 1, 'Bob Lee', 'avatar4.png', 20241004, 20241004),
    (5, 'sara_connor', 'sarapass', 'sara.connor@example.com', '5544332211', 0, 'Sara Connor', 'avatar5.png', 20241005, 20241005);

INSERT INTO Bookings (booking_id, check_in_date, check_out_date, deposit_price, total_price, status, created_at, updated_at, user_id)
VALUES 
    (1, 20241010, 20241015, 200.00, 1000.00, 1, 20241001, 20241001, 1),
    (2, 20241012, 20241016, 150.00, 800.00, 1, 20241002, 20241002, 2),
    (3, 20241014, 20241018, 100.00, 600.00, 0, 20241003, 20241003, 3),
    (4, 20241016, 20241020, 250.00, 1200.00, 1, 20241004, 20241004, 4),
    (5, 20241018, 20241022, 180.00, 900.00, 0, 20241005, 20241005, 5);


INSERT INTO Homestays (homestay_id, name, description, ward, district, province, created_at, updated_at, user_id)
VALUES 
    (1, 'Homestay Hanoi Central', 'A cozy homestay located in the heart of Hanoi', 'Phuong 7', 'Quan Ba Dinh', 'Ha Noi', 20241001, 20241001, 1),
    (2, 'Saigon Riverside Homestay', 'Beautiful homestay with a view of Saigon River', 'Phuong Binh Thanh', 'Quan 1', 'Ho Chi Minh', 20241002, 20241002, 2),
    (3, 'Homestay Da Nang Beach', 'Relaxing homestay just minutes from the beach', 'Phuong Ngu Hanh Son', 'Quan Ngu Hanh Son', 'Da Nang', 20241003, 20241003, 3),
    (4, 'Ninh Binh Nature Homestay', 'Experience nature and stunning views in Ninh Binh', 'Phuong Tam Coc', 'Huyen Hoa Lu', 'Ninh Binh', 20241004, 20241004, 1),
    (5, 'Sapa Mountain Homestay', 'Beautiful homestay in the mountains of Sapa', 'Phuong Sa Pa', 'Huyen Sa Pa', 'Lao Cai', 20241005, 20241005, 2);
INSERT INTO Rooms (room_id, room_name, room_type, price, status, created_at, updated_at, homestay_id, booking_id)
VALUES 
    (1, 'Deluxe Room', 1, 150.00, 1, 20241015, 20241015, 1, 1),
    (2, 'Standard Room', 2, 100.00, 1, 20241015, 20241015, 1, 2),
    (3, 'Family Room', 1, 200.00, 0, 20241017, 20241017, 2, 3),
    (4, 'Single Room', 2, 80.00, 1, 20241018, 20241018, 3, 4),
    (5, 'Superior Suite', 3, 300.00, 1, 20241019, 20241019, 4, 5);

INSERT INTO Reviews (review_id, rate, comment, created_at, updated_at, homestay_id, user_id)
VALUES 
    (1, 5, 'Wonderful stay! The place was beautiful and very clean.', 20241015, 20241015, 1, 1),
    (2, 4, 'Very nice location, but the room could be bigger.', 20241016, 20241016, 1, 2),
    (3, 3, 'Average experience. The service was okay, but not great.', 20241017, 20241017, 2, 3),
    (4, 5, 'Loved it! Perfect for families, very spacious and clean.', 20241018, 20241018, 3, 4),
    (5, 2, 'Not as expected. The photos were misleading.', 20241019, 20241019, 4, 5);
INSERT INTO Photos (photo_id, name_photo, created_at, updated_at, homestay_id)
VALUES 
    (1, 'Cozy Cottage Living Room', 20241015, 20241015, 1),
    (2, 'Beachside Bungalow View', 20241016, 20241016, 2),
    (3, 'Mountain Retreat Exterior', 20241017, 20241017, 3),
    (4, 'Luxurious Suite with Ocean View', 20241018, 20241018, 4),
    (5, 'Charming Garden Area', 20241019, 20241019, 5);
INSERT INTO Services (service_id, service_name, description, price, created_at, updated_at)
VALUES 
    (1, 'Free WiFi', 'High-speed internet available throughout the homestay.', 0.00, 20241001, 20241001),
    (2, 'Airport Pickup', 'Convenient airport pickup service.', 30.00, 20241002, 20241002),
    (3, 'Breakfast Included', 'Delicious breakfast served every morning.', 10.00, 20241003, 20241003),
    (4, 'Guided Tours', 'Explore the local area with our guided tours.', 50.00, 20241004, 20241004),
    (5, 'Spa Access', 'Relax and enjoy our on-site spa services.', 100.00, 20241005, 20241005);
INSERT INTO Services_advantage (homestay_id, service_id, description)
VALUES 
    (1, 1, 'Enjoy complimentary high-speed WiFi in all areas.'),
    (1, 3, 'Breakfast included in your stay.'),
    (2, 2, 'Airport pickup available upon request.'),
    (2, 4, 'Guided tours can be arranged for your convenience.'),
    (3, 1, 'Free WiFi available for all guests.');



INSERT INTO Users (user_id, user_name, password, email, phone_number, gender, full_name, avatar, created_at, updated_at)
VALUES 
    (6, 'michael_brown', 'passmike', 'michael.brown@example.com', '1234561111', 1, 'Michael Brown', 'avatar6.png', 20241006, 20241006),
    (7, 'lisa_white', 'lisapass', 'lisa.white@example.com', '6543212222', 0, 'Lisa White', 'avatar7.png', 20241007, 20241007),
    (8, 'charles_tan', 'charlessecure', 'charles.tan@example.com', '5556667777', 1, 'Charles Tan', 'avatar8.png', 20241008, 20241008),
    (9, 'nancy_lee', 'nancy123', 'nancy.lee@example.com', '1112223333', 0, 'Nancy Lee', 'avatar9.png', 20241009, 20241009),
    (10, 'george_miller', 'georgepass', 'george.miller@example.com', '9998887777', 1, 'George Miller', 'avatar10.png', 20241010, 20241010),
    (11, 'emma_davis', 'emmapass', 'emma.davis@example.com', '3332221111', 0, 'Emma Davis', 'avatar11.png', 20241011, 20241011),
    (12, 'jack_wilson', 'jacksecure', 'jack.wilson@example.com', '6665554444', 1, 'Jack Wilson', 'avatar12.png', 20241012, 20241012),
    (13, 'anna_thompson', 'annapass', 'anna.thompson@example.com', '7776665555', 0, 'Anna Thompson', 'avatar13.png', 20241013, 20241013),
    (14, 'robert_clark', 'robertsecure', 'robert.clark@example.com', '8887776666', 1, 'Robert Clark', 'avatar14.png', 20241014, 20241014),
    (15, 'sophia_moore', 'sophiapass', 'sophia.moore@example.com', '4443332222', 0, 'Sophia Moore', 'avatar15.png', 20241015, 20241015);

-- Thêm dữ liệu vào bảng Bookings
INSERT INTO Bookings (booking_id, check_in_date, check_out_date, deposit_price, total_price, status, created_at, updated_at, user_id)
VALUES 
    (6, 20241020, 20241025, 220.00, 1100.00, 1, 20241006, 20241006, 6),
    (7, 20241021, 20241026, 180.00, 900.00, 1, 20241007, 20241007, 7),
    (8, 20241022, 20241027, 150.00, 750.00, 0, 20241008, 20241008, 8),
    (9, 20241023, 20241028, 240.00, 1200.00, 1, 20241009, 20241009, 9),
    (10, 20241024, 20241029, 200.00, 1000.00, 1, 20241010, 20241010, 10),
    (11, 20241025, 20241030, 210.00, 1050.00, 1, 20241011, 20241011, 11),
    (12, 20241026, 20241031, 170.00, 850.00, 0, 20241012, 20241012, 12),
    (13, 20241027, 20241101, 190.00, 950.00, 1, 20241013, 20241013, 13),
    (14, 20241028, 20241102, 230.00, 1150.00, 0, 20241014, 20241014, 14),
    (15, 20241029, 20241103, 250.00, 1250.00, 1, 20241015, 20241015, 15);

-- Thêm dữ liệu vào bảng Homestays
INSERT INTO Homestays (homestay_id, name, description, ward, district, province, created_at, updated_at, user_id)
VALUES 
    (6, 'Hue Imperial Homestay', 'Elegant homestay near the ancient city', 'Phuong 5', 'Quan Thanh Pho', 'Hue', 20241006, 20241006, 6),
    (7, 'Hoi An Riverside Homestay', 'Charming homestay by the river', 'Phuong 2', 'Quan Hoi An', 'Quang Nam', 20241007, 20241007, 7),
    (8, 'Phu Quoc Paradise Homestay', 'Tropical retreat with beach access', 'Phuong 3', 'Quan Phu Quoc', 'Kien Giang', 20241008, 20241008, 8),
    (9, 'Can Tho Eco Homestay', 'Eco-friendly homestay in the Mekong Delta', 'Phuong 4', 'Quan Ninh Kieu', 'Can Tho', 20241009, 20241009, 9),
    (10, 'Da Lat Flower Homestay', 'Beautiful homestay surrounded by flowers', 'Phuong 6', 'Quan Da Lat', 'Lam Dong', 20241010, 20241010, 10),
    (11, 'Ha Giang Loop Homestay', 'Homestay with scenic mountain views', 'Phuong 7', 'Huyen Dong Van', 'Ha Giang', 20241011, 20241011, 11),
    (12, 'Vung Tau Coastal Homestay', 'Relaxing homestay with sea views', 'Phuong 8', 'Quan Vung Tau', 'Ba Ria - Vung Tau', 20241012, 20241012, 12),
    (13, 'Con Dao Hideaway Homestay', 'Isolated retreat on Con Dao Island', 'Phuong 9', 'Quan Con Dao', 'Ba Ria - Vung Tau', 20241013, 20241013, 13),
    (14, 'Bac Ninh Heritage Homestay', 'Traditional homestay near pagodas', 'Phuong 10', 'Quan Bac Ninh', 'Bac Ninh', 20241014, 20241014, 14),
    (15, 'Sa Dec Garden Homestay', 'Homestay with beautiful garden views', 'Phuong 11', 'Huyen Sa Dec', 'Dong Thap', 20241015, 20241015, 15);

-- Thêm dữ liệu vào bảng Rooms
INSERT INTO Rooms (room_id, room_name, room_type, price, status, created_at, updated_at, homestay_id, booking_id)
VALUES 
    (6, 'Garden Room', 1, 140.00, 1, 20241020, 20241020, 6, 6),
    (7, 'Twin Room', 2, 120.00, 1, 20241021, 20241021, 7, 7),
    (8, 'King Room', 1, 180.00, 1, 20241022, 20241022, 8, 8),
    (9, 'Double Room', 2, 110.00, 1, 20241023, 20241023, 9, 9),
    (10, 'Executive Suite', 3, 250.00, 1, 20241024, 20241024, 10, 10),
    (11, 'Budget Room', 2, 90.00, 0, 20241025, 20241025, 11, 11),
    (12, 'Studio Room', 1, 160.00, 1, 20241026, 20241026, 12, 12),
    (13, 'Suite Room', 3, 280.00, 1, 20241027, 20241027, 13, 13),
    (14, 'Panorama Room', 1, 200.00, 0, 20241028, 20241028, 14, 14),
    (15, 'Penthouse Suite', 3, 350.00, 1, 20241029, 20241029, 15, 15);
-- Thêm dữ liệu vào bảng Reviews
INSERT INTO Reviews (review_id, rate, comment, created_at, updated_at, homestay_id, user_id)
VALUES 
    (6, 5, 'Amazing stay with lovely hosts!', 20241020, 20241020, 6, 6),
    (7, 4, 'Great location but slightly overpriced.', 20241021, 20241021, 7, 7),
    (8, 3, 'Decent experience but could be cleaner.', 20241022, 20241022, 8, 8),
    (9, 4, 'Nice homestay with friendly staff.', 20241023, 20241023, 9, 9),
    (10, 5, 'Exceptional view and service!', 20241024, 20241024, 10, 10),
    (11, 2, 'Room didn’t match the photos.', 20241025, 20241025, 11, 11),
    (12, 3, 'Comfortable but could be better.', 20241026, 20241026, 12, 12),
    (13, 4, 'Good value for the price.', 20241027, 20241027, 13, 13),
    (14, 1, 'Not as expected, too noisy.', 20241028, 20241028, 14, 14),
    (15, 5, 'Perfect place for a getaway!', 20241029, 20241029, 15, 15);

-- Thêm dữ liệu vào bảng Photos
INSERT INTO Photos (photo_id, name_photo, created_at, updated_at, homestay_id)
VALUES 
    (6, 'Hue Homestay Interior', 20241020, 20241020, 6),
    (7, 'Riverside Balcony', 20241021, 20241021, 7),
    (8, 'Beachfront Bungalow', 20241022, 20241022, 8),
    (9, 'Mekong Delta View', 20241023, 20241023, 9),
    (10, 'Garden Area', 20241024, 20241024, 10),
    (11, 'Mountain Scenery', 20241025, 20241025, 11),
    (12, 'Ocean Suite', 20241026, 20241026, 12),
    (13, 'Island Retreat', 20241027, 20241027, 13),
    (14, 'Heritage House', 20241028, 20241028, 14),
    (15, 'Garden Pathway', 20241029, 20241029, 15);

-- Thêm dữ liệu vào bảng Services
INSERT INTO Services (service_id, service_name, description, price, created_at, updated_at)
VALUES 
    (6, 'Laundry Service', 'Convenient laundry services available.', 15.00, 20241006, 20241006),
    (7, 'Room Service', 'Order food and drinks to your room.', 20.00, 20241007, 20241007),
    (8, 'Yoga Class', 'Morning yoga classes offered.', 25.00, 20241008, 20241008),
    (9, 'BBQ Facility', 'Access to outdoor BBQ area.', 30.00, 20241009, 20241009),
    (10, 'Bike Rental', 'Rent bikes for local sightseeing.', 10.00, 20241010, 20241010),
    (11, 'Kids Club', 'Activities and fun for children.', 40.00, 20241011, 20241011),
    (12, 'Airport Drop-off', 'Convenient airport drop-off service.', 35.00, 20241012, 20241012),
    (13, 'Cooking Class', 'Learn to cook local dishes.', 50.00, 20241013, 20241013),
    (14, 'Car Rental', 'Rent a car for your stay.', 60.00, 20241014, 20241014),
    (15, 'Massage Therapy', 'Relaxing massage therapy.', 80.00, 20241015, 20241015);

-- Thêm dữ liệu vào bảng Services_advantage
INSERT INTO Services_advantage (homestay_id, service_id, description)
VALUES 
    (6, 1, 'High-speed internet in all rooms.'),
    (7, 2, 'Free airport pickup for all guests.'),
    (8, 3, 'Daily breakfast included.'),
    (9, 4, 'Guided tours to local attractions.'),
    (10, 5, 'Spa and wellness services available.'),
    (11, 6, 'Laundry service on request.'),
    (12, 7, 'Room service from 7 AM to 10 PM.'),
    (13, 8, 'Yoga classes available in the mornings.'),
    (14, 9, 'BBQ area available for guests.'),
    (15, 10, 'Bike rental for easy exploration.');

INSERT INTO Homestays (homestay_id, name, description, ward, district, province, created_at, updated_at, user_id)
VALUES
    (20, 'Sapa Mountain Homestay', 'A retreat with breathtaking mountain views', 'Cau May Ward', 'Sapa Town', 'Lao Cai', 20241207, 20241207, 0),
    (21, 'Hanoi Old Quarter Homestay', 'Homestay near the heart of Hanoi Old Quarter', 'Hang Buom Ward', 'Hoan Kiem District', 'Hanoi', 20241207, 20241207, 0),
    (22, 'Danang Beach Homestay', 'Comfortable homestay near My Khe Beach', 'An Hai Bac Ward', 'Son Tra District', 'Da Nang', 20241207, 20241207, 0),
    (23, 'Saigon Central Homestay', 'Luxury homestay in the heart of Saigon', 'Ben Nghe Ward', 'District 1', 'Ho Chi Minh City', 20241207, 20241207, 0),
    (24, 'Quy Nhon Blue Sea Homestay', 'Peaceful homestay with blue sea views', 'Hai Cang Ward', 'Quy Nhon City', 'Binh Dinh', 20241207, 20241207, 0),
    (25, 'Phong Nha Scenic Homestay', 'Homestay near the Phong Nha natural heritage', 'Son Trach Ward', 'Bo Trach District', 'Quang Binh', 20241207, 20241207, 0),
    (26, 'Can Tho Mekong Homestay', 'Peaceful homestay with views of the Mekong River', 'Xuan Khanh Ward', 'Ninh Kieu District', 'Can Tho', 20241207, 20241207, 0),
    (27, 'Da Lat Flower Hill Homestay', 'Homestay surrounded by beautiful flower hills', 'Ward 8', 'Da Lat City', 'Lam Dong', 20241207, 20241207, 0),
    (28, 'Hoi An Countryside Homestay', 'Peaceful homestay in the ancient countryside', 'Cam Chau Ward', 'Hoi An City', 'Quang Nam', 20241207, 20241207, 0),
    (29, 'Hue Imperial Homestay', 'Homestay near the historic Hue Imperial City', 'Thuan Thanh Ward', 'Hue City', 'Thua Thien Hue', 20241207, 20241207, 0),
    (30, 'Ha Long Bay Homestay', 'Homestay near the beautiful Ha Long Bay', 'Bai Chay Ward', 'Ha Long City', 'Quang Ninh', 20241207, 20241207, 0),
    (31, 'Tay Ninh Mountain Homestay', 'Homestay near the Ba Den Mountain tourist area', 'Ninh Son Ward', 'Tay Ninh City', 'Tay Ninh', 20241207, 20241207, 0),
    (32, 'Vung Tau Coastal Homestay', 'Well-equipped homestay with sea views', 'Thang Tam Ward', 'Vung Tau City', 'Ba Ria - Vung Tau', 20241207, 20241207, 0),
    (33, 'Ha Giang Highland Homestay', 'Homestay near the Dong Van Karst Plateau', 'Tam Son Ward', 'Dong Van District', 'Ha Giang', 20241207, 20241207, 0),
    (34, 'Moc Chau Tea Hill Homestay', 'Homestay located in the middle of Moc Chau tea hills', 'Tan Lap Ward', 'Moc Chau District', 'Son La', 20241207, 20241207, 0),
    (35, 'Nha Trang Beach Homestay', 'Homestay near Nha Trang beach', 'Loc Tho Ward', 'Nha Trang City', 'Khanh Hoa', 20241207, 20241207, 0),
    (36, 'Phu Quoc Paradise Homestay', 'Homestay in the paradise island of Phu Quoc', 'Duong Dong Ward', 'Phu Quoc City', 'Kien Giang', 20241207, 20241207, 0);
-- Insert into Rooms for Homestay ID 1 to 36, each having 7 rooms with actual room names and types (1 or 2)
INSERT INTO Rooms (room_id, room_name, room_type, price, status, created_at, updated_at, homestay_id, booking_id)
VALUES
    -- Homestay Hanoi CITY (ID 1)
    (16, 'Hanoi City Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 1, 0),
    (17, 'Hanoi City Standard Room', 2, 120.00, 1, 20241207, 20241207, 1, 0),
    (18, 'Hanoi City Economy Room', 1, 110.00, 1, 20241207, 20241207, 1, 0),
    (19, 'Hanoi City Family Suite', 2, 130.00, 1, 20241207, 20241207, 1, 0),
    (20, 'Hanoi City Executive Suite', 1, 140.00, 1, 20241207, 20241207, 1, 0),
    (21, 'Hanoi City Premium Room', 2, 150.00, 1, 20241207, 20241207, 1, 0),
    (22, 'Hanoi City Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 1, 0),

    -- Homestay Saigon Riverside (ID 2)
    (23, 'Saigon Riverside Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 2, 0),
    (24, 'Saigon Riverside Standard Room', 2, 120.00, 1, 20241207, 20241207, 2, 0),
    (25, 'Saigon Riverside Economy Room', 1, 110.00, 1, 20241207, 20241207, 2, 0),
    (26, 'Saigon Riverside Family Suite', 2, 130.00, 1, 20241207, 20241207, 2, 0),
    (27, 'Saigon Riverside Executive Suite', 1, 140.00, 1, 20241207, 20241207, 2, 0),
    (28, 'Saigon Riverside Premium Room', 2, 150.00, 1, 20241207, 20241207, 2, 0),
    (29, 'Saigon Riverside Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 2, 0),

    -- Homestay Da Nang Beach (ID 3)
    (30, 'Da Nang Beach Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 3, 0),
    (31, 'Da Nang Beach Standard Room', 2, 120.00, 1, 20241207, 20241207, 3, 0),
    (32, 'Da Nang Beach Economy Room', 1, 110.00, 1, 20241207, 20241207, 3, 0),
    (33, 'Da Nang Beach Family Suite', 2, 130.00, 1, 20241207, 20241207, 3, 0),
    (34, 'Da Nang Beach Executive Suite', 1, 140.00, 1, 20241207, 20241207, 3, 0),
    (35, 'Da Nang Beach Premium Room', 2, 150.00, 1, 20241207, 20241207, 3, 0),
    (36, 'Da Nang Beach Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 3, 0),

    -- Homestay Ninh Binh Nature (ID 4)
    (37, 'Ninh Binh Nature Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 4, 0),
    (38, 'Ninh Binh Nature Standard Room', 2, 120.00, 1, 20241207, 20241207, 4, 0),
    (39, 'Ninh Binh Nature Economy Room', 1, 110.00, 1, 20241207, 20241207, 4, 0),
    (40, 'Ninh Binh Nature Family Suite', 2, 130.00, 1, 20241207, 20241207, 4, 0),
    (41, 'Ninh Binh Nature Executive Suite', 1, 140.00, 1, 20241207, 20241207, 4, 0),
    (42, 'Ninh Binh Nature Premium Room', 2, 150.00, 1, 20241207, 20241207, 4, 0),
    (43, 'Ninh Binh Nature Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 4, 0),

    -- Homestay Sapa Mountain (ID 5)
    (44, 'Sapa Mountain Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 5, 0),
    (45, 'Sapa Mountain Standard Room', 2, 120.00, 1, 20241207, 20241207, 5, 0),
    (46, 'Sapa Mountain Economy Room', 1, 110.00, 1, 20241207, 20241207, 5, 0),
    (47, 'Sapa Mountain Family Suite', 2, 130.00, 1, 20241207, 20241207, 5, 0),
    (48, 'Sapa Mountain Executive Suite', 1, 140.00, 1, 20241207, 20241207, 5, 0),
    (49, 'Sapa Mountain Premium Room', 2, 150.00, 1, 20241207, 20241207, 5, 0),
    (50, 'Sapa Mountain Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 5, 0),

    -- Homestay Hue Imperial (ID 6)
    (51, 'Hue Imperial Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 6, 0),
    (52, 'Hue Imperial Standard Room', 2, 120.00, 1, 20241207, 20241207, 6, 0),
    (53, 'Hue Imperial Economy Room', 1, 110.00, 1, 20241207, 20241207, 6, 0),
    (54, 'Hue Imperial Family Suite', 2, 130.00, 1, 20241207, 20241207, 6, 0),
    (55, 'Hue Imperial Executive Suite', 1, 140.00, 1, 20241207, 20241207, 6, 0),
    (56, 'Hue Imperial Premium Room', 2, 150.00, 1, 20241207, 20241207, 6, 0),
    (57, 'Hue Imperial Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 6, 0),

    -- Homestay Hoi An Riverside (ID 7)
    (58, 'Hoi An Riverside Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 7, 0),
    (59, 'Hoi An Riverside Standard Room', 2, 120.00, 1, 20241207, 20241207, 7, 0),
    (60, 'Hoi An Riverside Economy Room', 1, 110.00, 1, 20241207, 20241207, 7, 0),
    (61, 'Hoi An Riverside Family Suite', 2, 130.00, 1, 20241207, 20241207, 7, 0),
    (62, 'Hoi An Riverside Executive Suite', 1, 140.00, 1, 20241207, 20241207, 7, 0),
    (63, 'Hoi An Riverside Premium Room', 2, 150.00, 1, 20241207, 20241207, 7, 0),
    (64, 'Hoi An Riverside Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 7, 0);

  
-- Homestay Phu Quoc Paradise (ID 8)
INSERT INTO Rooms (room_id, room_name, room_type, price, status, created_at, updated_at, homestay_id, booking_id)
VALUES
    (65, 'Phu Quoc Paradise Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 8, 0),
    (66, 'Phu Quoc Paradise Standard Room', 2, 120.00, 1, 20241207, 20241207, 8, 0),
    (67, 'Phu Quoc Paradise Economy Room', 1, 110.00, 1, 20241207, 20241207, 8, 0),
    (68, 'Phu Quoc Paradise Family Suite', 2, 130.00, 1, 20241207, 20241207, 8, 0),
    (69, 'Phu Quoc Paradise Executive Suite', 1, 140.00, 1, 20241207, 20241207, 8, 0),
    (70, 'Phu Quoc Paradise Premium Room', 2, 150.00, 1, 20241207, 20241207, 8, 0),
    (71, 'Phu Quoc Paradise Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 8, 0),

-- Homestay Can Tho Eco (ID 9)
    (72, 'Can Tho Eco Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 9, 0),
    (73, 'Can Tho Eco Standard Room', 2, 120.00, 1, 20241207, 20241207, 9, 0),
    (74, 'Can Tho Eco Economy Room', 1, 110.00, 1, 20241207, 20241207, 9, 0),
    (75, 'Can Tho Eco Family Suite', 2, 130.00, 1, 20241207, 20241207, 9, 0),
    (76, 'Can Tho Eco Executive Suite', 1, 140.00, 1, 20241207, 20241207, 9, 0),
    (77, 'Can Tho Eco Premium Room', 2, 150.00, 1, 20241207, 20241207, 9, 0),
    (78, 'Can Tho Eco Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 9, 0),

-- Homestay Da Lat Flower (ID 10)
    (79, 'Da Lat Flower Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 10, 0),
    (80, 'Da Lat Flower Standard Room', 2, 120.00, 1, 20241207, 20241207, 10, 0),
    (81, 'Da Lat Flower Economy Room', 1, 110.00, 1, 20241207, 20241207, 10, 0),
    (82, 'Da Lat Flower Family Suite', 2, 130.00, 1, 20241207, 20241207, 10, 0),
    (83, 'Da Lat Flower Executive Suite', 1, 140.00, 1, 20241207, 20241207, 10, 0),
    (84, 'Da Lat Flower Premium Room', 2, 150.00, 1, 20241207, 20241207, 10, 0),
    (85, 'Da Lat Flower Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 10, 0),

-- Homestay Ha Giang Loop (ID 11)
    (86, 'Ha Giang Loop Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 11, 0),
    (87, 'Ha Giang Loop Standard Room', 2, 120.00, 1, 20241207, 20241207, 11, 0),
    (88, 'Ha Giang Loop Economy Room', 1, 110.00, 1, 20241207, 20241207, 11, 0),
    (89, 'Ha Giang Loop Family Suite', 2, 130.00, 1, 20241207, 20241207, 11, 0),
    (90, 'Ha Giang Loop Executive Suite', 1, 140.00, 1, 20241207, 20241207, 11, 0),
    (91, 'Ha Giang Loop Premium Room', 2, 150.00, 1, 20241207, 20241207, 11, 0),
    (92, 'Ha Giang Loop Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 11, 0),

-- Homestay Vung Tau Coastal (ID 12)
    (93, 'Vung Tau Coastal Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 12, 0),
    (94, 'Vung Tau Coastal Standard Room', 2, 120.00, 1, 20241207, 20241207, 12, 0),
    (95, 'Vung Tau Coastal Economy Room', 1, 110.00, 1, 20241207, 20241207, 12, 0),
    (96, 'Vung Tau Coastal Family Suite', 2, 130.00, 1, 20241207, 20241207, 12, 0),
    (97, 'Vung Tau Coastal Executive Suite', 1, 140.00, 1, 20241207, 20241207, 12, 0),
    (98, 'Vung Tau Coastal Premium Room', 2, 150.00, 1, 20241207, 20241207, 12, 0),
    (99, 'Vung Tau Coastal Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 12, 0),

-- Homestay Con Dao Hideaway (ID 13)
    (100, 'Con Dao Hideaway Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 13, 0),
    (101, 'Con Dao Hideaway Standard Room', 2, 120.00, 1, 20241207, 20241207, 13, 0),
    (102, 'Con Dao Hideaway Economy Room', 1, 110.00, 1, 20241207, 20241207, 13, 0),
    (103, 'Con Dao Hideaway Family Suite', 2, 130.00, 1, 20241207, 20241207, 13, 0),
    (104, 'Con Dao Hideaway Executive Suite', 1, 140.00, 1, 20241207, 20241207, 13, 0),
    (105, 'Con Dao Hideaway Premium Room', 2, 150.00, 1, 20241207, 20241207, 13, 0),
    (106, 'Con Dao Hideaway Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 13, 0),

-- Homestay Bac Ninh Heritage (ID 14)
    (107, 'Bac Ninh Heritage Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 14, 0),
    (108, 'Bac Ninh Heritage Standard Room', 2, 120.00, 1, 20241207, 20241207, 14, 0),
    (109, 'Bac Ninh Heritage Economy Room', 1, 110.00, 1, 20241207, 20241207, 14, 0),
    (110, 'Bac Ninh Heritage Family Suite', 2, 130.00, 1, 20241207, 20241207, 14, 0),
    (111, 'Bac Ninh Heritage Executive Suite', 1, 140.00, 1, 20241207, 20241207, 14, 0),
    (112, 'Bac Ninh Heritage Premium Room', 2, 150.00, 1, 20241207, 20241207, 14, 0),
    (113, 'Bac Ninh Heritage Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 14, 0),

-- Homestay Sapa Mountain (ID 20)
    (114, 'Sapa Mountain Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 20, 0),
    (115, 'Sapa Mountain Standard Room', 2, 120.00, 1, 20241207, 20241207, 20, 0),
    (116, 'Sapa Mountain Economy Room', 1, 110.00, 1, 20241207, 20241207, 20, 0),
    (117, 'Sapa Mountain Family Suite', 2, 130.00, 1, 20241207, 20241207, 20, 0),
    (118, 'Sapa Mountain Executive Suite', 1, 140.00, 1, 20241207, 20241207, 20, 0),
    (119, 'Sapa Mountain Premium Room', 2, 150.00, 1, 20241207, 20241207, 20, 0),
    (120, 'Sapa Mountain Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 20, 0);

-- Continue with the rest of the homestays (ID 21 to 36)
-- Please let me know if you want me to continue or have any adjustments!
INSERT INTO Rooms (room_id, room_name, room_type, price, status, created_at, updated_at, homestay_id, booking_id)
VALUES
    (121, 'Hanoi Old Quarter Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 21, 0),
    (122, 'Hanoi Old Quarter Standard Room', 2, 120.00, 1, 20241207, 20241207, 21, 0),
    (123, 'Hanoi Old Quarter Economy Room', 1, 110.00, 1, 20241207, 20241207, 21, 0),
    (124, 'Hanoi Old Quarter Family Suite', 2, 130.00, 1, 20241207, 20241207, 21, 0),
    (125, 'Hanoi Old Quarter Executive Suite', 1, 140.00, 1, 20241207, 20241207, 21, 0),
    (126, 'Hanoi Old Quarter Premium Room', 2, 150.00, 1, 20241207, 20241207, 21, 0),
    (127, 'Hanoi Old Quarter Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 21, 0),

-- Homestay Danang Beach (ID 22)
    (128, 'Danang Beach Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 22, 0),
    (129, 'Danang Beach Standard Room', 2, 120.00, 1, 20241207, 20241207, 22, 0),
    (130, 'Danang Beach Economy Room', 1, 110.00, 1, 20241207, 20241207, 22, 0),
    (131, 'Danang Beach Family Suite', 2, 130.00, 1, 20241207, 20241207, 22, 0),
    (132, 'Danang Beach Executive Suite', 1, 140.00, 1, 20241207, 20241207, 22, 0),
    (133, 'Danang Beach Premium Room', 2, 150.00, 1, 20241207, 20241207, 22, 0),
    (134, 'Danang Beach Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 22, 0),

-- Homestay Saigon Central (ID 23)
    (135, 'Saigon Central Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 23, 0),
    (136, 'Saigon Central Standard Room', 2, 120.00, 1, 20241207, 20241207, 23, 0),
    (137, 'Saigon Central Economy Room', 1, 110.00, 1, 20241207, 20241207, 23, 0),
    (138, 'Saigon Central Family Suite', 2, 130.00, 1, 20241207, 20241207, 23, 0),
    (139, 'Saigon Central Executive Suite', 1, 140.00, 1, 20241207, 20241207, 23, 0),
    (140, 'Saigon Central Premium Room', 2, 150.00, 1, 20241207, 20241207, 23, 0),
    (141, 'Saigon Central Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 23, 0),

-- Homestay Quy Nhon Blue Sea (ID 24)
    (142, 'Quy Nhon Blue Sea Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 24, 0),
    (143, 'Quy Nhon Blue Sea Standard Room', 2, 120.00, 1, 20241207, 20241207, 24, 0),
    (144, 'Quy Nhon Blue Sea Economy Room', 1, 110.00, 1, 20241207, 20241207, 24, 0),
    (145, 'Quy Nhon Blue Sea Family Suite', 2, 130.00, 1, 20241207, 20241207, 24, 0),
    (146, 'Quy Nhon Blue Sea Executive Suite', 1, 140.00, 1, 20241207, 20241207, 24, 0),
    (147, 'Quy Nhon Blue Sea Premium Room', 2, 150.00, 1, 20241207, 20241207, 24, 0),
    (148, 'Quy Nhon Blue Sea Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 24, 0),

-- Homestay Phong Nha Scenic (ID 25)
    (149, 'Phong Nha Scenic Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 25, 0),
    (150, 'Phong Nha Scenic Standard Room', 2, 120.00, 1, 20241207, 20241207, 25, 0),
    (151, 'Phong Nha Scenic Economy Room', 1, 110.00, 1, 20241207, 20241207, 25, 0),
    (152, 'Phong Nha Scenic Family Suite', 2, 130.00, 1, 20241207, 20241207, 25, 0),
    (153, 'Phong Nha Scenic Executive Suite', 1, 140.00, 1, 20241207, 20241207, 25, 0),
    (154, 'Phong Nha Scenic Premium Room', 2, 150.00, 1, 20241207, 20241207, 25, 0),
    (155, 'Phong Nha Scenic Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 25, 0),

-- Homestay Can Tho Mekong (ID 26)
    (156, 'Can Tho Mekong Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 26, 0),
    (157, 'Can Tho Mekong Standard Room', 2, 120.00, 1, 20241207, 20241207, 26, 0),
    (158, 'Can Tho Mekong Economy Room', 1, 110.00, 1, 20241207, 20241207, 26, 0),
    (159, 'Can Tho Mekong Family Suite', 2, 130.00, 1, 20241207, 20241207, 26, 0),
    (160, 'Can Tho Mekong Executive Suite', 1, 140.00, 1, 20241207, 20241207, 26, 0),
    (161, 'Can Tho Mekong Premium Room', 2, 150.00, 1, 20241207, 20241207, 26, 0),
    (162, 'Can Tho Mekong Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 26, 0),

-- Homestay Da Lat Flower Hill (ID 27)
    (163, 'Da Lat Flower Hill Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 27, 0),
    (164, 'Da Lat Flower Hill Standard Room', 2, 120.00, 1, 20241207, 20241207, 27, 0),
    (165, 'Da Lat Flower Hill Economy Room', 1, 110.00, 1, 20241207, 20241207, 27, 0),
    (166, 'Da Lat Flower Hill Family Suite', 2, 130.00, 1, 20241207, 20241207, 27, 0),
    (167, 'Da Lat Flower Hill Executive Suite', 1, 140.00, 1, 20241207, 20241207, 27, 0),
    (168, 'Da Lat Flower Hill Premium Room', 2, 150.00, 1, 20241207, 20241207, 27, 0),
    (169, 'Da Lat Flower Hill Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 27, 0),

-- Homestay Hoi An Countryside (ID 28)
    (170, 'Hoi An Countryside Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 28, 0),
    (171, 'Hoi An Countryside Standard Room', 2, 120.00, 1, 20241207, 20241207, 28, 0),
    (172, 'Hoi An Countryside Economy Room', 1, 110.00, 1, 20241207, 20241207, 28, 0),
    (173, 'Hoi An Countryside Family Suite', 2, 130.00, 1, 20241207, 20241207, 28, 0),
    (174, 'Hoi An Countryside Executive Suite', 1, 140.00, 1, 20241207, 20241207, 28, 0),
    (175, 'Hoi An Countryside Premium Room', 2, 150.00, 1, 20241207, 20241207, 28, 0),
    (176, 'Hoi An Countryside Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 28, 0);
-- Homestay Hue Imperial (ID 29)
INSERT INTO Rooms (room_id, room_name, room_type, price, status, created_at, updated_at, homestay_id, booking_id)
VALUES
    (177, 'Hue Imperial Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 29, 0),
    (178, 'Hue Imperial Standard Room', 2, 120.00, 1, 20241207, 20241207, 29, 0),
    (179, 'Hue Imperial Economy Room', 1, 110.00, 1, 20241207, 20241207, 29, 0),
    (180, 'Hue Imperial Family Suite', 2, 130.00, 1, 20241207, 20241207, 29, 0),
    (181, 'Hue Imperial Executive Suite', 1, 140.00, 1, 20241207, 20241207, 29, 0),
    (182, 'Hue Imperial Premium Room', 2, 150.00, 1, 20241207, 20241207, 29, 0),
    (183, 'Hue Imperial Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 29, 0),

-- Homestay Ha Long Bay (ID 30)
    (184, 'Ha Long Bay Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 30, 0),
    (185, 'Ha Long Bay Standard Room', 2, 120.00, 1, 20241207, 20241207, 30, 0),
    (186, 'Ha Long Bay Economy Room', 1, 110.00, 1, 20241207, 20241207, 30, 0),
    (187, 'Ha Long Bay Family Suite', 2, 130.00, 1, 20241207, 20241207, 30, 0),
    (188, 'Ha Long Bay Executive Suite', 1, 140.00, 1, 20241207, 20241207, 30, 0),
    (189, 'Ha Long Bay Premium Room', 2, 150.00, 1, 20241207, 20241207, 30, 0),
    (190, 'Ha Long Bay Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 30, 0),

-- Homestay Tay Ninh Mountain (ID 31)
    (191, 'Tay Ninh Mountain Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 31, 0),
    (192, 'Tay Ninh Mountain Standard Room', 2, 120.00, 1, 20241207, 20241207, 31, 0),
    (193, 'Tay Ninh Mountain Economy Room', 1, 110.00, 1, 20241207, 20241207, 31, 0),
    (194, 'Tay Ninh Mountain Family Suite', 2, 130.00, 1, 20241207, 20241207, 31, 0),
    (195, 'Tay Ninh Mountain Executive Suite', 1, 140.00, 1, 20241207, 20241207, 31, 0),
    (196, 'Tay Ninh Mountain Premium Room', 2, 150.00, 1, 20241207, 20241207, 31, 0),
    (197, 'Tay Ninh Mountain Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 31, 0),

-- Homestay Vung Tau Coastal (ID 32)
    (198, 'Vung Tau Coastal Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 32, 0),
    (199, 'Vung Tau Coastal Standard Room', 2, 120.00, 1, 20241207, 20241207, 32, 0),
    (200, 'Vung Tau Coastal Economy Room', 1, 110.00, 1, 20241207, 20241207, 32, 0),
    (201, 'Vung Tau Coastal Family Suite', 2, 130.00, 1, 20241207, 20241207, 32, 0),
    (202, 'Vung Tau Coastal Executive Suite', 1, 140.00, 1, 20241207, 20241207, 32, 0),
    (203, 'Vung Tau Coastal Premium Room', 2, 150.00, 1, 20241207, 20241207, 32, 0),
    (204, 'Vung Tau Coastal Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 32, 0),

-- Homestay Ha Giang Highland (ID 33)
    (205, 'Ha Giang Highland Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 33, 0),
    (206, 'Ha Giang Highland Standard Room', 2, 120.00, 1, 20241207, 20241207, 33, 0),
    (207, 'Ha Giang Highland Economy Room', 1, 110.00, 1, 20241207, 20241207, 33, 0),
    (208, 'Ha Giang Highland Family Suite', 2, 130.00, 1, 20241207, 20241207, 33, 0),
    (209, 'Ha Giang Highland Executive Suite', 1, 140.00, 1, 20241207, 20241207, 33, 0),
    (210, 'Ha Giang Highland Premium Room', 2, 150.00, 1, 20241207, 20241207, 33, 0),
    (211, 'Ha Giang Highland Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 33, 0),

-- Homestay Moc Chau Tea Hill (ID 34)
    (212, 'Moc Chau Tea Hill Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 34, 0),
    (213, 'Moc Chau Tea Hill Standard Room', 2, 120.00, 1, 20241207, 20241207, 34, 0),
    (214, 'Moc Chau Tea Hill Economy Room', 1, 110.00, 1, 20241207, 20241207, 34, 0),
    (215, 'Moc Chau Tea Hill Family Suite', 2, 130.00, 1, 20241207, 20241207, 34, 0),
    (216, 'Moc Chau Tea Hill Executive Suite', 1, 140.00, 1, 20241207, 20241207, 34, 0),
    (217, 'Moc Chau Tea Hill Premium Room', 2, 150.00, 1, 20241207, 20241207, 34, 0),
    (218, 'Moc Chau Tea Hill Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 34, 0),

-- Homestay Nha Trang Beach (ID 35)
    (219, 'Nha Trang Beach Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 35, 0),
    (220, 'Nha Trang Beach Standard Room', 2, 120.00, 1, 20241207, 20241207, 35, 0),
    (221, 'Nha Trang Beach Economy Room', 1, 110.00, 1, 20241207, 20241207, 35, 0),
    (222, 'Nha Trang Beach Family Suite', 2, 130.00, 1, 20241207, 20241207, 35, 0),
    (223, 'Nha Trang Beach Executive Suite', 1, 140.00, 1, 20241207, 20241207, 35, 0),
    (224, 'Nha Trang Beach Premium Room', 2, 150.00, 1, 20241207, 20241207, 35, 0),
    (225, 'Nha Trang Beach Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 35, 0),

-- Homestay Phu Quoc Paradise (ID 36)
    (226, 'Phu Quoc Paradise Deluxe Room', 1, 100.00, 1, 20241207, 20241207, 36, 0),
    (227, 'Phu Quoc Paradise Standard Room', 2, 120.00, 1, 20241207, 20241207, 36, 0),
    (228, 'Phu Quoc Paradise Economy Room', 1, 110.00, 1, 20241207, 20241207, 36, 0),
    (229, 'Phu Quoc Paradise Family Suite', 2, 130.00, 1, 20241207, 20241207, 36, 0),
    (230, 'Phu Quoc Paradise Executive Suite', 1, 140.00, 1, 20241207, 20241207, 36, 0),
    (231, 'Phu Quoc Paradise Premium Room', 2, 150.00, 1, 20241207, 20241207, 36, 0),
    (232, 'Phu Quoc Paradise Presidential Suite', 1, 160.00, 1, 20241207, 20241207, 36, 0);
INSERT INTO Users (user_id, user_name, password, email, phone_number, gender, full_name, avatar, created_at, updated_at)
VALUES
(16, 'william_jones', 'williampass', 'william.jones@example.com', '9991112222', 1, 'William Jones', 'avatar16.png', 20241016, 20241016),
(17, 'olivia_smith', 'oliviapass', 'olivia.smith@example.com', '8889957777', 0, 'Olivia Smith', 'avatar17.png', 20241017, 20241017),
(18, 'daniel_taylor', 'danielpass', 'daniel.taylor@example.com', '7778886666', 1, 'Daniel Taylor', 'avatar18.png', 20241018, 20241018),
(19, 'sophia_martin', 'sophiapass', 'sophia.martin@example.com', '6667775555', 0, 'Sophia Martin', 'avatar19.png', 20241019, 20241019),
(20, 'james_clark', 'jamespass', 'james.clark@example.com', '5556664444', 1, 'James Clark', 'avatar20.png', 20241020, 20241020),
(21, 'mia_king', 'miapass', 'mia.king@example.com', '4445553333', 0, 'Mia King', 'avatar21.png', 20241021, 20241021),
(22, 'lucas_harris', 'lucaspass', 'lucas.harris@example.com', '3334442222', 1, 'Lucas Harris', 'avatar22.png', 20241022, 20241022),
(23, 'isabella_davis', 'isabellapass', 'isabella.davis@example.com', '2223331111', 0, 'Isabella Davis', 'avatar23.png', 20241023, 20241023),
(24, 'henry_walker', 'henrypass', 'henry.walker@example.com', '1112220000', 1, 'Henry Walker', 'avatar24.png', 20241024, 20241024),
(25, 'ava_lee', 'avapass', 'ava.lee@example.com', '9998887777', 0, 'Ava Lee', 'avatar25.png', 20241025, 20241025),
(26, 'jackson_hall', 'jacksonpass', 'jackson.hall@example.com', '8887776666', 1, 'Jackson Hall', 'avatar26.png', 20241026, 20241026),
(27, 'emily_allen', 'emilypass', 'emily.allen@example.com', '7776665555', 0, 'Emily Allen', 'avatar27.png', 20241027, 20241027),
(28, 'logan_sanders', 'loganpass', 'logan.sanders@example.com', '6665554444', 1, 'Logan Sanders', 'avatar28.png', 20241028, 20241028),
(29, 'amelia_baker', 'ameliapass', 'amelia.baker@example.com', '5554443333', 0, 'Amelia Baker', 'avatar29.png', 20241029, 20241029),
(30, 'sebastian_adams', 'sebastianpass', 'sebastian.adams@example.com', '4443332222', 1, 'Sebastian Adams', 'avatar30.png', 20241030, 20241030),
(31, 'harper_collins', 'harperpass', 'harper.collins@example.com', '3332221111', 0, 'Harper Collins', 'avatar31.png', 20241031, 20241031),
(32, 'grayson_hernandez', 'graysonpass', 'grayson.hernandez@example.com', '2221110000', 1, 'Grayson Hernandez', 'avatar32.png', 20241101, 20241101),
(33, 'scarlett_evans', 'scarlettpass', 'scarlett.evans@example.com', '1110009999', 0, 'Scarlett Evans', 'avatar33.png', 20241102, 20241102),
(34, 'luke_morris', 'lukepass', 'luke.morris@example.com', '9998887777', 1, 'Luke Morris', 'avatar34.png', 20241103, 20241103),
(35, 'chloe_thomas', 'chloepass', 'chloe.thomas@example.com', '8887776666', 0, 'Chloe Thomas', 'avatar35.png', 20241104, 20241104),
(36, 'elijah_thompson', 'elijahpass', 'elijah.thompson@example.com', '7776665555', 1, 'Elijah Thompson', 'avatar36.png', 20241105, 20241105),
(37, 'sofia_anderson', 'sofiapass', 'sofia.anderson@example.com', '6665554444', 0, 'Sofia Anderson', 'avatar37.png', 20241106, 20241106),
(38, 'aiden_white', 'aidenpass', 'aiden.white@example.com', '5554443333', 1, 'Aiden White', 'avatar38.png', 20241107, 20241107),
(39, 'aria_wilson', 'ariapass', 'aria.wilson@example.com', '4443332222', 0, 'Aria Wilson', 'avatar39.png', 20241108, 20241108),
(40, 'mason_taylor', 'masonpass', 'mason.taylor@example.com', '3332221111', 1, 'Mason Taylor', 'avatar40.png', 20241109, 20241109),
(41, 'lily_clark', 'lilypass', 'lily.clark@example.com', '2221110000', 0, 'Lily Clark', 'avatar41.png', 20241110, 20241110);


INSERT INTO Reviews (review_id, rate, comment, created_at, updated_at, homestay_id, user_id)
VALUES 
-- Reviews for Homestay Hanoi CITY
(16, 5, 'The best homestay in Hanoi!', 20241201, 20241201, 1, 16),
(17, 4, 'Comfortable and clean.', 20241202, 20241202, 1, 17),
(18, 3, 'Good but a bit noisy.', 20241203, 20241203, 1, 18),
(19, 5, 'Perfect for a weekend trip.', 20241204, 20241204, 1, 19),
(20, 4, 'Very friendly hosts.', 20241205, 20241205, 1, 20),
(21, 2, 'Needs better maintenance.', 20241206, 20241206, 1, 21),

-- Reviews for Saigon Riverside Homestay
(22, 5, 'Stunning view of the river!', 20241201, 20241201, 2, 22),
(23, 4, 'Excellent location, decent service.', 20241202, 20241202, 2, 23),
(24, 3, 'Good value for money.', 20241203, 20241203, 2, 24),
(25, 4, 'Relaxing and peaceful.', 20241204, 20241204, 2, 25),
(26, 5, 'Highly recommend this place.', 20241205, 20241205, 2, 26),
(27, 1, 'Had some issues with cleanliness.', 20241206, 20241206, 2, 27),

-- Reviews for Homestay Da Nang Beach
(28, 5, 'Amazing beach access!', 20241201, 20241201, 3, 28),
(29, 4, 'Good service, great location.', 20241202, 20241202, 3, 29),
(30, 3, 'Average experience.', 20241203, 20241203, 3, 30),
(31, 5, 'Loved the relaxing vibe.', 20241204, 20241204, 3, 31),
(32, 4, 'Would stay here again.', 20241205, 20241205, 3, 32),
(33, 2, 'Expected more for the price.', 20241206, 20241206, 3, 33),

-- Reviews for Ninh Binh Nature Homestay
(34, 5, 'Breathtaking views!', 20241201, 20241201, 4, 34),
(35, 4, 'Very close to nature.', 20241202, 20241202, 4, 35),
(36, 3, 'Good but lacks amenities.', 20241203, 20241203, 4, 36),
(37, 5, 'Best stay in Ninh Binh.', 20241204, 20241204, 4, 37),
(38, 4, 'Friendly staff and clean.', 20241205, 20241205, 4, 38),
(39, 2, 'Could improve the rooms.', 20241206, 20241206, 4, 39),

-- Reviews for Sapa Mountain Homestay
(40, 5, 'Stunning mountain view!', 20241201, 20241201, 5, 40),
(41, 4, 'Cozy and peaceful.', 20241202, 20241202, 5, 41),
(42, 3, 'A bit hard to reach.', 20241203, 20241203, 5, 42),
(43, 5, 'Perfect for nature lovers.', 20241204, 20241204, 5, 43),
(44, 4, 'Highly recommend for hiking.', 20241205, 20241205, 5, 44),
(45, 1, 'Not worth the price.', 20241206, 20241206, 5, 45);
INSERT INTO Admins (admin_id, user_name, pass_word) 
VALUES 
	(1, 'admin1', 'password123'),
	(2, 'admin2', 'securepass456'),
	(3, 'admin3', 'adminpass789');
    
SELECT * FROM Users;
SELECT * FROM Bookings;
SELECT * FROM Homestays;
SELECT * FROM Rooms;
SELECT * FROM Reviews;
SELECT * FROM Photos;
SELECT * FROM Services;
SELECT * FROM Services_advantage; 
SELECT * FROM Admins;
