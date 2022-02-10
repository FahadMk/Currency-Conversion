package com.dgsl.currencyconverter.data.roomModal;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;

import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CurrencyDataModel model);

    // below method is use to update
    // the data in our database.
    @Update
    void update(CurrencyDataModel model);

    // below line is use to delete a
    // specific course in our database.
    @Delete
    void delete(CurrencyDataModel model);

    // on below line we are making query to
    // delete all courses from our database.
    @Query("DELETE FROM currency_table")
    void deleteAllCourses();

    // below line is to read all the courses from our database.
    // in this we are ordering our courses in ascending order
    // with our course name.
    @Query("SELECT * FROM currency_table ORDER BY currency_code_name ASC")
    LiveData<List<CurrencyDataModel>> getAllCurrencyData();

    @Query("SELECT * FROM currency_table WHERE currency_table.currency_code_name = :currencyCode")
    LiveData<CurrencyDataModel> findCurrencyData(String currencyCode);

}
