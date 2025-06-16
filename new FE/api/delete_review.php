<?php
header("Content-Type: application/json");
require_once "db_connect.php";

// Bật hiển thị lỗi để debug (Chỉ dùng khi test)
error_reporting(E_ALL);
ini_set('display_errors', 1);

// Kiểm tra reviewID có trong URL không
if (!isset($_GET['reviewID'])) {
    echo json_encode(["success" => false, "message" => "Thiếu reviewID"]);
    exit;
}

$reviewID = intval($_GET['reviewID']);

// Kiểm tra reviewID hợp lệ
if ($reviewID <= 0) {
    echo json_encode(["success" => false, "message" => "reviewID không hợp lệ"]);
    exit;
}

try {
    // Kiểm tra xem review có tồn tại không
    $stmt = $conn->prepare("SELECT * FROM review WHERE reviewID = ?");
    $stmt->execute([$reviewID]);
    $review = $stmt->fetch(PDO::FETCH_ASSOC);

    if (!$review) {
        echo json_encode(["success" => false, "message" => "Đánh giá không tồn tại"]);
        exit;
    }

    // Tiến hành xóa review
    $stmt = $conn->prepare("DELETE FROM review WHERE reviewID = ?");
    $result = $stmt->execute([$reviewID]);

    if ($result) {
        echo json_encode(["success" => true, "message" => "Xóa đánh giá thành công"]);
    } else {
        echo json_encode(["success" => false, "message" => "Lỗi khi xóa đánh giá"]);
    }
} catch (PDOException $e) {
    echo json_encode(["success" => false, "message" => "Lỗi SQL: " . $e->getMessage()]);
}

// Đóng kết nối
$conn = null;
?>
