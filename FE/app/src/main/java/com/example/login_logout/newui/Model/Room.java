package com.example.login_logout.newui.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Room implements Parcelable {
    private Long roomId;
    private String roomName;
    private int roomType;
    private double price;
    private int status;
    private String createdAt;
    private String updatedAt;

    // Thay đổi ở đây: giữ id thay vì object
    private Long homestayId;
    private Long bookingId;

    public Room(Long roomId, String roomName, int roomType, double price, int status, String createdAt, String updatedAt, Long homestayId, Long bookingId) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomType = roomType;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.homestayId = homestayId;
        this.bookingId = bookingId;
    }

    public Room() {
    }

    protected Room(Parcel in) {
        if (in.readByte() == 0) {
            roomId = null;
        } else {
            roomId = in.readLong();
        }
        roomName = in.readString();
        roomType = in.readInt();
        price = in.readDouble();
        status = in.readInt();
        createdAt = in.readString();
        updatedAt = in.readString();

        if (in.readByte() == 0) {
            homestayId = null;
        } else {
            homestayId = in.readLong();
        }
        if (in.readByte() == 0) {
            bookingId = null;
        } else {
            bookingId = in.readLong();
        }
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    // Getter và Setter
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getRoomType() {
        return roomType;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public Long getHomestayId() {
        return homestayId;
    }

    public void setHomestayId(Long homestayId) {
        this.homestayId = homestayId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        if (roomId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(roomId);
        }
        dest.writeString(roomName);
        dest.writeInt(roomType);
        dest.writeDouble(price);
        dest.writeInt(status);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);

        if (homestayId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(homestayId);
        }

        if (bookingId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(bookingId);
        }
    }
}
