-- Tạo bảng vouchers đơn giản
CREATE TABLE vouchers (
   id INT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(200) NOT NULL COMMENT 'Tên voucher',
   value DECIMAL(10,2) NOT NULL COMMENT 'Giá trị voucher',
   expired_date DATE NOT NULL COMMENT 'Ngày hết hạn',
   quantity INT NOT NULL DEFAULT 1 COMMENT 'Số lượng voucher',
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Thêm một số voucher mẫu
INSERT INTO vouchers (name, value, expired_date, quantity) VALUES
('Giảm giá 50K', 50000.00, '2024-12-31', 100),
('Giảm giá 100K', 100000.00, '2024-12-31', 50),
('Giảm giá 200K', 200000.00, '2024-12-31', 25),
('Giảm giá 500K', 500000.00, '2024-12-31', 10);
