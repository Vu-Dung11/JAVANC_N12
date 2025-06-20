<?php
header("Content-Type: application/json; charset=UTF-8");
require_once 'db_connect.php';

// Nhận dữ liệu JSON từ yêu cầu
$json = file_get_contents('php://input');
$data = json_decode($json, true);

// Kiểm tra xem dữ liệu đầu vào có phải là mảng không
if (!is_array($data) || empty($data)) {
    echo json_encode(["success" => false, "message" => "Dữ liệu đầu vào phải là một mảng không rỗng"]);
    exit;
}

$results = []; // Mảng lưu kết quả xử lý từng địa điểm
$successCount = 0; // Đếm số địa điểm thêm thành công

try {
    // Chuẩn bị câu lệnh SQL
    $sql = "INSERT INTO location (locationName, locationDescription, locationImage, locationRating, locationReview, locationAddress, locationCostLevel, categories, activities, foodOptions, bestSeason)
            VALUES (:locationName, :locationDescription, :locationImage, :locationRating, :locationReview, :locationAddress, :locationCostLevel, :categories, :activities, :foodOptions, :bestSeason)";
    $stmt = $conn->prepare($sql);

    // Hàm chuyển mảng thành chuỗi với dấu phẩy
    function arrayToString($array) {
        return is_array($array) ? implode(",", $array) : $array;
    }

    // Duyệt qua từng địa điểm trong mảng
    foreach ($data as $index => $locationData) {
        // Kiểm tra các trường bắt buộc
        if (!isset($locationData['locationName'], $locationData['locationDescription'], $locationData['locationAddress'])) {
            $results[] = [
                "index" => $index,
                "name" => $locationData['locationName'] ?? "Unknown",
                "success" => false,
                "message" => "Thiếu dữ liệu đầu vào bắt buộc (locationName, locationDescription, locationAddress) cho địa điểm này"
            ];
            continue;
        }

        // Lấy dữ liệu từ JSON
        $locationName = $locationData['locationName'];
        $locationDescription = $locationData['locationDescription'];
        $locationImage = $locationData['locationImage'] ?? null;
        $locationRating = isset($locationData['locationRating']) ? (float)$locationData['locationRating'] : 0;
        $locationReview = $locationData['locationReview'] ?? null;
        $locationAddress = $locationData['locationAddress'];
        $locationCostLevel = $locationData['locationCostLevel'] ?? null;
        $categories = arrayToString($locationData['categories'] ?? null); // Chuyển mảng thành chuỗi
        $activities = arrayToString($locationData['activities'] ?? null); // Chuyển mảng thành chuỗi
        $foodOptions = arrayToString($locationData['foodOptions'] ?? null); // Chuyển mảng thành chuỗi
        $bestSeason = arrayToString($locationData['bestSeason'] ?? null); // Chuyển mảng thành chuỗi

        // Liên kết tham số
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

        // Thực thi câu lệnh
        if ($stmt->execute()) {
            $lastId = $conn->lastInsertId();
            $results[] = [
                "index" => $index,
                "name" => $locationName,
                "success" => true,
                "locationID" => $lastId,
                "message" => "Thêm địa điểm thành công"
            ];
            $successCount++;
        } else {
            $results[] = [
                "index" => $index,
                "name" => $locationName,
                "success" => false,
                "message" => "Thêm địa điểm thất bại"
            ];
        }
    }

    // Trả về kết quả tổng quát
    echo json_encode([
        "success" => $successCount > 0,
        "message" => "Đã xử lý $successCount/" . count($data) . " địa điểm",
        "details" => $results
    ], JSON_PRETTY_PRINT);

} catch (PDOException $e) {
    echo json_encode([
        "success" => false,
        "message" => "Lỗi SQL: " . $e->getMessage(),
        "details" => $results
    ], JSON_PRETTY_PRINT);
}

// Đóng kết nối
$conn = null;
?>