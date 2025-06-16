<?php
header("Content-Type: application/json; charset=UTF-8");
require_once "db_connect.php";

try {
    // Bật hiển thị lỗi
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Kiểm tra tham số locationID
    if (!isset($_GET['locationID']) || empty($_GET['locationID'])) {
        echo json_encode(["success" => false, "message" => "Thiếu tham số locationID"], JSON_UNESCAPED_UNICODE);
        exit;
    }

    // Kiểm tra locationID có phải là số hợp lệ không
    $locationID = $_GET['locationID'];
    if (!is_numeric($locationID)) {
        echo json_encode(["success" => false, "message" => "locationID phải là số"], JSON_UNESCAPED_UNICODE);
        exit;
    }

    // Truy vấn danh sách user liên quan đến các review
    $stmt_users = $conn->prepare("SELECT DISTINCT u.userID, u.username
                                  FROM user u
                                  JOIN review r ON u.userID = r.userID
                                  WHERE r.locationID = :locationID");
    $stmt_users->bindParam(':locationID', $locationID);
    $stmt_users->execute();
    $users = $stmt_users->fetchAll(PDO::FETCH_ASSOC);

    // Truy vấn danh sách review, lấy userID, rating, comment
    $stmt_reviews = $conn->prepare("SELECT r.userID, r.rating, r.comment
                                    FROM review r
                                    WHERE r.locationID = :locationID
                                    ORDER BY r.date DESC");
    $stmt_reviews->bindParam(':locationID', $locationID);
    $stmt_reviews->execute();
    $reviews = $stmt_reviews->fetchAll(PDO::FETCH_ASSOC);

    // Kiểm tra nếu không có review
    if (empty($reviews)) {
        echo json_encode(["success" => false, "message" => "Không có đánh giá cho địa điểm này"], JSON_UNESCAPED_UNICODE);
        exit;
    }

    // Trả về dữ liệu
    echo json_encode([
        "success" => true,
        "users" => $users,
        "reviews" => $reviews
    ], JSON_UNESCAPED_UNICODE);

} catch (PDOException $e) {
    echo json_encode(["success" => false, "message" => "Lỗi truy vấn: " . $e->getMessage()], JSON_UNESCAPED_UNICODE);
} catch (Exception $e) {
    echo json_encode(["success" => false, "message" => "Lỗi: " . $e->getMessage()], JSON_UNESCAPED_UNICODE);
}
?>