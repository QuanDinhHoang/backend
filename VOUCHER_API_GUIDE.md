# Hướng dẫn sử dụng API Voucher (Đơn giản)

## Cài đặt Database

1. Chạy file `voucher_table.sql` để tạo bảng vouchers và dữ liệu mẫu:
```sql
source voucher_table.sql
```

## API Endpoints

### 1. Lấy danh sách voucher (có phân trang)
```
GET /api/v1/vouchers?page=0&limit=10
```

### 2. Lấy danh sách voucher có sẵn
```
GET /api/v1/vouchers/available
```

### 3. Lấy voucher theo ID
```
GET /api/v1/vouchers/{id}
```

### 4. Tạo voucher mới
```
POST /api/v1/vouchers
Content-Type: application/json

{
    "name": "Giảm giá 100K",
    "value": 100000.00,
    "expiredDate": "2024-12-31",
    "quantity": 50
}
```

### 5. Cập nhật voucher
```
PUT /api/v1/vouchers/{id}
Content-Type: application/json

{
    "name": "Giảm giá 150K",
    "value": 150000.00,
    "expiredDate": "2024-12-31",
    "quantity": 30
}
```

### 6. Xóa voucher
```
DELETE /api/v1/vouchers/{id}
```

### 7. Tìm kiếm voucher theo tên
```
GET /api/v1/vouchers/search?name=giảm giá
```

## Các trường dữ liệu

### VoucherDTO
- `name`: Tên voucher (bắt buộc)
- `value`: Giá trị voucher (bắt buộc, phải lớn hơn 0)
- `expiredDate`: Ngày hết hạn (bắt buộc, phải trong tương lai)
- `quantity`: Số lượng voucher (bắt buộc, phải lớn hơn 0)

## Ví dụ sử dụng

### Tạo voucher giảm 50K
```json
{
    "name": "Giảm giá 50K",
    "value": 50000.00,
    "expiredDate": "2024-12-31",
    "quantity": 100
}
```

### Tạo voucher giảm 200K
```json
{
    "name": "Giảm giá 200K",
    "value": 200000.00,
    "expiredDate": "2024-12-31",
    "quantity": 25
}
```

## Validation Rules

- Tên voucher không được để trống
- Giá trị voucher phải lớn hơn 0
- Ngày hết hạn phải trong tương lai
- Số lượng phải lớn hơn 0
- Voucher chỉ hiển thị khi còn hạn và còn số lượng

## Database Schema

```sql
CREATE TABLE vouchers (
   id INT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(200) NOT NULL,
   value DECIMAL(10,2) NOT NULL,
   expired_date DATE NOT NULL,
   quantity INT NOT NULL DEFAULT 1,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## Mối quan hệ với Orders

Voucher có mối quan hệ **1-nhiều** với Orders:
- 1 voucher có thể được sử dụng bởi nhiều đơn hàng
- Mỗi đơn hàng chỉ có thể sử dụng 1 voucher
- Khi sử dụng voucher, số lượng sẽ giảm đi 1
