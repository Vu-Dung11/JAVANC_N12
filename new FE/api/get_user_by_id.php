<?php
header("Content-Type: application/json; charset=UTF-8");
require_once "db_connect.php";

// Kiểm tra userID trên URL
if (!isset($_GET['userID'])) {
    echo json_encode(["success" => false, "message" => "Thiếu userID"]);
    exit;
}

$userID = intval($_GET['userID']);

try {
    $stmt = $conn->prepare("SELECT * FROM user WHERE userID = ?");
    $stmt->execute([$userID]);
    $user = $stmt->fetch(PDO::FETCH_ASSOC);

    if ($user) {
        echo json_encode(["success" => true, "user" => $user], JSON_UNESCAPED_UNICODE);
    } else {
        echo json_encode(["success" => false, "message" => "Người dùng không tồn tại"]);
    }
} catch (PDOException $e) {
    echo json_encode(["success" => false, "message" => "Lỗi truy vấn: " . $e->getMessage()]);
}

$conn = null;
?>
