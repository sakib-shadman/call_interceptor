package com.example.instorage_caller.roomdb.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.instorage_caller.roomdb.UserLocation;

import java.util.List;

@Dao
public interface UserModelDao {

    @Query("select * from user_location")
    List<UserLocation> getAllUser();

    @Insert
    void addUser(UserLocation userLocation);

    @Delete
    void deleteUser(UserLocation userLocation);

    @Query ("delete from user_location where id>=:firstLimit and id<=:lastLimit")
    void deleteLocationData(Integer firstLimit, Integer lastLimit);
}
