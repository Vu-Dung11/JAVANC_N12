<?php
header("Content-Type: application/json; charset=UTF-8");
require_once "db_connect.php";

// Bật hiển thị lỗi để debug (Chỉ dùng khi test)
error_reporting(E_ALL);
ini_set('display_errors', 1);

// Kiểm tra xem locationID có trong URL không
if (!isset($_GET['locationID'])) {
    echo json_encode(["success" => false, "message" => "Thiếu locationID"]);
    exit;
}

$locationID = isset($_GET['locationID']) ? intval($_GET['locationID']) : null;

if (!$locationID) {
    echo json_encode(["success" => false, "message" => "Thiếu hoặc sai locationID", "debug_locationID" => $_GET['locationID'] ?? null]);
    exit;
}

// Lấy dữ liệu JSON từ request body
$json = file_get_contents("php://input");
$data = json_decode($json, true);

// Kiểm tra xem JSON có hợp lệ không
if (json_last_error() !== JSON_ERROR_NONE) {
    echo json_encode(["success" => false, "message" => "Lỗi JSON: " . json_last_error_msg()]);
    exit;
}

// Kiểm tra xem các thông tin cần thiết có tồn tại không
if (!isset($data["locationName"], $data["locationDescription"], $data["locationAddress"])) {
    echo json_encode(["success" => false, "message" => "Thiếu thông tin cần thiết (locationName, locationDescription, locationAddress)"]);
    exit;
}

// Lấy dữ liệu từ JSON và xử lý mảng
$locationName = $data["locationName"];
$locationDescription = $data["locationDescription"];
$locationImage = $data["locationImage"] ?? null;
$locationRating = isset($data["locationRating"]) ? floatval($data["locationRating"]) : 0;
$locationReview = $data["locationReview"] ?? null;
$locationAddress = $data["locationAddress"];
$locationCostLevel = $data["locationCostLevel"] ?? null;

// Hàm chuyển mảng thành chuỗi với dấu phẩy
function arrayToString($array) {
    return is_array($array) ? implode(",", $array) : $array;
}

$categories = arrayToString($data["categories"] ?? null);
$activities = arrayToString($data["activities"] ?? null);
$foodOptions = arrayToString($data["foodOptions"] ?? null);
$bestSeason = arrayToString($data["bestSeason"] ?? null);

try {
    // Kiểm tra xem địa điểm có tồn tại không
    $stmt = $conn->prepare("SELECT * FROM location WHERE locationID = ?");
    $stmt->execute([$locationID]);
    $location = $stmt->fetch(PDO::FETCH_ASSOC);

    if (!$location) {
        echo json_encode([
            "success" => false,
            "message" => "Địa điểm không tồn tại",
            "debug_SQL" => implode(", ", $stmt->errorInfo())
        ]);
        exit;
    }

    // Tiến hành UPDATE
    $stmt = $conn->prepare("UPDATE location SET 
        locationName = ?, 
        locationDescription = ?, 
        locationImage = ?, 
        locationRating = ?, 
        locationReview = ?, 
        locationAddress = ?, 
        locationCostLevel = ?, 
        categories = ?, 
        activities = ?, 
        foodOptions = ?, 
        bestSeason = ? 
        WHERE locationID = ?");
    $result = $stmt->execute([
        $locationName,
        $locationDescription,
        $locationImage,
        $locationRating,
        $locationReview,
        $locationAddress,
        $locationCostLevel,
        $categories,
        $activities,
        $foodOptions,
        $bestSeason,
        $locationID
    ]);

    if ($result) {
        echo json_encode(["success" => true, "message" => "Cập nhật địa điểm thành công"]);
    } else {
        echo json_encode(["success" => false, "message" => "Lỗi khi cập nhật địa điểm"]);
    }
} catch (PDOException $e) {
    echo json_encode(["success" => false, "message" => "Lỗi SQL: " . $e->getMessage()]);
}

// Đóng kết nối
$conn = null;
?>