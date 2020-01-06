package com.example.instorage_caller.roomdb;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "log_info")
public class LogInfo {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    @ColumnInfo(name = "created_at")
    private Long createdAt;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "category")
    private String category;

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
