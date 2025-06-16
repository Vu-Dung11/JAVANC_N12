<?php
header('Content-Type: application/json'); // Trả về JSON
$databaseFile = "D:/nckh/TravelAppNCKH-Nam_Brach1/TravelAppNCKH-Nam_Brach1/app/assets/travelApp.db";
 // Sử dụng đường dẫn chính xác

if (!file_exists($databaseFile)) {
    die(json_encode(["success" => false, "message" => "File database không tồn tại: $databaseFile"]));
}

try {
    $conn = new PDO("sqlite:" . $databaseFile);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $conn->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);

    
} catch (PDOException $e) {
    die(json_encode(["success" => false, "message" => "Kết nối thất bại: " . $e->getMessage()]));
}
?>

