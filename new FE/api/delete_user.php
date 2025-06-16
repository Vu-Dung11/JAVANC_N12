<?php
header("Content-Type: application/json");
require_once "db_connect.php";

try {
    // Kiểm tra xem userID có được truyền qua URL không
    if (!isset($_GET['userID'])) {
        echo json_encode(["success" => false, "message" => "Thiếu userID"]);
        exit;
    }

    $userID = intval($_GET['userID']);

    // Kiểm tra xem người dùng có tồn tại không
    $stmt = $conn->prepare("SELECT * FROM user WHERE userID = ?");
    $stmt->execute([$userID]);
    $user = $stmt->fetch();

    if (!$user) {
        echo json_encode(["success" => false, "message" => "Người dùng không tồn tại"]);
        exit;
    }

    // Tiến hành xóa người dùng
    $stmt = $conn->prepare("DELETE FROM user WHERE userID = ?");
    $stmt->execute([$userID]);

    echo json_encode(["success" => true, "message" => "Xóa người dùng thành công"]);
} catch (PDOException $e) {
    echo json_encode(["success" => false, "message" => "Lỗi xóa người dùng: " . $e->getMessage()]);
}

$conn = null;
?>
