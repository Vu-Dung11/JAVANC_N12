<?php
header("Content-Type: application/json");
require_once "db_connect.php";

// Bật hiển thị lỗi để debug (Chỉ dùng khi test)
error_reporting(E_ALL);
ini_set('display_errors', 1);

// Kiểm tra reviewID trên URL
if (!isset($_GET['reviewID'])) {
    echo json_encode(["success" => false, "message" => "Thiếu reviewID"]);
    exit;
}

$reviewID = intval($_GET['reviewID']);
if ($reviewID <= 0) {
    echo json_encode(["success" => false, "message" => "reviewID không hợp lệ"]);
    exit;
}

// Nhận dữ liệu JSON
$json = file_get_contents("php://input");
$data = json_decode($json, true);

// Kiểm tra dữ liệu hợp lệ
if (!isset($data['userID'], $data['locationID'], $data['landscape'], $data['price'], $data['qualityOfService'], 
          $data['people'], $data['eventActivities'], $data['accessibility'], $data['rating'], $data['comment'], $data['date'])) {
    echo json_encode(["success" => false, "message" => "Thiếu dữ liệu bắt buộc"]);
    exit;
}

// Lấy dữ liệu
$userID = intval($data['userID']);
$locationID = intval($data['locationID']);
$landscape = intval($data['landscape']);
$price = intval($data['price']);
$qualityOfService = intval($data['qualityOfService']);
$people = intval($data['people']);
$eventActivities = intval($data['eventActivities']);
$accessibility = intval($data['accessibility']);
$rating = floatval($data['rating']);
$comment = trim($data['comment']);
$date = trim($data['date']);

try {
    // Kiểm tra review có tồn tại không
    $stmt = $conn->prepare("SELECT * FROM review WHERE reviewID = ?");
    $stmt->execute([$reviewID]);
    $review = $stmt->fetch(PDO::FETCH_ASSOC);

    if (!$review) {
        echo json_encode(["success" => false, "message" => "Đánh giá không tồn tại"]);
        exit;
    }

    // Tiến hành UPDATE
    $stmt = $conn->prepare("UPDATE review SET userID = ?, locationID = ?, landscape = ?, price = ?, qualityOfService = ?, people = ?, eventActivities = ?, accessibility = ?, rating = ?, comment = ?, date = ? WHERE reviewID = ?");
    $result = $stmt->execute([$userID, $locationID, $landscape, $price, $qualityOfService, $people, $eventActivities, $accessibility, $rating, $comment, $date, $reviewID]);

    if ($result) {
        echo json_encode(["success" => true, "message" => "Cập nhật đánh giá thành công"]);
    } else {
        echo json_encode(["success" => false, "message" => "Lỗi khi cập nhật đánh giá"]);
    }
} catch (PDOException $e) {
    echo json_encode(["success" => false, "message" => "Lỗi SQL: " . $e->getMessage()]);
}

// Đóng kết nối
$conn = null;
?>
