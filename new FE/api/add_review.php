<?php
header("Content-Type: application/json");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type");

require_once 'db_connect.php';

$json = file_get_contents('php://input');
$data = json_decode($json, true);

// Kiểm tra nếu thiếu dữ liệu
if (!isset($data['userID'], $data['locationID'], $data['landscape'], $data['price'],
          $data['qualityOfService'], $data['people'], $data['eventActivities'],
          $data['accessibility'], $data['rating'], $data['comment'])) {
    echo json_encode(["success" => false, "message" => "Thiếu dữ liệu đầu vào"]);
    exit;
}

$userID = $data['userID']; // Giữ nguyên dưới dạng String, SQLite sẽ tự xử lý
$locationID = (int)$data['locationID'];
$landscape = (int)$data['landscape'];
$price = (int)$data['price'];
$qualityOfService = (int)$data['qualityOfService'];
$people = (int)$data['people'];
$eventActivities = (int)$data['eventActivities'];
$accessibility = (int)$data['accessibility'];
$rating = (float)$data['rating'];
$comment = $data['comment'];
// Lấy date từ dữ liệu gửi lên, nếu không có thì để SQLite tự động tạo
$date = isset($data['date']) ? $data['date'] : null;

try {
    $sql = "INSERT INTO review (userID, locationID, landscape, price, qualityOfService, people, eventActivities, accessibility, rating, comment, date)
            VALUES (:userID, :locationID, :landscape, :price, :qualityOfService, :people, :eventActivities, :accessibility, :rating, :comment, :date)";

    $stmt = $conn->prepare($sql);
    $stmt->bindValue(":userID", $userID, PDO::PARAM_STR); // Sử dụng PARAM_STR cho userID
    $stmt->bindValue(":locationID", $locationID, PDO::PARAM_INT);
    $stmt->bindValue(":landscape", $landscape, PDO::PARAM_INT);
    $stmt->bindValue(":price", $price, PDO::PARAM_INT);
    $stmt->bindValue(":qualityOfService", $qualityOfService, PDO::PARAM_INT);
    $stmt->bindValue(":people", $people, PDO::PARAM_INT);
    $stmt->bindValue(":eventActivities", $eventActivities, PDO::PARAM_INT);
    $stmt->bindValue(":accessibility", $accessibility, PDO::PARAM_INT);
    $stmt->bindValue(":rating", $rating, PDO::PARAM_STR);
    $stmt->bindValue(":comment", $comment, PDO::PARAM_STR);
    // Nếu date không được gửi lên, SQLite sẽ dùng DEFAULT CURRENT_TIMESTAMP
    $stmt->bindValue(":date", $date, $date ? PDO::PARAM_STR : PDO::PARAM_NULL);

    if ($stmt->execute()) {
        echo json_encode(["success" => true, "message" => "Đánh giá đã được thêm thành công"]);
    } else {
        echo json_encode(["success" => false, "message" => "Thêm đánh giá thất bại"]);
    }
} catch (PDOException $e) {
    echo json_encode(["success" => false, "message" => "Lỗi SQL: " . $e->getMessage()]);
}
?>