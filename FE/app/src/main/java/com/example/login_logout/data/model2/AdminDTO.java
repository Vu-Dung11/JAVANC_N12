package com.example.login_logout.data.model2;

public class AdminDTO {
    private Long adminId;
    private String userName;
    private String passWord;

    // Constructor
    public AdminDTO() {}

    public AdminDTO(Long adminId, String userName, String passWord) {
        this.adminId = adminId;
        this.userName = userName;
        this.passWord = passWord;
    }

    // Getters and Setters
    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
