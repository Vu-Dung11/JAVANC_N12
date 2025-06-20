<?php
header("Content-Type: application/json; charset=UTF-8");
require_once "db_connect.php"; // Kết nối SQLite

// Đọc JSON từ request
$data = json_decode(file_get_contents("php://input"), true);

if (!$data || !isset($data["email"]) || !isset($data["password"])) {
    echo json_encode(["success" => false, "message" => "Thiếu dữ liệu đăng nhập"]);
    exit;
}

// Gán biến
$email = trim($data["email"]);
$password = trim($data["password"]);

try {
    // Kiểm tra email có tồn tại không
    $stmt = $conn->prepare("SELECT userID, username, email, fullName, phoneNumber, role, password FROM user WHERE email = :email");
    $stmt->bindValue(":email", $email, PDO::PARAM_STR);
    $stmt->execute();
    $user = $stmt->fetch(PDO::FETCH_ASSOC);

    if (!$user) {
        echo json_encode(["success" => false, "message" => "Email không tồn tại"]);
        exit;
    }

    // Kiểm tra mật khẩu
    if (!password_verify($password, $user["password"])) {
        echo json_encode(["success" => false, "message" => "Sai mật khẩu"]);
        exit;
    }

    // Xóa password trước khi trả về JSON
    unset($user["password"]);

    // Trả về dữ liệu user
    echo json_encode( ["user" => $user]);
} catch (PDOException $e) {
    echo json_encode(["success" => false, "message" => "Lỗi hệ thống: " . $e->getMessage()]);
}
?>
