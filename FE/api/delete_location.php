<?php
header("Content-Type: application/json");
require_once "db_connect.php";

// Bật hiển thị lỗi để debug (Chỉ dùng khi test)
error_reporting(E_ALL);
ini_set('display_errors', 1);

// Kiểm tra xem locationID có trong URL không
if (!isset($_GET['locationID'])) {
    echo json_encode(["success" => false, "message" => "Thiếu locationID"]);
    exit;
}

$locationID = intval($_GET['locationID']);

// Kiểm tra locationID hợp lệ
if ($locationID <= 0) {
    echo json_encode(["success" => false, "message" => "locationID không hợp lệ"]);
    exit;
}

try {
    // Kiểm tra xem địa điểm có tồn tại không
    $stmt = $conn->prepare("SELECT * FROM location WHERE locationID = ?");
    $stmt->execute([$locationID]);
    $location = $stmt->fetch(PDO::FETCH_ASSOC);

    if (!$location) {
        echo json_encode(["success" => false, "message" => "Địa điểm không tồn tại"]);
        exit;
    }

    // Tiến hành xóa địa điểm
    $stmt = $conn->prepare("DELETE FROM location WHERE locationID = ?");
    $result = $stmt->execute([$locationID]);

    if ($result) {
        echo json_encode(["success" => true, "message" => "Xóa địa điểm thành công"]);
    } else {
        echo json_encode(["success" => false, "message" => "Lỗi khi xóa địa điểm"]);
    }
} catch (PDOException $e) {
    echo json_encode(["success" => false, "message" => "Lỗi SQL: " . $e->getMessage()]);
}

// Đóng kết nối
$conn = null;
?>
