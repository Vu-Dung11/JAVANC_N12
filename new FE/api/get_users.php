<?php
header("Content-Type: application/json; charset=UTF-8");
require_once "db_connect.php"; // Kết nối SQLite

try {
    // Truy vấn danh sách user
    $stmt = $conn->query("SELECT userID, username, email, fullName, phoneNumber, role FROM user");
    $users = $stmt->fetchAll(PDO::FETCH_ASSOC); // Lấy kết quả dạng mảng

    // Trả kết quả JSON
    echo json_encode(["success" => true, "users" => $users], JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
} catch (PDOException $e) {
    echo json_encode(["success" => false, "message" => "Lỗi truy vấn: " . $e->getMessage()]);
}
?>
// <?php
// echo json_encode(["success" => true, "message" => "API đang hoạt động"]);
// ?>
