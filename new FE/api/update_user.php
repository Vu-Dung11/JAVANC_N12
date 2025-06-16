<?php
header("Content-Type: application/json");
include 'db_connect.php';

$json = file_get_contents('php://input');
$data = json_decode($json, true);

// Kiểm tra dữ liệu đầu vào
if (!isset($data['userID'], $data['username'], $data['email'], $data['fullName'], $data['phoneNumber'], $data['role'])) {
    echo json_encode(["success" => false, "message" => "Thiếu dữ liệu bắt buộc"]);
    exit;
}

// Lấy dữ liệu
$userID = $data['userID'];
$username = $data['username'];
$email = $data['email'];
$fullName = $data['fullName'];
$phoneNumber = $data['phoneNumber'];
$role = $data['role'];
$password = !empty($data['password']) ? password_hash($data['password'], PASSWORD_DEFAULT) : null;

// Kiểm tra dữ liệu hợp lệ
if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
    echo json_encode(["success" => false, "message" => "Email không hợp lệ"]);
    exit;
}

if (!preg_match('/^[0-9]{10}$/', $phoneNumber)) {
    echo json_encode(["success" => false, "message" => "Số điện thoại không hợp lệ"]);
    exit;
}

if (!in_array($role, ['User', 'Admin'])) {
    echo json_encode(["success" => false, "message" => "Vai trò không hợp lệ"]);
    exit;
}

try {
    // Xây dựng câu SQL với đúng thứ tự
    if ($password !== null) {
        $sql = "UPDATE user SET username = ?, password = ?, email = ?, fullName = ?, phoneNumber = ?, role = ? WHERE userID = ?";
        $params = [$username, $password, $email, $fullName, $phoneNumber, $role, $userID];
    } else {
        $sql = "UPDATE user SET username = ?, email = ?, fullName = ?, phoneNumber = ?, role = ? WHERE userID = ?";
        $params = [$username, $email, $fullName, $phoneNumber, $role, $userID];
    }

    $stmt = $conn->prepare($sql);
    $stmt->execute($params);

    echo json_encode(["success" => true, "message" => "Cập nhật người dùng thành công"]);
} catch (PDOException $e) {
    echo json_encode(["success" => false, "message" => "Lỗi cập nhật: " . $e->getMessage()]);
}
?>
