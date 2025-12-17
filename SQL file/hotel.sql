-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 21, 2025 at 06:23 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotel`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `BookRoom` (IN `bookedRN` INT, IN `bookedDays` INT, IN `firstName` VARCHAR(50), IN `lastName` VARCHAR(50), IN `aadharNo` BIGINT, IN `guestCount` INT, IN `totalAmt` INT)   BEGIN
    -- Insert into customers table
    INSERT INTO customers (c_first_name, c_last_name, c_aadhar_no, c_room_no, c_booked_days, c_balance_amt, c_guest_count)
    VALUES (firstName, lastName, aadharNo, bookedRN, bookedDays, totalAmt, guestCount);

    -- Update rooms table (matches your hotel.sql definition)
    UPDATE rooms
    SET r_availability = 'booked',
        c_aadhar_no = aadharNo,
        c_days = bookedDays
    WHERE r_no = bookedRN;
END$$

--
-- Functions
--
CREATE DEFINER=`root`@`localhost` FUNCTION `isRoomAvailable` (`p_roomNo` INT) RETURNS TINYINT(1) DETERMINISTIC BEGIN
    DECLARE availability VARCHAR(20);

    SELECT r_availability INTO availability
    FROM rooms
    WHERE r_no = p_roomNo;

    IF availability = 'available' THEN
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `c_id` int(11) NOT NULL,
  `c_first_name` varchar(100) DEFAULT NULL,
  `c_last_name` varchar(100) DEFAULT NULL,
  `c_aadhar_no` bigint(20) NOT NULL,
  `c_room_no` int(11) DEFAULT NULL,
  `c_booked_days` int(11) DEFAULT NULL,
  `c_balance_amt` int(11) DEFAULT NULL,
  `c_guest_count` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`c_id`, `c_first_name`, `c_last_name`, `c_aadhar_no`, `c_room_no`, `c_booked_days`, `c_balance_amt`, `c_guest_count`) VALUES
(86, 'kunj', 'panara', 123456781234, 102, 2, 0, 1);

--
-- Triggers `customers`
--
DELIMITER $$
CREATE TRIGGER `UpdateRoomAvailability` AFTER DELETE ON `customers` FOR EACH ROW BEGIN
    UPDATE rooms 
    SET r_availability = 'Available', 
        c_aadhar_no = 0,
        c_days = 0
    WHERE r_no = OLD.c_room_no;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `food_menu`
--

CREATE TABLE `food_menu` (
  `f_id` int(11) NOT NULL,
  `f_name` varchar(30) NOT NULL,
  `f_price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `food_menu`
--

INSERT INTO `food_menu` (`f_id`, `f_name`, `f_price`) VALUES
(1, 'Paneer Butter Masala', 380),
(2, 'Dal Makhani', 320),
(3, 'Aloo Gobi', 280),
(4, 'Palak Paneer', 360),
(5, 'Chole Bhature', 250),
(6, 'Masala Dosa', 150),
(7, 'Tandoori Roti', 40),
(8, 'Garlic Naan', 60),
(9, 'Vegetable Biryani', 300),
(10, 'Samosa (2 pcs)', 80),
(11, 'Vegetable Pakoda', 120),
(12, 'Rajma Chawal', 290),
(13, 'Mutter Paneer', 350),
(14, 'Mushroom Do Pyaza', 310),
(15, 'Tawa Roti', 20),
(16, 'Kadai Paneer', 390),
(17, 'Plain Dosa', 110),
(18, 'Jeera Rice', 220),
(19, 'Veg Fried Rice', 280),
(20, 'Gobi Manchurian', 260);

-- --------------------------------------------------------

--
-- Table structure for table `food_order`
--

CREATE TABLE `food_order` (
  `fid` int(11) NOT NULL,
  `room_no` int(11) NOT NULL,
  `fname` varchar(30) NOT NULL,
  `fprice` double NOT NULL,
  `fqty` int(11) NOT NULL,
  `ftotal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE `rooms` (
  `r_no` int(11) NOT NULL,
  `r_details` varchar(30) NOT NULL,
  `r_price` int(11) NOT NULL,
  `r_availability` varchar(30) NOT NULL,
  `c_aadhar_no` bigint(30) DEFAULT 0,
  `c_days` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rooms`
--

INSERT INTO `rooms` (`r_no`, `r_details`, `r_price`, `r_availability`, `c_aadhar_no`, `c_days`) VALUES
(101, 'Single Bed Non-AC', 1200, 'available', 0, 0),
(102, 'Double Bed Non-AC', 1800, 'booked', 123456781234, 2),
(103, 'Deluxe Room Non-AC', 3000, 'available', 0, 0),
(104, 'Suite Non-AC', 4000, 'available', 0, 0),
(105, 'Luxury Room Non-AC', 5000, 'available', 0, 0),
(106, 'Single Bed Non-AC', 1200, 'available', 0, 0),
(107, 'Double Bed Non-AC', 1800, 'available', 0, 0),
(108, 'Deluxe Room Non-AC', 3000, 'available', 0, 0),
(109, 'Suite Non-AC', 4000, 'available', 0, 0),
(110, 'Luxury Room Non-AC', 5000, 'available', 0, 0),
(111, 'Single Bed AC', 2000, 'available', 0, 0),
(112, 'Double Bed AC', 2500, 'available', 0, 0),
(113, 'Deluxe Room AC', 3500, 'available', 0, 0),
(114, 'Suite AC', 4500, 'available', 0, 0),
(115, 'Luxury Room AC', 5500, 'available', 0, 0),
(116, 'Single Bed AC', 2000, 'available', 0, 0),
(117, 'Double Bed AC', 2500, 'available', 0, 0),
(118, 'Deluxe Room AC', 3500, 'available', 0, 0),
(119, 'Suite AC', 4500, 'available', 0, 0),
(120, 'Luxury Room AC', 5500, 'available', 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`c_id`),
  ADD UNIQUE KEY `c_aadhar_no` (`c_aadhar_no`),
  ADD UNIQUE KEY `c_aadhar_no_2` (`c_aadhar_no`);

--
-- Indexes for table `food_order`
--
ALTER TABLE `food_order`
  ADD KEY `fid` (`fid`) USING BTREE;

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `c_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=87;

--
-- AUTO_INCREMENT for table `food_order`
--
ALTER TABLE `food_order`
  MODIFY `fid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
