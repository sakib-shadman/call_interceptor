package com.example.instorage_caller.roomdb.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.instorage_caller.roomdb.CustomerInfo;
import com.example.instorage_caller.roomdb.LogInfo;
import com.example.instorage_caller.roomdb.UserLocation;

import java.util.List;

@Dao
public interface CustomerDao {

    @Insert
    void addCustomer(CustomerInfo customerInfo);

    @Insert
    void addAllCustomer(List<CustomerInfo> customerInfo);

    @Update
    void updateCustomer(CustomerInfo customerInfo);

    @Query ("select * from customer_info where formatted_number =:phoneNumber")
    CustomerInfo getCustomerByPhoneNo(String phoneNumber);

    @Query("select * from customer_info")
    List<CustomerInfo> getAllCustomer();
}
