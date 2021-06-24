package com.example.milan;

public class RoomDetails {
    String roomName,category;

    public RoomDetails(String roomName, String category) {
        this.roomName = roomName;
        this.category = category;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getCategory() {
        return category;
    }
}
