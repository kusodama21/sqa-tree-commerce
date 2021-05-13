SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sqa2`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `id` int UNSIGNED NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `address` text NOT NULL,
  `roleId` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`id`, `username`, `password`, `fullname`, `address`, `roleId`) VALUES
(1, 'admin', '$2a$10$OXbtb6vj4EcQ8CgjUJeqAuyL6tVjxSr6cILKpiHJT1GLZkz1i9y7u', 'Tà Minh Bảo', '30 Phố Bên Nhà', 1),
(2, 'cust1', '$2a$10$KYBkAGgyonWurg.e/0c7ieG.L6GqFJ2b3HpT1JNRpHJVXzCV.PXe6', 'Duyễn Hiển Vinh', '25 Phố Ở Xa', 2),
(4, 'cust2', '$2a$10$7NnDQGB3AP.hTTsqlsKhbubTbVGjXcwZ0rIb95MfVCs153bAI9umi', 'Hoãn Giải Vân', '30 Phiến Luệ', 2),
(5, 'cust3', '$2a$10$7NnDQGB3AP.hTTsqlsKhbubTbVGjXcwZ0rIb95MfVCs153bAI9umi', 'Toàn Hệ Lôi', '25 Hãn Phiêu', 2),
(6, 'custspec1', '$2a$10$s3JLiGSa6L3KWxhLhABxXuUoatgO85r.QRa92rxGZPSytV8dQPrXq', 'Suy Thận', '25 Hoãn Khởi', 2);

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `accountId` int UNSIGNED NOT NULL,
  `couponCode` char(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`accountId`, `couponCode`) VALUES
(1, NULL),
(2, NULL),
(4, NULL),
(5, NULL),
(6, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `cart_item`
--

CREATE TABLE `cart_item` (
  `accountId` int UNSIGNED NOT NULL,
  `productId` int UNSIGNED NOT NULL,
  `quantity` int UNSIGNED NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int UNSIGNED NOT NULL,
  `name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`) VALUES
(2, 'Cactus 2'),
(10, 'Chana'),
(1, 'Fheng Shui Tree'),
(4, 'Office Tree'),
(3, 'Succulent Tree');

-- --------------------------------------------------------

--
-- Table structure for table `coupon`
--

CREATE TABLE `coupon` (
  `code` char(6) NOT NULL,
  `effect` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `coupon`
--

INSERT INTO `coupon` (`code`, `effect`) VALUES
('223431', '10'),
('ABCDEF', '55.55'),
('ABui10', '20'),
('ACHJF2', '23.23');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int UNSIGNED NOT NULL,
  `accountId` int UNSIGNED DEFAULT NULL,
  `couponCode` char(6) DEFAULT NULL,
  `totalPrice` varchar(30) NOT NULL,
  `finalPrice` varchar(30) NOT NULL,
  `state` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `accountId`, `couponCode`, `totalPrice`, `finalPrice`, `state`) VALUES
(6, 2, 'ABCDEF', '5775020', '2566996.39', 1),
(8, 2, NULL, '5030000', '5030000', NULL),
(9, 2, NULL, '300000', '300000', 0),
(10, 2, 'ABCDEF', '3705000', '1646872.5', NULL),
(11, 6, 'ABCDEF', '3515060', '1562444.17', 1);

-- --------------------------------------------------------

--
-- Table structure for table `order_line`
--

CREATE TABLE `order_line` (
  `orderId` int UNSIGNED NOT NULL,
  `productId` int UNSIGNED DEFAULT NULL,
  `quantity` int UNSIGNED NOT NULL,
  `totalPrice` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_line`
--

