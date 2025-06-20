<?php
header("Content-Type: application/json; charset=UTF-8");
require_once "db_connect.php";

try {
    // Bật chế độ hiển thị lỗi SQL
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    
    // Truy vấn lấy danh sách đánh giá
    $stmt = $conn->prepare("SELECT r.reviewID, u.username,r.locationID, l.name AS location_name, 
        r.landscape, r.price, r.qualityOfService, r.people, 
        r.eventActivities, r.accessibility, r.rating, r.comment, r.date
        FROM review r
        JOIN user u ON r.userID = u.userID
        JOIN location l ON r.locationID = l.locationID
        ORDER BY r.date DESC");
    
    $stmt->execute();
    $reviews = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
    if (empty($reviews)) {
        echo json_encode(["success" => false, "message" => "Không có dữ liệu trong bảng review"]);
        exit;
    }
    
    echo json_encode(["success" => true, "reviews" => $reviews], JSON_UNESCAPED_UNICODE);
} catch (PDOException $e) {
    echo json_encode(["success" => false, "message" => "Lỗi truy vấn: " . $e->getMessage()]);
}
?>
