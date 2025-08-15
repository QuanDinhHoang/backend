-- CREATE DATABASE shopapp;
USE shopapp;

-- Khách hàng khi muốn mua hàng => phải đăng ký tài khoản => bảng users
-- CREATE TABLE users (
--    id INT PRIMARY KEY AUTO_INCREMENT,
--    fullname VARCHAR(100) DEFAULT '',
--    phone_number VARCHAR(10) NOT NULL,
--    address VARCHAR(200) DEFAULT '',
--    password VARCHAR(100) NOT NULL DEFAULT '',
--    created_at DATETIME,
--    updated_at DATETIME,
--    is_active TINYINT(1) DEFAULT 1,
--    date_of_birth DATE,
--    facebook_account_id INT DEFAULT 0,
--    google_account_id INT DEFAULT 0
-- );

-- ALTER TABLE users ADD COLUMN role_id INT;
-- CREATE TABLE roles(
--	id INT PRIMARY KEY,
--    name VARCHAR(20) NOT NULL
-- );
-- ALTER TABLE users ADD FOREIGN KEY (role_id) REFERENCES roles (id);

-- CREATE TABLE tokens (
 --   id INT PRIMARY KEY AUTO_INCREMENT,
 --   token VARCHAR(255) UNIQUE NOT NULL,
 --   token_type VARCHAR(50) NOT NULL,
 --   expiration_date DATETIME,
 --   revoked TINYINT(1) NOT NULL,
 --   expired TINYINT(1) NOT NULL,
 --   user_id INT,
--    FOREIGN KEY (user_id) REFERENCES users(id)
-- );

-- hỗ trợ đăng nhập từ Facebook và Google (This table supports login through social networks like Facebook and Google. It links a user account in your system to a social media account.)
-- CREATE TABLE social_accounts (
--    id INT PRIMARY KEY AUTO_INCREMENT,
--    provider VARCHAR(20) NOT NULL COMMENT 'Tên nhà social network',
--    provider_id VARCHAR(50) NOT NULL,
--    email VARCHAR(150) NOT NULL COMMENT 'Email tài khoản',
--    name VARCHAR(100) NOT NULL COMMENT 'Tên người dùng',
--    user_id INT,
--    FOREIGN KEY (user_id) REFERENCES users(id)
-- );

-- Bảng danh mục sản phẩm (Category)
-- CREATE TABLE categories (
--    id INT PRIMARY KEY AUTO_INCREMENT,
--    name VARCHAR(100) NOT NULL DEFAULT '' COMMENT 'Tên danh mục, vd: đồ điện tử'
-- );

 -- CREATE TABLE products (
 --   id INT PRIMARY KEY AUTO_INCREMENT,
 --   name VARCHAR(350) COMMENT 'Tên sản phẩm', -- Tên hiển thị cho người dùng
 --   price FLOAT NOT NULL CHECK(price >=0), -- Giá của sản phẩm
 --   thumbnail VARCHAR(300) DEFAULT '', -- Link ảnh sản phẩm
 --   description LONGTEXT, -- Mô tả chi tiết
 --   created_at TIMESTAMP, -- Thời gian tạo
 --   updated_at TIMESTAMP, -- Thời gian cập nhật
 --   category_id INT, -- a product is from a category
 --   FOREIGN KEY (category_id) REFERENCES categories(id)
 -- );

-- tao them bang product_images de co the gui len database nhieu anh cho 1 product thay vi 1 product chi 1 anh(thumbnail) nhu truoc
 -- CREATE TABLE product_images(
--	id INT PRIMARY KEY AUTO_INCREMENT,
--    product_id INT, -- 1 product se co nhieu anh
--    FOREIGN KEY(product_id) REFERENCES products (id),
--    CONSTRAINT fk_product_images_product_id
--		FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE, -- co nghia neu 1 product bi xoa trong bang csdl thi cac row o product images cung bi xoa theo
--	image_url VARCHAR(300) -- chi la ten cua anh ko phai duong link den anh nhu thumbnail cua bang products
 -- );

-- orders
-- CREATE TABLE orders(
--	id INT PRIMARY KEY AUTO_INCREMENT,
--    user_id INT,
--    FOREIGN KEY (user_id) REFERENCES users(id),
--    fullname VARCHAR(100),
--    email VARCHAR(100),
--    phone_number VARCHAR(20) NOT NULL,
--	address VARCHAR(200) NOT NULL,
--    note VARCHAR(100) DEFAULT '',
--    order_date DATETIME DEFAULT current_timestamp,
--    status VARCHAR(20),
--    total_money FLOAT CHECK(total_money>=0)
-- );
-- ALTER TABLE orders ADD COLUMN shipping_method VARCHAR(100) COMMENT 'Phương thức vận chuyển';
-- ALTER TABLE orders ADD COLUMN shipping_address VARCHAR(200) COMMENT 'Địa chỉ giao hàng';
-- ALTER TABLE orders ADD COLUMN shipping_date DATE COMMENT 'Ngày giao hàng';
-- ALTER TABLE orders ADD COLUMN tracking_number VARCHAR(100) COMMENT 'Mã theo dõi đơn hàng';
-- ALTER TABLE orders ADD COLUMN payment_method VARCHAR(100) COMMENT 'Phương thức thanh toán';

-- Xoa 1 don hang => xoa mem(ko co xoa thang no ma chi disable no thoi) => them truong active
-- ALTER TABLE orders ADD COLUMN active TINYINT(1);

-- Trang thai don hang chi dc phep nhan "mot so gia tri cu the"
-- ALTER TABLE orders
-- MODIFY COLUMN status ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled')
-- COMMENT 'Trang thai dat hang';

-- one order can have many order details(ex: 1 order has two pc, 3 iphone, 1 table ..., those are order details
-- CREATE TABLE order_details(
--	id INT PRIMARY KEY AUTO_INCREMENT,
--    order_id INT,
--    FOREIGN KEY (order_id) REFERENCES orders(id),
--    product_id INT,
--    FOREIGN KEY (product_id) REFERENCES products(id),
--    price FLOAT CHECK(price >= 0),
--    number_of_products INT CHECK(number_of_products > 0),
--    total_money FLOAT CHECK(total_money >= 0),
--    color VARCHAR(20) DEFAULT ''
-- );

-- Thêm trường voucher_id vào bảng orders để liên kết với voucher
-- ALTER TABLE orders ADD COLUMN voucher_id INT;
-- ALTER TABLE orders ADD FOREIGN KEY (voucher_id) REFERENCES vouchers(id);

-- 1. Tạo bảng brands
CREATE TABLE brands (
    id BIGINT NOT NULL AUTO_INCREMENT,
    brand_img VARCHAR(500) DEFAULT NULL,
    active BIT(1) NOT NULL,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- 2. Thêm brand_id vào products
-- ALTER TABLE products
-- ADD COLUMN brand_id BIGINT,
-- ADD CONSTRAINT fk_products_brands
--    FOREIGN KEY (brand_id) REFERENCES brands(id);