INSERT INTO `order_line` (`orderId`, `productId`, `quantity`, `totalPrice`) VALUES
(6, 3, 3, '1170000'),
(6, 4, 5, '3750000'),
(6, 9, 2, '855020'),
(8, 4, 5, '3750000'),
(8, 10, 4, '1280000'),
(9, 1, 1, '270000'),
(9, NULL, 1, '30000'),
(10, 6, 5, '2025000'),
(10, 11, 3, '1680000'),
(11, 2, 5, '950000'),
(11, 9, 6, '2565060');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int UNSIGNED NOT NULL,
  `name` varchar(50) NOT NULL,
  `price` varchar(20) NOT NULL,
  `image` varchar(50) DEFAULT NULL,
  `description` text,
  `categoryId` int UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `name`, `price`, `image`, `description`, `categoryId`) VALUES
(1, 'Prince Charm Tree', '270000', 'pic1.png', 'Cây hoàng tử, chắc thế?', 1),
(2, 'Poinsettia', '190000', 'pic2.png', NULL, 1),
(3, 'Flowers Of Beauty', '390000', 'pic3.png', 'Hẳn là hoa của vẻ đẹp luôn đã quá trời', 1),
(4, 'Daffodill', '750000', 'pic4.png', 'Đôi lúc thắc mắc đặt tên cây kỳ kỳ', 1),
(5, 'The Holly Tree', '455000', 'pic5.png', 'Xương rồng tên thần thành luôn', 2),
(6, 'Water Lilly', '405000', 'pic6.png', 'Cái tên làm ta gợi nhớ đến một ca sĩ lâu rồi không nghe', 3),
(7, 'Tulip', '288000', 'pic7.png', 'Bình thường', 3),
(8, 'Primrose', '304000', 'pic8.png', 'Tên cũng hay mà, nhưng sao lại là prim?', 3),
(9, 'Crocus', '427510', 'pic9.png', 'Nó lại ngầu quá bạn ơi tên oách xì ngầu', 2),
(10, 'Cryssanthemum', '320000', 'pic10.png', 'Cái tên khó đọc nhất trong đồng bọn', 4),
(11, 'Blue Bell', '560000', 'pic11.png', 'Chuông xanh, hmm, this is so deep bruh', 4),
(12, 'Fox Glove', '441000', 'pic12.png', 'Nhìn giống cái pháo đài hơn là cái găng tay', 4);

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` int UNSIGNED NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_CUSTOMER');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `account_username` (`username`),
  ADD KEY `account_ibfk_1` (`roleId`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD UNIQUE KEY `accountId` (`accountId`),
  ADD KEY `couponCode` (`couponCode`);

--
-- Indexes for table `cart_item`
--
ALTER TABLE `cart_item`
  ADD UNIQUE KEY `productId` (`productId`,`accountId`),
  ADD KEY `accountId` (`accountId`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `category_name` (`name`);

--
-- Indexes for table `coupon`
--
ALTER TABLE `coupon`
  ADD PRIMARY KEY (`code`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `accountId` (`accountId`),
  ADD KEY `couponCode` (`couponCode`);

--
-- Indexes for table `order_line`
--
ALTER TABLE `order_line`
  ADD KEY `orderId` (`orderId`),
  ADD KEY `productId` (`productId`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `product_name` (`name`),
  ADD UNIQUE KEY `product_image_UNIQUE` (`image`),
  ADD KEY `product_ibfk_1` (`categoryId`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `role_name` (`name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `account_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`);

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`accountId`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`couponCode`) REFERENCES `coupon` (`code`) ON DELETE SET NULL ON UPDATE RESTRICT;

--
-- Constraints for table `cart_item`
--
ALTER TABLE `cart_item`
  ADD CONSTRAINT `cart_item_ibfk_1` FOREIGN KEY (`accountId`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `cart_item_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT;

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`accountId`) REFERENCES `account` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`couponCode`) REFERENCES `coupon` (`code`) ON DELETE SET NULL ON UPDATE RESTRICT;

--
-- Constraints for table `order_line`
--
ALTER TABLE `order_line`
  ADD CONSTRAINT `order_line_ibfk_1` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `order_line_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `product` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT;

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`categoryId`) REFERENCES `category` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
