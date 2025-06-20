package com.example.login_logout.newui.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Homestay implements Parcelable {
    private Long homestayId;
    private String name;
    private String description;
    private String ward;
    private String district;
    private String province;
    private String createdAt;
    private String updatedAt;
    private User owner;
    private List<Room> rooms;
    private int imageResourceId; // Thêm trường cho ảnh
    private Double rate; // Thêm trường rate để hiển thị đánh giá

    public Homestay() {
        this.rooms = new ArrayList<>();
    }

    protected Homestay(Parcel in) {
        if (in.readByte() == 0) {
            homestayId = null;
        } else {
            homestayId = in.readLong();
        }
        name = in.readString();
        description = in.readString();
        ward = in.readString();
        district = in.readString();
        province = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        // Không xử lý user
        rooms = new ArrayList<>();
        in.readList(rooms, Room.class.getClassLoader());
        imageResourceId = in.readInt(); // Đọc imageResourceId
        rate = (Double) in.readValue(Double.class.getClassLoader()); // Đọc rate
    }

    public static final Creator<Homestay> CREATOR = new Creator<Homestay>() {
        @Override
        public Homestay createFromParcel(Parcel in) {
            return new Homestay(in);
        }

        @Override
        public Homestay[] newArray(int size) {
            return new Homestay[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (homestayId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(homestayId);
        }
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(ward);
        dest.writeString(district);
        dest.writeString(province);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        // Không xử lý user
        dest.writeList(rooms);
        dest.writeInt(imageResourceId); // Ghi imageResourceId
        dest.writeValue(rate); // Ghi rate
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters and Setters
    public Long getHomestayId() {
        return homestayId;
    }

    public void setHomestayId(Long homestayId) {
        this.homestayId = homestayId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}