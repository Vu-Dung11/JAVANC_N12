<?php
header("Content-Type: application/json; charset=UTF-8");
require_once "db_connect.php";

try {
    // Truy vấn tất cả các cột từ bảng location
    $stmt = $conn->query("SELECT locationID, locationName, locationImage, locationDescription, locationRating, locationReview, locationAddress, locationCostLevel, categories, activities, foodOptions, bestSeason FROM location");
    $locations = $stmt->fetchAll(PDO::FETCH_ASSOC);

    // Chuyển chuỗi thành mảng cho các trường categories, activities, foodOptions, bestSeason
    foreach ($locations as &$location) {
        $location['categories'] = !empty($location['categories']) ? explode(",", $location['categories']) : [];
        $location['activities'] = !empty($location['activities']) ? explode(",", $location['activities']) : [];
        $location['foodOptions'] = !empty($location['foodOptions']) ? explode(",", $location['foodOptions']) : [];
        $location['bestSeason'] = !empty($location['bestSeason']) ? explode(",", $location['bestSeason']) : [];
    }
    unset($location); // Xóa tham chiếu

    // Trả về JSON với định dạng dễ đọc
    echo json_encode(["success" => true, "locations" => $locations], JSON_PRETTY_PRINT);
} catch (PDOException $e) {
    echo json_encode(["success" => false, "message" => "Lỗi truy vấn: " . $e->getMessage()], JSON_PRETTY_PRINT);
}

// Đóng kết nối
$conn = null;
?>