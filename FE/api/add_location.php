<?php
header("Content-Type: application/json; charset=UTF-8");
require_once 'db_connect.php';

$json = file_get_contents('php://input');
$data = json_decode($json, true);

// Kiểm tra xem JSON có hợp lệ không
if (json_last_error() !== JSON_ERROR_NONE) {
    echo json_encode(["success" => false, "message" => "Lỗi JSON: " . json_last_error_msg()]);
    exit;
}

// Kiểm tra các trường bắt buộc
if (!isset($data['locationName'], $data['locationDescription'], $data['locationAddress'])) {
    echo json_encode(["success" => false, "message" => "Thiếu dữ liệu đầu vào bắt buộc (locationName, locationDescription, locationAddress)"]);
    exit;
}

// Lấy dữ liệu từ JSON và xử lý mảng
$locationName = $data['locationName'];
$locationDescription = $data['locationDescription'];
$locationImage = $data['locationImage'] ?? null;
$locationRating = isset($data['locationRating']) ? (float)$data['locationRating'] : 0;
$locationReview = $data['locationReview'] ?? null;
$locationAddress = $data['locationAddress'];
$locationCostLevel = $data['locationCostLevel'] ?? null;

// Hàm chuyển mảng thành chuỗi với dấu phẩy
function arrayToString($array) {
    return is_array($array) ? implode(",", $array) : $array;
}

$categories = arrayToString($data['categories'] ?? null);
$activities = arrayToString($data['activities'] ?? null);
$foodOptions = arrayToString($data['foodOptions'] ?? null);
$bestSeason = arrayToString($data['bestSeason'] ?? null);

try {
    $sql = "INSERT INTO location (locationName, locationDescription, locationImage, locationRating, locationReview, locationAddress, locationCostLevel, categories, activities, foodOptions, bestSeason)
            VALUES (:locationName, :locationDescription, :locationImage, :locationRating, :locationReview, :locationAddress, :locationCostLevel, :categories, :activities, :foodOptions, :bestSeason)";

    $stmt = $conn->prepare($sql);
    $stmt->bindValue(":locationName", $locationName, PDO::PARAM_STR);
    $stmt->bindValue(":locationDescription", $locationDescription, PDO::PARAM_STR);
    $stmt->bindValue(":locationImage", $locationImage, PDO::PARAM_STR);
    $stmt->bindValue(":locationRating", $locationRating, PDO::PARAM_STR);
    $stmt->bindValue(":locationReview", $locationReview, PDO::PARAM_STR);
    $stmt->bindValue(":locationAddress", $locationAddress, PDO::PARAM_STR);
    $stmt->bindValue(":locationCostLevel", $locationCostLevel, PDO::PARAM_STR);
    $stmt->bindValue(":categories", $categories, PDO::PARAM_STR);
    $stmt->bindValue(":activities", $activities, PDO::PARAM_STR);
    $stmt->bindValue(":foodOptions", $foodOptions, PDO::PARAM_STR);
    $stmt->bindValue(":bestSeason", $bestSeason, PDO::PARAM_STR);

    if ($stmt->execute()) {
        $lastId = $conn->lastInsertId();
        echo json_encode([
            "success" => true,
            "message" => "Đã thêm địa điểm thành công",
            "locationID" => $lastId
        ]);
    } else {
        echo json_encode(["success" => false, "message" => "Thêm địa điểm thất bại"]);
    }
} catch (PDOException $e) {
    echo json_encode(["success" => false, "message" => "Lỗi SQL: " . $e->getMessage()]);
}

// Đóng kết nối
$conn = null;
?>