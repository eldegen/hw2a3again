package com.example.hw2a3again.data.models;

import com.google.gson.annotations.SerializedName;

public class Post {
    int id;
    String title;
    String content;
    @SerializedName("group")
    int groupId;
    @SerializedName("user")
    int userId;

    public Post(String title, String content, int groupId, int userId) {
        this.title = title;
        this.content = content;
        this.groupId = groupId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
