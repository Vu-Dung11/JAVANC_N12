<?php
header("Content-Type: application/json");
require_once "db_connect.php"; // Kết nối SQLite

// Lấy dữ liệu JSON từ request
$data = json_decode(file_get_contents("php://input"), true);

// Kiểm tra dữ liệu đầu vào
if (!isset($data["username"]) || !isset($data["password"]) || !isset($data["email"]) ||
    !isset($data["fullName"]) || !isset($data["phoneNumber"]) || !isset($data["role"])) {
    echo json_encode(["success" => false, "message" => "Thiếu dữ liệu"]);
    exit;
}

// Gán biến
$username = $data["username"];
$password = password_hash($data["password"], PASSWORD_BCRYPT); // Mã hóa mật khẩu
$email = $data["email"];
$fullName = $data["fullName"];
$phoneNumber = $data["phoneNumber"];
$role = ($data["role"] === "Admin") ? "Admin" : "User"; // Chỉ nhận 'Admin' hoặc 'User'

try {
    // Tạo truy vấn SQL
    $stmt = $conn->prepare("INSERT INTO user (username, password, email, fullName, phoneNumber, role)
                            VALUES (:username, :password, :email, :fullName, :phoneNumber, :role)");

    // Gán giá trị vào câu lệnh SQL
    $stmt->bindValue(":username", $username, PDO::PARAM_STR);
    $stmt->bindValue(":password", $password, PDO::PARAM_STR);
    $stmt->bindValue(":email", $email, PDO::PARAM_STR);
    $stmt->bindValue(":fullName", $fullName, PDO::PARAM_STR);
    $stmt->bindValue(":phoneNumber", $phoneNumber, PDO::PARAM_STR);
    $stmt->bindValue(":role", $role, PDO::PARAM_STR);

    // Thực thi truy vấn
    if ($stmt->execute()) {
        echo json_encode(["success" => true, "message" => "Thêm user thành công"]);
    } else {
        echo json_encode(["success" => false, "message" => "Lỗi khi thêm user"]);
    }
} catch (PDOException $e) {
    echo json_encode(["success" => false, "message" => "Lỗi: " . $e->getMessage()]);
}
?>
