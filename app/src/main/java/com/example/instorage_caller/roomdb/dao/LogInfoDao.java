package com.example.instorage_caller.roomdb.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.instorage_caller.roomdb.LogInfo;

import java.util.List;

@Dao
public interface LogInfoDao {

    @Query("select * from log_info")
    List<LogInfo> getAllLogs();

    @Insert
    void addLog(LogInfo logInfo);

    @Delete
    void deleteLog(LogInfo logInfo);
}
